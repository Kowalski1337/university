import java.util.Scanner;
import java.io.*;


public class Bridge {

    public static int m1(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    public static int m2(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    public static int[][][] d = new int[200][200][200];

    public static int[][] SWAG = new int[200][200];

   /* public static int calc(int x, int a, int y, int b, int l) {
        if (d[x][y][l] == -1) {
            if (l == 1) {
                d[x][y][l] = x * a + y * b;
            } else {
                for (int j = 1; j <= x + y - l + 1; j++) {
                    for (int k = m2(0, j - y); k <= m1(x, j); k++) {
                        d[x][y][l] = m2(d[x][y][l], m1(k * a + (j - k) * b, calc(x - k, a, y - (j - k), b, l - 1)));
                    }
                }
            }
        }
        return d[x][y][l];
    }

    public static int calc2(int w, int x, int a, int b, int l) {
        if (SWAG[x][l] == -1) {
            if (l == 1) {
                int y = 0;
                while (x * a + y * b < w) {
                    y++;
                }
                SWAG[x][l] = y;
            } else {
                SWAG[x][l] = Integer.MAX_VALUE;
                int i = 0;
                while (i * a <= w && i <= x) {
                    int y = 0;
                    while (y * b + i * a < w) {
                        y++;
                    }
                    SWAG[x][l] = m1(SWAG[x][l], y + calc2(w, x - i, a, b, l - 1));
                    i++;
                }
            }
        }
        return SWAG[x][l];
    }*/

    public static void iter(int w, int x, int a, int b, int l) {
        for (int i = 0; i < x + 1; i++) {
            int cp = w - i * a;
            int y;
            if (cp < 0) {
                y = 0;
            } else {
                y = cp / b;
                if (y * b < cp) {
                    y++;
                }
            }

            /*while (b * y + a * i < w) {
                y++;
            }*/
            SWAG[i][1] = y;
        }

        for (int i = 2; i < l + 1; i++)

        {
            for (int j = 0; j < x + 1; j++) {
                SWAG[j][i] = Integer.MAX_VALUE;
                int k = 0;
                while ((k * a <= w || (k * a > w && (k - 1) * a < w)) && k <= j) {
                    int cp = w - k * a;
                    int y;
                    if (cp < 0) {
                        y = 0;
                    } else {
                        y = cp / b;
                        if (y * b < cp) {
                            y++;
                        }
                    }
                    SWAG[j][i] = m1(SWAG[j][i], y + SWAG[j - k][i - 1]);
                    k++;
                }
            }
        }

    }


    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("bridge.in"));
        PrintWriter out = new PrintWriter("bridge.out");

        int x = in.nextInt();
        int a = in.nextInt();
        int y = in.nextInt();
        int b = in.nextInt();
        int l = in.nextInt();

        /*for (int i = 0; i < x + 1; i++) {
            for (int j = 0; j < y + 1; j++) {
                for (int k = 0; k < l + 1; k++) {
                    d[i][j][k] = -1;
                }
            }
        }*/


        int left = m1(a, b) - 1;
        int right = a * x + b * y + 1;

        while (right > left + 1) {
            int m = left + (right - left) / 2;
            for (int i = 0; i < x + 1; i++) {
                for (int j = 0; j < l + 1; j++) {
                    SWAG[i][j] = -1;
                }
            }

            iter(m, x, a, b, l);

            /*out.print(m + " ");


            out.println(SWAG[x][l]);

            for (int i = 0; i < x + 1; i++) {
                for (int j = 0; j < l + 1; j++) {
                    out.print(SWAG[i][j] + " ");
                }
                out.println("");
            }*/

            if (SWAG[x][l] > y) {
                right = m;
            } else {
                left = m;
            }
        }

        out.println(left);
        out.close();


    }
}