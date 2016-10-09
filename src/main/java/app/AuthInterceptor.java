package app;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mysql.fabric.xmlrpc.base.Array;

import transactions.Transaction.Status;

@Configuration
public class AuthInterceptor extends HandlerInterceptorAdapter {

  private static final HashMap<String, List<String>> allowedPaths = new HashMap<String, List<String>>();
  static {
    allowedPaths.put("/prss", Arrays.asList("POST"));
    allowedPaths.put("/snss", Arrays.asList("POST"));

  }

  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj) {
    System.out.println(req.getServletPath());
    if (allowedPaths.get(req.getServletPath()) != null
        && allowedPaths.get(req.getServletPath()).contains(req.getMethod())) {
      return true;
    }
    boolean hasSession = false;
    if (req.getCookies() != null) {
      for (Cookie c : req.getCookies()) {
        if (c.getName().equals(Session.COOKIE_NAME) && Session.getSession(c.getValue()) != null) {
          req.setAttribute(app.Session.ATTRIBUTE_NAME, Session.getSession(c.getValue()));
          hasSession = true;
        }
      }
    }
    if (!hasSession) {
      res.setStatus(Status.UNAUTHORIZED.getValue());
    }
    return hasSession;
  }


}
