import java.util.*;
import java.io.*;

public class TelephoneSystem{
  
  public class Telephone{
    public double eastPos;
    public double northPos;
    
    
    
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
    
    
    public class TelephoneRadius{
      ArrayList <Telephone> telephones =  new ArrayList<Telephone>();
      
    }
  }
  
  
  
  
  public static void main (String[]args){
        
    try {      
      
      // input file comes in directly through stdin e.g. java Etude05 < input_file.txt
      Scanner ifile = new Scanner(System.in);
      
      String[] inputs;
      while (ifile.hasNextLine()) {
        
        String line = ifile.nextLine();
        inputs = line.split(" "); // split line into multiple strings, separated by comma
        System.out.println(inputs[0]);
        
        if (inputs.length != 2) {
          throw new Exception("Invalid");
        }
        
      
      }
    } catch(Exception e) {
      System.out.println(e.getMessage());
      e.printStackTrace();
      ;//TODO: remove
    }
  }
}
