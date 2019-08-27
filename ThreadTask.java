package com.util;
import java.util.Random;
class ThreadTask implements Runnable
{
   private int sleepTime; 
   private String threadName; 
   private static Random generator = new Random();
   public ThreadTask( String name ) {
      threadName = name; 
      sleepTime = generator.nextInt( 5000 );
   } 

   public void run() {
     try {
         System.out.println(" going to sleep: " + threadName);
         Thread.sleep( sleepTime ); 
         } 
         catch ( InterruptedException exception ) {
                exception.printStackTrace();
         } 
         System.out.println("done sleeping:" + threadName );
      } 
}


