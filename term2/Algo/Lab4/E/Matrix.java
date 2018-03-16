import java.util.Scanner;
import java.io.*;


public class Matrix {

    public static int[][] index = new int[1000][1000];

    public static String  open = "(";

    public static String  close = ")";

    public static String answer(int l, int r) {
        if (r - l == 1) {
            return "A";
        } else {
            if (r - l == 2) {
                return "(AA)";
            } else {
                String cur = open.concat(answer(l, index[l][r])).concat(answer(index[l][r], r)).concat(close);
                return cur;
            }
        }
    }

    public static int dynamic(int[][] d, int[] sizes, int l, int r) {
        if (d[l][r] == -1) {
            if (r - l == 1) {
                d[l][r] = 0;
            } else {
                d[l][r] = Integer.MAX_VALUE;
                for (int i = l + 1; i < r; i++) {
                    int k = sizes[l] * sizes[i] * sizes[r] + dynamic(d, sizes, i, r) + dynamic(d, sizes, l, i);
                    if (k < d[l][r]) {
                        d[l][r] = k;
                        index[l][r] = i;
                    }
                }
            }
        }
        return d[l][r];
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("matrix.in"));
        PrintWriter out = new PrintWriter("matrix.out");

        int n = in.nextInt();

        int[] sizes = new int[n + 1];

        sizes[0] = in.nextInt();
        sizes[1] = in.nextInt();

        for (int i = 2; i <= n; i++) {
            sizes[i] = in.nextInt();
            sizes[i] = in.nextInt();
        }


        int[][] d = new int[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                d[i][j] = -1;
            }
        }
        int k = dynamic(d, sizes, 0, n);

        String ans = answer(0, n);
        out.println(ans);
        out.close();
    }
}