import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by vadim on 14.04.2017.
 */

class graph {
    ArrayList<Integer> child;
}


public class Cycle {

    public static int color[];
    public static boolean isAns = false;
    public static int parent[];
    public static int cycleStart, cycleEnd;

    public static void dfs(graph g[], int v) {
        color[v] = 1;
        for (int i = 0; i < g[v].child.size(); i++) {
            int cur = g[v].child.get(i);
            if (color[cur] == 0) {
                parent[cur] = v;
                dfs(g, cur);
            } else {
                if (color[cur] == 1) {
                    isAns = true;
                    cycleEnd = v;
                    cycleStart = cur;
                    return;
                }
            }
        }
        color[v] = 2;
    }


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("cycle.in"));
        PrintWriter out = new PrintWriter("cycle.out");

        int n = in.nextInt();
        int m = in.nextInt();

        graph g[] = new graph[n + 1];
        color = new int[n + 1];
        parent = new int[n + 1];

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
            g[second].child.add(first);
        }

        for (int i = 1; i <= n; i++) {
            if (isAns) break;
            if (color[i] == 0) {
                dfs(g, i);
            }
        }

        out.println(cycleStart + " " + cycleEnd);


        if (isAns) {
            out.println("YES");
            out.print(cycleStart + " ");
            for (int v = cycleEnd; v != cycleStart; v = parent[v]) {
                out.print(v + " ");
            }
        } else {
            out.print("NO");
        }

        out.close();
    }
}