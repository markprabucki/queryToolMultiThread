package com.util;
import edu.emory.mathcs.backport.java.util.concurrent.*;     

public class ThreadBlaster     
{   
	private static int threads = 10;
	public static void main( String[] args )      
	{  
		System.out.println( "Starting threads" );  
		ExecutorService threadExecutor = Executors.newFixedThreadPool( threads );  
		for (int x=0;x<=threads;x++) {
		     threadExecutor.execute( new ThreadTask("thread" + x) ); 
		}
		threadExecutor.shutdown(); 		
		System.out.println( "Threads started, main ends\n" );  
	} 
}
