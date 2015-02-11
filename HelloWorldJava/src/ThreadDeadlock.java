
public class ThreadDeadlock {

	String st1 = "asdf";
	String st2 = "fdsa";
	
	Thread t1 = new Thread() {
		public void run() {
			while(true) {
				synchronized (st1) {
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					synchronized (st2) {
						System.out.println(st1 + st2);
					}
				}
			}
		}
	};
	Thread t2 = new Thread() {
		public void run() {
			while(true) {
				synchronized (st2) {
//					try {
//						Thread.sleep(10);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
					synchronized (st1) {
						System.out.println(st1 + st2);
					}
				}
			}
		}
	};
			

	public static void main(String[] args) {
		// Comments
		ThreadDeadlock td = new ThreadDeadlock();
		td.t1.start();
		td.t2.start();
	}

}
