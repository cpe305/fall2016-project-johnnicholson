package app;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class AuthInterceptor extends HandlerInterceptorAdapter {

  private static HashSet<String> allowedPaths = new HashSet<String>(Arrays.asList("/snss","/"));
  
  @Override
  public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object obj)
      throws Exception {
    System.out.print(req.getServletPath());

    System.out.println(allowedPaths.contains(req.getServletPath()));
    return true;
  }

}
