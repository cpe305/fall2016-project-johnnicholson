package transactions;

import dao.PrintLocationDAO;
import hibernate.HibernateUtil;
import model.PrintLocation;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import java.util.List;

public class PrintLocationTransactions {
  
  public static class GetAllLocations extends Transaction<List<PrintLocation>>{
    
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
      }
      else if(nameLoc != null || loc.getId() != null) {
        responseCode = HttpStatus.BAD_REQUEST;
      }
      else {
        locDAO.makePersistent(loc);
      }
      return loc.getId();
    }
    
  }
  public static class PutLocation extends Transaction<Integer>{
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
      }
      else if(nameLoc != null || loc.getId() != null) {
        responseCode = HttpStatus.BAD_REQUEST;
      }
      else if(dbloc == null) {
        responseCode = HttpStatus.NOT_FOUND;
      }
      else {
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
      if (loc != null) {
        responseCode = HttpStatus.NOT_FOUND;
      }
      return loc;
    }
    
  }
  
}
