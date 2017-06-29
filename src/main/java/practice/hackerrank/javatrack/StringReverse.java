package practice.hackerrank.javatrack;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/28.
 */
public class StringReverse {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        String A=sc.next();
        /* Enter your code here. Print output to STDOUT. */
        if (A.equalsIgnoreCase(reverse(A))) {
            System.out.println("Yes");
        } else {
            System.out.println("No");
        }
    }

    private static String reverse(String a) {
        String result = "";
        for (int i = a.length() - 1; i >= 0; --i) {
            result += a.charAt(i);
        }
        return result;
    }
}
