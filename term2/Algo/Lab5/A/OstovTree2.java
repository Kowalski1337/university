import java.io.File;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

class ways {
    int w, b, e;
}

/**
 * Created by vadim on 13.04.2017.
 */
public class OstovTree2 {
    public static ways g[];

    public static void sort(int l, int r) {
        if (l >= r) {
            return;
        }

        int m = l + (r - l) / 2;
        int key = g[m].w;

        int i = l, j = r;
        while (i <= j) {
            while (g[i].w < key) {
                i++;
            }
            while (g[j].w > key) {
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

    public static void swap(int i, int j) {
        int temp = g[i].b;
        g[i].b = g[j].b;
        g[j].b = temp;

        temp = g[i].e;
        g[i].e = g[j].e;
        g[j].e = temp;

        temp = g[i].w;
        g[i].w = g[j].w;
        g[j].w = temp;

    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("spantree2.in"));
        PrintWriter out = new PrintWriter("spantree2.out");

        int n = in.nextInt();
        int m = in.nextInt();

        g = new ways[m];

        for (int i = 0; i < m; i++) {
            g[i] = new ways();
            g[i].b = in.nextInt();
            g[i].e = in.nextInt();
            g[i].w = in.nextInt();
        }

        sort(0, m - 1);


        int id[] = new int[n + 1];

        for (int i = 1; i <= n; i++) {
            id[i] = i;
        }

        long res = 0;

        for (int i = 0; i < m; i++) {
            int bi = g[i].b;
            int ei = g[i].e;
            int wi = g[i].w;

            if (id[bi] != id[ei]) {
                res += wi;
                int newId = id[bi];
                int oldId = id[ei];
                for (int j = 1; j <= n; j++) {
                    if (id[j] == oldId) {
                        id[j] = newId;
                    }
                }
            }
        }

        out.print(res);
        out.close();

    }
}
