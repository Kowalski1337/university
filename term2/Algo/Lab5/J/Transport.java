import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by vadim on 18.04.2017.
 */
class ways {
    int b, e;
    double w;
}
public class Transport {
    public static ways g[];
    public static void sort(int l, int r) {
        if (l >= r) {
            return;
        }

        int m = l + (r - l) / 2;
        double key = g[m].w;

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

        double tempd;

        tempd = g[i].w;
        g[i].w = g[j].w;
        g[j].w = tempd;

    }
    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(new File("transport.in"));
        PrintWriter out = new PrintWriter("transport.out");
        in.useLocale(Locale.UK);
        Locale.setDefault(Locale.US);

        int n = in.nextInt();
        long[] x = new long[n];
        long[] y = new long[n];

        for (int i = 0; i < n; i++){
            x[i] = in.nextLong();
        }
        for (int i = 0; i < n; i++){
            y[i] = in.nextLong();
        }

        double R = in.nextDouble();
        double A = in.nextDouble();



        int m = n*(n-1)/2;

        double res1 =  0;

        g = new ways[m + n];

        int index = -1;

        for (int i = 0; i < n; i++){
            for (int j = i + 1; j < n; j++){
                index++;
                g[index] = new ways();
                g[index].b = i;
                g[index].e = j;
                g[index].w = R*Math.sqrt((x[i]-x[j])*(x[i]-x[j]) + (y[i]-y[j])*(y[i]-y[j]));
                //out.println(g[index].w);
            }
        }
        sort(0, m-1);

       /* for (int i = 0; i < m; i++){
            out.println(g[i].b + " " + g[i].e  + " " + g[i].w);
        }*/


        int id[] = new int[n + 1];

        for (int i = 0; i < n; i++) {
            id[i] = i;
        }

        for (int i = 0; i < m; i++) {
            int bi = g[i].b;
            int ei = g[i].e;
            double wi = g[i].w;

            if (id[bi] != id[ei]) {
                res1 += wi;
                int newId = id[bi];
                int oldId = id[ei];
                for (int j = 0; j < n; j++) {
                    if (id[j] == oldId) {
                        id[j] = newId;
                    }
                }
            }
        }

        for (int i = 0; i <= n; i++){
            id[i] = i;
        }
        index = 0;
        for (int i = m; i < m + n; i++){
            g[i] = new ways();
            g[i].b = index++;
            g[i].e = n;
            g[i].w = A;
        }

        sort(0, m + n - 1);

        for (int i = 0; i < n+ m; i++){
            out.println(g[i].w + " " +g[i].b + " " +g[i].e );
        }

        double res2 = 0;

        for (int i = 0; i < m + n; i++) {
            int bi = g[i].b;
            int ei = g[i].e;
            double wi = g[i].w;

            if (id[bi] != id[ei]) {
                res2 += wi;
                int newId = id[bi];
                int oldId = id[ei];
                for (int j = 0; j <= n; j++) {
                    if (id[j] == oldId) {
                        id[j] = newId;
                    }
                }
            }
        }

        out.println(res1 + " " + res2);

        out.printf("%.12f", Math.min(res1, res2));
        out.close();
    }
}

