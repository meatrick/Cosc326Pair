import java.util.*;
import java.io.*;

public class BusRoutes {
 public static class City implements Comparable<City>{
  String city_name;
  ArrayList<Route> routes_from;
  ArrayList<Route> routes_to;
  double bestDist;
  ArrayList<City>bestRoute;
  
  public City(String city_name) {
   this.city_name = city_name;
   this.routes_from = new ArrayList<Route>();
   this.routes_to = new ArrayList<Route>();
   this.bestRoute = new ArrayList<City>();
   
   
  }
  
  public int compareTo(City c){
    return Double.compare(bestDist, c.bestDist);
  }
  public String getCityName() {
   return city_name;
  }
  public void setCityName(String city_name) {
   this.city_name = city_name;
  }
  
  public ArrayList<Route> getRoutesFrom() {
   return routes_from;
  }
  
  
  public void addRouteFrom(Route route) {
    
    routes_from.add(route);    
   
  }
  
  public ArrayList<Route> getRoutesTo() {
   return routes_to;
  }
  
  public void addRouteTo(Route route) {
   
    routes_to.add(route);    
    
   
  }
  
 
  
  public void setBestDist(double bestDist){
   this.bestDist = bestDist;
  }
  
  public double getBestDist() {
   return bestDist;
  }
  
  public void setBestRoute(ArrayList <City> bestRoute){
   this.bestRoute = bestRoute;
   
  }
  
  public void clearBest(){
    this.bestRoute.clear();
  }
  
  public ArrayList<City> getBestRoute(){
   return bestRoute;
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
  
  public City getDest(){
    return city_to;
  }
  
  public City getFrom() {
	  return city_from;
  }
  public City getTo() {
	  return city_to;
  }
  
  
  public void setDest(City dest){
    this.city_to = dest;
 }
 }
 
  
  
  
 
 public static class RouteMap {
  ArrayList<City> all_cities;
  ArrayList<Route> all_routes;
  ArrayList<String> all_cities_str;
  
  public RouteMap() {
    this.all_cities = new ArrayList<City>();
    this.all_routes  = new ArrayList<Route>();
    this.all_cities_str = new ArrayList<String>();
  }
  
  public int length() {
    
    return all_cities.size();
  }
 
  
  // can replace check to improve runtime
  public void addCity(City city) { 
   if (!all_cities_str.contains(city.getCityName())) {
    all_cities.add(city);
    all_cities_str.add(city.getCityName());
   }
  }
  
public void addRoute(City city_from, City city_to, double fare) throws Exception {
   Route routeFrom = new Route(city_from, city_to, fare);
   Route routeTo = new Route(city_to, city_from, fare);
   int q = all_cities_str.indexOf(city_from.getCityName());
   
   int contain = 1;
   City w = all_cities.get(q);
   for(Route c : all_routes) {
	  
	   if(((routeFrom.getTo().getCityName().equals(c.getTo().getCityName())) &&
			   (routeFrom.getFrom().getCityName().equals(c.getFrom().getCityName())))
			   
			   ||((routeFrom.getTo().getCityName().equals(c.getFrom().getCityName())) && 
			   (routeFrom.getFrom().getCityName().equals(c.getTo().getCityName()))))
			   {
		   System.out.println(city_from.getCityName());
		   contain = 0;
		   
	   }
   }
   if(q >= 0 && contain == 1) {// && contain == 1){
     w.addRouteFrom(routeFrom);
     w.addRouteFrom(routeTo); //zzz
     
   }
   else if(contain == 1){

     city_from.addRouteFrom(routeFrom);
     city_from.addRouteFrom(routeTo);
     //city_to.addRouteFrom(routeTo);
    // city_to.addRouteFrom(routeFrom);
     //all_routes.add(routeTo);
     //all_routes.add(routeFrom);
   }
   
   else if (contain == 0) {
	   System.out.println("Invalid: Non-unique routes");
   }
  
   //city_to.addRouteTo(routeTo);
   //city_from.addRouteFrom(routeFrom);
   if (!(all_routes.contains(routeTo) || all_routes.contains(routeFrom))) {
    all_routes.add(routeTo);
   }
  // else {
    //throw new Exception("Invalid: Non-unique routes");
	 //  System.out.println("not unique1");
   //}
   
   if(!(all_routes.contains(routeFrom) || all_routes.contains(routeTo))){
     all_routes.add(routeFrom);
   }
   //else {
   // throw new Exception("Invalid: Non-unique routes");
	  // System.out.println("not unique2");
   //}
   
  }
  
