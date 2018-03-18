import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by vadim on 03.05.2017.
 */
public class Main {
    public static final String name = "problem4";
    public static final int inf = (int) Math.pow(10, 9) + 7;
    public static ArrayList<ArrayList<Integer>> dka = new ArrayList<>();
    public static HashSet<Integer> blackHolesDka = new HashSet<>();
    public static int[][] d;

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

    public static BitSet calc(ArrayList<Integer> bits) {
        BitSet res = new BitSet();
        int size = bits.size();
        for (int i = 0; i < size; i++) {
            res.set(bits.get(i));
        }
        return res;
    }

    public static boolean checkBlackHole(ArrayList<Integer> list, HashSet<Integer> blackHoles) {
        for (int i = 0; i < list.size(); i++) {
            if (blackHoles.contains(list.get(i))) {
                return true;
            }
        }
        return false;
    }

    public static void nkaToDka(ArrayList<ArrayList<ArrayList<Integer>>> g, HashSet<Integer> blackHoles) {
        int value = 1;
        dka.add(new ArrayList<>());
        dka.add(new ArrayList<>());
        HashMap<BitSet, Integer> Q = new HashMap<>();
        ArrayList<Integer> start = new ArrayList<>();
        start.add(1);
        LinkedList<ArrayList<Integer>> queue = new LinkedList<>();
        queue.add(start);
        Q.put(calc(start), value++);
        if (blackHoles.contains(1)) {
            blackHolesDka.add(1);
        }
        while (queue.size() != 0) {
            ArrayList<Integer> cur = queue.peekFirst();
            queue.removeFirst();

            for (int j = 0; j < 26; j++) {
                ArrayList<Integer> curr = new ArrayList<>();
                for (int i = 0; i < cur.size(); i++) {
                    ArrayList<Integer> curWays = g.get(cur.get(i)).get(j);
                    for (int k = 0; k < curWays.size(); k++) {
                        int curDot = curWays.get(k);
                        if (!curr.contains(curDot)) {
                            curr.add(curDot);
                        }
                    }
                }
                if (curr.size() != 0) {
                    BitSet currr = calc(curr);
                    if (!Q.containsKey(currr)) {
                        Q.put(currr, value++);
                        if (checkBlackHole(curr, blackHoles)) {
                            blackHolesDka.add(value - 1);
                        }
                        queue.add(curr);
                        dka.add(new ArrayList<>());
                    }
                    int first = Q.get(calc(cur));
                    int second = Q.get(currr);
                    dka.get(first).add(second);
                }
            }
        }
    }

    public static int solve(int i, int j) {
        if (d[i][j] != -1) {
            return d[i][j];
        }
        if (j == 0) {
            if (blackHolesDka.contains(i)) {
                return 1;
            }
            return 0;
        }

        d[i][j] = 0;

        for (int k = 0; k < dka.get(i).size(); k++) {
            int next = dka.get(i).get(k);
            d[next][j - 1] = solve(next, j - 1) % inf;
            d[i][j] += d[next][j - 1] % inf;
            d[i][j] %= inf;
        }
        return d[i][j];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader(name + ".in"));
        PrintWriter out = new PrintWriter(name + ".out");

        int n = nextInt(in), m = nextInt(in), k = nextInt(in), l = nextInt(in);

        HashSet<Integer> blackHoles = new HashSet<>();

        for (int i = 0; i < k; i++) {
            blackHoles.add(nextInt(in));
        }
        ArrayList<ArrayList<ArrayList<Integer>>> g = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            g.add(new ArrayList<>());
            for (int j = 0; j < 26; j++) {
                g.get(i).add(new ArrayList<>());
            }
        }

        for (int i = 0; i < m; i++) {
            int a = nextInt(in), b = nextInt(in), c = in.read() - 'a';
            g.get(a).get(c).add(b);
        }

        nkaToDka(g, blackHoles);

        int dkaN = dka.size() - 1;

        d = new int[dkaN + 1][l + 1];

        for (int i = 1; i <= dkaN; i++) {
            for (int j = 0; j <= l; j++) {
                d[i][j] = -1;
            }
        }

        out.print(solve(1, l) % inf);

        out.close();
    }
}
