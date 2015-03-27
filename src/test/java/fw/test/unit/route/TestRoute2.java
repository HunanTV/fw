package fw.test.unit.route;
//package fw.test.unit;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import junit.framework.TestCase;
//
//import org.junit.Test;
//
//import com.hunantv.fw.route.Route2;
//
//public class TestRoute2 extends TestCase {
//	
//	@Test
//	public void testBaseConstructor() throws Exception {
//		Route2 route = new Route2("/save", "fw.test.unit.TestController.save");
//		assertEquals("/save/", route.getUriReg());
//		assertEquals(fw.test.unit.TestController.class, route.getControllerClass());
//		assertEquals(fw.test.unit.TestController.class.getMethod("save"), route.getControllerMethod());
//	}
//
//	@Test
//	public void testIntRegConstructor() throws Exception {
//		Route2 route = new Route2("/save/<int:age>", "fw.test.unit.TestController.save");
//		assertEquals("/save/\\d+/", route.getUriReg());
//		assertEquals(fw.test.unit.TestController.class, route.getControllerClass());
//		assertEquals(fw.test.unit.TestController.class.getMethod("save", Integer.TYPE), route.getControllerMethod());
//	}
//	
//	@Test
//	public void testStrRegConstructor() throws Exception {
//		Route2 route = new Route2("/save/<string:name>", "fw.test.unit.TestController.save");
//		assertEquals("/save/\\w+/", route.getUriReg());
//		assertEquals(fw.test.unit.TestController.class, route.getControllerClass());
//		assertEquals(fw.test.unit.TestController.class.getMethod("save", String.class), route.getControllerMethod());
//	}
//	
//	@Test
//	public void testStrRegConstructor2() throws Exception {
//		Route2 route = new Route2("/save/<name>", "fw.test.unit.TestController.save");
//		assertEquals("/save/\\w+/", route.getUriReg());
//		assertEquals(fw.test.unit.TestController.class, route.getControllerClass());
//		assertEquals(fw.test.unit.TestController.class.getMethod("save", String.class), route.getControllerMethod());
//	}
//	
//	@Test
//	public void testListRegConstructor() throws Exception {
//		Route2 route = new Route2("/save/<list:a,b,c,d,e,>", "fw.test.unit.TestController.save");
//		assertEquals("/save/\\w+(,\\w+)*?/", route.getUriReg());
//		assertEquals(fw.test.unit.TestController.class, route.getControllerClass());
//		assertEquals(fw.test.unit.TestController.class.getMethod("save", List.class), route.getControllerMethod());
//	}
//	
//	@Test
//	public void testComplexConstructor() throws Exception {
//		Route2 route = new Route2("/save/<name>/<int:age>/<list:types>", "fw.test.unit.TestController.save");
//		assertEquals("/save/\\w+/\\d+/\\w+(,\\w+)*?/", route.getUriReg());
//		assertEquals(fw.test.unit.TestController.class, route.getControllerClass());
//		assertEquals(fw.test.unit.TestController.class.getMethod("save", String.class, Integer.TYPE, List.class), route.getControllerMethod());
//	}
//	
//	@Test
//	public void testComplexMatch() {
//		Route2 route = new Route2("/save/<name>/<int:age>/<list:types>", "fw.test.unit.TestController.save");
//		assertEquals(new Object[] {
//						"pengyi", 
//						29, 
//						new ArrayList() {{ add("a"); add("b"); add("c"); add("d"); add("e"); }} 
//					}, route.match("/save/pengyi/29/a,b,c,d,e"));
//	}
//	
//	@Test
//	public void testComplexNotMatch() {
//		Route2 route = new Route2("/save/<name>/<int:age>/<list:types>/a", "fw.test.unit.TestController.save");
//		assertEquals(null, route.match("/save/pengyi/29/a,b,c,d,e"));
//	}
//}
