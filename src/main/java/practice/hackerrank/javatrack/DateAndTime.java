package practice.hackerrank.javatrack;

import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by Hulk on 2017/6/21.
 */
public class DateAndTime {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String month = in.next();
        String day = in.next();
        String year = in.next();
        Calendar c = Calendar.getInstance();
        c.set(Integer.valueOf(year), Integer.valueOf(month) - 1, Integer.valueOf(day));
        int weekInt = c.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        switch (weekInt) {
            case 2:
                weekStr = "MONDAY";
                break;
            case 3:
                weekStr = "TUESDAY";
                break;
            case 4:
                weekStr = "WEDNESDAY";
                break;
            case 5:
                weekStr = "THURSDAY";
                break;
            case 6:
                weekStr = "FRIDAY";
                break;
            case 7:
                weekStr = "SATURDAY";
                break;
            case 1:
                weekStr = "SUNDAY";
                break;

        }
        System.out.println(weekStr);
    }
}
