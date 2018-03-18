import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by vadim on 17.05.2017.
 */
public class Main {
    public static ArrayList<Character> al = new ArrayList<>();

    static class ways {
        char S;
        ArrayList<Character> nonTerm;

        //HashSet<Integer> containsTerm;
        ways(char S, ArrayList<Character> nonTerm) {
            this.S = S;
            //this.containsTerm = containsTerm;
            this.nonTerm = nonTerm;
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

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("useless.in"));
        PrintWriter out = new PrintWriter("useless.out");

        ArrayList<ways> w = new ArrayList<>();

        int n = nextInt(in);
        char S = (char) in.read();
        al.add(S);
        String s = in.readLine();

        for (int i = 0; i < n; i++) {
            s = in.readLine();
            char cur = s.charAt(0);
            if (!al.contains(cur)) {
                al.add(cur);
            }
            w.add(new ways(cur, get(s.substring(1, s.length()))));

        }

        //ArrayList<Character> solve = new ArrayList<>();
        boolean used[] = new boolean[n];
        boolean used1[] = new boolean[n];
        for (int i = 0; i < n; i++) {
            used1[i] = used[i] = true;
        }
        HashSet<Character> queue = new HashSet<>();
        boolean change;

        boolean ans[] = new boolean['Z' - 'A' + 1];
        for (int i = 0; i < ans.length; i++) {
            ans[i] = false;
        }

        for (int i = 0; i < n; i++) {
            if (w.get(i).nonTerm.size() == 0) {
                queue.add(w.get(i).S);
            }
        }

        change = true;

        while (change) {
            change = false;
            for (int i = 0; i < n; i++) {
                int m = w.get(i).nonTerm.size();
                for (int j = 0; j < m; j++) {
                    if (!queue.contains(w.get(i).nonTerm.get(j))) {
                        break;
                    }
                    if (j == m - 1) {
                        //used[i] = true;
                        if (!queue.contains(w.get(i).S)) {
                            queue.add(w.get(i).S);
                            change = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++){
            if (queue.contains(w.get(i).S)){
                for (int j = 0; j < w.get(i).nonTerm.size(); j++){
                    if (!queue.contains(w.get(i).nonTerm.get(j))){
                        used[i] = false;
                    }
                }
                continue;
            }
            used[i] = false;
        }


        /*for (int i = 0; i < n; i++) {
            if (!used[i]) {
                if (!queue.contains(w.get(i).S)) {
                    solve.add(w.get(i).S);
                }
                for (int j = 0; j < w.get(i).nonTerm.size(); j++) {
                    if (!queue.contains(w.get(i).nonTerm.get(j))) {
                        solve.add(w.get(i).nonTerm.get(j));
                    }
                }
            }
        }*/

        /*for (int i = 0; i < al.size(); i++) {
            if (!queue.contains(al.get(i)) && !solve.contains(al.get(i))) {
                solve.add(al.get(i));
            }
        }*/

        HashSet<Character> queue1 = new HashSet<>();
        queue1.add(S);

        change = true;
        while (change) {
            change = false;
            for (int i = 0; i < n; i++) {
                if (used[i] && queue1.contains(w.get(i).S) /*&& queue.contains(w.get(i).S)*/) {
                    int m = w.get(i).nonTerm.size();
                    for (int j = 0; j < m; j++) {
                        if (!queue1.contains(w.get(i).nonTerm.get(j))) {
                            queue1.add(w.get(i).nonTerm.get(j));
                            //used1[i] = true;
                            change = true;
                        }
                    }
                }
            }
        }

        for (int i = 0; i < n; i++){
            if (queue1.contains(w.get(i).S)){
                for (int j = 0; j < w.get(i).nonTerm.size(); j++){
                    if (!queue1.contains(w.get(i).nonTerm.get(j))){
                        used1[i] = false;
                    }
                }
                continue;
            }
            used1[i] = false;
        }

        /*for (int i = 0; i < al.size(); i++) {
            if (!queue1.contains(al.get(i)) && !solve.contains(al.get(i))) {
                solve.add(al.get(i));
            }
        }*/

        ArrayList<Character> godno = new ArrayList<>();

        for (int i = 0; i < n; i++){
            if (used[i] && used1[i]){
                if (!godno.contains(w.get(i).S)){
                    godno.add(w.get(i).S);
                }
                for (int j = 0; j < w.get(i).nonTerm.size(); j++){
                    if (!godno.contains(w.get(i).nonTerm.get(j))){
                        godno.add(w.get(i).nonTerm.get(j));
                    }
                }
            }
        }


        for (int i = 0; i < al.size(); i++) {
            if (!godno.contains(al.get(i))) {
                ans[al.get(i) - 'A'] = true;
            }
        }

        for (int i = 0; i < ans.length; i++) {
            if (ans[i]) {
                out.print((char) (i + 'A') + " ");
            }
        }

        out.close();

    }
}
