import java.util.Scanner;
import java.io.*;


public class Knapsack {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("knapsack.in"));
        PrintWriter out = new PrintWriter("knapsack.out");

        int n = in.nextInt();
        int m = in.nextInt();

        int[] c = new int[n + 1];
        int[] w = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            w[i] = in.nextInt();
        }

        for (int i = 1; i <= n; i++) {
            c[i] = in.nextInt();
        }

        int[][] d = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            d[i][0] = 0;
        }

        for (int i = 0; i <= m; i++) {
            d[0][i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (j >= w[i]) {
                    d[i][j] = max(d[i - 1][j], d[i - 1][j - w[i]] + c[i]);
                } else {
                    d[i][j] = d[i - 1][j];
                }
            }
        }

        int[] answer = new int[d[n][m]];
        int s = 0;

        while (d[n][m] != 0) {
            if (d[n - 1][m] == d[n][m]) {
                n--;
            } else {
                answer[s++] = n;
                m -= w[n--];
            }
        }

        out.println(s);

        for (int i = 0; i < s; i++) {
            out.print(answer[s - i - 1] + " ");
        }

        out.close();


    }

    static int max(int a, int b) {
        if (a > b) return a;
        else return b;
    }

}
