package New;

import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static class Edge {
        int backId;
        long flow, capacity;
        int cost;

        Edge(int capacity, int flow, int cost, int backId) {
            this.cost = cost;
            this.backId = backId;
            this.capacity = capacity;
            this.flow = flow;
        }

        int getBackId() {
            return backId;
        }

        long getCapacity() {
            return capacity;
        }

        long getFlow() {
            return flow;
        }

        void increaseFlow(long df) {
            flow += df;
        }

        int getCost() {
            return cost;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        try (Scanner in = new Scanner(new FileReader("mincost.in"));
             PrintWriter out = new PrintWriter(new FileWriter("mincost.out"))) {
            int n = in.nextInt();

            ArrayList<Edge> edges = new ArrayList<>();
            ArrayList<ArrayList<Pair<Integer, Integer>>> g = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                g.add(new ArrayList<>());
            }

            int m = in.nextInt();

            for (int i = 0; i < m; i++) {
                int f = in.nextInt() - 1;
                int s = in.nextInt() - 1;
                int capacity = in.nextInt();
                int cost = in.nextInt();
                edges.add(new Edge(capacity, 0, cost, 2 * i + 1));
                edges.add(new Edge(0, 0, -cost, 2 * i));
                g.get(f).add(new Pair<>(s, 2 * i));
                g.get(s).add(new Pair<>(f, 2 * i + 1));
            }

            long flow = 0;
            long cost = 0;
            long maxFlow = Long.MAX_VALUE;
            int s = 0;
            int t = n - 1;

            while (flow < maxFlow) {
                long[] id = new long[n], d = new long[n];
                int[] q = new int[n], p = new int[n], rib = new int[n];
                for (int i = 0; i < n; i++) {
                    id[i] = 0;
                    d[i] = Long.MAX_VALUE;
                }
                int qh = 0, qt = 0;
                q[qt++] = s;
                d[s] = 0;
                while (qh != qt) {
                    int v = q[qh++];
                    id[v] = 2;
                    if (qh == n) qh = 0;
                    for (int i = 0; i < g.get(v).size(); ++i) {
                        Edge r = edges.get(g.get(v).get(i).getValue());
                        if (r.getFlow() < r.getCapacity() && d[v] + r.getCost() < d[g.get(v).get(i).getKey()]) {
                            d[g.get(v).get(i).getKey()] = d[v] + r.getCost();
                            if (id[g.get(v).get(i).getKey()] == 0) {
                                q[qt++] = g.get(v).get(i).getKey();
                                if (qt == n) qt = 0;
                            } else if (id[g.get(v).get(i).getKey()] == 2) {
                                if (--qh == -1) qh = n - 1;
                                q[qh] = g.get(v).get(i).getKey();
                            }
                            id[g.get(v).get(i).getKey()] = 1;
                            p[g.get(v).get(i).getKey()] = v;
                            rib[g.get(v).get(i).getKey()] = g.get(v).get(i).getValue();
                        }
                    }
                }
                if (d[t] == Long.MAX_VALUE) break;
                long addflow = Long.MAX_VALUE;
                for (int v = t; v != s; v = p[v]) {
                    int pr = rib[v];
                    addflow = Math.min(addflow, edges.get(pr).getCapacity() - edges.get(pr).getFlow());
                }
                for (int v = t; v != s; v = p[v]) {
                    int pr = rib[v], r = edges.get(pr).getBackId();
                    edges.get(pr).increaseFlow(addflow);
                    edges.get(r).increaseFlow(-addflow);
                    cost += edges.get(pr).getCost() * addflow;
                }
                flow += addflow;
            }

            out.println(cost);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}