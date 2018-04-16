import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static ArrayList<Boolean> visited;
    private static ArrayList<Boolean> ost;
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> g;
    private static ArrayList<Edge> edges = new ArrayList<>();
    private static List<Integer> delEdges;


    static class Edge {
        int id, backId;
        int flow, capacity;

        Edge(int capacity, int flow, int id, int backId) {
            this.id = id;
            this.backId = backId;
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
            if (!ost.get(temp.getKey()) && edges.get(temp.getValue()).getCapacity() == edges.get(temp.getValue()).getFlow()) {
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


    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("cut.in"));
             PrintWriter out = new PrintWriter("cut.out")) {
            int n = in.nextInt();

            g = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                g.add(new ArrayList<>());
            }

            int m = in.nextInt();

            for (int i = 0; i < m; i++) {
                int f = in.nextInt() - 1;
                int s = in.nextInt() - 1;
                int capacity = in.nextInt();
                edges.add(new Edge(capacity, 0, 2 * i, 2 * i + 1));
                edges.add(new Edge(capacity, 0, 2 * i + 1, 2 * i));
                g.get(f).add(new Pair<>(s, 2 * i));
                g.get(s).add(new Pair<>(f, 2 * i + 1));
            }

            visited = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                visited.add(false);
            }

            int ans = 0;
            while (true) {
                clear();
                int d = dfs(n, 0, Integer.MAX_VALUE);
                ans += d;
                if (d == 0) {
                    break;
                }
            }

            ost = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                ost.add(false);
            }

            delEdges = new ArrayList<>();

            dfsOst(0);


            clear();

            dfs(0);

            delEdges = delEdges.stream().map(a -> a / 2 + 1).collect(Collectors.toList());

            Collections.sort(delEdges);


            out.println(delEdges.size() + " " + ans);

            delEdges.forEach(out::println);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
