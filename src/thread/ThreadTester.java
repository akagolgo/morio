/**
 * 
 */
package thread;

/**
 * @author mori
 *
 */
public class ThreadTester {

	public class Sleeper implements Runnable {
		
		public Status live;
		
		public Sleeper(Status live) {
			super();
			this.live = live;
		}

		@Override
		public void run() {
			for(int i = 0; i < 3; i++) {
				System.out.println("sleep round: " + (i+1));
				try {
					Thread.sleep(1000*5);
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
			live.live = false;
			System.out.println("sleep thread exiting.");
		}
	}

	public class Waiter implements Runnable {
		public Status live;
		
		public Waiter(Status live) {
			super();
			this.live = live;
		}

		@Override
		public void run() {
			int i = 1;
			while(live.live) {
				System.out.println("wait round: " + (i++));
				synchronized (this) {
					try {
						wait(1000);
					} catch (InterruptedException e) {	e.printStackTrace();}
				}
			}
			System.out.println("wait thread exiting.");
		}		
	}
	
	public class Status {
		public boolean live;
		public Status() { live = true; };
	}

	public ThreadTester() {
		
		Status live = new Status(); 
		Thread t1 = new Thread(new Sleeper(live));  // tick logger - .sleep
		Thread t2 = new Thread(new Waiter(live));  // tick stream monitor - .wait
		
		t1.start();
		t2.start();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		ThreadTester tt = new ThreadTester();
		System.out.println("main thread exiting.");
	}

}
