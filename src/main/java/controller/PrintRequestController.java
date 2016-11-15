package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import api.PrintRequestPost;
import api.PrintRequestPut;
import transactions.PrintRequestTransactions.DeleteReq;
import transactions.PrintRequestTransactions.GetRequestFile;
import transactions.PrintRequestTransactions.PostRequest;
import transactions.PrintRequestTransactions.PutRequest;

@RestController
@RequestMapping(value = "/api/reqs")
public class PrintRequestController {
  
  @RequestMapping(value = "/{reqId}", method = RequestMethod.DELETE)
  public void postRequest(@PathVariable(value="reqId") int preqId, HttpServletRequest req,
      HttpServletResponse res) {
    new DeleteReq(preqId).run(req, res);
    return;
  }
  
  @RequestMapping(value = "/{reqId}", method = RequestMethod.PUT)
  public void putRequest(@PathVariable(value="reqId") int preqId, @RequestBody PrintRequestPut preqPut, HttpServletRequest req,
      HttpServletResponse res) {
    new PutRequest(preqId, preqPut).run(req, res);
    return;
  }
  
  @RequestMapping(value = "/{reqId}/file", method = RequestMethod.GET, produces = {"application/octet-stream"})
  public byte[] getRequestFile(@PathVariable(value="reqId") int preqId, HttpServletRequest req,
      HttpServletResponse res) {
    byte[] file = new GetRequestFile(preqId).run(req, res);
    return file;
  }
  

}
