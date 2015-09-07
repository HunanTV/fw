package fw.test.unit;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections.map.LinkedMap;

import com.hunantv.fw.utils.SeqID;

public class SeqIDTest {
	public static void main(String[] args) {
//		long b = System.currentTimeMillis();
//		Set<String> set = new HashSet<String>(1000000);
//		for (int i = 0; i < 1000000; i++) {
//			set.add(SeqID.rnd().toString());
////			UUID.randomUUID().toString();
//		}
//		long e = System.currentTimeMillis();
//		System.out.println(e - b);
//		System.out.println(set.size());
		Map<String, String> map = new LinkedHashMap<String, String>(1);
		map.put("1", "1");
		map.put("2", "2");
		System.out.println(map.size());
	}
}
