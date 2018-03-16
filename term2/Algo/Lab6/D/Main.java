import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by vadim on 08.05.2017.
 */

class Node {
    int key;//index in the massive
    double value;//current min length

    Node(int key, double value) {
        this.key = key;
        this.value = value;
    }
}

class Heap {
    private final double inf = Math.pow(10, 40);
    public ArrayList<Node> heap;
    private ArrayList<Integer> keyToIndex;

    private void swap(int first, int second) {
        Node cur1 = new Node(heap.get(first).key, heap.get(first).value);
        Node cur2 = new Node(heap.get(second).key, heap.get(second).value);
        heap.set(first, cur2);
        heap.set(second, cur1);
        int temp = keyToIndex.get(first);
        keyToIndex.set(heap.get(first).key, first);
        keyToIndex.set(heap.get(second).key, second);
    }

    private void siftDown(int index) {
        while (2 * index + 1 < size()) {
            int change = 2 * index + 1;
            if (2 * index + 2 < size() && heap.get(2 * index + 2).value < heap.get(change).value) {
                change = 2 * index + 2;
            }
            if (heap.get(index).value < heap.get(change).value) {
                break;
            }
            swap(index, change);

            index = change;

        }
    }

    private void siftUp(int index) {
        while (index != 0) {
            int top = (index - 1) / 2;
            if (heap.get(top).value < heap.get(index).value) {
                break;
            }
            swap(top, index);
            index = top;
        }
    }

    public int size() {
        return heap.size();
    }

    public void insert(int key, double value) {
        heap.add(new Node(key, value));
        keyToIndex.add(key);
        //System.out.println((keyToIndex.size()-1) + " -> " + key);
        siftUp(keyToIndex.get(key));
    }

    public Node top() {
        Node ans = new Node(heap.get(0).key, heap.get(0).value);
        //System.out.print(ans.key + " " + ans.value);
        swap(0, size() - 1);
        heap.remove(size() - 1);
        //heap.get(size() - 1).value = inf;
        siftDown(0);
        return ans;
    }

    public void changeValue(int key, double newValue) {
        int index = keyToIndex.get(key);
        //System.out.println("index: " + index);
        double value = heap.get(index).value;
        heap.get(index).value = newValue;

        if (newValue < value) {
            //System.out.println("UP");
            siftUp(index);
        } else {
            //System.out.println("DOWN");
            siftDown(index);
        }
    }

    Heap(int size) {
        heap = new ArrayList<>();
        heap.add(new Node(0, 0));

        for (int i = 1; i < size; i++) {
            heap.add(new Node(i, inf));
        }

        keyToIndex = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            keyToIndex.add(i);
        }
    }
}

public class Main {
    public static final long inf = (long) Math.pow(10, 9);

    static class graph {
        int e, w;

        graph(int e, int w) {
            this.e = e;
            this.w = w;
        }
    }

    public static int nextInt(BufferedReader in) throws Exception {
        int ans = 0;
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
        BufferedReader in = new BufferedReader(new FileReader("pathbgep.in"));
        PrintWriter out = new PrintWriter("pathbgep.out");

        int n = nextInt(in);
        //int m = nextInt(in);

        int s = nextInt(in);
        int e = nextInt(in);

        ArrayList<ArrayList<graph>> g = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int next = nextInt(in);
                if (next != -1 && i != j) {
                    g.get(i).add(new graph(j, next));
                }
            }
        }

        /*for (int i = 0; i < m; i++) {
            int a = nextInt(in) - 1, b = nextInt(in) - 1, c = nextInt(in);
            g.get(a).add(new graph(b, c));
        }*/

        /*for (int i = 0; i < n; i++){
            out.print(i + ": ");
            for (int j = 0; j < g.get(i).size(); j++){
                out.print(g.get(i).get(j).e + " " + g.get(i).get(j).w + " " );
            }
            out.println();
        }*/


        double[] d = new double[n];

        //d[0] = 0;

        for (int i = 1; i < n; i++) {
            d[i] = inf;
        }

        d[s - 1] = 0;

        boolean[] used = new boolean[n];

        for (int i = 0; i < n; i++) {
            used[i] = false;
        }

        Heap myHeap = new Heap(n);

        while (myHeap.size() != 0) {

            Node cur = myHeap.top();
            //out.println(cur.key + " " + cur.value);
            int bi = cur.key;

            if (cur.value == inf && bi == e) {
                break;
            }

            used[bi] = true;

            for (int j = 0; j < g.get(bi).size(); j++) {
                int ei = g.get(bi).get(j).e;
                int wi = g.get(bi).get(j).w;

                if (!used[ei] && d[ei] > d[bi] + wi) {
                    d[ei] = d[bi] + wi;
                    myHeap.changeValue(ei, d[ei]);
                }
            }
        }

        /*for (int j = 0; j < n; j++) {
            out.printf("%.0f ", d[j]);
        }*/

        out.printf(d[e - 1] == inf ? "-1" : "%.0f", d[e - 1]);

        out.close();


    }
}