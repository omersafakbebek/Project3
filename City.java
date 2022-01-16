import java.util.ArrayList;
import java.util.HashMap;

public class City implements Comparable<City>{	
	HashMap<String, Integer> neighbors = new HashMap<>();
	String ID;
	City parent;
	
		
	int rank=0;
	String parentID="-1";
	int minLength=Integer.MAX_VALUE;
	City(String ID){
		this.ID=ID;
	}
	@Override
	public int compareTo(City o) {
			return this.minLength-o.minLength;
		
							
	}
}