  public void addRoute(String city_from_str, String city_to_str, double fare) throws Exception {
   City city_from, city_to;
   /*
    * need to make sure cities dont have the same from and to even if fare is difff
    * compare before adding
    */
   if (!all_cities_str.contains(city_from_str)) {
    city_from = new City(city_from_str);
    addCity(city_from);
    
   }

    else {
    int city_from_index = all_cities_str.indexOf((city_from_str)); //used to be new
    city_from = all_cities.get(city_from_index);
   }
   if (!all_cities_str.contains(city_to_str)) {
    city_to = new City(city_to_str);
    addCity(city_to);

    
   } else {
    int city_to_index = all_cities_str.indexOf((city_to_str));
    city_to = all_cities.get(city_to_index); //watch indexes
   }
   
   
   addRoute(city_from, city_to, fare);
   
      
  }
  
  
  public boolean containsCityCalled(String city) { 
    if (all_cities.contains((city))) { //used to be new
    return true;
   }
   
   return false;
  }
  
  
  // TODO
  public Vector<City> findCheapestRoute(RouteMap rm, City city_from, City city_to) {
   Vector<City> final_route = new Vector<City>();
   PriorityQueue<City> cities = new PriorityQueue<City>(); 
   //all_cities.size()
   
   for(int i = 0; i < all_cities.size(); i++) {
     City c = all_cities.get(i);
     c.setBestDist(10000);
     c.setBestRoute(null);
     
   }
   
   
   for(int a = 0; a < all_cities.size(); a++){
     if(all_cities.get(a).getCityName().equals(city_from.getCityName())){
        
        cities.add(all_cities.get(a));
        cities.peek().setBestDist(0);
        final_route.add(all_cities.get(a));
     }
   }

   do {
   
    City v = cities.poll();
    ArrayList <Route> routes= new ArrayList<Route>();
    routes = v.getRoutesFrom();
    
   
   for(int i = 0; i < routes.size(); i++) {
      Route r = routes.get(i);
 
     
     if(r.getDest().getBestDist() > v.getBestDist() + r.getFare()) {
     
      r.getDest().setBestDist(v.getBestDist () + r.getFare());
      cities.add(r.getDest());
      ArrayList<City> br = new ArrayList<City>();
      if(v.getBestRoute() != null){
      for(int u = 0; u < v.getBestRoute().size(); u++){
        br.add(u, v.getBestRoute().get(u));
      }
      }
      
        br.add(r.getDest());
      
      r.getDest().setBestRoute(br);
      
      

     }
    }
   } while(!cities.isEmpty());
    
  for(int a = 0; a < all_cities.size(); a++){
     if(all_cities.get(a).getCityName().equals(city_to.getCityName())){
         
        
     
   
  for(int x = 0; x < all_cities.get(a).getBestRoute().size(); x++){   
    final_route.add(all_cities.get(a).getBestRoute().get(x));
   }
     }
  }

   
   return final_route;
  }
  
  
 }
 
 
 

 // TODO
 public static void main(String [] Args) {
  // create RouteMap 
   RouteMap rm = new RouteMap();

  // debug
//  File file = new File(".");
//  for(String fileNames : file.list()) System.out.println(fileNames);
  
  // get filename from stdin
  
  City final_city_from = null, final_city_to = null;
  
  
  
  // open file and get input line by line
  // get input, creating Cities and routes and adding to the RouteMap
  try {
   // load file into scanner
   // TEMPORARY 
 //  filename = //"src/Cosc326Pair/" //+ filename;
   
   
    // program takes input from command line like: java Etude08 < inputFile.txt
   Scanner ifile = new Scanner(System.in);

   
   
   // first line handled separately
   
   // get and handle fist line of input
   String final_city_from_str, final_city_to_str; // load these variables
   String line = ifile.nextLine();
   line = line.toLowerCase();
   String[] inputs = line.split(",");
   
  /** try{
   if (inputs.length != 2) {
   }
   }catch (Exception e){
    System.out.println("Invalid: route");
   }
   
   **/
   if(inputs.length != 2){
     System.out.println("Invalid: route");
     return;
   }
   
   
   
   // instantiate Cities
   final_city_from = new City(inputs[0].trim());
   final_city_to = new City(inputs[1].trim());
   
   // add cities to routemap: not necessary?
//   rm.addCity(final_city_from);
//   rm.addCity(final_city_to);
   
   // handle the remaining lines of the file
   while (ifile.hasNextLine()) {
    
    line = ifile.nextLine().toLowerCase();
    inputs = line.split(","); // split line into multiple strings, separated by comma
    
    
    if(inputs.length != 3){
      System.out.println("Invalid: route set");
      return;
    }
    
    
    
    String city_from_str = inputs[0], city_to_str = inputs[1], fare_str = inputs[2];
    double fare = Double.parseDouble(fare_str.trim());
    rm.addRoute(city_from_str.trim(), city_to_str.trim(), fare);
   }
  } catch(Exception e) {
   System.out.println(e.getMessage());
   e.printStackTrace();
  }
  
  
  // use the method to find the cheapest route
  Vector<City> final_route = rm.findCheapestRoute(rm, final_city_from, final_city_to);
  

  
  // output the cheapest route
  
  for (int i = 0; i < final_route.size(); i++) {
    if(final_route.get(i) != null){
   System.out.print(final_route.get(i).city_name);
    }
   
   if (i != final_route.size()-1) {
    System.out.print("-");
   }
  }
  
  
  return;
 }
 }
 
 