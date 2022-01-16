
public class Road implements Comparable<Road> {
	String start;
	String destination;
	int distance;
	Road(String start,String destination,int distance){
		this.start=start;
		this.destination=destination;
		this.distance=distance;
	}
	@Override
	public int compareTo(Road o) {
		// TODO Auto-generated method stub
		return this.distance-o.distance;
	}
	
}
