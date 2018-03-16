import java.util.Scanner;
import java.io.*;


public class Lis {

    public static int m(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public static int bin_search(long[] a, int l, int r, int key) {
        while (r > l + 1) {
            int m = (r - l) / 2 + l;
            if (a[m] >= key) {
                r = m;
            } else {
                l = m;
            }
        }
        return r;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("lis.in"));
        PrintWriter out = new PrintWriter("lis.out");

        int n = in.nextInt();

        int[] a = new int[n];

        for (int i = 0; i < n; i++) {
            a[i] = in.nextInt();
        }

        int[] pos = new int[n + 1];
        int[] prev = new int[n];
        int length = 0;

        pos[0] = -1;

        long[] D = new long[n + 1];

        D[0] = -Long.MAX_VALUE;
        for (int i = 1; i < n + 1; i++) {
            D[i] = Long.MAX_VALUE;
        }

        for (int i = 0; i < n; i++) {
            int j = bin_search(D, -1, n + 1, a[i]);
            if (a[i] < D[j] && a[i] > D[j - 1]) {
                D[j] = a[i];
                pos[j] = i;
                prev[i] = pos[j - 1];
                length = m(length, j);
            }
        }

        out.println(length);

        int[] result = new int[length];

        int i = 0;

        int cur = pos[length];
        while (cur != -1) {
            result[i++] = a[cur];
            cur = prev[cur];
        }

        for (i = 0; i < length; i++) {
            out.print(result[length - i - 1] + " ");
        }

        out.close();
    }
}