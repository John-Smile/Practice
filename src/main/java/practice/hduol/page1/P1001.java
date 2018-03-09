package practice.hduol.page1;

import java.util.Scanner;

public class P1001 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNextInt()) {
			int n = sc.nextInt();
			int sum = 0;
			for (int i = 1; i <= n; ++i) {
				sum += i;
			}
			System.out.println(sum);
			System.out.println();
		}
		sc.close();
	}
}
