package practice.hackerrank.javatrack;

public class Fourarr2 {

    static boolean resolve(int[] a) {

        if (a == null || a.length < 7)
            return false;

        // 定义两个个index
        int i = 0;
        int j = a.length - 1;
        int mid = 0;
        // 自定义三个变量, suml 和 sumr分别表示两边的和, suma表示最后的结果和
        int suma = 0, sumb = 0, suml = 0, sumr = 0;
        // 初值
        suml += a[i];
        sumr += a[j];
        while (i < j) {
            // 左边i自加
            if (suml < sumr) {
                i++;
                suml += a[i];
            } else if (suml > sumr) {// 右边j自减
                j--;
                sumr += a[j];
            } else {// suml==sumr
                mid = Math.max(mid, i + 2);
                suma = 0;
                sumb = 0;
                while (mid < j - 3) {
                    suma += a[mid];
                        if (suma == suml) {
                            mid += 2;
                            while (mid < j - 1) {
                                sumb += a[mid];
                                if (sumb == suma && mid + 2 == j)
                                    return true;
                                else if (sumb < suml)
                                    mid++;
                                else
                                    break;
                            }
                            break;
                    } else if (suma < suml)
                        mid++;
                    else {
                        i++;
                        suml += a[i];
                        break;
                    }
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        // int[] a = { 1, 1, 1, 1, 5, 4, 6, 4, 5, 1, 3 };
        // int[] a = { 1, 1, 1, 4, 4, 6, 4, 1, 3 };
        // int[] a = { 2, 5, 1, 1, 1, 1, 4, 1, 7, 3, 7 }; // true
        int[] a = { 2, 5, 1, 7, 1, 7, 3, 7 }; // true
        int[] a1 = { 10, 2, 13, 1, 1, 1, 1, 1 }; // false
        int[] a2 = { 10, 1, 1, 11, 1, 11, 1, 1, 10 }; // true

        System.out.println(resolve(a));
    }
}