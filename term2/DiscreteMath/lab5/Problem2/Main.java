import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by vadim on 26.04.2017.
 */
public class Main {
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

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("problem2.in"));
        PrintWriter out = new PrintWriter("problem2.out");

        String s = in.readLine();
        int n = nextInt(in);
        int m = nextInt(in);
        int k = nextInt(in);

        HashSet<Integer> blackHoles = new HashSet<>();

        for (int i = 0; i < k; i++) {
            blackHoles.add(nextInt(in));
        }

        ArrayList<ArrayList<ArrayList<Integer>>> graph = new ArrayList<>();

        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < 26; j++) {
                graph.get(i).add(new ArrayList<>());
            }
        }

        for (int i = 0; i < m; i++) {
            int first = nextInt(in);
            int second = nextInt(in);
            int third = in.read() - 'a';
            graph.get(first).get(third).add(second);
        }

        LinkedList<Integer> cur = new LinkedList<>();

        cur.add(1);

        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'a';
            int l = cur.size();
            if (l == 0) {
                out.print("Rejects");
                out.close();
                return;
            }
            LinkedList<Integer> currr = new LinkedList<>();
            for (int j = 0; j < l; j++) {
                int curr = cur.getFirst();
                cur.removeFirst();
                for (int g = 0; g < graph.get(curr).get(index).size(); g++) {
                    if (!currr.contains(graph.get(curr).get(index).get(g))) {
                        currr.addLast(graph.get(curr).get(index).get(g));
                    }
                }
            }
            cur = currr;
        }
        for (Integer i : cur) {
            if (blackHoles.contains(i)) {
                out.print("Accepts");
                out.close();
                return;
            }
        }
        out.print("Rejects");
        out.close();
    }
}