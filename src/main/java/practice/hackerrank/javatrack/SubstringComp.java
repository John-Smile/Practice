package practice.hackerrank.javatrack;

import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/26.
 */
public class SubstringComp {
    public static String getSmallestAndLargest(String s, int k) {
        String smallest = null;
        String largest = null;

        // Complete the function
        // 'smallest' must be the lexicographically smallest substring of length 'k'
        // 'largest' must be the lexicographically largest substring of length 'k'
        for (int i = 0; i < s.length() - k + 1; ++i) {
            String tmp = s.substring(i, i + 3);
            if (largest == null || tmp.compareTo(largest) > 0) {
                largest = tmp;
            }
            if (smallest == null || tmp.compareTo(smallest) < 0) {
                smallest = tmp;
            }
        }

        return smallest + "\n" + largest;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String s = scan.next();
        int k = scan.nextInt();
        scan.close();

        System.out.println(getSmallestAndLargest(s, k));
    }
}
