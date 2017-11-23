package fw.test.unit.model;

import com.hunantv.fw.model.Model;

public class User extends Model {
	public void setAge(int v) {
		this.set("age", v);
	}

	public int getAge() {
		return (Integer) this.get("age");
	}

}
