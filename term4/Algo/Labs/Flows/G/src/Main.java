import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static ArrayList<Boolean> visited;
    private static ArrayList<Boolean> ost;
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> g;
    private static ArrayList<Edge> edges = new ArrayList<>();
    private static List<Integer> delEdges;

    static class Edge {
        int backId;
        int flow, capacity;
        int from, to;

        Edge(int capacity, int flow, int backId, int from, int to) {
            this.backId = backId;
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = flow;
        }

        int getBackId() {
            return backId;
        }

        int getCapacity() {
            return capacity;
        }

        int getFlow() {
            return flow;
        }

        void increaseFlow(int df) {
            flow += df;
        }
    }

    private static void dfsOst(int v) {
        ost.set(v, true);
        for (Pair<Integer, Integer> temp : g.get(v)) {
            if (!ost.get(temp.getKey()) && edges.get(temp.getValue()).getCapacity() > edges.get(temp.getValue()).getFlow()) {
                dfsOst(temp.getKey());
            }
        }
    }

    private static void dfs(int v) {
        visited.set(v, true);
        for (Pair<Integer, Integer> temp : g.get(v)) {
            if (edges.get(temp.getValue()).getCapacity() != 0 && !ost.get(edges.get(temp.getValue()).to) && edges.get(temp.getValue()).getCapacity() == edges.get(temp.getValue()).getFlow()) {
                delEdges.add(temp.getValue());
            }
        }

        for (Pair<Integer, Integer> temp : g.get(v)) {
            if (!visited.get(temp.getKey()) && edges.get(temp.getValue()).getCapacity() > edges.get(temp.getValue()).getFlow()) {
                dfs(temp.getKey());
            }
        }
    }

    private static int dfs(int n, int v, int C) {
        if (v == n - 1) {
            return C;
        }
        visited.set(v, true);
        for (Pair<Integer, Integer> temp : g.get(v)) {
            int vertex = temp.getKey();
            int id = temp.getValue();
            if (!visited.get(vertex) && edges.get(id).getCapacity() > edges.get(id).getFlow()) {
                int d = dfs(n, vertex, Math.min(C, edges.get(id).getCapacity() - edges.get(id).getFlow()));
                if (d > 0) {
                    edges.get(id).increaseFlow(d);
                    edges.get(edges.get(id).getBackId()).increaseFlow(-d);
                    return d;
                }
            }
        }
        return 0;
    }

    private static void clear() {
        for (int i = 0; i < visited.size(); i++) {
            visited.set(i, false);
        }
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

    private static int nextToken(BufferedReader in) throws IOException {
        int ch = in.read();
        while (Character.isWhitespace(ch)) {
            ch = in.read();
        }
        return ch;
    }

    private static ArrayList<Integer> getNeighbors(int i, int j, int n, int m) {
        ArrayList<Integer> ans = new ArrayList<>();
        Integer left = getNumber(i, j - 1, n, m);
        Integer right = getNumber(i, j + 1, n, m);
        Integer up = getNumber(i - 1, j, n, m);
        Integer down = getNumber(i + 1, j, n, m);
        if (left != null) {
            ans.add(left);
        }
        if (right != null) {
            ans.add(right);
        }
        if (up != null) {
            ans.add(up);
        }
        if (down != null) {
            ans.add(down);
        }
        return ans;
    }

    private static Integer getNumber(int i, int j, int n, int m) {
        if (i < 0 || i > n - 1 || j < 0 || j > m - 1) {
            return null;
        }
        return m * i + j;
    }

    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(System.out)) {
            int n = nextInt(in);
            int m = nextInt(in);

            int s = 0, t = 0;

            ArrayList<ArrayList<Integer>> map = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                map.add(new ArrayList<>());
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    int token = nextToken(in);
                    map.get(i).add(token);
                }
            }

            g = new ArrayList<>();
            for (int i = 0; i < n * m * 2; i++) {
                g.add(new ArrayList<>());
            }

            int temp = 0;
            //neighbours
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    ArrayList<Integer> neigh = getNeighbors(i, j, n, m);
                    Integer index = getNumber(i, j, n, m);
                    assert (index != null);
                    for (int cur : neigh) {
                        edges.add(new Edge(Integer.MAX_VALUE, 0, temp + 1, index + n * m, cur));
                        edges.add(new Edge(0, 0, temp, cur, index + n * m));
                        g.get(index + n * m).add(new Pair<>(cur, temp));
                        g.get(cur).add(new Pair<>(index + n * m, temp + 1));
                        temp += 2;
                    }
                }
            }

            //square
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    Integer index = getNumber(i, j, n, m);
                    assert (index != null);
                    int capacity;
                    switch ((char) (int) map.get(i).get(j)) {
                        case 'A': {
                            s = index + n * m;
                            capacity = Integer.MAX_VALUE;
                            break;
                        }

                        case 'B': {
                            capacity = Integer.MAX_VALUE;
                            t = index;
                            break;
                        }

                        case '#': {
                            capacity = 0;
                            break;
                        }

                        case '-': {
                            capacity = Integer.MAX_VALUE;
                            break;
                        }

                        default: {
                            capacity = 1;
                            break;
                        }
                    }
                    edges.add(new Edge(capacity, 0, temp + 1, index, index + n * m));
                    edges.add(new Edge(0, 0, temp, index + n * m, index));
                    g.get(index).add(new Pair<>(index + n * m, temp));
                    g.get(index + n * m).add(new Pair<>(index, temp + 1));
                    temp += 2;
                }
            }

            visited = new ArrayList<>();
            for (int i = 0; i < n * m * 2; i++) {
                visited.add(false);
            }

            long ans = 0;
            while (true) {
                clear();
                int d = dfs(t + 1, s, Integer.MAX_VALUE);
                ans += d;
                if (ans >= Integer.MAX_VALUE) {
                    out.println();
                    out.print(-1);
                    out.close();
                    return;
                }
                if (d == 0) {
                    break;
                }
            }

            ost = new ArrayList<>();
            for (int i = 0; i < n * m * 2; i++) {
                ost.add(false);
            }

            delEdges = new ArrayList<>();

            dfsOst(s);


            clear();

            dfs(s);

            out.println();
            //delEdges.forEach(a -> out.println(edges.get(a).from + " " + edges.get(a).to));
            out.println(delEdges.size());
            delEdges.forEach(a -> {
                int i = edges.get(a).from / m;
                int j = edges.get(a).from % m;
                map.get(i).set(j, (int) '+');
            });

            StringBuilder sb = new StringBuilder();
            map.forEach(a -> {
                a.forEach(b -> sb.append((char) (int) b));
                sb.append('\n');
            });
            out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
