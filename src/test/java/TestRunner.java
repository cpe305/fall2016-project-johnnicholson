import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import Person.PersonTest;
import Person.StudentTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ PersonTest.class, StudentTest.class})
public class TestRunner {

}
