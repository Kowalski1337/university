import java.util.Scanner;
import java.io.*;

public class Palindrome {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("palindrome.in"));
        PrintWriter out = new PrintWriter("palindrome.out");

        String a = in.nextLine();

        int n = a.length();
        char[] b = new char[n];

        for (int i = 0; i < n; i++) {
            b[i] = a.charAt(n - i - 1);
        }

        int[][] prevx = new int[n + 1][n + 1];
        int[][] prevy = new int[n + 1][n + 1];

        int[][] D = new int[n + 1][n + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                D[i][j] = 0;
            }
        }


        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b[j - 1]) {
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

        char[] answer = new char[D[n][n]];


        out.println(D[n][n]);

        int s = D[n][n] - 1;

        int x = D[n][n];


        int m = n;

        while (n != 0 && m != 0) {
            if (prevx[n][m] == n - 1 && prevy[n][m] == m - 1) {
                answer[s--] = a.charAt(n - 1);
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
            out.print(answer[i]);
        }
        out.close();
    }

}