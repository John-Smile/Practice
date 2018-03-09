package practice.jvmgo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hulk on 2017/8/3.
 */
public class BoxTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.toString());
        for (int x : list) {
            System.out.println(x);
        }
    }
}
