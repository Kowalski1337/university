import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Boolean> visited;
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> g;
    private static ArrayList<Edge> edges = new ArrayList<>();


    static class Edge {
        int backId;
        int flow, capacity;
        int first, second;

        Edge(int capacity, int flow, int backId, int first, int second) {
            this.backId = backId;
            this.capacity = capacity;
            this.flow = flow;
            this.first = first;
            this.second = second;
        }

        int getFirst() {
            return first;
        }

        int getSecond() {
            return second;
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
        try (Scanner in = new Scanner(new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(System.out)) {
            int n = in.nextInt();
            int m = in.nextInt();

            g = new ArrayList<>();
            for (int i = 0; i < n + m + 2; i++) {
                g.add(new ArrayList<>());
            }

            int temp = 0;
            for (int i = 1; i <= n; i++) {
                int cur = in.nextInt();
                while (cur > 0) {
                    cur += n;
                    edges.add(new Edge(1, 0, temp + 1, i, cur));
                    edges.add(new Edge(0, 0, temp, cur, i));
                    g.get(i).add(new Pair<>(cur, temp));
                    g.get(cur).add(new Pair<>(i, temp + 1));
                    temp += 2;
                    cur = in.nextInt();
                }
            }

            for (int i = 1; i <= n; i++) {
                edges.add(new Edge(1, 0, temp + 1, 0, i));
                edges.add(new Edge(0, 0, temp, i, 0));
                g.get(0).add(new Pair<>(i, temp));
                g.get(i).add(new Pair<>(0, temp + 1));
                temp += 2;
            }

            for (int i = n + 1; i <= m + n; i++) {
                edges.add(new Edge(1, 0, temp + 1, i, n + m + 1));
                edges.add(new Edge(0, 0, temp, n + m + 1, i));
                g.get(i).add(new Pair<>(n + m + 1, temp));
                g.get(n + m + 1).add(new Pair<>(i, temp + 1));
                temp += 2;
            }

            visited = new ArrayList<>();
            for (int i = 0; i < n + m + 2; i++) {
                visited.add(false);
            }

            int ans = 0;
            while (true) {
                clear();
                int d = dfs(n + m + 2, 0, Integer.MAX_VALUE);
                ans += d;
                if (d == 0) {
                    break;
                }
            }

            out.println(ans);

            edges.forEach(edge -> {
                if (edge.getFlow() == 1 && edge.getSecond() != n + m + 1 && edge.getFirst() != 0) {
                    out.println(edge.getFirst() + " " + (edge.getSecond() - n));
                }
            });
        }
    }
}
