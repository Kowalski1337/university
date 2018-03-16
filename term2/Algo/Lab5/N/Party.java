/**
 * Created by vadim on 21.04.2017.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.*;

class graph {
    ArrayList<Integer> child;
}

class ways {
    int s, e;
}


public class Party {

    public static boolean used[];
    public static int time_out[];
    public static int cur_number = 1;
    public static int res[];
    public static HashSet<Integer> can_come[];

    public static void dfs(graph g[], int v) {
        used[v] = true;
        for (int i = 0; i < g[v].child.size(); i++)
            if (!used[g[v].child.get(i)]) {
                dfs(g, g[v].child.get(i));
            }
        time_out[cur_number++] = v;
    }


    private static void components(graph t[], int v, int color) {
        used[v] = true;
        res[v] = color;
        for (int i = 0; i < t[v].child.size(); i++) {
            if (!used[t[v].child.get(i)]) {
                components(t, t[v].child.get(i), color);
            }
        }

    }

    private static void dfs2(graph g[], int v) {
        can_come[v].add(v);
        used[v] = true;
        for (int i = 0; i < g[v].child.size(); i++) {
            int cur = g[v].child.get(i);
            can_come[cur].addAll(can_come[v]);
            if (!used[cur]) {
                dfs2(g, cur);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter("party.out");

        int n = in.nextInt();
        int m = in.nextInt();

        graph g[] = new graph[2 * n + 1];
        graph t[] = new graph[2 * n + 1];
        used = new boolean[2 * n + 1];
        time_out = new int[2 * n + 1];
        res = new int[2 * n + 1];

        ArrayList<String> names = new ArrayList<>();

        String cur = in.nextLine();
        for (int i = 0; i < n; i++) {
            cur = in.nextLine();
            names.add(cur);
        }

        for (int i = 0; i <= 2 * n; i++) {
            used[i] = false;
        }

        for (int i = 0; i <= 2 * n; i++) {
            g[i] = new graph();
            t[i] = new graph();
            t[i].child = new ArrayList<>();
            g[i].child = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            cur = in.nextLine();
            int first, second;
            int j = 0;
            while (!Character.isWhitespace(cur.charAt(j++))) {

            }
            first = names.indexOf(cur.substring(1, j - 1)) + 1;
            second = names.indexOf(cur.substring(j + 4, cur.length())) + 1;
            first += cur.charAt(0) == '+' ? 0 : n;
            second += cur.charAt(j + 3) == '+' ? 0 : n;
            //System.out.println(first + " " + second);
            t[second].child.add(first);
            g[first].child.add(second);
            if (first <= n) {
                first += n;
            } else {
                first -= n;
            }

            if (second <= n) {
                second += n;
            } else {
                second -= n;
            }
            //System.out.println(first + " " + second);
            g[second].child.add(first);
            t[first].child.add(second);
        }

        /*System.out.println(2*n);

        for (int i = 1; i <= 2*n; i++){
            for (int j = 0; j < g[i].child.size(); j++){
                System.out.print(g[i].child.get(j) + " ");
            }
            System.out.println();
        }

        System.out.println();*/


        for (int i = 1; i <= 2 * n; i++) {
            if (!used[i]) {
                dfs(g, i);
            }
        }


        for (int i = 0; i <= 2 * n; i++) {
            used[i] = false;
        }


        int color = 0;

        for (int i = 1; i <= 2 * n; i++) {
            int v = time_out[cur_number - i];
            if (!used[v]) {
                components(t, v, ++color);
            }
        }

        /*for (int i = 1; i <= 2 * n; i++) {
            System.out.println(res[i]);
        }*/

        boolean solve[] = new boolean[n + 1];
        int size = 0;

        for (int i = 1; i <= n; i++) {
            if (res[i] == res[i + n]) {
                size = -1;
                break;
            }
            if (res[i] > res[i + n]) {
                solve[i] = true;
                size++;
            } else {
                solve[i] = false;
            }
        }

        ways w[] = new ways[2 * m];
        int index = 0;
        for (int i = 1; i <= 2 * n; i++) {
            for (int j = 0; j < g[i].child.size(); j++) {
                w[index] = new ways();
                w[index].s = i;
                w[index++].e = g[i].child.get(j);
            }
        }
        graph cond[] = new graph[color + 1];
        HashSet[] help = new HashSet[color + 1];
        for (int i = 0; i <= color; i++) {
            cond[i] = new graph();
            cond[i].child = new ArrayList<>();
            help[i] = new HashSet<>();
        }

        for (int i = 0; i < 2 * m; i++) {
            int f = res[w[i].s];
            int s = res[w[i].e];

            if (f != s) {
                if (!help[f].contains(s)) {
                    help[f].add(s);
                    cond[f].child.add(s);
                }
            }

        }

        can_come = new HashSet[color + 1];

        for (int i = 0; i <= color; i++) {
            used[i] = false;
            can_come[i] = new HashSet<>();
        }

        for (int i = 1; i <= color; i++) {
            if (!used[i]) {
                dfs2(cond, i);
            }
        }

                /*for (int i = 1; i <= color; i++){
                    for (int j = 1; j < 2*n; j++){
                        if (can_come[i].contains(j)){
                            System.out.print(j + " ");
                        }
                    }
                    System.out.println();
                }*/

        switch (size) {
            case -1:
                System.out.print(-1);
                break;
            case 0:

                for (int i = 1; i <= n; i++) {
                    if (!can_come[res[i]].contains(res[i + n]) && !can_come[res[i + n]].contains(res[i])) {
                        System.out.println(1);
                        System.out.print(names.get(i - 1));
                        break;
                    }
                }

                break;

            default:
                System.out.println(size);
                for (int i = 1; i <= n; i++) {
                    if (solve[i]) {
                        System.out.println(names.get(i - 1));
                    }
                }
        }
        System.out.close();

    }
}
