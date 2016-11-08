package controller;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.PrintRequestPost;
import transactions.PrintRequestTransactions.DeleteReq;
import transactions.PrintRequestTransactions.GetRequestFile;
import transactions.PrintRequestTransactions.PostRequest;

@RestController
@RequestMapping(value = "/api/reqs")
public class PrintRequestController {

  //TODO redo this request, currrently does not work
  @RequestMapping(value = "", method = RequestMethod.POST)
  public void postRequest(@RequestBody PrintRequestPost preq, HttpServletRequest req,
      HttpServletResponse res) {
    Integer reqId = new PostRequest(preq).run(req, res);
    res.setHeader("Location", "reqs/" + reqId);
    return;
  }
  
  @RequestMapping(value = "/{reqId}", method = RequestMethod.DELETE)
  public void postRequest(@PathVariable(value="reqId") int preqId, HttpServletRequest req,
      HttpServletResponse res) {
    new DeleteReq(preqId).run(req, res);
    return;
  }
  
  @RequestMapping(value = "/{reqId}/file", method = RequestMethod.GET, produces = {"application/octet-stream"})
  public byte[] getRequestFile(@PathVariable(value="reqId") int preqId, HttpServletRequest req,
      HttpServletResponse res) {
    byte[] file = new GetRequestFile(preqId).run(req, res);
    return file;
  }
  

}
