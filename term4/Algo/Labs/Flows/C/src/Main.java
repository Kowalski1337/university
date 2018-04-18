import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Boolean> visited;
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> g;
    private static ArrayList<Edge> edges = new ArrayList<>();


    static class Edge {
        int backId;
        int flow, capacity;

        Edge(int capacity, int flow, int backId) {
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

    private static void dfs(PrintWriter out, int v, int n) {
        visited.set(v, true);
        out.print((v + 1) + " ");
        if (v == n - 1)
            return;
        for (Pair<Integer, Integer> temp : g.get(v)) {
            if (!visited.get(temp.getKey()) && edges.get(temp.getValue()).getFlow() == 1) {
                edges.get(temp.getValue()).increaseFlow(-1);
                dfs(out, temp.getKey(), n);
                return;
            }
        }
    }

    private static void clear() {
        for (int i = 0; i < visited.size(); i++) {
            visited.set(i, false);
        }
    }


    public static void main(String[] args) {
        try (Scanner in = new Scanner(new File("snails.in"));
             PrintWriter out = new PrintWriter("snails.out")) {
            int n = in.nextInt();

            g = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                g.add(new ArrayList<>());
            }

            int m = in.nextInt();
            int s = in.nextInt();
            int t = in.nextInt();

            for (int i = 0; i < m; i++) {
                int a = in.nextInt() - 1;
                int b = in.nextInt() - 1;
                edges.add(new Edge(1, 0, 2 * i + 1));
                edges.add(new Edge(0, 0, 2 * i));
                g.get(a).add(new Pair<>(b, 2 * i));
                g.get(b).add(new Pair<>(a, 2 * i + 1));
            }

            visited = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                visited.add(false);
            }

            int ans = 0;
            while (true) {
                clear();
                int d = dfs(t, s - 1, Integer.MAX_VALUE);
                ans += d;
                if (d == 0) {
                    break;
                }
            }

            if (ans < 2) {
                out.println("NO");
            } else {
                out.println("YES");
                clear();
                dfs(out, s - 1, t);
                out.println();
                clear();
                dfs(out, s - 1, t);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
