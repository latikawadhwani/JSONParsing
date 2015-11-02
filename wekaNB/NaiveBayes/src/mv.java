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
import java.nio.file.Files;
import java.text.ParseException;



import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.core.Instances;
import weka.core.converters.ConverterUtils;
import weka.core.converters.TextDirectoryLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;


public class mv {
	public static void main(String args[])
	{
		try
		{
			readFile();
		//	NaiveBayes();
			NB();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	public static void readFile() throws org.json.simple.parser.ParseException
	{
		 String path=mv.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		 path=path.substring(0, path.length()-4);
		String eol=System.getProperty("line.separator");
		String str="@relation movies"+eol+eol+"@Attribute Director string"+eol+"@Attribute Actor string"+eol+"@Attribute Writer string"+eol+"@Attribute Genre string"+eol+"@Attribute Class {Yes,No}"+eol+eol+"@Data"+eol+eol;
				
				

	try {
		JSONParser parser = new JSONParser();
		
		FileInputStream fis = new FileInputStream(path+"\\finalList.json");
		BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
        
		Object obj; 
		
		JSONObject jsonObj;
		JSONObject obj1 ;
          String line = reader.readLine();
          int count=0;
          String[] remove={"?","�",""};
          while(line != null && count<600){
            
             
              obj=parser.parse(line);
              jsonObj=(JSONObject) obj;
             
      			
    			 
    			 
      			String Genre=(String)jsonObj.get("Genre");
      			String[] g=Genre.split(",");
      			Genre=g[0];
      			int gi=Genre.indexOf('(');
      			if(gi>0)
      			{
      			Genre=Genre.substring(0,gi);
      			}
      			String Director=(String)jsonObj.get("Director"); 
      			String[] d=Director.split(",");
      			if(d.length>1)
      			{
      				Director=d[0];
      			}
      			String Writer=(String)jsonObj.get("Writer");
      			String[] w=Writer.split(",");
      			if(w.length>1)
      			{
      				Writer=w[0];
      			}
      			int wi=Writer.indexOf('(');
      			if(wi>0){
      				Writer=Writer.substring(0,wi);
      			}
      			String Actors=(String)jsonObj.get("Actors");
      			String[] s=Actors.split(",");
      			String Actor="";
      			String SecondaryActor="";
      			if(s.length>1)
      			{
      			Actor=s[0];
      			 SecondaryActor=s[1];
      			}
      			else
      			{
      				 Actor=Actors;
      				 SecondaryActor=Actors;
      			}
      			int ai=Actor.indexOf('(');
      			if(ai>0)
      			{
      				Actor=Actor.substring(0,ai);
      			}
      			String Success=(String)jsonObj.get("Success");
      			
      			
      			if(Genre.trim().equals("")|| Director.trim().equals("")|| Writer.trim().equals("") || Actor.trim().equals("")||Genre.contains("'")||Actor.contains("'")||Director.contains("'")||Writer.contains("'"))
      			{
      			//do nothing
      			}
      			else
      			{
      			
	      			String str1="'"+Director.trim()+"'"+","+"'"+Actor.trim()+"'"+","+"'"+Writer.trim()+"'"+","+"'"+Genre.trim()+"'"+","+"'"+Success.trim()+"'"+"\n";
	      			
		      			
		      				//add string
		      				str=str+str1;
		      				count++;
		      			}
	      			
      			
      			
      			
      				line = reader.readLine();
      				
       		 
          }
            
          writeToFile(str); 

 
	} 
	catch (FileNotFoundException e) 
	{
		e.printStackTrace();
	} 
	catch (IOException e)
	{
		e.printStackTrace();
	} 
	
}
     
     public static void writeToFile(String line)
     {
    	
    	 String path=mv.class.getProtectionDomain().getCodeSource().getLocation().getPath().toString();
		 path=path.substring(0, path.length()-4);
		 File f = new File(path+"\\mv2.arff");
		 if(f.exists() && !f.isDirectory()) { f.delete();}

        	 try(PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(path+"\\mv2.arff", true)))) {
        		    out.println(line);
        		}catch (IOException e) {
        		    e.printStackTrace();
        		}
            
        
         catch (Exception ex)
         {
        	 ex.printStackTrace();
         }
             
        
 
     }
        public static void NB()
     {
    	 try
    	 {
    	 ConverterUtils.DataSource source1 = new ConverterUtils.DataSource("./mv2.arff");
         Instances train = source1.getDataSet();
         // setting class attribute if the data format does not provide this information
         // For example, the XRFF format saves the class attribute information as well
         if (train.classIndex() == -1)
             train.setClassIndex(train.numAttributes() - 1);

         ConverterUtils.DataSource source2 = new ConverterUtils.DataSource("./testData.arff");
         Instances test = source2.getDataSet();
         // setting class attribute if the data format does not provide this information
         // For example, the XRFF format saves the class attribute information as well
    if (test.classIndex() == -1)
          test.setClassIndex(test.numAttributes() - 1);
         StringToWordVector filter = new StringToWordVector();
 	    filter.setInputFormat(train);
 	    Instances traindataFiltered = Filter.useFilter(train, filter);
         // model

         NaiveBayes naiveBayes = new NaiveBayes();
         naiveBayes.buildClassifier(traindataFiltered);
         for(int i=0;i<30;i++)
         {
         // this does the trick  
         double label = naiveBayes.classifyInstance(test.instance(i));
         test.instance(i).setClassValue(label);
         System.out.println(label);
         }
       //  System.out.println(test.instance(0).stringValue(4));
    	 }
    	 catch(Exception e){
    		 e.printStackTrace();
    	 }
     }
}

