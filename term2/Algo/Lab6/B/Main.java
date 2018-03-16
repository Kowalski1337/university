import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by vadim on 08.05.2017.
 */




public class Main {
    public static final long inf = Long.MAX_VALUE;

    static class graph {
        int e;
        long w;

        graph(int e, long w) {
            this.e = e;
            this.w = w;
        }
    }

    public static long nextLong(BufferedReader in) throws Exception {
        long ans = 0;
        int b = in.read();
        boolean isNeg = false;
        while ((b < '0' || b > '9') && b != '-') {
            b = in.read();
        }
        if (b == '-') {
            isNeg = true;
            b = in.read();
        }
        while (b >= '0' && b <= '9') {
            ans = ans * 10 + (b - '0');
            b = in.read();
        }
        return isNeg ? -ans : ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("pathmgep.in"));
        PrintWriter out = new PrintWriter("pathmgep.out");

        long n = nextLong(in);
        long s = nextLong(in) - 1;
        long e = nextLong(in) - 1;

        ArrayList<ArrayList<graph>> g = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                long cur = nextLong(in);
                //out.print(cur + " ");
                if (cur != -1) {
                    //  out.println("save");
                    g.get(i).add(new graph(j, cur));
                }
            }
        }

        /*for (int i = 0; i < n; i++){
            out.print(i + ": ");
            for (int j = 0; j < g.get(i).size(); j++){
                out.print(g.get(i).get(j).e + " " + g.get(i).get(j).w + " " );
            }
            out.println();
        }*/


        long[] d = new long[(int)n];

        for (int i = 0; i < n; i++) {
            d[i] = inf;
        }

        d[(int)s] = 0;

        boolean[] used = new boolean[(int)n];

        for (int i = 0; i < n; i++) {
            used[i] = false;
        }

        for (int i = 0; i < n; i++) {
            int bi = -1;

            for (int j = 0; j < n; j++) {
                if (!used[j] && (bi == -1 || d[j] < d[bi])) {
                    bi = j;
                }
            }

            if (d[bi] == inf || bi == e) {
                break;
            }

            used[bi] = true;

            for (int j = 0; j < g.get(bi).size(); j++) {
                int ei = g.get(bi).get(j).e;
                long wi = g.get(bi).get(j).w;

                if (d[ei] > d[bi] + wi) {
                    d[ei] = d[bi] + wi;
                }
            }
        }

        /*for (int j = 0; j < n; j++){
            out.println(j + ": " + d[j]);
        }*/

        out.print(d[(int)e] == inf ? "-1" : d[(int)e]);

        out.close();

    }
}

