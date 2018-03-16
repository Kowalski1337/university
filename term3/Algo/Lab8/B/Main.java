import java.io.*;
        import java.util.ArrayList;

public class Main {
    private static int[] height, parent, pathNum, pathPos, rang, depth;
    private static boolean[] used;
    private static ArrayList<ArrayList<Integer>> g;
    private static ArrayList<ArrayList<Integer>> paths, pathsVertex;
    private static final int N = 50001, logN = 20;
    private static int n;
    private static int[][] dp;
    private static ArrayList<ArrayList<Integer>> segmentTree;
    private static int[] logs, pows;
    private static int[] pathHead;
    private static BufferedReader in;
    private static BufferedWriter out;

    private static void calcPows() {
        pows = new int[logN];
        pows[0] = 1;
        for (int i = 1; i < logN; i++) {
            pows[i] = pows[i - 1] * 2;
        }
    }

    private static void calcLogs() {
        logs = new int[N];
        logs[0] = 0;
        logs[1] = 0;
        for (int i = 2; i < N; i++) {
            logs[i] = logs[i / 2] + 1;
        }
    }

    private static void dfsHLD(int node, int pathNumber) {
        used[node] = true;
        paths.get(pathNumber).add(node);
        for (int i = 0; i < g.get(node).size(); i++) {
            int V = g.get(node).get(i);
            if (!used[V]) {
                if (2 * rang[V] >= rang[node]) {
                    dfsHLD(V, pathNumber);
                } else {
                    paths.add(new ArrayList<>());
                    dfsHLD(V, paths.size() - 1);
                }
            }
        }
    }

