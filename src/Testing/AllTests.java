package Testing;


import Testing.RoomTest;
import junit.framework.*;

public class AllTests extends TestSuite {

    public static TestSuite suite() {
        TestSuite suite = new TestSuite("Test for ZuuM game");
        //$JUnit-BEGIN$
        //System.out.println(AllTests.class.getName());
        suite.addTest(new TestSuite(RoomTest.class));
        //$JUnit-END$
        return suite;
    }

}
