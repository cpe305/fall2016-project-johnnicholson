package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import api.PrintRequestPost;
import app.Application;
import model.PrintLocation;
import model.PrintRequest;
import transactions.PrintLocationTransactions.GetAllLocations;
import transactions.PrintLocationTransactions.GetLocation;
import transactions.PrintLocationTransactions.GetLocationReqs;
import transactions.PrintLocationTransactions.PostLocation;
import transactions.PrintLocationTransactions.PutLocation;
import transactions.PrintRequestTransactions.PostRequest;

@RestController
@RequestMapping(value = "/api/locs")
public class PrintLocationController {
  Logger lgr = Logger.getLogger(PrintLocationController.class);
  @RequestMapping(value = "", method = RequestMethod.GET)
  public static List<PrintLocation> getAllLocations(HttpServletRequest req,
      HttpServletResponse res) {
    List<PrintLocation> locs = new GetAllLocations().run(req, res);
    return locs;
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
  public static void postLocation(@RequestBody PrintLocation loc, HttpServletRequest req,
      HttpServletResponse res) {
    Integer locId = new PostLocation(loc).run(req, res);
    res.setHeader("Location", "api/locs/" + locId);
    return;
  }

  @RequestMapping(value = "/{locId}", method = RequestMethod.PUT)
  public static void putLocation(@Valid @RequestBody PrintLocation location,
      @PathVariable(value = "locId") int locId, HttpServletRequest req, HttpServletResponse res) {
    new PutLocation(location, locId).run(req, res);
    return;
  }

  @RequestMapping(value = "/{locId}", method = RequestMethod.GET)
  public static PrintLocation getLocation(@PathVariable(value = "locId") int locId,
      HttpServletRequest req, HttpServletResponse res) {
    PrintLocation loc = new GetLocation(locId).run(req, res);
    return loc;
  }

  @RequestMapping(value = "/{locId}/reqs", method = RequestMethod.GET)
  public static List<PrintRequest> getLocationReqs(@PathVariable(value = "locId") int locId,
      HttpServletRequest req, HttpServletResponse res) {
    List<PrintRequest> reqs = new GetLocationReqs(locId).run(req, res);
    return reqs;
  }
  
  @RequestMapping(consumes = {"multipart/form-data"}, value = "/{locId}/reqs", method = RequestMethod.POST)
  public void postRequest(@PathVariable(value = "locId") int locId,
      @RequestParam("file") MultipartFile file, @RequestParam("ownerId") Integer ownerId, HttpServletRequest req,
      HttpServletResponse res) {
    
    Application.lgr.warn(ownerId);
    PrintRequestPost preq = new PrintRequestPost();
    preq.ownerId = ownerId;
    preq.locationId = locId;
    preq.fileName = file.getOriginalFilename();
    try {
      preq.file = file.getBytes();
    } catch (IOException e) {
      lgr.error(e);
      res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
      return;
    }
    Integer reqId = new PostRequest(preq).run(req, res);
    res.setHeader("Location", "reqs/" + reqId);
    return;
  }

}
