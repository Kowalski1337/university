import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;

/**
 * Created by vadim on 12.04.2017.
 */

class graph {
    ArrayList<Integer> child;
}

class ways {
    int s, e;
}


public class Fire {

    public static boolean used[];
    public static int time_out[];
    public static int cur_number = 1;
    public static int res[];

    public static void dfs(graph g[], int v) {
        used[v] = true;
        for (int i = 0; i < g[v].child.size(); i++)
            if (!used[g[v].child.get(i)]) {
                dfs(g, g[v].child.get(i));
            }
        time_out[cur_number++] = v;
    }


    public static void components(graph t[], int v, int color) {
        used[v] = true;
        res[v] = color;
        for (int i = 0; i < t[v].child.size(); i++)
            if (!used[t[v].child.get(i)])
                components(t, t[v].child.get(i), color);

    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("fire.in"));
        PrintWriter out = new PrintWriter("fire.out");

        int n = in.nextInt();
        int m = in.nextInt();

        graph g[] = new graph[n + 1];
        graph t[] = new graph[n + 1];
        used = new boolean[n + 1];
        time_out = new int[n + 1];
        res = new int[n + 1];
        ways w[] = new ways[m];

        for (int i = 0; i <= n; i++) {
            used[i] = false;
        }

        for (int i = 0; i <= n; i++) {
            g[i] = new graph();
            t[i] = new graph();
            t[i].child = new ArrayList<Integer>();
            g[i].child = new ArrayList<Integer>();
        }


        for (int i = 0; i < m; i++) {
            w[i] = new ways();
            int first = in.nextInt();
            int second = in.nextInt();
            w[i].s = first;
            w[i].e = second;
            g[first].child.add(second);
            t[second].child.add(first);
        }

        for (int i = 1; i <= n; i++) {
            if (!used[i]) {
                dfs(g, i);
            }
        }


        for (int i = 0; i <= n; i++) {
            used[i] = false;
        }


        int color = 0;

        for (int i = 1; i <= n; i++) {
            int v = time_out[cur_number - i];
            if (!used[v]) {
                components(t, v, ++color);
            }
        }

        ArrayList<Integer> cond[] = new ArrayList[color+1];
        HashSet<Integer> help[] = new HashSet[color+1];
        for (int i = 0; i <= color; i++){
            cond[i] = new ArrayList<>();
            help[i] = new HashSet<>();
        }

        for (int i = 0; i < m; i++) {
            int f = res[w[i].s];
            int s = res[w[i].e];

            if (f != s) {
                if (!help[f].contains(s)) {
                    help[f].add(s);
                    cond[f].add(s);
                }
            }
        }

        int solv = 0;

        for (int i = 1; i <= color; i++){
            if (cond[i].size() == 0){
                solv++;
            }
        }

        out.print(solv);

        out.close();
    }
}
