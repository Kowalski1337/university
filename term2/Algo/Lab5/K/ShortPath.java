import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by vadim on 14.04.2017.
 */

class graph {
    ArrayList<lol> child;
}

class lol {
    int e, w;
}


public class ShortPath {

    public static int color[];
    public static int time_out[];
    public static int cur_number = 1;
    public static boolean isAns = true;

    public static void dfs(graph g[], int v) {
        color[v] = 1;
        for (int i = 0; i < g[v].child.size(); i++)
            if (color[g[v].child.get(i).e] == 0) {
                dfs(g, g[v].child.get(i).e);
            } else {
                if (color[g[v].child.get(i).e] == 1) {
                    isAns = false;
                }
            }
        color[v] = 2;
        time_out[cur_number++] = v;
    }


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("shortpath.in"));
        PrintWriter out = new PrintWriter("shortpath.out");

        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt();
        int t = in.nextInt();

        graph g[] = new graph[n + 1];
        color = new int[n + 1];
        time_out = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            color[i] = 0;
        }

        for (int i = 0; i <= n; i++) {
            g[i] = new graph();
            g[i].child = new ArrayList<>();
        }


        for (int i = 0; i < m; i++) {
            int first = in.nextInt();
            int second = in.nextInt();
            int third = in.nextInt();
            lol cur = new lol();
            cur.e = second;
            cur.w = third;
            g[first].child.add(cur);
        }

        dfs(g, s);

        int d[] = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            d[i] = Integer.MAX_VALUE;
        }
        d[s] = 0;

        if (color[t] == 0) {
            out.println("Unreachable");
        } else {
            for (int i = cur_number - 1; i > 0; i--) {
                int bi = time_out[i];
              //  out.println(bi);
                for (int j = 0; j < g[bi].child.size(); j++) {
                    int wi = g[bi].child.get(j).w;
                    int ei = g[bi].child.get(j).e;
                    d[ei] = Math.min(d[ei], d[bi] + wi);
                }
            }
            if (d[t] == Integer.MAX_VALUE) {
                out.println("Unreachable");
            } else {
                out.print(d[t]);
            }
        }
        out.close();
    }
}