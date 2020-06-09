import java.util.*;
import java.io.*;
import java.text.*; 


public class TelephoneSystem{

  public static class Telephone {
    public double eastPos;
    public double northPos;

    public Telephone(double eastPos, double northPos) {
      this.eastPos = eastPos;
      this.northPos = northPos;
    }
    
    public void setEP(double eastPos){
      this.eastPos = eastPos;
    }
    
    public double getEP(){
      return this.eastPos;
    }
    
    public void setNP(double northPos){
      this.northPos = northPos;
    }
    
    public double getNP(){
      return this.northPos;
    }

    public String toString() {
      return eastPos + " " + northPos;
    }
  }
  

  public static class Circle {
    double eastPos, northPos;
    double radius;
    int numPointsContained = -1;

    public Circle(double eastPos, double northPos, double radius) {
      this.eastPos = eastPos;
      this.northPos = northPos;
      this.radius = radius;
    }

    public String toString() {
      return "(" + eastPos + ", " + northPos + "), r=" + radius + " numPoints=" + numPointsContained; 
    }
    
    public double getRadius(){
      return radius;
    }
  }
  
  
  
  public static void main (String[]args){

    ArrayList<Telephone> allTelephones = new ArrayList<Telephone>();   
    
    try {      
      
      // input file comes in directly through stdin e.g. java Etude05 < input_file.txt
      Scanner ifile = new Scanner(System.in);
      
      // first line should be "Telephone sites"
      String line = ifile.nextLine();
      if (!line.equals("Telephone sites")) {
        
        throw new Exception("First line is invalid");
      }

      // Get all input points
      String[] inputs;
      while (ifile.hasNextLine()) {
        line = ifile.nextLine();
        inputs = line.split(" "); // split line into multiple strings, separated by space

        // Handle some invalid inputs
        if (inputs.length != 2) {
          throw new Exception("Invalid line");
        }

        // create Telephone object
        double eastPos = Double.parseDouble(inputs[0]), northPos = Double.parseDouble(inputs[1]);
        Telephone telephone = new Telephone(eastPos, northPos);

        // add Telephone to list of telephones if there are no duplicates
        boolean duplicate = false;
        for (int i = 0; i < allTelephones.size(); i++) {
          Telephone t = allTelephones.get(i);
          if (t.eastPos == telephone.eastPos && t.northPos == telephone.northPos) {
            duplicate = true;
            break;
          }
        }
        if (!duplicate) {
          allTelephones.add(telephone);
        }
      
      }
    } catch(Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
    }

    // TESTING: print out all telephones
    // for (Telephone t : allTelephones) {
    //   System.err.println(t);
    // }

    // Processing

    // Create all possible circles out of three points
    ArrayList<Circle> allCircles = new ArrayList<Circle>();

    

    for (int i = 0; i < allTelephones.size(); i++) {
      Telephone t1 = allTelephones.get(i);
      for (int j = i + 1; j < allTelephones.size(); j++) {
        Telephone t2 = allTelephones.get(j);
        for (int k = j + 1; k < allTelephones.size(); k++) {
          Telephone t3 = allTelephones.get(k);

          Circle circle = findCircle(t1.eastPos, t1.northPos, t2.eastPos, t2.northPos, t3.eastPos, t3.northPos);
          allCircles.add(circle);
        }
      }
    }

    // new arraylist will only contain circles with 12 or less points in them
    ArrayList<Circle> validCircles = new ArrayList<Circle>();

    // if a circle contains 14 points, the final radius can be no larger than that circles radius
    // we can use this information to eliminate potential circles from processing
    double maxRadius = Double.MAX_VALUE;
    
    // count the number of points each circle contains
    for (int i = 0; i < allCircles.size(); i++) {
      Circle c = allCircles.get(i);

      // skip this circle because its radius is too big
      if (c.radius >= maxRadius) {
        continue;
      }

      int numPointsContained = countPointsInCircle(c, allTelephones);
      c.numPointsContained = numPointsContained;

      // this radius, and all radii larger, belong to invalid circles
      if (numPointsContained >= 14) {
        maxRadius = c.radius;
      }

      // circle is valid only if it has 12 points in it
      if (numPointsContained == 12) {
        validCircles.add(c);
      }
    }
    
    // TESTING: print out all circles
    // for (Circle c : allCircles) {
    //   System.err.println(c);
    // }

    // TESTING: print out only valid circles
    // this output should only have any circles with numPoints==12
    // for (Circle c : validCircles) {
    //   System.err.println(c);
    // }
    
    double bestRadius = Double.POSITIVE_INFINITY;
    for(Circle cir : validCircles){
      if(cir.getRadius() < bestRadius){
        bestRadius = cir.getRadius();
      }
    }

    // System.out.println("Maximum range: " + bestRadius);
    // System.out.print("Maximum range - .01 to make that circle not enclose 12 pts: ");
    System.out.format("%.2f", (bestRadius - .01));
    System.out.println();
      
      
  }

