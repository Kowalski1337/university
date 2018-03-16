import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by vadim on 14.04.2017.
 */


public class PathBge {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("pathbge1.in"));
        PrintWriter out = new PrintWriter("pathbge1.out");

        int n = in.nextInt();
        int m = in.nextInt();

        ArrayList<Integer> g[] = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++) {
            int b = in.nextInt();
            int e = in.nextInt();

            g[b].add(e);
            g[e].add(b);
        }

        /*for (int i = 1; i <= n; i++){
            for (int j = 0; j < g[i].size(); j++){
                out.print(g[i].get(j) + " ");
            }
            out.println();
        }*/

        boolean used[] = new boolean[n + 1];
        int ans[] = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            used[i] = false;
        }

        ans[1] = 0;

        LinkedList<Integer> q = new LinkedList<>();

        q.add(1);
        used[1] = true;

        while (q.size() != 0) {
            int v = q.peekFirst();
            q.removeFirst();
            for (int i = 0; i < g[v].size(); i++) {
                int cur = g[v].get(i);
                if (!used[cur]) {
                    q.add(cur);
                    used[cur] = true;
                    ans[cur] = ans[v] + 1;
                }
            }
        }


        for (int i = 1; i <= n; i++) {
            out.print(ans[i] + " ");
        }
        out.close();

    }
}
