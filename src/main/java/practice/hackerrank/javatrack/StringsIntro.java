package practice.hackerrank.javatrack;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/26.
 */
public class StringsIntro {
    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        String A=sc.next();
        String B=sc.next();
        /* Enter your code here. Print output to STDOUT. */
        System.out.println(A.length() + B.length());
        if (A.compareTo(B) > 0) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
        System.out.print(capitalizeFirstLetter(A));
        System.out.println(capitalizeFirstLetter(A));
    }

    private static String capitalizeFirstLetter(String a) {
        return a.substring(0, 1).toUpperCase() + a.substring(1);
    }
}
