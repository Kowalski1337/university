import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by vadim on 08.05.2017.
 */
public class Main {

    static class dot {
        int e;
        double w;

        dot(int e, double w) {
            this.e = e;
            this.w = w;
        }
    }

    public static final double inf = Math.pow(10, 18);

    public static ArrayList<ArrayList<dot>> graph;
    public static boolean[] used;

    static class edge {
        int a, b;
        double w;

        edge(int a, int b, double w) {
            this.a = a;
            this.b = b;
            this.w = w;
        }
    }

    public static void dfs(int v) {
        used[v] = true;
        for (int i = 0; i < graph.get(v).size(); i++) {
            if (!used[graph.get(v).get(i).e]) {
                dfs(graph.get(v).get(i).e);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("path.in"));
        PrintWriter out = new PrintWriter("path.out");

        int n = in.nextInt();

        int m = in.nextInt();

        int s = in.nextInt() - 1;

        ArrayList<edge> ways = new ArrayList<>();
        graph = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt() - 1, b = in.nextInt() - 1;
            long c = in.nextLong();
            ways.add(new edge(a, b, c));
            graph.get(a).add(new dot(b, c));
        }

        double[] d = new double[n];

        int[] p = new int[n];

        for (int i = 0; i < n; i++) {
            d[i] = inf;
        }
        d[s] = 0;
        for (int i = 0; i < n; i++) {
            p[i] = -1;
        }

        used = new boolean[n];

        boolean[] changes = new boolean[n];

        for (int i = 0; i < n; i++) {
            used[i] = false;
            changes[i] = false;
        }

        int x = 0;
        for (int i = 0; i < n; ++i) {
            x = -1;
            for (int j = 0; j < m; j++)
                if (d[ways.get(j).a] < inf) {
                if (d[ways.get(j).b] > d[ways.get(j).a] + ways.get(j).w) {
                    d[ways.get(j).b] = d[ways.get(j).a] + ways.get(j).w;
                    p[ways.get(j).b] = ways.get(j).a;
                    if (i == n - 1) {
                        changes[ways.get(j).b] = true;
                    }
                }
            }

        }


        LinkedList<Integer> ans = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (changes[i]) {
                int y = i;
                for (int j = 0; j < n; ++j) {
                    y = p[y];
                }

                int cur = y;

                if (!used[y]) {
                    ans.clear();
                    while (cur != y || ans.size() == 0) {
                        ans.add(cur);
                        cur = p[cur];
                    }
                    for (int j = 0; j < ans.size(); j++) {
                        if (!used[ans.get(j)]) {
                            dfs(ans.get(j));
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (used[i]) {
                out.println("-");
            } else {
                if (d[i] == inf) {
                    out.println("*");
                } else {
                    out.printf("%.0f\n", d[i]);
                }
            }
        }


        out.close();

    }
}
