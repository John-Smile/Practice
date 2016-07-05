package practice.jcip.chp16;

public class PossibleReordering {
	static int x = 0, y = 0;
	static int a = 0, b = 0;
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10000; ++i) {
			String res = test();
			if (res.equals("(0,0)")) {
				System.out.println(res);
			}
		}
	}
	
	public static String test() throws InterruptedException {
		Thread one = new Thread(new Runnable() {
			public void run() {
				a = 1;
				x = b;
			}
		});
		Thread other = new Thread(new Runnable() {
			public void run() {
				b = 1;
				y = a;
			}
		});
		one.start();
		other.start();
		one.join();
		other.join();
		return "(" + x + "," + y + ")";
	}

}
