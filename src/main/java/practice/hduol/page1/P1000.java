package practice.hduol.page1;

import java.util.Scanner;

public class P1000 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int a, b;
		while (sc.hasNextInt()) {
			a = sc.nextInt();
			b = sc.nextInt();
			System.out.println(a + b);
		}
		sc.close();
	}
}