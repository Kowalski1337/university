import javax.smartcardio.ATR;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    public static ArrayList<Character> al = new ArrayList<>();

    static class ways {
        char S;
        ArrayList<Character> nonTerm;
        ArrayList<Character> term;

        ways(char S, ArrayList<Character> nonTerm, ArrayList<Character> term) {
            this.S = S;
            this.nonTerm = nonTerm;
            this.term = term;
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

    public static ArrayList<Character> get(String s) {
        ArrayList<Character> ans = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);

            if (cur >= 'A' && cur <= 'Z') {
                if (!al.contains(cur)) {
                    al.add(cur);
                }
                ans.add(cur);
            }
        }
        return ans;
    }

    public static ArrayList<Character> get1(String s) {
        ArrayList<Character> ans = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char cur = s.charAt(i);

            if (cur >= 'a' && cur <= 'z') {
                if (!al.contains(cur)) {
                    al.add(cur);
                }
                ans.add(cur);
            }
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("epsilon.in"));
        PrintWriter out = new PrintWriter("epsilon.out");

        ArrayList<ways> w = new ArrayList<>();

        int n = nextInt(in);
        char S = (char) in.read();
        String s = in.readLine();

        boolean ans[] = new boolean['Z' - 'A' + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = false;
        }

        HashSet<Character> queue = new HashSet<>();
        boolean change;

        for (int i = 0; i < n; i++) {
            s = in.readLine();
            char cur = s.charAt(0);
            if (!al.contains(cur)) {
                al.add(cur);
            }
            //System.out.println(s.length());
            String lol = s.substring(1, s.length());
            w.add(new ways(cur, get(lol), get1(lol)));
            if (w.get(i).term.size() == 0 && w.get(i).nonTerm.size() == 0) {
                ans[w.get(i).S - 'A'] = true;
                queue.add(w.get(i).S);
            }

        }

        change = true;

        while (change) {
            change = false;
            for (int i = 0; i < n; i++) {
                if (w.get(i).term.size() == 0) {
                    int m = w.get(i).nonTerm.size();
                    for (int j = 0; j < m; j++) {
                        if (!queue.contains(w.get(i).nonTerm.get(j))) {
                            break;
                        }
                        if (j == m - 1) {
                            //used[i] = true;
                            if (!queue.contains(w.get(i).S)) {
                                queue.add(w.get(i).S);
                                ans[w.get(i).S - 'A'] = true;
                                change = true;
                            }
                        }
                    }
                }
            }
        }


       /* for (int i = 0; i < al.size(); i++) {
            if (!queue.contains(al.get(i))) {
                ans[al.get(i) - 'A'] = false;
            }
        }*/

        for (int i = 0; i < ans.length; i++) {
            if (ans[i]) {
                out.print((char) (i + 'A') + " ");
            }
        }

        out.close();

    }
}