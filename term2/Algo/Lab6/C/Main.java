import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by vadim on 08.05.2017.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("pathsg.in"));
        PrintWriter out = new PrintWriter("pathsg.out");

        int n = in.nextInt();

        int l = in.nextInt();

        int[][] d = new int[n][n];

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                d[i][j] = i == j ? 0 : (int)Math.pow(10,9);
            }
        }

        for (int i = 0; i < l; i++){
            int a = in.nextInt() - 1, b = in.nextInt() - 1, c = in.nextInt();
            d[a][b] = c;
        }

        /*for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                out.print(d[i][j] + " ");
            }
            out.println();
        }*/

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    d[i][j] = Math.min(d[i][j], d[i][k] + d[k][j]);
                }
            }
        }

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                out.print(d[i][j] + " ");
            }
            out.println();
        }

        out.close();

    }
}
