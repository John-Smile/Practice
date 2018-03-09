package practice.hackerrank.javatrack;

public class FourArr {

    /**
     * 整体思路是 左右分别开始累加求和 当左边小时左边下标右移，右边小时右边下标左移
     * 值到左右相等时，取中间部分在对其做一次如上的左右求和对比过程看能否找到中间的平衡点
     * 记得要效验 平衡点的和与之前的和是否相等
     * @return
     */

    private static Boolean resolve(int[] a) {
        if (a == null || a.length < 7)
            return false;
        boolean result = false;
        int i = 0;
        int j = a.length - 1;
        int suml = a[i];
        int sumr = a[j];
        while (i < j) {
            if (suml < sumr) {
                i++;
                suml += a[i];
            } else if (suml > sumr) {
                j--;
                sumr += a[j];
            } else if (suml == sumr) {
                result = checkMiddle(i + 1, j - 1, suml, a);
                if (result) {
                    return result;
                } else {
                    i++;
                    suml += a[i];
                }
            }
        }
        return result;
    }

    private static boolean checkMiddle(int start, int end, int sum, int[] a) {

        int i = start + 1;
        int j = end - 1;
        int suml = a[i];
        int sumr = a[j];
        while (j > i) {
            if (suml < sumr) {
                i++;
                suml += a[i];
            } else if (suml > sumr) {
                j--;
                sumr += a[j];
            } else if (suml == sumr) {
                if (j - i == 2 && suml == sum) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[] a = { 2, 5, 1, 7, 1, 7, 3, 7 }; // true
        int[] a1 = { 10, 2, 13, 1, 1, 1, 1, 1 }; // false
        int[] a2 = { 10, 1, 1, 11, 1, 11, 1, 1, 10 }; // true
        System.out.println(resolve(a2));
    }

}