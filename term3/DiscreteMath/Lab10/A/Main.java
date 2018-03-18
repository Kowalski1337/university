import javafx.util.Pair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main {

    static class Heap {
        private ArrayList<Integer> heap;
        private int size;

        Heap() {
            heap = new ArrayList<>();
            size = 0;
        }

        public int getSize() {
            return size;
        }

        private void swap(int a, int b) {
            int k = heap.get(a);
            heap.set(a, heap.get(b));
            heap.set(b, k);
        }

        private void siftUp(int v) {
            while (heap.get(v) < heap.get((v - 1) / 2)) {
                swap(v, (v - 1) / 2);
                v = (v - 1) / 2;
            }
        }

        private void siftDown(int v) {
            while (2 * v + 1 < size) {
                int left = 2 * v + 1;
                int right = 2 * v + 2;

                int who = left;

                if (right < size && heap.get(right) < heap.get(left)) {
                    who = right;
                }
                if (heap.get(who) >= heap.get(v)) {
                    break;
                }
                swap(v, who);
                v = who;
            }
        }

        public void insert(int key) {
            heap.add(key);
            size++;
            siftUp(size - 1);
        }

        public int extractMin() {
            int ans = heap.get(0);
            size--;
            heap.set(0, heap.get(size));
            heap.remove(size);
            siftDown(0);
            return ans;
        }

        public int getMin() {
            return heap.get(0);
        }
    }

    static class MyReaderWriter {
        private BufferedReader in;
        private BufferedWriter out;

        MyReaderWriter(boolean fromFile, String read, String write) throws IOException {
            in = new BufferedReader(fromFile ? new FileReader(read) : new InputStreamReader(System.in));
            out = new BufferedWriter(fromFile ? new FileWriter(write) : new OutputStreamWriter(System.out));
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

        public void print(int what) throws IOException {
            out.write(Integer.toString(what));
        }

        public void println(int what) throws IOException {
            out.write(Integer.toString(what) + '\n');
        }

        public void println(long what) throws IOException {
            out.write(Long.toString(what) + '\n');
        }

        public void close() throws IOException {
            in.close();
            out.close();
        }
    }

    public static void main(String[] args) {
        try {

            MyReaderWriter rw = new MyReaderWriter(true, "schedule.in", "schedule.out");
            ArrayList<Pair<Integer, Integer>> deadLines = new ArrayList<>();

            int n = rw.nextInt();

            for (int i = 0; i < n; i++) {
                deadLines.add(new Pair<>(rw.nextInt(), rw.nextInt()));
            }
            Collections.sort(deadLines, Comparator.comparing(Pair::getKey));

            Heap heap = new Heap();
            long ans = 0;

            int curTime = 1;
            for (int i = 0; i < n; i++) {
                /*if (deadLines.get(i).getKey() > heap.getSize()) {
                    heap.insert(deadLines.get(i).getValue());
                    continue;
                }
                if (heap.getMin() >= deadLines.get(i).getValue()) {
                    ans += deadLines.get(i).getValue();
                } else {
                    ans += heap.extractMin();
                    heap.insert(deadLines.get(i).getValue());
                }*/

                /*if (deadLines.get(i).getKey() == 0){
                    ans += deadLines.get(i).getValue();
                } else {
                    if (heap.getSize() < deadLines.get(i).getKey()){
                        heap.insert(deadLines.get(i).getValue());
                    } else {
                        if (heap.getMin() < deadLines.get(i).getValue()){
                            ans += heap.extractMin();
                            heap.insert(deadLines.get(i).getValue());
                        }
                        else {
                            ans += deadLines.get(i).getValue();
                        }
                    }
                }*/

                /*if (deadLines.get(i).getKey() == 0){
                    ans += deadLines.get(i).getValue();
                    continue;
                }

                heap.insert(deadLines.get(i).getValue());
                if (deadLines.get(i).getKey() >= curTime){
                    curTime++;
                } else {
                    ans += heap.extractMin();
                }*/
                heap.insert(deadLines.get(i).getValue());
                if (deadLines.get(i).getKey() < heap.getSize()){
                    ans += heap.extractMin();
                }
            }

            rw.println(ans);

            rw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
