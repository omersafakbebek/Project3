import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;



public class project3main {

	public static void main(String[] args) throws FileNotFoundException {
		HashMap<String, City> mapOfCities= new HashMap<>();
		PriorityQueue<Road> roads=new PriorityQueue<>();
		PriorityQueue<City> cities=new PriorityQueue<>();
		Scanner in=new Scanner(new File(args[0]));
		PrintStream out=new PrintStream(new File(args[1]));		
		int limit=in.nextInt();
		int numberOfCities=in.nextInt();
		String MecnunsCity=in.next();		
		String LeylasCity=in.next();		
		int numberOfHoneyCities=0;
		for(int i=0;i<numberOfCities;i++) {
			String IDofCity=in.next();				
			if(IDofCity.equals(LeylasCity)) {
				City inCity=new City(IDofCity);
				cities.add(inCity);				
				mapOfCities.put(IDofCity, inCity);					
				numberOfHoneyCities+=1;
				if(in.hasNext()) {					
					String line=in.nextLine();
					line = line.replaceAll("^\\s+", "");
					String[] lst = line.split(" ");
					if(lst.length>1) {
						for(int j=0;j<lst.length;j+=2) {
							if(!IDofCity.equals(lst[j])&&lst[j].charAt(0)!='c') {
								roads.add(new Road(IDofCity, lst[j],Integer.valueOf(lst[j+1]) ));	
							}													
						}
					}					
				}				
			}				
			else if (IDofCity.charAt(0)=='c') {
				City inCity=new City(IDofCity);
				cities.add(inCity);
				mapOfCities.put(IDofCity, inCity);					
				if(in.hasNext()) {
					String line=in.nextLine();
					line = line.replaceAll("^\\s+", "");
					String[] lst = line.split(" ");	
					if(lst.length>1) {
						for(int j=0;j<lst.length;j+=2) {
							if(!IDofCity.equals(lst[j])) {
								if(inCity.neighbors.get(lst[j]) != null) {
									if(inCity.neighbors.get(lst[j])>=Integer.valueOf(lst[j+1])) {
										inCity.neighbors.put(lst[j], Integer.valueOf(lst[j+1]));
									}
								}else {
									inCity.neighbors.put(lst[j], Integer.valueOf(lst[j+1]));
								}								
							}														
						}		
					}							
				}
		   }else {
				City inCity=new City(IDofCity);
				mapOfCities.put(IDofCity, inCity);	
				numberOfHoneyCities+=1;
				if(in.hasNext()) {					
					String line=in.nextLine();
					line = line.replaceAll("^\\s+", "");
					String[] lst = line.split(" ");
					if(lst.length>1) {
						for(int j=0;j<lst.length;j+=2) {
							if(!IDofCity.equals(lst[j])) {
								roads.add(new Road(IDofCity, lst[j],Integer.valueOf(lst[j+1]) ));	
							}													
						}
					}					
				}				
			}			
		}
		//first part
		boolean check=true;
		mapOfCities.get(MecnunsCity).minLength=0;		
		City currentCity=mapOfCities.get(MecnunsCity);		
		while(check) {						
			HashMap<String,Integer> neighbors=currentCity.neighbors;
			for(String key: neighbors.keySet() ) {
				City newCity=mapOfCities.get(key);
				if(currentCity.minLength+neighbors.get(key)<newCity.minLength) {
					newCity.minLength=currentCity.minLength+neighbors.get(key);
					newCity.parent=currentCity;
					cities.remove(newCity);
					cities.add(newCity);
				}
			}			
			cities.remove(currentCity);
			if(cities.peek().minLength!=Integer.MAX_VALUE) {
				currentCity=cities.peek();
			}else {
				check=false;
			}
			if (currentCity.ID.equals(LeylasCity)) {
				check=false;
			}
		}
		boolean marriage=false;
		if(!currentCity.ID.equals(LeylasCity)) {
			 out.println(-1);
		}
		else {
			if(currentCity.minLength<=limit) {
				marriage=true;
			}
			String path="";			
			boolean parentCheck=true;			
			while(parentCheck) {
				path=currentCity.ID +" "+path;
				if(currentCity.parent==null) {
					parentCheck=false;
				}
				else {
					currentCity=currentCity.parent;
				}			
			}out.println(path.stripTrailing()); 
		}
		//second part
		if (marriage==true){
			int IDproducer=1;
			
			int totalDistance=0;
			check=true;			
			while(check) {				
				Road road=roads.poll();				
				City start=mapOfCities.get(road.start); City dest=mapOfCities.get(road.destination);		
				String startParent=findParent(mapOfCities, start.ID);String destParent=findParent(mapOfCities, dest.ID);
				if(!startParent.equals(destParent)) {
					if(mapOfCities.get(startParent).rank>mapOfCities.get(destParent).rank) {
						mapOfCities.get(destParent).parentID=startParent;
					}else if(mapOfCities.get(startParent).rank<mapOfCities.get(destParent).rank) {
						mapOfCities.get(startParent).parentID=destParent;
					}else {
						mapOfCities.get(destParent).parentID=startParent;
						mapOfCities.get(startParent).rank+=1;
					}					
					numberOfHoneyCities-=1;
					totalDistance+=road.distance;
				}
				if(numberOfHoneyCities==1||roads.isEmpty()==true) {
					check=false;
				}				
			}if(numberOfHoneyCities!=1) {
				out.print(-2);
			}else {
				out.print(totalDistance*2);
			}			
		}else {
			out.print(-1);
		}		
	}public static String findParent(HashMap<String,City> mapOfCities,String cityID){
		City temp=mapOfCities.get(cityID);
		while(temp.parentID!="-1") {
			temp=mapOfCities.get(temp.parentID);
		}return temp.ID;
	}
}
