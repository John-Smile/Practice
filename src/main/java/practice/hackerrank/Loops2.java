package practice.hackerrank;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/20.
 */
public class Loops2 {
    public static void main(String []argh){
        Scanner in = new Scanner(System.in);
        int t=in.nextInt();
        for(int i=0;i<t;i++){
            int a = in.nextInt();
            int b = in.nextInt();
            int n = in.nextInt();
            fun(a, b, n);
        }
        in.close();
    }

    private static void fun(int a, int b, int n) {
        long res = a;
        for (int i = 0; i < n; ++i) {
            res += (1 << i) * b;
            System.out.print(res + " ");
        }
        System.out.println();
    }
}
