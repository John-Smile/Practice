package practice.hackerrank;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/20.
 */
public class EndOfFile {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int i = 1;
        String line;
        try {
            while (true) {
                line = sc.nextLine();
                String str = String.format("%d %s", i, line);
                System.out.println(str);
                ++i;
            }
        } catch (Exception e) {
            sc.close();
        }

    }
}
