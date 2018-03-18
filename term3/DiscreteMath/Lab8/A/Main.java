import com.sun.javafx.image.IntPixelGetter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Queue;
import java.util.Scanner;

public class Main {

    public static boolean m[][];

    public static int nextInt(BufferedReader in) throws IOException {
        int ch = in.read();
        while (ch > '9' || ch < '0') {
            ch = in.read();
        }
        int a = 0;
        while (ch >= '0' && ch <= '9') {
            a = 10 * a + ch - '0';
            ch = in.read();
        }
        return a;
    }

    public static boolean nextByte(BufferedReader in) throws IOException {
        int ch = in.read();
        while (ch != '0' && ch != '1') {
            ch = in.read();
        }
        return ch == '1';
    }


    public static boolean check(int i, int j) {
        return m[Math.min(i, j)][Math.max(i, j)];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("fullham.in"));
        PrintWriter out = new PrintWriter("fullham.out");


        int n = nextInt(in);

        m = new boolean[n][n];

        //String s = in.readLine();

        for (int i = 0; i <= n - 1; i++) {
            //s = in.readLine();
            for (int j = 0; j < i; j++) {
                m[j][i] = nextByte(in);
            }
        }

        int f = 0;
        ArrayList<Integer> lol = new ArrayList<>();
        int r = 0;
        for (int i = 0; i < n; i++) {
            lol.add(i);
            if (i < n - 1 && m[i][i + 1]) {
                r++;
            }
        }
        if (m[0][n - 1]) {
            r++;
        }

        for (int i = 0; i < n * (n - 1); i++) {
            if (!check(lol.get(0), lol.get(1))) {
                int j = 2;

                while (!check(lol.get(0), lol.get(j)) || !check(lol.get(1), lol.get(j + 1))){
                    j++;
                }

                int k = 0;
                while (1 + k < j - k) {
                    int a = lol.get(1 + k);
                    lol.set(1 + k, lol.get(j - k));
                    lol.set(j - k, a);
                    k++;
                }
                r++;
                if (!check(lol.get(1), lol.get(j + 1))) {
                    r++;
                }
            }
            if (r == n) {
                break;
            }
            lol.add(lol.remove(0));
        }

        for (int i = 0; i < n; i++) {
            out.print((lol.get(i) + 1) + " ");
        }

        out.close();
    }
}
