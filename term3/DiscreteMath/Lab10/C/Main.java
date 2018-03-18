import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {
    private static ArrayList<Integer> ans;
    private static ArrayList<Boolean> used;

    static class MyReaderWriter {
        private BufferedReader in;
        private BufferedWriter out;


        MyReaderWriter(String read, String write) throws IOException {
            in = new BufferedReader(new FileReader(read));
            out = new BufferedWriter(new FileWriter(write));
        }

        MyReaderWriter() {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        String nextLine() throws IOException {
            return in.readLine();
        }

        int nextInt() throws IOException {
            int ans = 0;
            boolean isNegate = false;
            int ch = in.read();
            while ((ch < '0' || ch > '9') && ch != '-') {
                ch = in.read();
            }
            if (ch == '-') {
                isNegate = true;
                ch = in.read();
            }
            while (ch >= '0' && ch <= '9') {
                ans = ans * 10 + ch - '0';
                ch = in.read();
            }
            return isNegate ? -ans : ans;
        }

        long nextLong() throws IOException {
            long ans = 0;
            boolean isNegate = false;
            int ch = in.read();
            while ((ch < '0' || ch > '9') && ch != '-') {
                ch = in.read();
            }
            if (ch == '-') {
                isNegate = true;
                ch = in.read();
            }
            while (ch >= '0' && ch <= '9') {
                ans = ans * 10 + ch - '0';
                ch = in.read();
            }
            return isNegate ? -ans : ans;
        }

        public void print(int what) throws IOException {
            out.write(Integer.toString(what));
        }

        public void println(int what) throws IOException {
            out.write(Integer.toString(what) + '\n');
        }

        public void print(long what) throws IOException {
            out.write(Long.toString(what));
        }

        public void println(long what) throws IOException {
            out.write(Long.toString(what) + '\n');
        }

        public void print(String what) throws IOException {
            out.write(what);
        }

        public void println(String what) throws IOException {
            out.write(what + '\n');
        }

        public void close() throws IOException {
            in.close();
            out.close();
        }
    }

    private static boolean dfs(int v, ArrayList<ArrayList<Integer>> g) {
        if (!used.get(v)) {
            used.set(v, true);
            for (Integer next : g.get(v)) {
                if (ans.get(next) == -1 || dfs(ans.get(next), g)) {
                    ans.set(next, v);
                    return true;
                }
            }
        }
        return false;
    }


    public static void main(String[] args) {
        try {
            MyReaderWriter rw = new MyReaderWriter("matching.in", "matching.out");
            ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
            int n = rw.nextInt();
            ArrayList<Pair<Integer, Integer>> help = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                help.add(new Pair<>(rw.nextInt(), i));
            }
            for (int i = 0; i < n; i++) {
                int size = rw.nextInt();
                graph.add(new ArrayList<>());
                for (int j = 0; j < size; j++) {
                    graph.get(i).add(rw.nextInt() - 1);
                }
            }

            help.sort(Comparator.comparing(Pair::getKey));
            Collections.reverse(help);
            /*for (int i = 0; i < n; i++) {
                System.out.println(help.get(i));
            }*/

            ans = new ArrayList<>();
            used = new ArrayList<>();

            for (int i = 0; i < n; i++) {
                ans.add(-1);
                used.add(false);
            }

            for (int i = 0; i < n; i++) {
                dfs(help.get(i).getValue(), graph);
                for (int j = 0; j < n; j++)
                    used.set(j, false);
            }

            /*for (int i = 0; i < n; i++) {
                rw.print(ans.get(i) + 1);
                rw.print(" ");
            }*/

            int[] solve = new int[n];
            for (int i = 0; i < n; i++) {
                solve[i] = 0;
            }
            for (int i = 0; i < n; i++) {
                if (ans.get(i) != -1) {
                    solve[ans.get(i)] = i + 1;
                }
            }

            for (int i = 0; i < n; i++) {
                rw.print(solve[i]);
                rw.print(" ");
            }

            rw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
