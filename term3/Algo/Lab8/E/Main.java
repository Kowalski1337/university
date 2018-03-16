import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {
    private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    private static ArrayList<Integer> ans, level;
    private static ArrayList<ArrayList<Integer>> graph;
    private static int centre, n;

    private static void build(int v, int size, int h, int parent) {
        centre = -1;
        dfs(v, size, -1);
        int cur = centre;
        level.set(cur, h);
        ans.set(cur, parent);
        for (int i = 0; i < graph.get(cur).size(); i++) {
            int next = graph.get(cur).get(i);
            if (level.get(next) == -1) {
                build(next, size / 2, h + 1, cur);
            }
        }
    }

    private static int dfs(int v, int size, int parent) {
        int sum = 1;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int next = graph.get(v).get(i);
            if (level.get(next) == -1 && next != parent) {
                sum += dfs(next, size, v);
            }
        }
        if (centre == -1 && (2 * sum >= size || parent == -1)) {
            centre = v;
        }
        return sum;
    }

    private static int nextInt() throws Exception {
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

    private static void read() throws Exception {
        n = nextInt();
        graph = new ArrayList<>();
        level = new ArrayList<>();
        ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            level.add(-1);
            ans.add(0);
        }
        for (int i = 0; i < n - 1; i++) {
            int f = nextInt() - 1;
            int s = nextInt() - 1;
            graph.get(f).add(s);
            graph.get(s).add(f);
        }
    }

    private static void solve() throws Exception{
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        build(0, n, 0, -1);

        for (int i = 0; i < n; i++){
            out.write(Integer.toString(ans.get(i) + 1) + " ");
        }
        out.close();
    }

    public static void main(String[] args) throws Exception{
        read();
        solve();
    }
}
