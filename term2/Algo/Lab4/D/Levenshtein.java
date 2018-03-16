import java.util.Scanner;
import java.io.*;


public class Levenshtein {
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("levenshtein.in"));
        PrintWriter out = new PrintWriter("levenshtein.out");

        String a = "";
        String b = "";

        if (in.hasNextLine()){
            a = in.nextLine();
        }

        if (in.hasNextLine()){
            b = in.nextLine();
        }

        int n = a.length();
        int m = b.length();

        int[][] d = new int[n + 1][m + 1];

        d[0][0] = 0;

        for (int i = 1; i <= n; i++) {
            d[i][0] = d[i - 1][0] + 1;
        }

        for (int i = 1; i <= m; i++) {
            d[0][i] = d[0][i - 1] + 1;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (a.charAt(i - 1) != b.charAt(j - 1)) {
                    d[i][j] = min(min(d[i - 1][j], d[i][j - 1]), d[i - 1][j - 1]) + 1;
                } else {
                    d[i][j] = d[i - 1][j - 1];
                }
            }
        }

        out.println(d[n][m]);
        out.close();
    }

    static int min(int a, int b) {
        if (a < b) return a;
        return b;
    }
}
