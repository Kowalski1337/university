

import java.io.*;
import java.util.*;

public class Main {
    private static final double E = 1e-10;
    private static double x1, y1, x2, y2;
    private static int n, index, ans;
    private static point p;
    private static edge e;
    private static HashMap<point, Integer> ps;
    private static ArrayList<Boolean> vertex;
    private static ArrayList<line> lines;
    private static ArrayList<ArrayList<point>> points, facets;
    private static ArrayList<ArrayList<Boolean>> used;
    private static ArrayList<ArrayList<Integer>> matrix;
    private static ArrayList<ArrayList<edge>> graph;
    private static ArrayList<Double> squares;

    static class point {
        double x, y;
        int key;

        boolean isLess(point dot) {
            return (x < dot.x - E) || (Math.abs(x - dot.x) < E && (y < dot.y - E))
                    || (Math.abs(x - dot.x) < E && Math.abs(y - dot.y) < E && key < dot.key);
        }

        boolean isEquals(point dot) {
            return Math.abs(x - dot.x) < E && Math.abs(y - dot.y) < E;
        }

        point(double x, double y, int key) {
            this.x = x;
            this.y = y;
        }
    }

    static class line {
        double A, B, C;

        double get(double a, double b, double c, double d) {
            return a * d - b * c;
        }

        boolean intersect(line line) {
            double x = get(line.A, line.B, A, B);
            if (Math.abs(x) < E) {
                return false;
            }
            x1 = -get(line.C, line.B, C, B) / x;
            y1 = -get(line.A, line.C, A, C) / x;
            //здесь надо написать какую-то хуйню если что просравнение -0 и 0
            return true;
        }

        line(double A, double B, double C) {
            this.A = A;
            this.B = B;
            this.C = C;
        }
    }

    class edge {
        point first, second;

        boolean isEquals(edge edge) {
            return first == edge.first && second == edge.second;
        }

        boolean isEqualsAngles(edge edge) {
            double xa = first.x - second.x;
            double xb = edge.first.x - edge.second.x;
            double ya = first.y - second.y;
            double yb = edge.first.y - edge.second.y;
            return Math.atan2(ya, xa) < Math.atan2(yb, xb) - E;
        }

        edge(point first, point second) {
            this.first = first;
            this.second = second;
        }
    }

    static class MyReaderWriter {
        BufferedWriter out;
        BufferedReader in;

        MyReaderWriter(String fileName) throws IOException {
            if (Objects.equals(fileName, "NO")) {
                in = new BufferedReader(new InputStreamReader(System.in));
                out = new BufferedWriter(new OutputStreamWriter(System.out));
            } else {
                in = new BufferedReader(new FileReader(fileName + ".in"));
                out = new BufferedWriter(new FileWriter(fileName + ".out"));
            }
        }

        int nextInt() throws IOException {
            int ans = 0;
            int ch = in.read();
            boolean isNegate = false;
            while ((ch < '0' || ch > '9') && ch != '-') {
                ch = in.read();
            }
            if (ch == '-') {
                isNegate = true;
                ch = in.read();
            }
            while (ch >= '0' && ch <= '9') {
                ans = 10 * ans + ch - '0';
                ch = in.read();
            }
            return isNegate ? -ans : ans;
        }

        void writeDouble(double a) throws IOException {
            out.write(Double.toString(a));
        }
    }

    private static boolean isLess(ArrayList<edge> a, ArrayList<edge> b) {
        if (a.isEmpty()) {
            return false;
        }
        if (b.isEmpty()) {
            return true;
        }
        return (a.get(0).first.x < b.get(0).first.x - E) ||
                ((Math.abs(a.get(0).first.x - b.get(0).first.x) < E) && a.get(0).first.y < b.get(0).first.y - E);
    }

    private static void sort(int v, int l, int r) {
        if (l >= r) {
            return;
        }

        int m = l + (r - l) / 2;
        point key = points.get(v).get(m);

        int i = l, j = r;
        while (i <= j) {
            while (points.get(v).get(i).isLess(key)) {
                i++;
            }
            while (key.isLess(points.get(v).get(j))) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (l < j) {
            sort(l, j);
        }
        if (r > i) {
            sort(i, r);
        }

    }

    private static void swap(int v, int i, int j) {

    }

    public static void main(String[] args) throws IOException {
        MyReaderWriter rofl = new MyReaderWriter("NO");
        n = rofl.nextInt();
        index = -1;
        lines = new ArrayList<>();
        points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            x1 = rofl.nextInt();
            y1 = rofl.nextInt();
            x2 = rofl.nextInt();
            y2 = rofl.nextInt();
            lines.add(new line(y1 - y2, x2 - x1, x1 * y2 - x2 * y1));
        }
        for (int i = 0; i < n; i++){
            for (int j = i+1; j < n; j++){
                if (lines.get(i).intersect(lines.get(j))){
                    index++;
                    points.get(i).add(new point(x1, y1, index));
                    points.get(j).add(new point(x1, y1, index));
                }
            }
        }
        index++;
        graph = new ArrayList<>();
        for (int i = 0; i < index; i++){
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++){
            if (!points.get(i).isEmpty()){

            }
        }
    }
}
