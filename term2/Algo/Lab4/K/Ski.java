import java.util.Scanner;
import java.io.*;

public class Ski {

    public static int[] d;

    public static void quickSort(int l, int r) {
        if (l >= r) {
            return;
        }

        int m = l + (r - l) / 2;
        int key = d[m];

        int i = l, j = r;
        while (i <= j) {
            while (d[i] < key) {
                i++;
            }
            while (d[j] > key) {
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
        int temp = d[x];
        d[x] = d[y];
        d[y] = temp;
    }

    public static int BinarySearch_Left(int left, int right, int key) {
        while (right > left + 1) {
            int mid = left + (right - left) / 2;
            if (d[mid] < key) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return left;
    }

    public static int BinarySearch_Right(int left, int right, int key) {
        while (right > left + 1) {
            int mid = left + (right - left) / 2;
            if (d[mid] < key) {
                left = mid;
            } else {
                right = mid;
            }
        }

        return right;
    }

    public static int m(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("ski.in"));
        PrintWriter out = new PrintWriter("ski.out");

        int n = in.nextInt();
        int k = in.nextInt();
        int m = in.nextInt();

        d = new int[m];

        for (int i = 0; i < m; i++) {
            d[i] = in.nextInt();
        }


        quickSort(0, m - 1);

        for (int i = 0; i < m; i++) {
            out.print(d[i] + " ");
        }

        out.println("");

        int[] a = new int[m];

        for (int i = 0; i < m; i++) {
            int j = BinarySearch_Left(-1, i, d[i] - k + 1);
            out.println(j + " ");
            if (j == -1) {
                a[i] = m(d[i] - k, 0);
                out.println(m(d[i] - k, 0));
            } else {
                a[i] = a[j] + d[i] - d[j] - k;
                out.println((a[j] + d[i] - d[j] - k));
            }
        }

        for (int i = 0; i < m; i++){
            out.print(a[i] + " ");
        }

        int j = BinarySearch_Right(-1, m, d[m - 1] - k + 1);

        //out.println(j);

        int ans = a[m - 1] + n - d[m - 1];

        for (int l = j; l < m; l++) {
            int dx;
            if (l >= 1) {
                dx = a[l - 1] + d[l] - d[l - 1] - 1 + m(0, n - d[l] - k + 1);
            } else {
                dx = d[l] - 1 + m(0, n - d[l] - k + 1);
            }
            if (dx > ans) {
                ans = dx;
            }
        }



        /*out.println("");
        for (int i = 0; i < m; i++) {
            out.print(a[i] + " ");
        }
        out.println("");*/
        out.println(ans);


        out.close();


    }
}