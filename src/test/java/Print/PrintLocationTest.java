package Print;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import Person.People;
import api.PrintRequestPost;
import app.AuthInterceptor;
import controller.PersonController;
import controller.SessionController;
import controller.SessionController.Login;
import hibernate.HibernateUtil;
import model.PrintRequest;

public class PrintLocationTest {

  private static People people;
  private static Locations locs;
  private static Requests reqs;

  AuthInterceptor auth;
  MockHttpServletResponse res;
  MockHttpServletRequest req;

  @Before
  public void setup() {
    people = new People();
    locs = new Locations();
    reqs = new Requests(people, locs);

    HibernateUtil.getFactory().getCurrentSession().beginTransaction();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from PrintRequest")
        .executeUpdate();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from PrintLocation")
        .executeUpdate();
    HibernateUtil.getFactory().getCurrentSession().createSQLQuery("delete from Person")
        .executeUpdate();
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsA);
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsB);
    HibernateUtil.getDAOFact().getPersonDAO().makePersistent(people.prsC);

    HibernateUtil.getDAOFact().getPrintLocationDAO().makePersistent(locs.locA);

    HibernateUtil.getDAOFact().getPrintRequestDAO().makePersistent(reqs.reqA);
    HibernateUtil.getDAOFact().getPrintRequestDAO().makePersistent(reqs.reqB);
    HibernateUtil.getFactory().getCurrentSession().getTransaction().commit();

    auth = new AuthInterceptor();
    res = new MockHttpServletResponse();
    req = new MockHttpServletRequest();

    req.setServletPath("/snss");
    req.setMethod("POST");
    SessionController.postSession(new Login(people.prsB.getEmail(), people.passB), req, res);

    req.setCookies(res.getCookies());
    assertTrue(auth.preHandle(req, res, null));
  }

  @Test
  public void createRequest() {
    PrintRequestPost preqPost = new PrintRequestPost();
    preqPost.file = null;
    preqPost.fileName = "test.file";
    preqPost.locationId = locs.locA.getId();
    preqPost.ownerId = people.prsB.getId();

    PersonController.createRequest(people.prsB.getId(), preqPost, req, res);
    assertEquals(200, res.getStatus());

    assertEquals(2, PersonController.getRequests(people.prsB.getId(), req, res).size());
  }

  @Test
  public void getQueueTest() {
    PrintRequestPost preqPost = new PrintRequestPost();
    preqPost.file = null;
    preqPost.fileName = "test.file";
    preqPost.locationId = locs.locA.getId();
    preqPost.ownerId = people.prsB.getId();
    PersonController.createRequest(people.prsB.getId(), preqPost, req, res);
    assertEquals(200, res.getStatus());
    List<PrintRequest> getReqs = PersonController.getRequests(people.prsB.getId(), req, res);
    assertEquals(2, getReqs.size());
    assertEquals("test.file", getReqs.get(1).getFileName());
  }

}
