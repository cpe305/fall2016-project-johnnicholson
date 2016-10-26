import Person.PersonTest;
import Person.StudentTest;
import Print.PrintLocationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({PersonTest.class, StudentTest.class, PrintLocationTest.class})
public class TestRunner {

}
