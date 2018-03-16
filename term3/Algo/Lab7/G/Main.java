import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    private static final int logN = 19;
    private static ArrayList<Integer> parent, depth;
    private static ArrayList<int[]> dp;
    private static ArrayList<Boolean> isDead;
    private static ArrayList<Integer> aliveParent;

    private static int nextInt(BufferedReader in) throws Exception {
        int ans = 0;
        int ch = in.read();
        while (ch < '0' || ch > '9') {
            ch = in.read();
        }
        while (ch <= '9' && ch >= '0') {
            ans = 10 * ans + ch - '0';
            ch = in.read();
        }
        return ans;
    }

    private static int nextAction(BufferedReader in) throws Exception {
        int ch = in.read();
        while (ch != '+' && ch != '-' && ch != '?') {
            ch = in.read();
        }
        return ch == '+' ? 0 : ch == '-' ? 1 : 2;
    }

    private static int lca(int u, int v) {
        if (depth.get(u) > depth.get(v)) {
            return lca(v, u);
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (depth.get(dp.get(v)[i]) >= depth.get(u)) {
                v = dp.get(v)[i];
            }
        }
        if (u == v) {
            return u;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (dp.get(u)[i] != dp.get(v)[i]) {
                u = dp.get(u)[i];
                v = dp.get(v)[i];
            }
        }
        return parent.get(v);
    }

    private static int calcAliveParent(int v) {
        if (isDead.get(aliveParent.get(v))) {
            aliveParent.set(v, calcAliveParent(aliveParent.get(v)));
        }
        return aliveParent.get(v);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("carno.in"));
        PrintWriter out = new PrintWriter("carno.out");

        parent = new ArrayList<>();
        depth = new ArrayList<>();
        aliveParent = new ArrayList<>();

        parent.add(0);
        depth.add(0);
        aliveParent.add(0);

        dp = new ArrayList<>();
        dp.add(new int[logN]);
        isDead = new ArrayList<>();
        isDead.add(false);

        int n = nextInt(in);
        for (int i = 0; i < n; i++) {
            int nextAction = nextAction(in);
            //System.out.println(nextAction);
            if (nextAction == 0) {
                int next = parent.size();
                parent.add(nextInt(in) - 1);
                depth.add(depth.get(parent.get(next)) + 1);
                isDead.add(false);
                aliveParent.add(parent.get(next));
                dp.add(new int[logN]);
                dp.get(next)[0] = parent.get(next);
                for (int j = 1; j < logN; j++) {
                    dp.get(next)[j] = dp.get(dp.get(next)[j - 1])[j - 1];
                }
            }
            if (nextAction == 1) {
                isDead.set(nextInt(in) - 1, true);
            }
            if (nextAction == 2) {
                int lca = lca(nextInt(in) - 1, nextInt(in) - 1);
                if (isDead.get(lca)) {
                    lca = calcAliveParent(lca);
                }
                out.println(lca + 1);
            }
        }
        out.close();

    }
}
