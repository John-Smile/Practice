package practice.hackerrank.javatrack;

/**
 * Created by Hulk on 2017/6/22.
 */
public class FourArr3 {
    public static boolean attemp(int[] A,int m1,int m3,int target) {
        int left_A_sum = A[m1+1];
        int right_A_sum = A[m3-1];
        int low = m1;
        int high =m3;
        for(low+=1,high-=1; low<high; ){
            if(low+1 == high) break;//不可划分
            if(left_A_sum == target && left_A_sum == right_A_sum && low+1 == high-1)//正确划分
                return true;
            if(left_A_sum == target && left_A_sum == right_A_sum && low+1 != high-1)//错误划分
                return false;
            if(left_A_sum > target) break;//超出判断范围
            if(left_A_sum < target){//防止 left_A_sum == right_A_sum 死循环
                left_A_sum += A[low+1];
                low++;
            }
            if(left_A_sum < right_A_sum){//正常判断
                left_A_sum += A[low+1];
                low++;
            }else if(right_A_sum < left_A_sum){
                right_A_sum += A[high-1];
                high--;
            }
        }
        return false;
    }
    static boolean resolve(int[] A) {
        //扣掉三个值:m1,m2,m3得到四等分，所以首先两头的必是从边界开始累加的
        boolean result = false;
        int A_length = A.length;
        int left_A_sum = A[0];
        int right_A_sum = A[A_length - 1];
        int low = 0, high= A_length-1;
        int m1=-1,m2=-1,m3=-1;
        while(low<high){
            if(left_A_sum == right_A_sum){
                //进行一次划分尝试
                if(low+1 == high-1)//不可划分表示不可四等分
                    return result;
                else if(attemp(A,low+1,high-1,left_A_sum)){//划分成功
                    System.out.println("m1:"+(low+1)+"---"+"m3:"+(high-1)+"划分正确");
                    return true;
                }else{
                    //划分失败，则用m1标记失败处，往后查找可行划分元
                    m1 = low + 1;
                    m3 = high - 1;
                    left_A_sum += A[low + 1];
                    right_A_sum += A[high - 1];
                    low++;
                    high--;
                    System.out.println("m1:"+m1+"---"+"m3:"+m3+"划分错误");
                    continue;
                }
            }

            if(left_A_sum < right_A_sum){
                left_A_sum += A[low+1];
                low++;
            }else if(right_A_sum < left_A_sum){
                right_A_sum += A[high-1];
                high--;
            }
        }
        return result;
    }
}
