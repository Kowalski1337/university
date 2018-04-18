import javafx.util.Pair;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Main {

    private static ArrayList<Boolean> visited;
    private static ArrayList<ArrayList<Pair<Integer, Integer>>> g;
    private static ArrayList<Edge> edges = new ArrayList<>();


    static class Edge {
        int backId;
        int flow, capacity;
        double weight;

        Edge(int capacity, int flow, int backId, double weight) {
            this.backId = backId;
            this.capacity = capacity;
            this.flow = flow;
            this.weight = weight;
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

        double getWeight() {
            return weight;
        }

        void setFlow(int flow) {
            this.flow = flow;
        }

    }

    private static int dfs(int n, int v, int C, double maxWeight) {
        if (v == n - 1) {
            return C;
        }
        visited.set(v, true);
        for (Pair<Integer, Integer> temp : g.get(v)) {
            if (edges.get(temp.getValue()).getWeight() > maxWeight) {
                continue;
            }
            int vertex = temp.getKey();
            int id = temp.getValue();
            if (!visited.get(vertex) && edges.get(id).getCapacity() > edges.get(id).getFlow()) {
                int d = dfs(n, vertex, Math.min(C, edges.get(id).getCapacity() - edges.get(id).getFlow()), maxWeight);
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

    private static double getWeitgh(double x1, double y1, double x2, double y2, double speed) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) / speed;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in);
             PrintWriter out = new PrintWriter(System.out)) {
            int n = in.nextInt();

            ArrayList<Pair<Pair<Integer, Integer>, Integer>> things = new ArrayList<>();
            ArrayList<Pair<Integer, Integer>> places = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                things.add(new Pair<>(new Pair<>(in.nextInt(), in.nextInt()), in.nextInt()));
            }

            for (int i = 0; i < n; i++) {
                places.add(new Pair<>(in.nextInt(), in.nextInt()));
            }

            g = new ArrayList<>();

            for (int i = 0; i < 2 * n + 2; i++) {
                g.add(new ArrayList<>());
            }

            int temp = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= n; j++) {
                    double weigth = getWeitgh(things.get(i - 1).getKey().getKey(), things.get(i - 1).getKey().getValue(),
                            places.get(j - 1).getKey(), places.get(j - 1).getValue(), things.get(i - 1).getValue());
                    edges.add(new Edge(1, 0, temp + 1, weigth));
                    edges.add(new Edge(0, 0, temp, weigth));
                    g.get(i).add(new Pair<>(j + n, temp));
                    g.get(j + n).add(new Pair<>(i, temp + 1));
                    temp += 2;
                }
            }

            for (int i = 1; i <= n; i++) {
                edges.add(new Edge(1, 0, temp + 1, 0));
                edges.add(new Edge(0, 0, temp, 0));
                g.get(0).add(new Pair<>(i, temp));
                g.get(i).add(new Pair<>(0, temp + 1));
                temp += 2;
            }

            for (int i = n + 1; i <= 2 * n; i++) {
                edges.add(new Edge(1, 0, temp + 1, 0));
                edges.add(new Edge(0, 0, temp, 0));
                g.get(i).add(new Pair<>(2 * n + 1, temp));
                g.get(2 * n + 1).add(new Pair<>(i, temp));
                temp += 2;
            }

            visited = new ArrayList<>();
            for (int i = 0; i < 2 * n + 2; i++) {
                visited.add(false);
            }


            int l = -1;
            int r = edges.size() / 2 - 2 * n;

            ArrayList<Edge> trueEdges = new ArrayList<>();
            for (int i = 0; i < r; i++) {
                trueEdges.add(edges.get(2 * i));
            }

            trueEdges.sort(Comparator.comparing(Edge::getWeight));

            while (r > l + 1) {
                int m = l + (r - l) / 2;
                int ans = 0;
                for (Edge edge : edges){
                    edge.setFlow(0);
                }
                while (true) {
                    clear();
                    int d = dfs(2 * n + 2, 0, Integer.MAX_VALUE, trueEdges.get(m).getWeight());
                    ans += d;
                    if (d == 0) {
                        break;
                    }
                }
                if (ans < n) {
                    l = m;
                } else {
                    r = m;
                }
            }

            out.print(trueEdges.get(r).getWeight());
        }
    }
}
