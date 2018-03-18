import javax.smartcardio.ATR;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {

    public static final int inf = 1000000007;

    public static boolean d[][][];
    public static boolean h[][][][];

    static class ways {
        char S;
        String right;

        ways(char S, String right) {
            this.right = right;
            this.S = S;
        }
    }

    public static int nextInt(BufferedReader sc) throws Exception {
        int a = 0;
        int b = sc.read();
        while (b < '0' || b > '9') b = sc.read();
        while (b >= '0' && b <= '9') {
            a = a * 10 + (b - '0');
            b = sc.read();
        }
        return a;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("cf.in"));
        PrintWriter out = new PrintWriter("cf.out");

        int n = nextInt(in);
        char S = (char) in.read();

        ArrayList<ways> productions = new ArrayList<>();

        ArrayList<ArrayList<Integer>> nonTerms = new ArrayList<>();

        for (int i = 'A'; i <= 'Z'; i++) {
            nonTerms.add(new ArrayList<>());
        }

        String cur = in.readLine();

        for (int i = 0; i < n; i++) {
            cur = in.readLine();
            char s = cur.charAt(0);
            nonTerms.get(s - 'A').add(i);

            if (cur.length() == 4) {
                productions.add(new ways(s, ""));
            } else {
                productions.add(new ways(s, cur.substring(5, cur.length())));
            }
        }

        String check = in.readLine();
        //out.println(check);

        int m = check.length();

        d = new boolean[26][m][m + 1];
        h = new boolean[n][m][m + 1][6];

        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < m + 1; k++) {
                    d[i][j][k] = false;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < m + 1; k++) {
                    for (int t = 0; t < 6; t++) {
                        h[i][j][k][t] = false;
                    }
                }
            }
        }

        for (int i = 'A'; i <= 'Z'; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < nonTerms.get(i - 'A').size(); k++) {
                    if (productions.get(nonTerms.get(i - 'A').get(k)).right.length() == 1
                            && productions.get(nonTerms.get(i - 'A').get(k)).right.charAt(0) == check.charAt(j)) {
                        d[i - 'A'][j][j + 1] = true;
                    }
                    if (productions.get(nonTerms.get(i - 'A').get(k)).right.length() == 0) {
                        d[i - 'A'][j][j] = true;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                h[i][j][j][0] = true;
            }
        }

        for (int dl = 1; dl <= m; dl++) {
            for (int i = 0; i <= m - dl ; i++) {
                int j = i + dl;
                for (int g = 1; g < 6; g++) {
                    for (int r = 0; r < n; r++) {
                        if (g > productions.get(r).right.length()) break;
                        char div = productions.get(r).right.charAt(g-1);
                        h[r][i][j][g] = h[r][i][j][g - 1] && div >= 'A' && div <= 'Z' && d[div - 'A'][j][j];
                        for (int x = i; x < j; x++) {
                            h[r][i][j][g] = h[r][i][j][g] ||
                                    h[r][i][x][g - 1] && (div >= 'A' && div <= 'Z' && d[div - 'A'][x][j] || check.charAt(x) == div);
                        }
                    }
                }
            }
            for (int i = 0; i < m; i++) {
                for (int j = i + 2; j <= m; j++) {
                    for (int k = 0; k < 26; k++) {
                        for (int t = 0; t < nonTerms.get(k).size(); t++) {
                            d[k][i][j] = d[k][i][j] || h[nonTerms.get(k).get(t)][i][j][productions.get(nonTerms.get(k).get(t)).right.length()];
                        }
                    }
                }
            }
        }



        out.println(d[S - 'A'][0][m] ? "yes" : "no");
        out.close();
    }
}