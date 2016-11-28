package transactions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import dao.PersonDAO;
import dao.PrintLocationDAO;
import dao.PrintRequestDAO;
import hibernate.HibernateUtil;
import model.PrintLocation;
import model.PrintRequest;

public class PrintLocationTransactions {

  public static class GetAllLocations extends Transaction<List<PrintLocation>> {

    @Override
    public List<PrintLocation> action() {
      PrintLocationDAO locDAO = HibernateUtil.getDAOFact().getPrintLocationDAO();
      return locDAO.findAll();
    }

  }
  
  public static class PostLocation extends Transaction<Integer> {

    PrintLocation loc;

    public PostLocation(PrintLocation loc) {
      this.loc = loc;
    }

    @Override
    public Integer action() {
      PrintLocationDAO locDAO = HibernateUtil.getDAOFact().getPrintLocationDAO();
      PrintLocation nameLoc = locDAO.findByName(loc.getName());
      if (!isAdmin()) {
        responseCode = HttpStatus.UNAUTHORIZED;
      } else if (nameLoc != null || loc.getId() != null) {
        responseCode = HttpStatus.BAD_REQUEST;
      } else {
        locDAO.makePersistent(loc);
      }
      return loc.getId();
    }

  }
  
  public static class PutLocation extends Transaction<Integer> {
    PrintLocation loc;
    int locId;

    public PutLocation(PrintLocation location, int locId) {
      this.loc = location;
      this.locId = locId;
    }

    @Override
    public Integer action() {
      PrintLocationDAO locDAO = HibernateUtil.getDAOFact().getPrintLocationDAO();
      PrintLocation dbloc = locDAO.findById(locId);
      PrintLocation nameLoc = locDAO.findByName(loc.getName());
      if (!isAdmin()) {
        responseCode = HttpStatus.UNAUTHORIZED;
      } else if (nameLoc != null || loc.getId() != null) {
        responseCode = HttpStatus.BAD_REQUEST;
      } else if (dbloc == null) {
        responseCode = HttpStatus.NOT_FOUND;
      } else {
        BeanUtils.copyProperties(loc, dbloc, "id");
      }
      return null;
    }

  }
  
  public static class GetLocation extends Transaction<PrintLocation> {
    int locId;

    public GetLocation(int locId) {
      this.locId = locId;
    }

    @Override
    public PrintLocation action() {
      PrintLocationDAO locDAO = HibernateUtil.getDAOFact().getPrintLocationDAO();
      PrintLocation loc = locDAO.findById(locId);
      if (loc == null) {
        responseCode = HttpStatus.NOT_FOUND;
      }
      return loc;
    }

  }

  public static class GetLocationReqs extends Transaction<List<PrintRequest>> {
    int locId;
    
    public GetLocationReqs(int locId) {
      this.locId = locId;
    }
    
    @Override
    public List<PrintRequest> action() {
      PrintLocationDAO locDAO = HibernateUtil.getDAOFact().getPrintLocationDAO();
      PrintRequestDAO reqDAO = HibernateUtil.getDAOFact().getPrintRequestDAO();
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      PrintLocation loc = locDAO.findById(locId);
      List<PrintRequest> reqs;
      if (isAdmin()) {
         reqs = new ArrayList<PrintRequest>(loc.getQueue().values());
      }
      else {
        reqs = reqDAO.findByPersonAndLocation(prsDAO.findById(getSession().prsId), loc);
      }
      return reqs;
    }
  }
  
}
