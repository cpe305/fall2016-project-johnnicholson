package controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hibernate.Transaction;
import transactions.SessionTransactions.PostSession;


@RestController
public class SessionController {
  
  public static class Login {
    public String email;
    public String password;
  }

  @RequestMapping("/snss")
  public void postSession(@RequestBody Login login, HttpServletResponse rsp) {
    PostSession post = new PostSession(login.email, login.password);
    String id = post.run();
    if (post.getResponseCode() == Transaction.Response.UNAUTHORIZED) {
      rsp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
    else {
      Cookie c = new Cookie("USERID",id);
      c.setHttpOnly(true);
      rsp.addCookie(c);
    }
  }
  
}
