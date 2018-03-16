import java.util.Scanner;
import java.io.*;

class problems {
    int[] s;
    int[] p;
    int[] t;
    int[] index;
}

class dynamic {
    boolean[] was;
    long R;
}

public class Practice {

    public static problems k = new problems();

    public static void quickSort(int l, int r) {
        if (l >= r) {
            return;
        }

        int m = l + (r - l) / 2;
        int key = k.s[m];

        int i = l, j = r;
        while (i <= j) {
            while (k.s[i] < key) {
                i++;
            }
            while (k.s[j] > key) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (l < j) {
            quickSort(l, j);
        }
        if (r > i) {
            quickSort(i, r);
        }
    }

    public static void swap(int x, int y) {
        int temp = k.s[x];
        k.s[x] = k.s[y];
        k.s[y] = temp;

        temp = k.p[x];
        k.p[x] = k.p[y];
        k.p[y] = temp;

        temp = k.t[x];
        k.t[x] = k.t[y];
        k.t[y] = temp;

        temp = k.index[x];
        k.index[x] = k.index[y];
        k.index[y] = temp;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("practice.in"));
        PrintWriter out = new PrintWriter("practice.out");

        int n = in.nextInt();
        int T = in.nextInt();
        int R0 = in.nextInt();

        k.s = new int[n];
        k.p = new int[n];
        k.t = new int[n];
        k.index = new int[n];


        for (int i = 0; i < n; i++) {
            k.s[i] = in.nextInt();
            k.p[i] = in.nextInt();
            k.t[i] = in.nextInt();
            k.index[i] = i + 1;
        }

        quickSort(0, n - 1);

        /*for (int i = 0; i < n; i++){
            out.print(k.s[i] + " " + k.p[i] + " " +  k.t[i] + " " + k.index[i]);
            out.println("");
        }*/

        dynamic[] d = new dynamic[T + 1];

        for (int i = 0; i <= T; i++) {
            d[i] = new dynamic();
            d[i].was = new boolean[n];
            for (int j = 0; j < n; j++) {
                d[i].was[j] = false;
            }
            d[i].R = R0;
        }

        for (int i = 0; i <= T; i++) {
            int j = 0;
            while (j < n && k.s[j] <= d[i].R) {
                if (!d[i].was[j] && i+k.t[j] <= T && d[i].R + k.p[j] > d[i + k.t[j]].R) {
                    d[i + k.t[j]].R = d[i].R + k.p[j];
                    for (int t = 0; t < n; t++) {
                        d[i + k.t[j]].was[t] = d[i].was[t];
                    }
                    d[i + k.t[j]].was[j] = true;
                }
                j++;
            }
            /*for (int s = i + 1; s <= T; s++) {
                if (d[i].R > d[s].R && !d[i].was[s]) {
                    d[s].R = d[i].R;
                    for (int t = 0; t < n; t++) {
                        d[s].was[t] = d[i].was[t];
                    }
                }
            }*/
        }


        /*for (int i = 0; i <= T; i++) {
            int j = 0;
            while (j < n && (k.s[j] <= d[i].R)) {
                if (!d[i].was[j] && i + k.t[j] <= T && d[i].R + k.p[j] > d[i + k.t[j]].R) {
                    for (int l = k.t[j]; l <= T - i; l++) {
                        d[i + l].R = d[i].R + k.p[j];
                        for (int t = 0; t < n; t++) {
                            d[i + l].was[t] = d[i].was[t];
                        }
                        d[i + l].was[j] = true;
                    }
                }
                j++;
            }
            for (int s = i + 1; s <= T; s++) {
                if (d[i].R > d[s].R) {
                    d[s].R = d[i].R;
                    for (int t = 0; t < n; t++) {
                        d[s].was[t] = d[i].was[t];
                    }
                }
            }
        }*/

        int x = 0;

        for (int i = 0; i <= T; i++) {
            if (d[i].R > d[x].R) {
                x = i;
            }
        }


        //for (int j = 0; j <= T; j++) {
        out.println(d[x].R);
        for (int i = 0; i < n; i++) {
            if (d[x].was[i]) {
                out.print(k.index[i] + " ");
            }
        }
        //out.println("");
        //out.println("");
        //}


        out.close();
    }
}