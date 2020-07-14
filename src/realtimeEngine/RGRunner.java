package realtimeEngine;

final class RGRunner implements Runnable {
   static boolean running = false;
   static long desiredFrameTimeNanos = (long)(1e9/65.0);//in nanoseconds
   static long lastFrameTime;
   static double tpf;
   
   public void run(){
      long systemTimeInNanoseconds = 0;
      long lastFrameTimeInNanoseconds = 0;
      running = true;
      while(running){
         systemTimeInNanoseconds = System.nanoTime();
         tpf = (double)(lastFrameTimeInNanoseconds)/1e9;
         RGSystem.step(tpf);
         lastFrameTimeInNanoseconds = System.nanoTime() - systemTimeInNanoseconds;
         
         if(lastFrameTimeInNanoseconds < desiredFrameTimeNanos){
             long waitTimeInNanoseconds = desiredFrameTimeNanos - lastFrameTimeInNanoseconds;
             try {
            	 Thread.sleep(waitTimeInNanoseconds/1000000, (int)(waitTimeInNanoseconds%1000000));
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             lastFrameTimeInNanoseconds = System.nanoTime() - systemTimeInNanoseconds;
             lastFrameTime = lastFrameTimeInNanoseconds;
         }
      }
   }
   
   public static long getFrameNanoTime(){
      return lastFrameTime;
   }
   
   public static long getFPS(){
	   if(tpf == 0){
		   return -1;
	   } else {
		   return (int)(1.0/tpf);
	   }
   }
}