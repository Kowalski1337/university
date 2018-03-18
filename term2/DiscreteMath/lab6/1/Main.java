import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by vadim on 18.05.2017.
 */
public class Main {
    public static boolean used[];
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

    public static boolean dfs(ArrayList<ArrayList<Integer>> g1, HashSet<Integer> blackHoles1, ArrayList<ArrayList<Integer>> g2, HashSet<Integer> blackHoles2, int v1, int v2){
        used[v1] = true;
        if (!blackHoles1.contains(v1) && blackHoles2.contains(v2) || blackHoles1.contains(v1) && !blackHoles2.contains(v2)){
            return false;
        }
        boolean ans = true;
        for (int i = 0; i <= 'z' - 'a'; i++){
            int t1 = g1.get(v1).get(i);
            int t2 = g2.get(v2).get(i);
            if (t1 == -1 && t2 != -1 || t1 != -1 && t2 == -1){
                return false;
            }
            if (t1 != -1 && !used[t1]){
                ans = ans && dfs(g1, blackHoles1, g2, blackHoles2, t1, t2);
            }
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("isomorphism.in"));
        PrintWriter out = new PrintWriter("isomorphism.out");

        ArrayList<ArrayList<Integer>> g1, g2;
        g1 = new ArrayList<>();
        g2 = new ArrayList<>();
        HashSet<Integer> blackHoles1 = new HashSet<>(), blackHoles2 = new HashSet<>();

        int n = nextInt(in), m = nextInt(in), k = nextInt(in);

        used = new boolean[n];

        for (int i = 0; i < k; i++) {
            blackHoles1.add(nextInt(in) - 1);
        }

        for (int i = 0; i < n; i++) {
            g1.add(new ArrayList<>());
            for (int j = 'a'; j <= 'z'; j++) {
                g1.get(i).add(-1);
            }
        }

        for (int i = 0; i < m; i++) {
            int a = nextInt(in) - 1, b = nextInt(in) - 1, c = in.read() - 'a';
            g1.get(a).set(c, b);
        }

        n = nextInt(in);
        m = nextInt(in);
        k = nextInt(in);

        for (int i = 0; i < k; i++) {
            blackHoles2.add(nextInt(in) - 1);
        }

        for (int i = 0; i < n; i++) {
            g2.add(new ArrayList<>());
            for (int j = 'a'; j <= 'z'; j++) {
                g2.get(i).add(-1);
            }
        }

        for (int i = 0; i < m; i++) {
            int a = nextInt(in) - 1, b = nextInt(in) - 1, c = in.read() - 'a';
            g2.get(a).set(c, b);
        }


        out.println(dfs(g1, blackHoles1, g2, blackHoles2, 0, 0) ? "YES" : "NO");
        out.close();


    }
}
