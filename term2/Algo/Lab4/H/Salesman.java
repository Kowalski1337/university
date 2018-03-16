import java.util.Scanner;
import java.io.*;

public class Salesman {

    public static long m(long a, long b) {
        if (a > b) return b;
        return a;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("salesman.in"));
        PrintWriter out = new PrintWriter("salesman.out");

        int n = in.nextInt();

        long[][] d = new long[n][1 << n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < (1 << n); j++) {
                d[i][j] = Long.MAX_VALUE >> 1;
            }
        }

        for (int i = 0; i < n; i++) {
            d[i][(1 << i)] = 0;
        }

        long[][] graph = new long[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = Long.MAX_VALUE >> 1;
            }
        }

        int m = in.nextInt();

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            long value = in.nextLong();

            graph[a - 1][b - 1] = value;
            graph[b - 1][a - 1] = value;
        }

        for (int j = 1; j < (1 << n); j++) {
            for (int i = 0; i < n; i++) {
                for (int k = 0; k < n; k++) {
                    if (((1 << k) & j) == 0) {
                        d[k][j ^ (1 << k)] = m(d[k][j ^ (1 << k)], d[i][j] + graph[i][k]);
                    }
                }
            }
        }

        long dist = Long.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            dist = m(dist, d[i][(1 << n) - 1]);
        }

        if (dist < Long.MAX_VALUE >> 2) {
            out.println(dist);
        } else {
            out.println("-1");
        }
        out.close();
    }
}


