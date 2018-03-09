package practice.hackerrank.javatrack;

import java.util.ArrayList;

public class ArrayQuarter {
    //找到所有能把数组头尾切分为两个相等部分的下标,并顺序存放在ArrayList中
    public static boolean arrayQuarter(int[] arr){
        //因为不确定有多少对m1,m3，所以用ArrayList将所有下标顺序存储，验证时一次取出三个就是m1,m3,sum
        ArrayList<Integer> list = new ArrayList<>();
        if (arr.length < 7)
            return false;
        int sum1 = arr[0];
        int sum3 = arr[arr.length-1];
        int m1 = 1;
        int m3 = arr.length-2;
        while(m1 < m3) {
            if (sum1 > sum3) {
                sum3 += arr[m3--];
            }else if (sum1 < sum3) {
                sum1 += arr[m1++];
            } else {
                list.add(m1);
                list.add(m3);
                list.add(sum1);
                sum1 += arr[m1++];
                sum3 += arr[m3--];
                continue;
            }
        }
        int[] result = checkingFind(arr, list);
        if (result == null)
            return false;
        for (int i = 0; i < result.length; i++)
            System.out.print(result[i] + " ");
        System.out.println();
        return true;
    }
    //对所有能将数组头尾切分为相等的下标进行验证,找到则返回m1,m2,m3数组
    public static int[] checkingFind(int[] arr, ArrayList<Integer> list) {
        if (list.size() == 0)
            return null;
        //依次检查所有m1,m3是否能找到匹配的m2满足题目要求
        int i = 0;
        int m1 = 0;
        int m2 = 0;
        int m3 = 0;
        int sum = 0;
        int[] result = {0, 0, 0};
        while (i < list.size()-1) {
            m1 = list.get(i++);
            m3 = list.get(i++);
            sum = list.get(i++);
            m2 = findMiddleIndex(arr, m1, m3, sum);
            if (m2 != 0){
                result[0] = m1;
                result[1] = m2;
                result[2] = m3;
                return result;
            }
        }
        return null;
    }
    //根据m1,m3找m2,找到则返回m2下标，找不到则返回0
    public static int findMiddleIndex(int[] arr, int m1, int m3, int sum) {
        int p = m1 + 1;
        int q = m3 - 1;
        int sum2 = 0;
        int sum3 = 0;
        //从m1+1和m3-1往中间找m2
        while (p < q) {
            while (sum > sum2)
                sum2 += arr[p++];
            while (sum > sum3)
                sum3 += arr[q--];
            if (p == q && sum2 == sum3) {
                return p;
            }else {
                return 0;
            }
        }
        return 0;
    }
    public static void main(String[] args){
        int[] A={2,5,1,5,8,4,1,7,3,1,7};
        boolean result = arrayQuarter(A);
        System.out.print(result);
    }
}