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


public class TopSort {

    public static int color[];
    public static int time_out[];
    public static int cur_number = 1;
    public static boolean isAns = true;

    public static void dfs(graph g[], int v) {
        color[v] = 1;
        for (int i = 0; i < g[v].child.size(); i++)
            if (color[g[v].child.get(i)] == 0) {
                dfs(g, g[v].child.get(i));
            } else {
                if (color[g[v].child.get(i)] == 1) {
                    isAns = false;
                }
            }
        color[v] = 2;
        time_out[cur_number++] = v;
    }


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("topsort.in"));
        PrintWriter out = new PrintWriter("topsort.out");

        int n = in.nextInt();
        int m = in.nextInt();

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
            g[first].child.add(second);
        }

        for (int i = 1; i <= n; i++) {
            if (color[i] == 0) {
                dfs(g, i);
            }
        }


        if (isAns) {
            for (int i = n; i >0; i--) {
                out.print(time_out[i] + " ");
            }
        } else{
            out.print(-1);
        }

        out.close();
    }
}

