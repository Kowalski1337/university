package old;
import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;

public class Main {

    private static ArrayList<ArrayList<Integer>> g, full;
    private static ArrayList<Boolean> used;
    private static int[][][] d;
    private static int[][] c;
    private static int penalty;
    private static int k;

    private static void dfs(int v) {
        used.set(v, true);
        for (int next : full.get(v)) {
            if (!used.get(next)) {
                g.get(v).add(next);
                dfs(next);
            }
        }
    }

    private static Pair<Integer, int[]> get(int[][] c) {
        int n = c.length - 1;
        int m = c[0].length - 1;
        int[] u = new int[n + 1];
        int[] v = new int[m + 1];
        int[] p = new int[m + 1];
        int[] w = new int[m + 1];

        for (int i = 1; i <= n; i++) {
            p[0] = i;
            boolean[] visited = new boolean[m + 1];
            int[] min = new int[m + 1];
            for (int j = 0; j <= m; j++) {
                min[j] = Integer.MAX_VALUE;
            }
            int j0 = 0;
            do {
                visited[j0] = true;
                int i0 = p[j0], delta = Integer.MAX_VALUE, j1 = 0;
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


    private static int calc(int vertex, int color, int parentColor) {
        if (parentColor == k && d[vertex][color][0] != -1) {
            return d[vertex][color][0];
        }

        if (parentColor != k && d[vertex][color][parentColor] != -1){
            return d[vertex][color][parentColor];
        }

        int[][] matryx = new int[g.get(vertex).size() + 1][k + 1];

        int first = penalty;
        for (int i = 0; i < g.get(vertex).size(); i++) {
            int temp = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                int cur = calc(g.get(vertex).get(i), j, color);
                temp = Math.min(temp, cur);
                matryx[i + 1][j + 1] = parentColor == j ? Integer.MAX_VALUE - 5 : cur;
            }
            first += temp;
        }

        int second = Integer.MAX_VALUE;
        if (g.get(vertex).size() < k + (parentColor == k ? 1 : 0)) {
            second = 0;
            Pair<Integer, int[]> temp = get(matryx);
            for (int i = 0; i < g.get(vertex).size(); i++) {
                second += matryx[i + 1][temp.getValue()[i + 1]];
            }
        }
        if (parentColor != k) {
            d[vertex][color][parentColor] = Math.min(first, second) + c[vertex][color];
        } else {
            for (int i = 0; i < k; i++) {
                d[vertex][color][i] = Math.min(first, second) + c[vertex][color];
            }
        }
        return Math.min(first, second) + c[vertex][color];
    }


    private static int nextInt(BufferedReader in) throws IOException {
        int ans = 0;
        int ch = in.read();
        while (ch < '0' || ch > '9') {
            ch = in.read();
        }
        while (ch >= '0' && ch <= '9') {
            ans = 10 * ans + ch - '0';
            ch = in.read();
        }
        return ans;
    }

    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out))) {
            int n = nextInt(in);
            k = nextInt(in);
            penalty = nextInt(in);
            c = new int[n][k];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < k; j++) {
                    c[i][j] = nextInt(in);
                }
            }

            g = new ArrayList<>();
            full = new ArrayList<>();
            used = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                full.add(new ArrayList<>());
                used.add(false);
                g.add(new ArrayList<>());
            }

            for (int i = 0; i < n - 1; i++) {
                int a = nextInt(in) - 1;
                int b = nextInt(in) - 1;
                full.get(a).add(b);
                full.get(b).add(a);
            }

            dfs(0);

            d = new int[n][k][k];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < k; j++) {
                    for (int l = 0; l < k; l++) {
                        d[i][j][l] = -1;
                    }
                }
            }

            /*for (int i = 0; i < n; i++) {
                System.out.print(i + ": ");
                for (int j : g.get(i)) {
                    System.out.print(j + " ");
                }
                System.out.println();
            }*/
            int ans = Integer.MAX_VALUE;

            for (int i = 0; i < k; i++) {
                ans = Math.min(ans, calc(0, i, k));
            }


            out.println(ans);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}