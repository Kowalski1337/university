import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by vadim on 18.05.2017.
 */
public class Main {
    public static boolean used1[];
    public static boolean used2[];

    public static int nextInt(BufferedReader sc) throws Exception {
        int a = 0;
        int b = sc.read();
        while (b < '0' || b > '9') b = sc.read();
        while (b >= '0' && b <= '9') {
            a = a * 10 + (b - '0');
            b = sc.read();
        }
        return a;
    }

    static class pair {
        int f, s;

        pair(int f, int s) {
            this.f = f;
            this.s = s;
        }
    }

    public static boolean bfs(ArrayList<ArrayList<Integer>> g1, HashSet<Integer> blackHoles1, ArrayList<ArrayList<Integer>> g2, HashSet<Integer> blackHoles2) {
        LinkedList<pair> Q = new LinkedList<>();
        Q.add(new pair(0, 0));
        while (!Q.isEmpty()) {
            int v1 = Q.getFirst().f;
            int v2 = Q.getFirst().s;
            Q.removeFirst();
            if (!blackHoles1.contains(v1) && blackHoles2.contains(v2) || blackHoles1.contains(v1) && !blackHoles2.contains(v2)) {
                return false;
            }
            used1[v1] = true;
            used2[v2] = true;
            for (int i = 0; i <= 'z' - 'a'; i++) {
                int t1 = g1.get(v1).get(i);
                int t2 = g2.get(v2).get(i);
                /*if (t1 == -1 && t2 != -1 || t1 != -1 && t2 == -1) {
                    return false;
                }*/
                if (!used1[t1] || !used2[t2]) {
                    Q.add(new pair(t1, t2));
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("equivalence.in"));
        PrintWriter out = new PrintWriter("equivalence.out");

        ArrayList<ArrayList<Integer>> g1, g2;
        g1 = new ArrayList<>();
        g2 = new ArrayList<>();
        HashSet<Integer> blackHoles1 = new HashSet<>(), blackHoles2 = new HashSet<>();

        int n = nextInt(in), m = nextInt(in), k = nextInt(in);

        used1 = new boolean[n + 1];

        for (int i = 0; i < k; i++) {
            blackHoles1.add(nextInt(in) - 1);
        }

        for (int i = 0; i <= n; i++) {
            g1.add(new ArrayList<>());
            for (int j = 'a'; j <= 'z'; j++) {
                g1.get(i).add(n);
            }
        }

        for (int i = 0; i < m; i++) {
            int a = nextInt(in) - 1, b = nextInt(in) - 1, c = in.read() - 'a';
            g1.get(a).set(c, b);
        }

        n = nextInt(in);
        m = nextInt(in);
        k = nextInt(in);

        used2 = new boolean[n +1];

        for (int i = 0; i < k; i++) {
            blackHoles2.add(nextInt(in) - 1);
        }

        for (int i = 0; i <= n; i++) {
            g2.add(new ArrayList<>());
            for (int j = 'a'; j <= 'z'; j++) {
                g2.get(i).add(n);
            }
        }

        for (int i = 0; i < m; i++) {
            int a = nextInt(in) - 1, b = nextInt(in) - 1, c = in.read() - 'a';
            g2.get(a).set(c, b);
        }


        out.println(bfs(g1, blackHoles1, g2, blackHoles2) ? "YES" : "NO");
        out.close();


    }
}