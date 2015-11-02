import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.*;
 
public class JSONParsing {
	
	
	// static FileWriter fileWriter = null;
	 //static File newTextFile = new File("C:\\Users\\Latika\\Desktop\\SWM-JSON\\part1\\MOvies\\finalList.json");
     public static void main(String[] args) {
 
	JSONParser parser = new JSONParser();
 
	try {
 
		FileInputStream fis = new FileInputStream("C:\\Users\\Latika\\Desktop\\SWM-JSON\\part1\\MOvies\\movies2015.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        
		Object obj; 
		
		JSONObject jsonObj;
		JSONObject obj1 ;
          String line = reader.readLine();
         // System.out.println(line);
          while(line != null){
             // System.out.println(line);
             
              obj=parser.parse(line);
              jsonObj=(JSONObject) obj;
             // String name = (String) jsonObj.get("Title");
      		 // System.out.println(name);
      		  String rating=(String) jsonObj.get("imdbRating");
      		  if(!rating.equals("N/A"))
      		  {
      			
    			  obj1=new JSONObject();
    			  String Runtime=(String)jsonObj.get("Runtime");
      			  String Genre=(String)jsonObj.get("Genre");
      			String Director=(String)jsonObj.get("Director"); 
      			String Writer=(String)jsonObj.get("Writer");
      			String Actors=(String)jsonObj.get("Actors");
      			String Language=(String)jsonObj.get("Language");
      			String Awards=(String)jsonObj.get("Awards");
      			String imdbRating=(String)jsonObj.get("imdbRating");
      			String imdbVotes=(String)jsonObj.get("imdbVotes");
      			  obj1.put("Genre", Genre);
      			obj1.put("Runtime", Runtime);
      			obj1.put("Director", Director);
      			obj1.put("Writer", Writer);
      			obj1.put("Actors", Actors);
      			obj1.put("Language", Language);
      			obj1.put("Awards", Awards);
      			obj1.put("imdbRating", imdbRating);
      			obj1.put("imdbVotes", imdbVotes);
      			double r = Double.parseDouble(rating);
      			String success;
      			if(r>=8)
      				success="Yes";
      			else
      				success="No";
      			obj1.put("Success", success);
      			String str=obj1.toJSONString();
      			  writeToFile(str);
      		  }
      		
      		  System.out.println(rating);
      		 line = reader.readLine();
      		
              }

		
	//	Object obj = parser.parse(new FileReader("C:\\Users\\Latika\\Desktop\\SWM-JSON\\part1\\MOvies\\mv.json"));
 
	//	JSONObject jsonObject = (JSONObject) obj;
 
	/*	String name = (String) jsonObject.get("Title");
		System.out.println(name);
 
		String  year = (String) jsonObject.get("Year");
		System.out.println(year);*/
 
		// loop array
	/*	JSONArray msg = (JSONArray) jsonObject.get("messages");
		Iterator<String> iterator = msg.iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}*/
 
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
 
     }
     public static void writeToFile(String line)
     {
    	
      //   try {
            // String content = "Hello! Java-Buddy :)";
        	 
        	 try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("C:\\Users\\Latika\\Desktop\\SWM-JSON\\part1\\MOvies\\finalList.json", true)))) {
        		    out.println(line);
        		}catch (IOException e) {
        		    //exception handling left as an exercise for the reader
        		}
            
         //    fileWriter = new FileWriter(newTextFile);
           //  fileWriter.write(line);
           //  fileWriter.close();
         //} 
         catch (Exception ex)
         {
        	 
         }
             
        //  finally {
           /*  try {
                 //fileWriter.close();
             } catch (IOException ex) {
                // Logger.getLogger(WriteStringToFile.class.getName()).log(Level.SEVERE, null, ex);
             }*/
   //  }
 
}}