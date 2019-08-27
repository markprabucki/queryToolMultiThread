package com.util;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class queryToolSingleThread 
{
   private static java.io.FileWriter outfile;
   private static java.io.PrintWriter pw;	
   public static void main(String[] args)  {

      try 
      {
         queryToolSingleThread(args[0]);
      }
      catch (Exception e)
      {
         System.err.println("Error: " + e.getMessage());
      }
  }

  private static void queryToolSingleThread(String file) throws Exception
  {
	    Connection connection = null;	
	    String driverName = "oracle.jdbc.driver.OracleDriver";
	    int lineCounter = 0;
	    int checkCounter = 0;
	  	int foundCount = 0;
		int notFoundCount = 0;   
		boolean found = false;	    
	    try {
	    	Class.forName(driverName);
	        String serverName = "127.0.0.2";
	        String portNumber = "1573";
	        String sid = "mySID";
	        String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
	        String username = "myUserName";
	        String password = "myPassword";
	        connection = DriverManager.getConnection(url, username, password);
	        System.out.println("Connection successful!");
	        Statement stmt = connection.createStatement ();
	        System.out.println("Testing file:" + file);
	        String inputFileName = file;
	        outfile = new java.io.FileWriter(inputFileName + ".output.txt");	   
	        pw = new java.io.PrintWriter(outfile);
	        FileInputStream fstream = new FileInputStream(file);
	        DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in));
	        String strLine;
	        while ((strLine = br.readLine()) != null)   
	        {
	           found = false;
	           lineCounter++;
	           checkCounter++;
		       // Set the fetch size on the statement
		       // stmt.setFetchSize(100);
		       ResultSet resultSet = stmt.executeQuery("SELECT column1 from database.table where column1 = '"+strLine+"'");
		       if(resultSet.getFetchSize() > 0){
	  	           found = true;
		        }
	           
	           if(!found) 
	           {
	              notFoundCount++;
	              pw.println(strLine);
	           }
	           else
	           {
	              foundCount++;
	           }
	           if(checkCounter>500){
	        	   checkCounter = 0;
	        	System.out.println("----------------------------------------");
	   	        System.out.println("Done - number of lines processed:" + lineCounter);
		        System.out.println("Found:" + foundCount);
		        System.out.println("Not Found:" + notFoundCount);	        	   
	           }
	        }
	        in.close();
	        System.out.println("Done - number of lines processed:" + lineCounter);
	        System.out.println("Found:" + foundCount);
	        System.out.println("Not Found:" + notFoundCount);
	        	
	    } catch (ClassNotFoundException e) {
	        System.out.println("Could not find database Driver");
	    } catch (SQLException e) {
	        System.out.println("Unable to connect to db");
	        e.printStackTrace();
	    }
	    finally {
	        try {
	            connection.close();
	        }
	        catch( SQLException e ) {
	            e.printStackTrace();
	        }
	    }
  }  
}

