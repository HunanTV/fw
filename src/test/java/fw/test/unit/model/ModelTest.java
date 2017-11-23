package fw.test.unit.model;

import junit.framework.TestCase;

public class ModelTest extends TestCase {

	public void testTableName() {
		assertEquals("users", new User().tableName());
		assertEquals("idcards", new IDCard().tableName());
	}

	public void testCreate() {
		User u = new User();
		u.set("name", "abc"); // u.setName("abc");
		u.set("age", 10); // u.setAge(10);
		SqlAndParams sp = u.create_misc();

		assertEquals("insert into users (name, age) values (?, ?)", sp.sql());
		assertEquals("abc", sp.params().get(0));
		assertEquals(10, sp.params().get(1));
	}

	public void testUpdate() {
		User u = new User();
		u.set("id", 9999);
		u.set("name", "abc"); // u.setName("abc");
		u.set("age", 10); // u.setAge(10);
		SqlAndParams sp = u.update_misc();

		assertEquals("update users set name = ?, age = ? where id = ?", sp.sql());
		assertEquals("abc", sp.params().get(0));
		assertEquals(10, sp.params().get(1));
		assertEquals(9999, sp.params().get(2));
	}
}
