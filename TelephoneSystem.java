import java.util.*;
import java.math.BigDecimal;
import java.math.RoundingMode;


public class TelephoneSystem{

  // the number of decimal places given in the inputs.  Used for accuracy
  static int decimalPlaces = -1;
  static double threshold = -1; // the threshold used for double comparison

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
        inputs = line.split("\\s+"); // split line into multiple strings, separated by any whitespace

        // determine the number of decimal places the input has
        int integerPlaces = inputs[0].indexOf('.');
        decimalPlaces = inputs[0].length() - integerPlaces - 1;
        
        // determine the threshold to be used for determining if a point lies on the boundary
        threshold = 1;
        for (int i = 0; i < decimalPlaces; i++) {
          threshold /= 10;
        }

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

    // Create all possible circles out of two or three points
    ArrayList<Circle> allCircles = new ArrayList<Circle>();

    for (int i = 0; i < allTelephones.size(); i++) {
      Telephone t1 = allTelephones.get(i);
      for (int j = i + 1; j < allTelephones.size(); j++) {
        Telephone t2 = allTelephones.get(j);
        Circle diametricCircle = findDiametricCircle(t1, t2);
        allCircles.add(diametricCircle);
        for (int k = j + 1; k < allTelephones.size(); k++) {
          Telephone t3 = allTelephones.get(k);
          Circle circle = findCircle(t1, t2, t3);
          // System.err.println(circle);
          // System.err.println(t1);
          // System.err.println(t2);
          // System.err.println(t3);
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
    int maxPointsFound = 0; // most points contained in any circle, used for special cases where there are few points
    for (int i = 0; i < allCircles.size(); i++) {
      Circle c = allCircles.get(i);

      // skip this circle because its radius is too big
      if (c.radius >= maxRadius) {
        continue;
      }

      // TODO: RETURN IF BOUNDARY POINTS FOR SPECIAL CASE
      int[] numPointsContained = countPointsInCircle(c, allTelephones);
      int interiorPoints = numPointsContained[0];
      int boundaryPoints = numPointsContained[1];
      int totalPoints = interiorPoints + boundaryPoints;
      c.numPointsContained = totalPoints;

      if (totalPoints > maxPointsFound) {
        maxPointsFound = c.numPointsContained;
      }

      // this radius, and all radii larger, belong to invalid circles
      if (interiorPoints > 12 && c.radius < maxRadius) {
        maxRadius = c.radius;
        // System.err.println("new max radius: " + maxRadius);
        continue;
      }

      // circle is valid only if it has 12 or less points in it 
      // OR the number of points contained minus the boundary points is less than 12
      // so when we reduce the boundary size at the end, the number of points remaining is less than 12
      if (interiorPoints < 12 && c.radius < maxRadius) {
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

    // special case
    if (maxPointsFound < 12) {
      System.out.println("Infinity");
      return;
    }
    
    double bestRadius = Double.POSITIVE_INFINITY; // smallest radius of 12 point circle 
    for(Circle cir : validCircles){
      if(cir.numPointsContained >= 12 && cir.radius < bestRadius){
        bestRadius = cir.radius;
      }
    }

    // subtract a small amount from the best radius: this is the output
    // System.err.println("best radius: " + bestRadius);
    double output = bestRadius - threshold;
    output = round(output, decimalPlaces);
    System.out.println(output);
  }

  // Function to count all of the points contained within a circle
  public static int[] countPointsInCircle(Circle circle, ArrayList<Telephone> allTelephones) {
    int interiorPoints = 0;
    int boundaryPoints = 0;

    for (Telephone t : allTelephones) {
      double dist = calculateDistanceBetweenPoints(t.eastPos, t.northPos, circle.eastPos, circle.northPos);
      // count interior and boundary points separately
      if (dist < circle.radius) {
        interiorPoints++;
        // System.err.println("interior point");
      } 
      else if (Math.abs(dist - circle.radius) < threshold) {
        // System.err.println("boundary point");
        boundaryPoints++;
      }
      
      // terminate early if it exceeds 12 points inside the circle
      if (interiorPoints >= 12) {
        // System.err.println("boundary Points: " + boundaryPoints);
        int[] ret = {interiorPoints, boundaryPoints};
        return ret;
      }
    }
    // System.err.println("boundary Points: " + boundaryPoints);
    int[] ret = {interiorPoints, boundaryPoints};
    return ret;
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
  public static Circle findCircle(Telephone t1, Telephone t2, Telephone t3) { 
    double x1 = t1.eastPos, y1 = t1.northPos;
    double x2 = t2.eastPos, y2 = t2.northPos;
    double x3 = t3.eastPos, y3 = t3.northPos;

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

  // makes a diametric circle given two points
  public static Circle findDiametricCircle(Telephone t1, Telephone t2) {
    double x1 = t1.eastPos, y1 = t1.northPos;
    double x2 = t2.eastPos, y2 = t2.northPos;
    double radius = calculateDistanceBetweenPoints(x1, y1, x2, y2) / 2;
    double midpointx = Math.abs((x2+x1) / 2.0);
    double midpointy = Math.abs((y1+y2)/ 2.0);

    Circle c = new Circle(midpointx, midpointy, radius);
    return c;
  }

  public static double round(double val, int places){
    if(places < 0) throw new IllegalArgumentException();
    
    BigDecimal bigDecimal = new BigDecimal(val);
    bigDecimal = bigDecimal.setScale(places, RoundingMode.HALF_UP);
    return bigDecimal.doubleValue();
  }

}


