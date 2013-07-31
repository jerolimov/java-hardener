import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Demo {
	public static void main(String[] args) {
		
		System.out.println("Demo!");
		
		Map<String, String> map = new LinkedHashMap<String, String>();
		List<String> list = new LinkedList<String>();
		
		map.put("key", "value");
		
		System.out.println("Key length: " + map.get("key2").length());
		
		
		list.get(0);
		
	}
}
