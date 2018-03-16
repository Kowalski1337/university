import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.security.Principal;
import java.util.ArrayList;

public class Main {
    private static final int logN = 18;
    private static ArrayList<ArrayList<Integer>> graph;
    private static int[] depth, parent;
    private static ArrayList<Boolean> used;
    private static int[][] dp;
    private static int curRoot;

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

    private static boolean nextAction(BufferedReader in) throws Exception {
        int cur = in.read();

        while (cur != '!' && cur != '?') {
            cur = in.read();
        }
        return cur == '!';
    }

    private static void dfs(int v, int curDepth) {
        used.set(v, true);
        depth[v] = curDepth;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int next = graph.get(v).get(i);
            if (!used.get(next)) {
                parent[next] = v;
                dfs(next, curDepth + 1);
            }
        }
    }

    private static int lca(int a, int b){
        if (depth[a] > depth[b]){
            return lca(b, a);
        }
        for (int i = logN - 1; i >= 0; i--){
            if (depth[dp[b][i]] >= depth[a]){
                b = dp[b][i];
            }
        }
        if (a == b){
            return a;
        }
        for (int i = logN - 1; i >= 0; i--){
            if (dp[a][i] != dp[b][i]){
                a = dp[a][i];
                b = dp[b][i];
            }
        }
        return dp[a][0];
    }

    private static boolean isAncestor(int a, int b){
        if (depth[a] > depth[b]){
            return false;
        }
        for (int i = logN - 1; i >= 0; i--){
            if (depth[dp[b][i]] >= depth[a]){
                b = dp[b][i];
            }
        }
        return a == b;
    }
    
    private static int getLca(int a, int b){
        int ans1 = lca(a, b);
//        if (curRoot == 0 || isAncestor(curRoot, ans1)){
//            return ans1;
//        }
        int ans2 = lca(curRoot, a);
        int ans3 = lca(curRoot, b);
        if (depth[ans2] > depth[ans1]){
            ans1 = ans2;
        }
        if (depth[ans3] > depth[ans1]){
            ans1 = ans3;
        }
        return ans1; //depth[ans2] > depth[ans3] ? ans2 : ans3;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("dynamic.in"));
        PrintWriter out = new PrintWriter("dynamic.out");
        
        while (true) {
            int n = nextInt(in);
            if (n == 0) {
                break;
            }
            
            curRoot = 0;

            depth = new int[n];
            parent = new int[n];
            used = new ArrayList<>();
            graph = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                graph.add(new ArrayList<>());
                used.add(false);
            }

            for (int i = 0; i < n - 1; i++) {
                int a = nextInt(in) - 1;
                int b = nextInt(in) - 1;
                graph.get(a).add(b);
                graph.get(b).add(a);
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

//            for (int i = 0; i < n; i++){
//                out.println(i + " : " + depth[i] + " " + parent[i]);
//            }
            int m = nextInt(in);
            for (int i = 0; i < m; i++){
                if (nextAction(in)){
                    curRoot = nextInt(in) - 1;
                } else {
                    out.println(getLca(nextInt(in)- 1, nextInt(in)- 1) + 1);
                }
            }
        }


        out.close();
    }
}
