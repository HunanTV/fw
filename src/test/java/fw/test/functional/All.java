package fw.test.functional;

import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class All {

	public static void main(String[] args) {
		TestRunner runner = new TestRunner();
		TestSuite suite = new TestSuite();
		suite.addTestSuite(DemoControllerTest.class);
		runner.run(suite);
	}
}
