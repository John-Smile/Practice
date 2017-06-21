package practice.hackerrank;

import java.util.Arrays;

/**
 * Created by Hulk on 2017/6/21.
 */
public class FourSplit {
    /**
     * 注意：
     * 1、只删除3个元素，等分为四份。
     * 2、数组元素为正整数。
     * 3、疑问：后面又说整数取值范围介于-1,000,000到1,000,000之间？明显混淆视听，看来阿里的题考查阅读与观察啊！
     * 方法一：
     * 1、先二等分，去除中间那个元素。至少中间左边还是右边有待考证
     *此方法有bug
     *
     * */
    /**
     * indexBegin开始索引，indexEnd结束索引。
     * 在sumArr,与chooseRemove中均计算的是indexBegin<=i<indexEnd的元素。
     * 元素分布：0至v1-1,v1+1到v2-1,v2+1到N-1
     * */
    /**测试用例：
     * {1,1,1,1,7,1,3,4,1,2,1,5,2,2} true;把7换成10，把4换成1，原式也可等分，但却如下
     * {1,1,1,1,10,1,3,1,1,2,1,5,2,2} false;
     * {2,2,5,1,2,1,1,3,1,10,1,1,1,1} false;上面的倒序。看来与顺序无关，算法还是存在问题，此种解法存在问题。
     *
     * */
    //方法二**********************************************

    /**
     * 从两边开始找，找到之后再找中间
     * 技巧，注意到只删除3个元素，又因为要第一分组与第四分组相等.
     * 设等分值为v,第一分组n1与第二分组元素n4个数共为n,则2<=N-3-n<=2v;
     * 此方问题，能够解决方法一的问题
     */
    static boolean resolve2(int[] A) {
        int[] re = findValLocate(A);
        System.out.println("寻找完毕，开始检查: " + Arrays.toString(re));
        re[2] = checkingFind(A, re[0], re[1] + 1, re[3] - 1); //减1是由于有4部分，最后一部分至少占用1个位置。
        System.out.println("检查: " + Arrays.toString(re));
        int v3 = checkingFind(A, re[0], re[2] + 1, re[3]);//检查第四部分，的分割点是否为re[3]
        if (v3 == re[3]) {
            return true;
        }
        return false;
    }

    static int checkingFind(int[] A, int val, int begin, int end) {
        int s = 0;
        for (int i = begin; i < end; ++i) {
            s = s + A[i];
            if (s == val) {
                //返回要去除那个点。
                return i + 1;
            }
        }
        return -1;
    }

    /*返回均分值，与要去除的第一个和第三个位置*/
    static int[] findValLocate(int[] A) {
        int v1 = 0, v4 = 0;
        for (int i = 0, j = A.length - 1; i < j; ) {

            if (v1 < v4) {
                v1 = v1 + A[i];
                ++i;
            } else if (v1 > v4) {
                v4 = v4 + A[j];
                --j;
            } else {
                /*验证：2<=N-3-n<=2v*/
                int m = A.length - 3 - (i + 1 + A.length - j);
                if (m >= 2 && m <= 2 * v1) {
                    /*这里返回的是去除点的位置，i,j没有加减，
                    是因为以前的操作都让它向后移了一位了，
                    现在指的就是要去除的点*/
                    int re[] = {v1, i, 0, j};
                    return re;
                } else {
                    v1 = v1 + A[i];
                    ++i;
                }

            }
        }
        return null;
    }

    //方法二结束**********************************************
    //方法一（此方有问题）**********************************************
    static boolean resolve(int[] A) {
        int v2 = chooseRemove(A, 0, A.length - 1);
        int v1 = chooseRemove(A, 0, v2 - 1);
        int v3 = chooseRemove(A, v2 + 1, A.length - 1);
        int s1 = sumArr(A, 0, v1 - 1);
        int s2 = sumArr(A, v1 + 1, v2 - 1);
        int s3 = sumArr(A, v2 + 1, v3 - 1);
        int s4 = sumArr(A, v3 + 1, A.length - 1);

        System.out.println("去除的元素依次是：A[" + v1 + "]=" + A[v1] + " ; "
                + "A[" + v2 + "]=" + A[v2] + " ; "
                + "A[" + v3 + "]=" + A[v3] + " ; ");
        System.out.println("四个部分和是：" + "s1=" + s1 + " ; "
                + "s2=" + s2 + " ; "
                + "s3=" + s3 + " ; "
                + "s4=" + s4 + " ; ");
        if (s1 == s2 && s3 == s4 && s2 == s3) {
            return true;
        }

        return false;

    }

    static int sumArr(int[] A, int indexBegin, int indexEnd) {
        int sum = 0;
        for (int i = indexBegin; i <= indexEnd; ++i) {
            sum = sum + A[i];
        }
        return sum;
    }

    static int chooseRemove(int[] A, int indexBegin, int indexEnd) {
        int ave = sumArr(A, indexBegin, indexEnd) / 2;
        int val = 0;
        for (int i = indexBegin; i <= indexEnd; ++i) {
            val = val + A[i];
            if (val > ave) {
                return i;
            }
        }
        return -1;
    }
    //方法一结束**********************************************

    public static void main(String[] args) {
        /*ArrayList<Integer> inputs = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        String line = in.nextLine();
        while(line != null && !line.isEmpty()) {
            int value = Integer.parseInt(line.trim());
            if(value == 0) break;
            inputs.add(value);
            line = in.nextLine();
        }
        int[] A = new int[inputs.size()];
        for(int i=0; i<inputs.size(); i++) {
            A[i] = inputs.get(i).intValue();
        }*/
        //int[] A={1,1,1,1,7,1,3,4,1,2,1,5,2,2};
        int[] A = {1, 1, 1, 1, 10, 1, 3, 1, 1, 2, 1, 5, 2, 2};
        Boolean res = resolve2(A);

        System.out.println(String.valueOf(res));
    }
}
