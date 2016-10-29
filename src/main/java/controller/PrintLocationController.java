package controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import model.PrintLocation;
import model.PrintRequest;
import transactions.PrintLocationTransactions.GetAllLocations;
import transactions.PrintLocationTransactions.GetLocation;
import transactions.PrintLocationTransactions.GetLocationReqs;
import transactions.PrintLocationTransactions.PostLocation;
import transactions.PrintLocationTransactions.PutLocation;

@RestController
@RequestMapping(value = "/api/locs")
public class PrintLocationController {
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

}
