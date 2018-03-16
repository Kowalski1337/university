import com.sun.javafx.image.IntPixelGetter;
import com.sun.org.apache.bcel.internal.generic.SWAP;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


public class Main {
    private static ArrayList<Pair<Integer, Long>> Mabel = new ArrayList<>();
    private static ArrayList<Integer> Dipper = new ArrayList<>();
    private static int logN = 20;
    private static Pair<Integer, Long>[][] dp;
    private static final long INF = Long.MAX_VALUE;

    private static long lca(int x, int y) {
        long ans = INF;
        if (Dipper.get(y) > Dipper.get(x)) {
            int temp = x;
            x = y;
            y = temp;
        }
        for (int i = logN - 1; i >= 0; i--) {
            if (Dipper.get(dp[x][i].getKey()) >= Dipper.get(y)) {
                ans = Math.min(ans, dp[x][i].getValue());
                x = dp[x][i].getKey();
            }
        }

        if (x == y) {
            return ans;
        }

        for (int i = logN - 1; i >= 0; i--) {
            if (!Objects.equals(dp[x][i].getKey(), dp[y][i].getKey())) {
                ans = Math.min(ans, Math.min(dp[x][i].getValue(), dp[y][i].getValue()));
                x = dp[x][i].getKey();
                y = dp[y][i].getKey();
            }
        }
        return Math.min(ans, Math.min(dp[x][0].getValue(), dp[y][0].getValue()));
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("minonpath.in"));
        PrintWriter out = new PrintWriter(new File("minonpath.out"));

        int n = in.nextInt();
        Mabel.add(new Pair<>(0, INF));
        Dipper.add(0);
        for (int i = 1; i < n; i++) {
            Mabel.add(new Pair<>(in.nextInt() - 1, in.nextLong()));
            Dipper.add(Dipper.get(Mabel.get(Mabel.size() - 1).getKey()) + 1);
        }

        dp = new Pair[n][logN];

        for (int i = 0; i < n; i++) {
            dp[i][0] = Mabel.get(i);
        }

        for (int i = 1; i < logN; i++) {
            for (int j = 0; j < n; j++) {
                dp[j][i] = new Pair<>(dp[dp[j][i - 1].getKey()][i - 1].getKey(),
                        Math.min(dp[j][i - 1].getValue(), dp[dp[j][i - 1].getKey()][i - 1].getValue()));
            }
        }

        int m = in.nextInt();
        for (int i = 0; i < m; i++) {
            int x = in.nextInt() - 1;
            int y = in.nextInt() - 1;
            out.println(lca(x, y));
        }

        out.close();
    }
}
