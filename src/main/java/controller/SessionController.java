package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.Session;
import transactions.SessionTransactions.DeleteSession;
import transactions.SessionTransactions.GetSession;
import transactions.SessionTransactions.PostSession;


@RestController
@RequestMapping(value = "/api/ssns")
public class SessionController {
  
  public static class Login {
    public String email;
    public String password;
    public Login(String email, String password) {
      this.email = email;
      this.password = password;
    }
    public Login() {
      
    }
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static void postSession(@RequestBody Login login, HttpServletRequest req, HttpServletResponse res) {
    PostSession post = new PostSession(login.email, login.password);
    String id = post.run(req, res);
    if (post.getResponseCode() == HttpStatus.UNAUTHORIZED) {
      res.setStatus(post.getResponseCode().value());
    }
    else {
      Cookie c = new Cookie(Session.COOKIE_NAME,id);
      c.setHttpOnly(true);
      res.addCookie(c);
    }
  }
  
  @RequestMapping(value = "/curssn", method = RequestMethod.GET)
  public static Session getSession(HttpServletRequest req, HttpServletResponse res) {
    return new GetSession().run(req, res);
  }
  
  @RequestMapping(value = "/curssn", method = RequestMethod.DELETE)
  public static Integer deleteSession(HttpServletRequest req, HttpServletResponse res) {
    new DeleteSession().run(req, res);
    res.addCookie(new Cookie(Session.COOKIE_NAME, null));
    return null;
  }
  
}
