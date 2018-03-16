import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.util.Pair;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.lang.invoke.VolatileCallSite;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;

public class Main {

    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<Boolean> used;
    private static ArrayList<Integer> order;
    private static int[] parent, depth;
    private static final int logN = 19;
    private static int[][] dp;

    private static int nextInt(BufferedReader in) throws Exception {
        int ans = 0;
        int cur = in.read();
        while (cur < '0' || cur > '9') {
            cur = in.read();
        }
        while (cur <= '9' && cur >= '0') {
            ans = 10 * ans + cur - '0';
            cur = in.read();
        }
        return ans;
    }

    private static void calcDepth(int v) {
        if (depth[v] != 0 || parent[v] == -1) {
            return;
        }
        calcDepth(parent[v]);
        depth[v] = depth[parent[v]] + 1;
    }

    private static void dfs(int v) {
        used.set(v, true);
        order.add(v);
        for (int i = 0; i < graph.get(v).size(); i++) {
            int next = graph.get(v).get(i);
            if (!used.get(next)) {
                dfs(next);
            }
        }
    }

    private static int lca(int v, int u) {
        if (depth[v] > depth[u]) {
            return lca(u, v);
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (depth[dp[u][i]] >= depth[v]) {
                u = dp[u][i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (dp[u][i] != dp[v][i]) {
                u = dp[u][i];
                v = dp[v][i];
            }
        }
        return parent[u];
    }

    private static int next(int n, int ans, BufferedReader in) throws Exception {
        return (nextInt(in) - 1 + ans) % n;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("lca3.in"));
        PrintWriter out = new PrintWriter("lca3.out");

        int n = nextInt(in);
        parent = new int[n];
        depth = new int[n];
        graph = new ArrayList<>();
        used = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            used.add(false);
        }

        for (int i = 0; i < n; i++) {
            int next = nextInt(in);
            parent[i] = next == 0 ? -1 : next - 1;
            if (next != 0) {
                graph.get(i).add(next - 1);
                graph.get(next - 1).add(i);
            }
        }

        for (int i = 0; i < n; i++) {
            calcDepth(i);
        }

        ArrayList<ArrayList<Integer>> forest = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            forest.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            if (parent[i] == -1) {
                order = new ArrayList<>();
                dfs(i);
                forest.set(i, order);
            }
        }

        dp = new int[n][logN];

        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i] == -1 ? i : parent[i];
        }

        for (int i = 1; i < logN; i++) {
            for (int j = 0; j < n; j++) {
                dp[j][i] = dp[dp[j][i - 1]][i - 1];
            }
        }

        int ans = 0;

        int m = nextInt(in);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++){
                System.out.println(parent[j] + " ");
            }
            System.out.println();
            if (nextInt(in) == 1) {
                int u = next(n, ans, in);
                int v = next(n, ans, in);
                parent[u] = v;
                dp[u][0] = v;
                for (int j = 1; j < logN; j++) {
                    for (int k = 0; k < forest.get(u).size(); k++) {
                        dp[forest.get(u).get(k)][j] = dp[dp[forest.get(u).get(k)][j - 1]][j - 1];
                    }
                }
                int dDepth = depth[v] + 1;
                for (int k = 0; k < forest.get(u).size(); k++) {
                    depth[forest.get(u).get(k)] += dDepth;
                }
                int root = dp[v][logN - 1];
                for (int j = 0; j < forest.get(u).size(); j++){
                    forest.get(root).add(forest.get(u).get(j));
                }
            } else {
                int u = next(n, ans, in);
                int v = next(n, ans, in);
                ans = lca(u, v) + 1;
                out.println(ans);
            }

        }
        out.close();
    }
}
