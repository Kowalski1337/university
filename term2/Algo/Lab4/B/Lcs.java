import java.util.Scanner;
import java.io.*;

public class Lcs {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("lcs.in"));
        PrintWriter out = new PrintWriter("lcs.out");

        int n = in.nextInt();

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int m = in.nextInt();
        int[] b = new int[m];

        for (int i = 0; i < m; i++) {
            b[i] = in.nextInt();
        }

        int[][] prevx = new int[n + 1][m + 1];
        int[][] prevy = new int[n + 1][m + 1];

        int[][] D = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                D[i][j] = 0;
            }
        }


        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a[i - 1] == b[j - 1]) {
                    D[i][j] = D[i - 1][j - 1] + 1;
                    prevx[i][j] = i - 1;
                    prevy[i][j] = j - 1;
                } else

                {
                    if (D[i - 1][j] > D[i][j - 1]) {
                        D[i][j] = D[i - 1][j];
                        prevx[i][j] = i - 1;
                        prevy[i][j] = j;
                    } else

                    {
                        D[i][j] = D[i][j - 1];
                        prevx[i][j] = i;
                        prevy[i][j] = j - 1;
                    }
                }
            }
        }

        int[] answer = new int[D[n][m]];


        out.println(D[n][m]);

        int s = D[n][m] - 1;

        int x = D[n][m];


        while (n != 0 && m != 0) {
            if (prevx[n][m] == n - 1 && prevy[n][m] == m - 1) {
                answer[s--] = a[n - 1];
                n--;
                m--;
            } else {
                if (prevx[n][m] == n) {
                    m--;
                } else

                {
                    n--;
                }
            }
        }

        for (int i = 0; i < x; i++) {
            out.print(answer[i] + " ");
        }
        out.close();
    }

}