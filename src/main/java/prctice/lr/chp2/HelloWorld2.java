package prctice.lr.chp2;

public class HelloWorld2 {
	JedisWrapper jedisWrapper = null;
	
	public HelloWorld2() {
		jedisWrapper = new JedisWrapper();
	}
	
	private void test() {
		jedisWrapper.set("MSG", "Hello world 2 ");
		
		String result = jedisWrapper.get("MSG");
		System.out.println("MSG : " + result);
	}
	
	public static void main(String[] args) {
		HelloWorld2 helloWorld2 = new HelloWorld2();
		helloWorld2.test();
	}

}
