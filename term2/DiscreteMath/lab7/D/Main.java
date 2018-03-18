import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Main {

    private static final int inf = 1000000007;

    private static long d[][][];

    static class pair {
        char first, second;

        pair(char first, char second) {
            this.first = first;
            this.second = second;
        }
    }

    static class ways {
        ArrayList<pair> next;
        ArrayList<Character> term;

        public void add(char first, char second) {
            for (pair aNext : next) {
                if (aNext.first == first && aNext.second == second) {
                    return;
                }
            }
            next.add(new pair(first, second));
        }

        ways() {
            next = new ArrayList<>();
            term = new ArrayList<>();
        }
    }

    private static int nextInt(BufferedReader sc) throws Exception {
        int a = 0;
        int b = sc.read();
        while (b < '0' || b > '9') b = sc.read();
        while (b >= '0' && b <= '9') {
            a = a * 10 + (b - '0');
            b = sc.read();
        }
        return a;
    }

    private static long calc(ArrayList<ways> w, String check, int i, int j, int k) {
        //System.out.println(i + " "  + j  + " " + k);
        if (d[i][j][k] != -1) {
            return d[i][j][k];
        }

        if (k == j) {
            d[i][j][k] = w.get(i).term.contains(check.charAt(j)) ? 1 : 0;
            return d[i][j][k];
        }

        d[i][j][k] = 0;

        for (int s = 0; s < w.get(i).next.size(); s++) {
            int first = w.get(i).next.get(s).first - 'A';
            int second = w.get(i).next.get(s).second - 'A';
            //System.out.println(first + " " + second);
            for (int g = j; g < k; g++) {
                d[i][j][k] += calc(w, check, first, j, g) * calc(w, check, second, g + 1, k) % inf;
                d[i][j][k] %= inf;
            }
        }
        return d[i][j][k] % inf;
    }


    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("nfc.in"));
        PrintWriter out = new PrintWriter("nfc.out");

        ArrayList<ways> w = new ArrayList<>();

        for (int i = 'a'; i <= 'z'; i++) {
            w.add(new ways());
        }

        int n = nextInt(in);
        char S = (char) in.read();
        String s = in.readLine();


        for (int i = 0; i < n; i++) {
            s = in.readLine();
            char cur = s.charAt(0);
            //System.out.println(s.length());
            String lol = s.substring(5, s.length());
            //System.out.println(lol);
            if (lol.length() == 1) {
                if (!w.get(cur - 'A').term.contains(s.charAt(5))) {
                    w.get(cur - 'A').term.add(s.charAt(5));
                }
            } else {
                //w.get(cur - 'A').add(s.charAt(5), s.charAt(6));
                w.get(cur - 'A').next.add(new pair(s.charAt(5), s.charAt(6)));
            }

        }

        String check = in.readLine();

        int m = check.length();

        d = new long['Z' - 'A' + 1][m][m];

        for (int i = 0; i < 'Z' - 'A' + 1; i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < m; k++) {
                    d[i][j][k] = -1;
                }
            }
        }

        out.println(calc(w, check, S - 'A', 0, m - 1) % inf);

        out.close();


    }
}