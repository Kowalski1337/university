import java.io.*;
import java.util.Scanner;

public class Rofl {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        String template = in.nextLine();
        String example = in.nextLine();

        int n = template.length();
        int m = example.length();

        boolean[][] d = new boolean[n + 1][m + 1];

        d[0][0] = true;

        for (int i = 1; i <= n; i++) {
            d[i][0] = d[i - 1][0] && (template.charAt(i - 1) == '*');
        }

        for (int i = 1; i <= m; i++) {
            d[0][i] = false;
        }

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (template.charAt(i - 1) == '*') {
                    d[i][j] = d[i - 1][j] || d[i][j - 1] || d[i - 1][j - 1];
                } else {
                    if (template.charAt(i - 1) == '?') {
                        d[i][j] = d[i - 1][j - 1];
                    } else {
                        if (template.charAt(i - 1) == example.charAt(j - 1)){
                            d[i][j] = d[i-1][j-1];
                        }else {
                            d[i][j] = false;
                        }
                    }
                }
            }
        }

        if (d[n][m] == true) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }
}