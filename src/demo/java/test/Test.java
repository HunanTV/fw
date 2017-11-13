package test;

import com.hunantv.fw.log.LogData;

public class Test {

	public static void main(String[] args) {
		System.out.println("value = " + LogData.instance().getId());
		System.out.println("value = " + LogData.instance().getId());
		LogData.instance().add("1", "a");
		System.out.println("value = " + LogData.instance().getData());
		LogData.instance().clear();
		LogData.instance().add("2", "b");
		System.out.println("value = " + LogData.instance().getData() + "+++++" + null);
	}
}
