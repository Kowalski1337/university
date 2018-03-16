import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    private static ArrayList<ArrayList<Integer>> g;
    private static ArrayList<Integer> order, heigths, first, pows, logs;
    private static ArrayList<Boolean> used;
   // private static ArrayList<ArrayList<Integer>> sparseTable;
    private static int[][] staticSparseTable;
    private static int sparseTableSize = 0;
    private static int logN;

    private static long a1, a2, x, y, z;

    private static long getNext(int n) {
        long t = a2;
        a2 = (x * a1 + y * a2 + z) % n;
        a1 = t;
        return a2;
    }

    private static void dfs(int v, int curH) {
        used.set(v, true);
        order.add(v);
        heigths.set(v, curH);
        for (int i = 0; i < g.get(v).size(); i++) {
            int next = g.get(v).get(i);
            if (!used.get(next)) {
                dfs(next, curH + 1);
                order.add(v);
            }
        }
    }

    private static int getLca(long v, long u) {
        if (v == u) {
            return (int) v;
        }
        int l = first.get((int) v);
        int r = first.get((int) u);

        return getMin(Math.min(l, r), Math.max(l, r));
    }

    private static int nextInt(BufferedReader in) throws IOException {
        int ans = 0;
        int next = in.read();

        while (next < '0' || next > '9') {
            next = in.read();
        }

        while (next >= '0' && next <= '9') {
            ans = 10 * ans + next - '0';
            next = in.read();
        }
        return ans;
    }

    private static void calcPows(int n) {
        int k = 1;
        while (k < n) {
            pows.add(k);
            k *= 2;
        }
    }

    private static void calcLogs(int n) {
        logs.add(0);
        logs.add(0);
        for (int i = 2; i <= n; i++) {
            logs.add(logs.get(i / 2) + 1);
        }
    }

    private static void buildSparseTable() {
        //sparseTable = new ArrayList<>();
        //sparseTable.add(order);
        staticSparseTable = new int[logN  + 1][order.size()];
        for (int i = 0; i < order.size(); i++) {
            staticSparseTable[0][i] = order.get(i);
        }


        int curPow = 2;
        for (int i = 1; i < logN; i++) {
            //int curPow = pows.get(i);
            //ArrayList<Integer> next = new ArrayList<>();
            for (int j = 0; j <= sparseTableSize - curPow; j++) {
              //  int s1 = sparseTable.get(i - 1).get(j);
               // int s2 = sparseTable.get(i - 1).get(j + curPow / 2);
                //next.add(heigths.get(s1) < heigths.get(s2) ? s1 : s2);
                int ans1 = staticSparseTable[i - 1][j];
                int ans2 = staticSparseTable[i - 1][j + curPow / 2];
                staticSparseTable[i][j] = heigths.get(ans1) < heigths.get(ans2) ? ans1 : ans2;
            }
            //sparseTable.add(next);
            curPow *= 2;
        }
    }

    private static int getMin(int l, int r) {
        int log = logs.get(r - l);
        int pow = pows.get(log);

        int a1 = staticSparseTable[log][l];
        int a2 = staticSparseTable[log][r-pow + 1];
        return heigths.get(a1) < heigths.get(a2) ? a1 : a2;
    }

    private static void prepare(int n) {
        used = new ArrayList<>();
        order = new ArrayList<>();
        heigths = new ArrayList<>();
        first = new ArrayList<>();
        logs = new ArrayList<>();
        pows = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            used.add(false);
            heigths.add(0);
            first.add(-1);
        }


        dfs(0, 0);


        for (int i = 0; i < order.size(); i++) {
            if (first.get(order.get(i)) == -1) {
                first.set(order.get(i), i);
                sparseTableSize = i;
            }
        }

        sparseTableSize++;

//        for (int i = 0; i < n; i++){
//            System.out.print(first.get(i) + " ");
//        }
//        System.out.println();

        calcLogs(sparseTableSize);
        calcPows(sparseTableSize);
        logN = pows.size();

        //System.out.println(logN);

        buildSparseTable();

//        for (int i = 0; i < order.size(); i++){
//            for (int j = 0; j < logN; j++){
//                System.out.print(sparseTable.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("lca_rmq.in"));
        PrintWriter out = new PrintWriter("lca_rmq.out");

        int n = nextInt(in);
        int m = nextInt(in);

        g = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }
        for (int i = 1; i < n; i++) {
            g.get(nextInt(in)).add(i);
        }

//        for (int i = 0; i < n; i++){
//            for (int j = 0; j < g.get(i).size(); j++){
//                System.out.print(g.get(i).get(j) + " ");
//            }
//            System.out.println();
//        }

        a1 = nextInt(in);
        a2 = nextInt(in);
        x = nextInt(in);
        y = nextInt(in);
        z = nextInt(in);

        prepare(n);

        long v = a1;
        long u = a2;
        long ans = 0;

        for (int i = 0; i < m; i++) {
            int next = getLca(v, u);
            ans += next;
            v = (getNext(n) + next) % n;
            u = getNext(n);
        }

        out.print(ans);
        out.close();

    }
}
