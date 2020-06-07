import java.util.*;
import java.io.*;

public class Etude08 {
	public static class City {
		String city_name;
		List<Route> routes_from;
		List<Route> routes_to;
		
		public City(String city_name) {
			this.city_name = city_name;
		}
		
		public String getCityName() {
			return city_name;
		}
		public void setCityName(String city_name) {
			this.city_name = city_name;
		}
		
		public List<Route> getRoutesFrom() {
			return routes_from;
		}
		
		public void addRouteFrom(Route route) {
			if (!routes_from.contains(route)) {
				routes_from.add(route);				
			}
		}
		
		public List<Route> getRoutesTo() {
			return routes_to;
		}
		
		public void addRouteTo(Route route) {
			if (!routes_to.contains(route)); {
				routes_to.add(route);				
			}
		}
		
	}
	public static class Route {
		City city_from;
		City city_to;
		double fare;
		
		public Route(City city_from, City city_to, double fare) {
			this.city_from = city_from;
			this.city_to = city_to;
			this.fare = fare;
		}
		
		public double getFare() {
			return fare;
		}
	}
	public static class RouteMap {
		List<City> all_cities;
		List<Route> all_routes;
		
		public RouteMap() {
			
		}
		
		// can replace check to improve runtime
		public void addCity(City city) {
			if (!all_cities.contains(city)) {
				all_cities.add(city);	
			}
		}
		
		public void addRoute(City city_from, City city_to, double fare) throws Exception {
			Route route = new Route(city_from, city_to, fare);
			city_from.addRouteFrom(route);
			city_to.addRouteTo(route);
			
			if (!all_routes.contains(route)) {
				all_routes.add(route);
			} else {
				throw new Exception("Invalid: Non-unique routes");
			}
			
		}
		
		public void addRoute(String city_from_str, String city_to_str, double fare) throws Exception {
			City city_from, city_to;
			
			// create and add the cities if they don't exist
			if (!containsCityCalled(city_from_str)) {
				city_from = new City(city_from_str);
				addCity(city_from);
			} else {
				int city_from_index = all_cities.indexOf(new City(city_from_str));
				city_from = all_cities.get(city_from_index);
			}
			if (!containsCityCalled(city_to_str)) {
				city_to = new City(city_to_str);
				addCity(city_to);
			} else {
				int city_to_index = all_cities.indexOf(new City(city_to_str));
				city_to = all_cities.get(city_to_index);
			}
			
			addRoute(city_from, city_to, fare);
						
		}
		
		public boolean containsCityCalled(String city) {
			if (all_cities.contains(new City(city))) {
				return true;
			}
			
			return false;
		}
		
		
		// TODO
		public Vector<City> findCheapestRoute(City city_from, City city_to) {
			Vector<City> final_route = new Vector<City>();
			
			// find cheapest route
			
			return final_route;
		}
	}
	// TODO
	public static void main(String [] args) {
		// create RouteMap 
		Etude08.RouteMap rm = new Etude08.RouteMap();
		
		// debug
//		File file = new File(".");
//		for(String fileNames : file.list()) System.out.println(fileNames);
		
		// get filename from stdin
		
		
		City final_city_from = null, final_city_to = null;
		
		// open file and get input line by line
		// get input, creating Cities and routes and adding to the RouteMap
		try {
			// load file into scanner
			// TEMPORARY 
			
			Scanner ifile = new Scanner(System.in);
			
			
			// first line handled separately
			
			// get and handle fist line of input
			String final_city_from_str, final_city_to_str; // load these variables
			String line = ifile.nextLine();
			line = line.toLowerCase();
			String[] inputs = line.split(",");
			
			if (inputs.length != 2) {
				throw new Exception("Invalid: route set");
			}
			
			
			// instantiate Cities
			final_city_from = new City(inputs[0].trim());
			final_city_to = new City(inputs[1].trim());
			
			// add cities to routemap: not necessary?
//			rm.addCity(final_city_from);
//			rm.addCity(final_city_to);
			
			// handle the remaining lines of the file
			while (ifile.hasNextLine()) {
				line = ifile.nextLine().toLowerCase();
				inputs = line.split(","); // split line into multiple strings, separated by comma
				
				if (inputs.length != 3) {
					throw new Exception("Invalid: route");
				}
				
				String city_from_str = inputs[0], city_to_str = inputs[1], fare_str = inputs[2];
				double fare = Double.parseDouble(fare_str.trim());
				rm.addRoute(city_from_str.trim(), city_to_str.trim(), fare);
			}
		} catch(Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(); //TODO: remove
		}
		
		// use the method to find the cheapest route
		Vector<City> final_route = rm.findCheapestRoute(final_city_from, final_city_to);

		
		// output the cheapest route
		for (int i = 0; i < final_route.size(); i++) {
			System.out.print(final_route.get(i).city_name);
			
			if (i != final_route.size()-1) {
				System.out.print("-");
			}
		}
		
		sc.close();
		
		
		return;
	}
	
}