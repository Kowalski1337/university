import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

class graph {
    ArrayList<pair> child;
}

class pair{
    int a, b;
}


public class Bridges {

    public static graph g[];
    public static boolean used[];
    public static int time_in[];
    public static int timer = 0;
    public static int fup[];
    public static ArrayList<Integer> ans = new ArrayList<>();

    public static int min(int a, int b) {
        return a > b ? b : a;
    }

    public static void dfs(graph g[], int v, int parent) {
        used[v] = true;
        time_in[v] = fup[v] = timer++;
        for (int i = 0; i < g[v].child.size(); i++) {
            int cur = g[v].child.get(i).a;
            if (cur != parent) {
                if (!used[cur]) {
                    dfs(g, cur, v);
                    fup[v] = min(fup[v], fup[cur]);
                    if (fup[cur] > time_in[v]) {
                        ans.add(g[v].child.get(i).b);
                    }
                }
                else{
                    fup[v] = min(fup[v], time_in[cur]);
                }
            }
        }
    }

    public static void sort( int l, int r) {
        if (l >= r) {
            return;
        }

        int m = l + (r - l) / 2;
        int key = ans.get(m);

        int i = l, j = r;
        while (i <= j) {
            while (ans.get(i) < key) {
                i++;
            }
            while (ans.get(j) > key) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (l < j) {
            sort(l, j);
        }
        if (r > i) {
            sort(i, r);
        }

    }

    public static void swap(int i, int j) {
        int temp = ans.get(i);
        ans.set(i, ans.get(j));
        ans.set(j, temp);
    }



    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("bridges.in"));
        PrintWriter out = new PrintWriter("bridges.out");

        int n = in.nextInt();
        int m = in.nextInt();

        g = new graph[n + 1];
        fup = new int[n + 1];
        time_in = new int[n + 1];
        used = new boolean[n + 1];

        for (int i = 0; i <= n; i++) {
            used[i] = false;
            time_in[i] = 0;
            fup[i] = 0;
        }


        for (int i = 0; i <= n; i++) {
            g[i] = new graph();
            g[i].child = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int index = i+1;
            pair cur = new pair();
            pair cur1 = new pair();
            cur.a = b;
            cur.b = index;
            g[a].child.add(cur);
            cur1.a = a;
            cur1.b = index;
            g[b].child.add(cur1);
        }

        for (int i = 1; i <= n; i++) {
            if (!used[i]) {
                dfs(g, i, -1);
            }
        }

        sort(0, ans.size()-1);

        out.println(ans.size());

        for (int i = 0; i < ans.size(); i++){
            out.println(ans.get(i));
        }
        out.close();
    }
}

