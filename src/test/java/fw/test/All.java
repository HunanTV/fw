package fw.test;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import fw.test.functional.FooControllerTest;
import fw.test.unit.LocalCacheTest;
import fw.test.unit.ViewTest;

public class All {

	public static void main(String[] args) {
		TestRunner runner = new TestRunner();
		TestSuite suite = new TestSuite();
		suite.addTestSuite(FooControllerTest.class);
		suite.addTestSuite(ViewTest.class);
		suite.addTestSuite(LocalCacheTest.class);
		runner.run(suite);
	}
}
