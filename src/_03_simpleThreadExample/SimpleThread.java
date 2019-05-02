package _03_simpleThreadExample;

public class SimpleThread {
	
	static void threadMessage(String message) {
		String threadName=Thread.currentThread().getName();
		System.out.format("%s : %s\n", threadName, message);
	}
	
	private static class MessageLoop implements Runnable{
		@Override
		public void run() {
			String importantInfo[] = {
	                "Mares eat oats",
	                "Does eat oats",
	                "Little lambs eat ivy",
	                "A kid will eat ivy too"
	            };
			try {
				for(String info:importantInfo) {
					Thread.sleep(4000);
					threadMessage(info);
				}
			}catch(InterruptedException ex) {
				threadMessage("I wasn't done");
			}
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		long patience=6000;
		threadMessage("Starting MessageLoop thread");
		long startTime=System.currentTimeMillis();
		Thread thread=new Thread(new MessageLoop());
		thread.start();
		threadMessage("Waiting for MessageLoop thread to finish");
		while(thread.isAlive()) {
			threadMessage("Still waiting for the thread to die...");
			thread.join(3000);
			if((System.currentTimeMillis()-startTime)>patience) {
				threadMessage("Tired of waiting");
				thread.interrupt();
				if(thread.isInterrupted()) {
					threadMessage("Thread is interrupted");
				}
				if(thread.isAlive()) {
					threadMessage("Thread is still alive after interruption for last joining");
				}
				thread.join();// Waits for this thread to die
				if(!thread.isAlive()) {
					threadMessage("Thread is dead after last joining");
				}
			}
		}
		threadMessage("Finally");
	}

}
