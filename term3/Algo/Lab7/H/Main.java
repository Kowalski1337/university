import com.sun.javafx.image.IntPixelGetter;
import com.sun.org.apache.bcel.internal.generic.SWAP;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.function.DoublePredicate;


public class Main {
    public static ArrayList<Integer> Mabel = new ArrayList<>();
    public static ArrayList<Integer> Dipper = new ArrayList<>();
    public static int logN = 20;
    public static int n;
    public static int dp[][];

    public static int lca(int x, int y) {
        if (Dipper.get(y) > Dipper.get(x)) {
            int temp = x;
            x = y;
            y = temp;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (Dipper.get(dp[x][i]) >= Dipper.get(y)) {
                x = dp[x][i];
            }
        }

        if (x == y) {
            return x;
        }

        for (int i = logN - 1; i >= 0; i--) {
            if (dp[x][i] != dp[y][i]) {
                x = dp[x][i];
                y = dp[y][i];
            }
        }
        return Mabel.get(x);
    }

    private static int calc(int v) {
        if (Dipper.get(v) != -1) {
            return Dipper.get(v);
        }
        Dipper.set(v, calc(Mabel.get(v)) + 1);
        return Dipper.get(v);
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("ancestor.in"));
        PrintWriter out = new PrintWriter(new File("ancestor.out"));

        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            Mabel.add(0);
            Dipper.add(-1);
        }

        for (int i = 0; i < n; i++) {
            int next = in.nextInt();
            if (next == 0) {
                Mabel.set(i, i);
                Dipper.set(i, 0);
            } else {
                Mabel.set(i, next - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            calc(i);
        }

//        for (int i = 0; i < n; i++) {
//            out.print(Mabel.get(i) + " ");
//        }
//        out.println();
//
//        for (int i = 0; i < n; i++) {
//            out.print(Dipper.get(i) + " ");
//        }
//        out.println();

        dp = new int[n][logN];

        for (int i = 0; i < n; i++) {
            dp[i][0] = Mabel.get(i);
        }
        for (int i = 1; i < logN; i++) {
            for (int j = 0; j < n; j++) {
                dp[j][i] = dp[dp[j][i - 1]][i - 1];
            }
        }

//        for (int i = 0; i < n; i++) {
//            for (int j = 0; j < logN; j++) {
//                out.print(dp[i][j] + " ");
//            }
//            out.println();
//        }

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            out.println(lca(x, y) == x ? 1 : 0);
        }

        out.close();
    }
}
