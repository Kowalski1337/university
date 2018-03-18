import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;

public class Main {

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

    static class Edge {
        int b, e, number;
        long w;

        Edge(int b, int e, long w, int number) {
            this.b = b;
            this.e = e;
            this.w = w;
            this.number = number;
        }

        long getW() {
            return w;
        }
    }

    static class DSU {
        int size;
        int[] id;
        int[] rang;

        DSU(int size) {
            this.size = size;
            id = new int[size];
            rang = new int[size];
        }

        private void init() {
            for (int i = 0; i < size; i++) {
                id[i] = i;
                rang[i] = 1;
            }
        }

        private int get(int x) {
            if (id[x] != x) {
                id[x] = get(id[x]);
            }
            return id[x];
        }

        public boolean checkEq(int a, int b) {
            return get(a) == get(b);
        }

        public void union(int a, int b) {
            int aa = get(a);
            int bb = get(b);
            if (aa != bb) {
                if (rang[aa] == rang[bb]) {
                    rang[aa]++;
                }
                if (rang[aa] < rang[bb]) {
                    id[aa] = bb;
                } else {
                    id[bb] = aa;
                }
            }
        }

    }

    public static void main(String[] args) {
        try {
            MyReaderWriter rw = new MyReaderWriter("destroy.in", "destroy.out");
            ArrayList<Edge> edges = new ArrayList<>();

            int n = rw.nextInt();
            int m = rw.nextInt();
            long max = rw.nextLong();
            long sum = 0;

            for (int i = 0; i < m; i++) {
                edges.add(new Edge(rw.nextInt() - 1, rw.nextInt() - 1, rw.nextLong(), i + 1));
            }

            for (Edge edge : edges) {
                sum += edge.w;
            }

            /*for (int i = 0; i < m; i++) {
                System.out.println(edges.get(i).b + " " + edges.get(i).e + " " + edges.get(i).w);
            }*/

            edges.sort(Comparator.comparing(Edge::getW));
            Collections.reverse(edges);

            /*for (int i = 0; i < m; i++){
                System.out.println(edges.get(i).b +  " " + edges.get(i).e + " " + edges.get(i).w);
            }*/

            DSU dsu = new DSU(n);
            dsu.init();

            HashSet<Integer> geted = new HashSet<>();

            long curSum = 0;
            for (int i = 0; i < m; i++) {
                if (!dsu.checkEq(edges.get(i).b, edges.get(i).e)) {
                    curSum += edges.get(i).w;
                    geted.add(edges.get(i).number);
                    dsu.union(edges.get(i).b, edges.get(i).e);
                }
                if (geted.size() == n - 1){
                    break;
                }
            }

            //rw.println(curSum);

            int cur = 0;
            while (sum - curSum > max) {
                if (!geted.contains(edges.get(cur).number)) {
                    geted.add(edges.get(cur).number);
                    curSum += edges.get(cur).w;
                }
                cur++;
            }

            rw.println(m - geted.size());
            ArrayList<Integer> ans = new ArrayList<>();
            for (Edge edge : edges) {
                if (!geted.contains(edge.number)) {
                    ans.add(edge.number);
                }
            }

            Collections.sort(ans);

            for (Integer an : ans) {
                rw.print(an);
                rw.print(" ");
            }
            rw.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
