package practice.hackerrank.javatrack;

public class Testpac {

    /**
     * @param args
     */
    int C = 12;
    int W[] = {2, 3, 4, 5, 6};
    int V[] = {1, 4, 3, 6, 8};
    int y[] = {-1, -1, -1, -1, -1};

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Testpac pp = new Testpac();

        System.out.println(pp.f(4, 12));
        pp.printY();


    }

    public void printY() {
        for (int i = 0; i < y.length; i++) {
            System.out.println(y[i]);
        }
    }

    public int f(int n, int C) {
        if (n == -1 || C == 0)
            return 0;
        int tmp1 = f(n - 1, C);
        if (W[n] > C) {
            y[n] = 0;
            return tmp1;
        }
        int tmp2 = V[n] + f(n - 1, C - W[n]);
        if (tmp1 > tmp2) {
            y[n] = 0;
            return tmp1;
        }
        y[n] = 1;
        return tmp2;
    }

}