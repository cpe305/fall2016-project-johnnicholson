package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.PrintRequestPost;
import transactions.PrintRequestTransactions.PostRequest;

@RestController
@RequestMapping(value = "/api/reqs")
public class PrintRequestController {

  @RequestMapping(value = "", method = RequestMethod.POST)
  public void postRequest(@RequestBody PrintRequestPost preq, HttpServletRequest req,
      HttpServletResponse res) {
    Integer reqId = new PostRequest(preq).run(req, res);
    res.setHeader("Location", "reqs/" + reqId);
    return;
  }
  

}
