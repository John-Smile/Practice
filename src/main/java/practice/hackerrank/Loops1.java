package practice.hackerrank;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/20.
 */
public class Loops1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        for( int i = 1; i <= 10; ++i) {
            String str = String.format("%d x %d = %d", n, i, n * i);
            System.out.println(str);
        }
    }
}