  // Function to count all of the points contained within a circle
  public static int countPointsInCircle(Circle circle, ArrayList<Telephone> allTelephones) {
    int pointsContained = 0;
    for (Telephone t : allTelephones) {
      if (calculateDistanceBetweenPoints(t.eastPos, t.northPos, circle.eastPos, circle.northPos) 
      <= circle.radius) {
        pointsContained++;
      }
      // terminate early if it exceeds 14 points
      if (pointsContained >= 14) {
        return pointsContained;
      }
    }
    return pointsContained;
  }

  // from "https://www.baeldung.com/java-distance-between-two-points"
  public static double calculateDistanceBetweenPoints(
  double x1, 
  double y1, 
  double x2, 
  double y2) {       
    return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
}

  // Function to find the circle on which the given three points lie 
  // derived from "https://www.geeksforgeeks.org/equation-of-circle-when-three-points-on-the-circle-are-given/"
  public static Circle findCircle(double x1, double y1, double x2, double y2, double x3, double y3) { 
    double x12 = x1 - x2; 
    double x13 = x1 - x3; 

    double y12 = y1 - y2; 
    double y13 = y1 - y3; 

    double y31 = y3 - y1; 
    double y21 = y2 - y1; 

    double x31 = x3 - x1; 
    double x21 = x2 - x1; 

    // x1^2 - x3^2 
    double sx13 = (Math.pow(x1, 2) - 
    Math.pow(x3, 2)); 

    // y1^2 - y3^2 
    double sy13 = (Math.pow(y1, 2) - 
    Math.pow(y3, 2)); 

    double sx21 = (Math.pow(x2, 2) - 
    Math.pow(x1, 2)); 

    double sy21 = (Math.pow(y2, 2) - 
    Math.pow(y1, 2)); 

    double f = ((sx13) * (x12) 
    + (sy13) * (x12) 
    + (sx21) * (x13) 
    + (sy21) * (x13)) 
    / (2 * ((y31) * (x12) - (y21) * (x13))); 
    double g = ((sx13) * (y12) 
    + (sy13) * (y12) 
    + (sx21) * (y13) 
    + (sy21) * (y13)) 
    / (2 * ((x31) * (y12) - (x21) * (y13))); 

    double c = -Math.pow(x1, 2) - Math.pow(y1, 2) - 
            2 * g * x1 - 2 * f * y1; 

    // eqn of circle be x^2 + y^2 + 2*g*x + 2*f*y + c = 0 
    // where centre is (h = -g, k = -f) and radius r 
    // as r^2 = h^2 + k^2 - c 
    double h = -g; 
    double k = -f; 
    double sqr_of_r = h * h + k * k - c; 

    // r is the radius 
    double r = Math.sqrt(sqr_of_r); 
    // DecimalFormat df = new DecimalFormat("#.#####"); 

    Circle circle = new Circle(h, k, r);
    return circle;

    // System.out.println("Centre = (" + h + "," + k + ")"); 
    // System.out.println("Radius = " + df.format(r)); 
  }

}