    private static void prepareHLD() {
        paths = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            used[i] = false;
        }
        paths.add(new ArrayList<>());
        dfsHLD(0, 0);
        prepareSegmentTree();
    }

    private static int getHLD(int a, int b, int lca) {
        return Math.max(getHLD(a, lca), getHLD(b, lca));
    }

    private static int getHLD(int a, int b) {
        int cur = a;
        int result = 0;
        while (true) {
            if (pathNum[b] == pathNum[cur]) {
                return Math.max(result,
                        getMax(pathPos[cur], pathPos[b], pathNum[b]));
            }
            result = Math.max(result, getMax(pathPos[cur], pathPos[pathHead[pathNum[cur]]], pathNum[cur]));
            cur = parent[pathHead[pathNum[cur]]];
        }
    }


    private static void prepareSegmentTree() {
        calcPows();
        calcLogs();
        segmentTree = new ArrayList<>();
        for (int i = 0; i < paths.size(); i++) {
            segmentTree.add(new ArrayList<>());
        }
        pathHead = new int[paths.size()];
        for (int i = 0; i < paths.size(); i++) {
            ArrayList<Integer> path = paths.get(i);
            pathHead[i] = path.get(0);
            int count = paths.get(i).size();
            for (int j = count - 1; j >= 0; j--) {
                pathNum[path.get(j)] = i;
                pathPos[path.get(j)] = count - j - 1;
            }
            int size = pows[logs[count] + 1];
            if (size == 2*count){
                size /= 2;
            }
            for (int j = 0; j < 2 * size; j++) {
                segmentTree.get(i).add(0);
            }
            for (int j = size; j < size + count; j++) {
                segmentTree.get(i).set(j, height[path.get(count - 1 - j + size)]);
            }
            for (int j = size - 1; j >= 1; j--) {
                segmentTree.get(i).set(j, Math.max(segmentTree.get(i).get(2 * j),
                        segmentTree.get(i).get(2 * j + 1)));
            }
        }
    }

    private static void setHeight(int key, int value, int segmentTreeNum) {
        setHeight(1, key, value, 0, segmentTree.get(segmentTreeNum).size() / 2 - 1, segmentTreeNum);
    }

    private static void setHeight(int node, int key, int value, int l, int r, int segmentTreeNum) {
        if (l == r) {
            segmentTree.get(segmentTreeNum).set(node, value);
        } else {
            if (l <= key && key <= r) {
                int m = (l + r) / 2;
                if (key <= m) {
                    setHeight(2 * node, key, value, l, m, segmentTreeNum);
                } else {
                    setHeight(2 * node + 1, key, value, m + 1, r, segmentTreeNum);
                }
                segmentTree.get(segmentTreeNum).set(node, Math.max(segmentTree.get(segmentTreeNum).get(2 * node),
                        segmentTree.get(segmentTreeNum).get(2 * node + 1)));
            }
        }
    }

    private static int getMax(int l, int r, int segmentTreeNum) {
        return getMax(1, l, r, 0, segmentTree.get(segmentTreeNum).size() / 2 - 1, segmentTreeNum);
    }

    private static int getMax(int node, int l, int r, int L, int R, int segmentTreeNum) {
        if (l <= L && R <= r) {
            return segmentTree.get(segmentTreeNum).get(node);
        }
        if (l <= R && r >= L) {
            int m = (L + R) / 2;
            return Math.max(getMax(2 * node, l, r, L, m, segmentTreeNum),
                    getMax(2 * node + 1, l, r, m + 1, R, segmentTreeNum));
        }
        return 0;
    }


    private static void dfsLCA(int node, int h) {
        used[node] = true;
        depth[node] = h;
        rang[node] = 1;
        for (int i = 0; i < g.get(node).size(); i++) {
            int next = g.get(node).get(i);
            if (!used[next]) {
                parent[next] = node;
                dfsLCA(next, h + 1);
                rang[node] += rang[next];
            }
        }
    }

    private static void prepareLca() {
        parent[0] = 0;
        dfsLCA(0, 0);
        dp = new int[n][logN];
        for (int i = 0; i < n; i++) {
            dp[i][0] = parent[i];
        }
        for (int i = 1; i < logN; i++) {
            for (int j = 0; j < n; j++) {
                dp[j][i] = dp[dp[j][i - 1]][i - 1];
            }
        }
    }

    private static int getLCA(int a, int b) {
        if (depth[a] < depth[b]) {
            return getLCA(b, a);
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (depth[dp[a][i]] >= depth[b]) {
                a = dp[a][i];
            }
        }
        if (a == b) {
            return a;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (dp[a][i] != dp[b][i]) {
                a = dp[a][i];
                b = dp[b][i];
            }
        }
        return dp[a][0];
    }

    private static int nextInt() throws IOException {
        int ans = 0;
        int cur = in.read();
        boolean isNegate = false;
        while ((cur < '0' || cur > '9') && cur != '-') {
            cur = in.read();
        }
        if (cur == '-') {
            isNegate = true;
            cur = in.read();
        }
        while (cur >= '0' && cur <= '9') {
            ans = 10 * ans + cur - '0';
            cur = in.read();
        }
        return isNegate ? -ans : ans;
    }

    private static boolean nextAction() throws IOException {
        int cur = in.read();
        while (cur != '!' && cur != '?') {
            cur = in.read();
        }
        return cur == '!';
    }


    private static void prepareYourAnus() throws IOException {
        in = new BufferedReader(new FileReader("mail.in"));
        out = new BufferedWriter(new FileWriter("mail.out"));
        n = nextInt();
        height = new int[n];
        parent = new int[n];
        pathPos = new int[n];
        pathNum = new int[n];
        depth = new int[n];
        rang = new int[n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            height[i] = nextInt();
            used[i] = false;
        }
        g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            int a = nextInt() - 1;
            int b = nextInt() - 1;
            g.get(a).add(b);
            g.get(b).add(a);
        }
        prepareLca();
        prepareHLD();
    }

    private static void solve() throws IOException {
        int m = nextInt();
        for (int i = 0; i < m; i++) {
            if (nextAction()) {
                int index = nextInt() - 1;
                int height = nextInt();
                setHeight(pathPos[index], height, pathNum[index]);
            } else {
                int f = nextInt() - 1;
                int s = nextInt() - 1;
                out.write(Integer.toString(getHLD(f, s, getLCA(f, s))) + '\n');
            }
        }
        out.close();

    }

    public static void main(String[] args) throws IOException {
        prepareYourAnus();
        solve();
    }
}