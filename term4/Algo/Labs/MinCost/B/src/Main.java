import javafx.util.Pair;

import java.io.*;
import java.util.Scanner;

public class Main {

    private static Pair<Integer, int[]> get(int[][] c, int n, int m) {
        int[] u = new int[n + 1];
        int[] v = new int[m + 1];
        int[] p = new int[m + 1];
        int[] w = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            p[0] = i;
            boolean[] visited = new boolean[m + 1];
            int[] min = new int[m + 1];
            for (int j = 0; j <= m; j++) {
                min[j] = 1000001;
            }
            int j0 = 0;
            do {
                visited[j0] = true;
                int i0 = p[j0], delta = 1000001, j1 = 0;
                for (int j = 1; j <= m; j++) {
                    if (!visited[j]) {
                        int temp = c[i0][j] - u[i0] - v[j];
                        if (temp < min[j]) {
                            min[j] = temp;
                            w[j] = j0;
                        }
                        if (min[j] < delta) {
                            delta = min[j];
                            j1 = j;
                        }
                    }
                }
                for (int j = 0; j <= m; j++) {
                    if (!visited[j]) {
                        min[j] -= delta;
                    } else {
                        u[p[j]] += delta;
                        v[j] -= delta;
                    }
                }
                j0 = j1;
            } while (p[j0] != 0);


            do {
                int j1 = w[j0];
                p[j0] = p[j1];
                j0 = j1;
            } while (j0 != 0);
        }

        int[] ans = new int[n + 1];

        for (int i = 1; i <= m; i++) {
            ans[p[i]] = i;
        }
        return new Pair<>(-v[0], ans);
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(new FileReader("assignment.in"));
             PrintWriter out = new PrintWriter(new FileWriter("assignment.out"))) {
            int n = in.nextInt();
            int[][] c = new int[n + 1][n + 1];
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    c[i][j] = in.nextInt();
                }
            }

            Pair<Integer, int[]> ans = get(c, n, n);

            out.println(ans.getKey());
            for (int i = 1; i <= n; i++) {
                out.println(i + " " + ans.getValue()[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
