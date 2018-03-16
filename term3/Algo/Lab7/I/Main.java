import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    private static ArrayList<ArrayList<Pair<Integer, Long>>> graph;
    private static ArrayList<Boolean> used;
    private static int[] depth, parent;
    private static int[][] dp;
    private static long[] distance;
    private static final int logN = 19;

    private static int nextInt(BufferedReader in) throws Exception {
        int ans = 0;
        int cur = in.read();
        while (cur < '0' || cur > '9') {
            cur = in.read();
        }
        while (cur >= '0' && cur <= '9') {
            ans = 10 * ans + cur - '0';
            cur = in.read();
        }
        return ans;
    }

    private static long nextLong(BufferedReader in) throws Exception {
        long ans = 0;
        int cur = in.read();
        while (cur < '0' || cur > '9') {
            cur = in.read();
        }
        while (cur >= '0' && cur <= '9') {
            ans = 10 * ans + cur - '0';
            cur = in.read();
        }
        return ans;
    }

    private static void dfs(int v, int curDepth) {
        used.set(v, true);
        depth[v] = curDepth;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int next = graph.get(v).get(i).getKey();
            if (!used.get(next)) {
                parent[next] = v;
                distance[next] = distance[v] + graph.get(v).get(i).getValue();
                dfs(next, curDepth + 1);
            }
        }
    }

    private static int lca(int a, int b) {
        if (depth[a] > depth[b]) {
            return lca(b, a);
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (depth[dp[b][i]] >= depth[a]) {
                b = dp[b][i];
            }
        }
        if (a == b) {
            return a;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (dp[a][i] != dp[b][i]) {
                b = dp[b][i];
                a = dp[a][i];
            }
        }
        return dp[a][0];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("tree.in"));
        PrintWriter out = new PrintWriter("tree.out");

        int n = nextInt(in);
        graph = new ArrayList<>();
        distance = new long[n];
        parent = new int[n];
        depth = new int[n];
        used = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            used.add(false);
        }

        for (int i = 0; i < n- 1; i++) {
            int a = nextInt(in);
            int b = nextInt(in);
            long w = nextLong(in);

            graph.get(a).add(new Pair<>(b, w));
            graph.get(b).add(new Pair<>(a, w));
        }

        dfs(0, 0);

        dp = new int[n][logN];
        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }

        for (int i = 1; i < logN; i++) {
            for (int j = 0; j < n; j++) {
                dp[j][i] = dp[dp[j][i - 1]][i - 1];
            }
        }

        int m = nextInt(in);
        for (int i = 0; i < m; i++) {
            int a = nextInt(in);
            int b = nextInt(in);
            out.println(distance[a] + distance[b] - 2 * distance[lca(a, b)]);
        }
        out.close();
    }
}
