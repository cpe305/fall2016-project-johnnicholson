package controller;

import model.PrintRequest;
import transactions.PrintRequestTransactions.PostRequest;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api/reqs")
public class PrintRequestController {

  @RequestMapping(value = "", method = RequestMethod.POST)
  public void postRequest(@RequestBody PrintRequest prntReq, HttpServletRequest req,
      HttpServletResponse res) {

    Integer prsId = new PostRequest(prntReq).run(req, res);
    res.setHeader("Location", "prss/" + prsId);
    return;
  }

}
