package transactions;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;

import api.PrintRequestPost;
import api.PrintRequestPut;
import app.EmailHelper;
import app.InvalidArgumentException;
import dao.PersonDAO;
import dao.PrintLocationDAO;
import dao.PrintRequestDAO;
import hibernate.HibernateUtil;
import model.Person;
import model.PrintLocation;
import model.PrintRequest;

public class PrintRequestTransactions {
  public static Logger lgr = Logger.getLogger(PrintRequestTransactions.class);

  public static class PostRequest extends Transaction<Integer> {
    private PrintRequestPost preqPost;

    public PostRequest(PrintRequestPost preqPost) {
      this.preqPost = preqPost;
    }

    @Override
    public Integer action() {
      PersonDAO prsDAO = HibernateUtil.getDAOFact().getPersonDAO();
      PrintLocationDAO locDAO = HibernateUtil.getDAOFact().getPrintLocationDAO();
      Person owner = null;
      PrintLocation loc = null;
      if (preqPost.ownerId != null && preqPost.locationId != null) {
        owner = prsDAO.findById(preqPost.ownerId);
        loc = locDAO.findById(preqPost.locationId); 
      }
      PrintRequest preq = null;
      
      if (owner == null || loc == null) {
        responseCode = HttpStatus.BAD_REQUEST;
      } else if (!isAdminOrUser(owner.getId())) {
        responseCode = HttpStatus.UNAUTHORIZED;
      } else {
        PrintRequestDAO reqDAO = HibernateUtil.getDAOFact().getPrintRequestDAO();
        preq = new PrintRequest(preqPost, owner, loc);
        reqDAO.makePersistent(preq);
        loc.addPrintRequest(preq);
      }
      if (preq != null)
        return preq.getId();
      return null;
    }
  }
  
  public static class DeleteReq extends Transaction<Integer> {
    private int preqId;
    public DeleteReq(int preqId) {
      this.preqId = preqId;
    }

    @Override
    public Integer action() {
      PrintRequestDAO reqDAO = HibernateUtil.getDAOFact().getPrintRequestDAO();
      PrintRequest req = reqDAO.findById(preqId);
      if (req == null) {
        responseCode = HttpStatus.NOT_FOUND;
      }
      else if (!isAdminOrUser(req.getOwner().getId())) {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      else {
        reqDAO.moveToEnd(req);
        reqDAO.makeTransient(req);
      }
      return null;
    }
  }
  
  public static class PutRequest extends Transaction<Integer> {
    private int preqId;
    private PrintRequestPut preqPut;
    public PutRequest(int preqId, PrintRequestPut preqPut) {
      this.preqId = preqId;
      this.preqPut = preqPut;
    }

    @Override
    public Integer action() {
      PrintRequestDAO reqDAO = HibernateUtil.getDAOFact().getPrintRequestDAO();
      PrintRequest req = reqDAO.findById(preqId);
      if (req == null) {
        responseCode = HttpStatus.NOT_FOUND;
      }
      else if (!isAdminOrUser(req.getOwner().getId())) {
        responseCode = HttpStatus.UNAUTHORIZED;
      }
      else {
        if (preqPut.sequence != null && isAdmin()) {
          try {
            reqDAO.resequence(req, preqPut.sequence);
          } catch(InvalidArgumentException e) {
            lgr.warn(e);
            responseCode = HttpStatus.BAD_REQUEST;
          }
        }
        if (preqPut.status != null && isStaff()) {
          req.setStatus(preqPut.status);
          if (req.getOwner().isSubscribed())
            EmailHelper.sendFromGMail(req.getOwner().getEmail(), "3D Print Status Update",
             "The status of your 3D print request has been changed to " + preqPut.status + ".");
        }
        BeanUtils.copyProperties(preqPut, req, "sequence", "ownerId", "locationId");
      }
      return null;
    }
  }
  
  public static class GetRequestFile extends Transaction<byte[]> {
    
    private int preqId;
    public GetRequestFile(int preqId) {
      this.preqId = preqId;
    }
    @Override
    public byte[] action() {
      PrintRequestDAO reqDAO = HibernateUtil.getDAOFact().getPrintRequestDAO();
      PrintRequest req = reqDAO.findById(preqId);
      if (!isStaffOrUser(req.getOwner().getId())) {
        responseCode = HttpStatus.UNAUTHORIZED;
        return null;
      }
      else {
        return req.getFile();        
      }
    }
    
  }

}
