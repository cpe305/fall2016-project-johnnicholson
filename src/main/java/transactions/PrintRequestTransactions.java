package transactions;

import org.springframework.http.HttpStatus;

import api.PrintRequestPost;
import dao.PersonDAO;
import dao.PrintLocationDAO;
import dao.PrintRequestDAO;
import hibernate.HibernateUtil;
import model.Person;
import model.PrintLocation;
import model.PrintRequest;

public class PrintRequestTransactions {

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
      if (preqPost.ownerId != null && preqPost.locationId != null)
        owner = prsDAO.findById(preqPost.ownerId);
        loc = locDAO.findById(preqPost.locationId);
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

}
