import java.util.Scanner;
import java.io.*;


public class Bookshelf {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("bookshelf.in"));
        PrintWriter out = new PrintWriter("bookshelf.out");

        int n = in.nextInt();
        int l = in.nextInt();

        int[] h = new int[n];
        int[] w = new int[n];

        for (int i = 0; i < n; i++) {
            h[i] = in.nextInt();
            w[i] = in.nextInt();
        }

        int[] d = new int[n + 1];
        d[0] = 0;

        for (int i = 1; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            int j = i - 1;
            int weigth = 0;
            int max = 0;
            while (j >= 0 && weigth + w[j] <= l) {
                weigth += w[j];
                if (h[j] > max) {
                    max = h[j];
                }
                if (max + d[j] < min) {
                    min = max + d[j];
                }
                j--;
            }
            d[i] = min;
        }
        out.println(d[n]);
        out.close();
    }
}
