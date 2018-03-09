package practice.hackerrank.javatrack;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/26.
 */
public class SubString1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String S = in.next();
        int start = in.nextInt();
        int end = in.nextInt();
        System.out.println(S.substring(start, end));
    }
}
