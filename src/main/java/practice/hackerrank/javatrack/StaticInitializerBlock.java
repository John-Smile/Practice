package practice.hackerrank.javatrack;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/21.
 */
public class StaticInitializerBlock {
    private static int B;
    private static int H;
    private static boolean flag = false;
    static {
        Scanner in = new Scanner(System.in);
        B = in.nextInt();
        H = in.nextInt();
        if (B <= 0 || H <= 0) {
            System.out.println("java.lang.Exception: Breadth and height must be positive");
        } else {
            flag = true;
        }
    }

    public static void main(String[] args){
        if(flag){
            int area=B*H;
            System.out.print(area);
        }

    }
}
