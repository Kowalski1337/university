import javax.smartcardio.ATR;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class Main {

    public static ArrayList<ArrayList<String>> productions = new ArrayList<>();

    public static boolean d[][][];
    public static boolean h[][][];

    static class pair {
        int first, second;

        pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }

    private static int symbol(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return ch;
        }
        return ch + 100;
    }

    private static char symbol2(int ch) {
        if ((char) ch >= 'a' && (char) ch <= 'z' || (char) ch == ' ') {
            return (char) ch;
        }
        return (char) (ch - 100);
    }

    private static ArrayList<ArrayList<pair>> delLong(ArrayList<ArrayList<String>> productions) {
        ArrayList<ArrayList<pair>> ans = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            ans.add(new ArrayList<>());
        }
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                String curRule = productions.get(i).get(j);

                switch (curRule.length()) {
                    case 0:
                        ans.get(i).add(new pair(' ', ' '));
                        break;
                    case 1:
                        ans.get(i).add(new pair(symbol(curRule.charAt(0)), ' '));
                        break;
                    case 2:
                        ans.get(i).add(new pair(symbol(curRule.charAt(0)), symbol(curRule.charAt(1))));
                        break;
                    default:
                        int index = i;
                        for (int k = 0; k < curRule.length() - 2; k++) {
                            ans.add(new ArrayList<>());
                            char next = (char) ('A' + 100 + ans.size() - 1);
                            ans.get(index).add(new pair(symbol(curRule.charAt(k)), next));
                            index = next - 'A' - 100;
                        }
                        ans.get(index).add(new pair(symbol(curRule.charAt(curRule.length() - 2)), symbol(curRule.charAt(curRule.length() - 1))));
                }
            }
        }
        return ans;
    }

    private static HashSet<Integer> findEpsNonTerm(ArrayList<ArrayList<pair>> productions) {
        HashSet<Integer> queue = new HashSet<>();
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                if (productions.get(i).get(j).second == ' ' &&
                        productions.get(i).get(j).first == ' ') {
                    queue.add(i);
                    productions.get(i).remove(j);
                    break;
                }
            }
        }

        boolean change = true;

        while (change) {
            change = false;
            for (int i = 0; i < productions.size(); i++) {
                for (int j = 0; j < productions.get(i).size(); j++) {
                    if (queue.contains(productions.get(i).get(j).first - 'A' - 100)
                            && (queue.contains(productions.get(i).get(j).second - 'A' - 100)
                            || productions.get(i).get(j).second == ' ')) {
                        //productions.get(i).remove(j);
                        if (!queue.contains(i)) {
                            queue.add(i);
                            change = true;
                        }
                    }
                }
            }
        }
        return queue;
    }

    private static ArrayList<ArrayList<pair>> delEps(ArrayList<ArrayList<pair>> productions, int Start) {
        HashSet<Integer> eps = findEpsNonTerm(productions);

        /*for (int i = 0; i < productions.size(); i++) {
            if (eps.contains(i)) {
                System.System.out.println(i);
            }
        }*/
        for (int i = 0; i < productions.size(); i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                if (productions.get(i).get(j).first == ' ' || productions.get(i).get(j).second == ' ') {
                    break;
                }
                int first = productions.get(i).get(j).first - 100 - 'A';
                int second = productions.get(i).get(j).second - 100 - 'A';
                /*if (first < 0) {
                    first = 1;
                }
                if (second < 0) {
                    second = 2;
                }*/

                if (eps.contains(first)) {
                    productions.get(i).add(new pair(second + 100 + 'A', ' '));
                }
                if (eps.contains(second)) {
                    productions.get(i).add(new pair(first + 100 + 'A', ' '));
                }
            }
        }
        if (eps.contains(Start)) {
            productions.get(Start).add(new pair(' ', ' '));
        }
        return productions;
    }

    private static ArrayList<ArrayList<pair>> delChains(ArrayList<ArrayList<pair>> productions) {
        ArrayList<pair> chains = new ArrayList<>();
        for (int i = 0; i < productions.size(); i++) {
            chains.add(new pair(i, i));
        }
        boolean change = true;

        for (int i = 0; i < chains.size(); i++) {
            System.out.println(chains.get(i).first + " " + chains.get(i).second);
        }

        while (change) {
            change = false;
            for (int i = 0; i < productions.size(); i++) {
                for (int j = 0; j < productions.get(i).size(); j++) {
                    if (!(productions.get(i).get(j).first >= 'a'
                            && productions.get(i).get(j).first <= 'z'
                            || productions.get(i).get(j).first == ' ') && productions.get(i).get(j).second == ' ') {
                        for (int k = 0; k < chains.size(); k++) {
                            int f = chains.get(k).first, s = chains.get(k).second;
                            if (s == i) {
                                boolean add = true;
                                int t = productions.get(i).get(j).first - 100 - 'A';
                                for (int h = 0; h < chains.size(); h++) {
                                    if (chains.get(h).first == f && chains.get(h).second == t) {
                                        add = false;
                                        break;
                                    }
                                }
                                if (add) {
                                    change = true;
                                    chains.add(new pair(f, t));
                                }

                            }
                        }

                    }
                }
            }
        }
        for (int i = 0; i < chains.size(); i++) {
            System.out.println(chains.get(i).first + " " + chains.get(i).second);
        }

        for (int i = 0; i < productions.size(); i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                if (!(productions.get(i).get(j).first >= 'a'
                        && productions.get(i).get(j).first <= 'z') && productions.get(i).get(j).second == ' ') {
                    productions.get(i).remove(j);
                }
            }
        }

        for (int i = 0; i < chains.size(); i++) {
            int f = chains.get(i).first;
            int s = chains.get(i).second;
            if (f == s) {
                continue;
            }
            for (int k = 0; k < productions.get(s).size(); k++) {
                productions.get(f).add(new pair(productions.get(s).get(k).first, productions.get(s).get(k).second));
            }
        }
        return productions;
    }

    private static ArrayList<ArrayList<pair>> delExtraRules(ArrayList<ArrayList<pair>> productions, int S) {
        HashSet<Integer> queue = new HashSet<>();
        for (int i = 0; i < productions.size(); i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                int f = productions.get(i).get(j).first;
                int s = productions.get(i).get(j).second;
                if (f >= 'a' && f <= 'z' && (s >= 'a' && s <= 'z' || s == ' ')) {
                    queue.add(i);
                    break;
                }
            }
        }
        /*for (int i = 0; i < productions.size(); i++) {
            if (queue.contains(i)) {
                System.System.out.println(i);
            }
        }*/

        boolean change = true;

        while (change) {
            change = false;
            for (int i = 0; i < productions.size(); i++) {
                for (int j = 0; j < productions.get(i).size(); j++) {
                    int f = productions.get(i).get(j).first;
                    int s = productions.get(i).get(j).second;
                    if ((f >= 'a' && f <= 'z' || queue.contains(f - 100 - 'A'))
                            && (s >= 'a' && s <= 'z' || queue.contains(s - 100 - 'A') || s == ' ') && !queue.contains(i)) {
                        change = true;
                        queue.add(i);
                    }
                }
            }
        }
        /*for (int i = 0; i < productions.size(); i++) {
            if (queue.contains(i)) {
                System.System.out.println(i);
            }
        }*/

        for (int i = 0; i < productions.size(); i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                int f = productions.get(i).get(j).first;
                int s = productions.get(i).get(j).second;
                if (!queue.contains(i)
                        || (!queue.contains(f - 100 - 'A') && !(f >= 'a' && f <= 'z'))
                        || (!queue.contains(s - 100 - 'A') && !(s >= 'a' && s <= 'z' || s == ' '))) {
                    productions.get(i).remove(j);
                }
            }
        }

        /*System.System.out.println();
        System.System.out.println();

        for (int i = 0; i < productions.size(); i++) {
            if (productions.get(i).size() != 0) {
                System.System.out.println();
                System.System.out.print(i + " : ");
            }
            for (int j = 0; j < productions.get(i).size(); j++) {
                System.System.out.print(symbol2(productions.get(i).get(j).first) + "&" + symbol2(productions.get(i).get(j).second) + " | ");
            }
        }*/

        HashSet<Integer> queue1 = new HashSet<>();
        queue1.add(S);

        change = true;
        while (change) {
            change = false;
            for (int i = 0; i < productions.size(); i++) {
                for (int j = 0; j < productions.get(i).size(); j++) {
                    if (queue1.contains(i) /*&& queue.contains(w.get(i).S)*/) {
                        int f = productions.get(i).get(j).first;
                        int s = productions.get(i).get(j).second;
                        if (!(f >= 'a' && f <= 'z') && !queue1.contains(f - 100 - 'A')) {
                            change = true;
                            queue1.add(f - 100 - 'A');
                        }
                        if (!(s >= 'a' && s <= 'z') && !queue1.contains(s - 100 - 'A')) {
                            change = true;
                            queue1.add(s - 100 - 'A');
                        }
                    }
                }
            }
        }

        for (int i = 0; i < productions.size(); i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                int f = productions.get(i).get(j).first;
                int s = productions.get(i).get(j).second;
                if (!queue1.contains(i)
                        || (!queue1.contains(f - 100 - 'A') && !(f >= 'a' && f <= 'z'))
                        || (!queue1.contains(s - 100 - 'A') && !(s >= 'a' && s <= 'z' || s == ' '))) {
                    productions.get(i).remove(j);
                }
            }
        }
        return productions;
    }

    private static ArrayList<ArrayList<pair>> delMyMind(ArrayList<ArrayList<pair>> productions) {
        for (int i = 0; i < productions.size(); i++) {
            for (int j = 0; j < productions.get(i).size(); j++) {
                int f = productions.get(i).get(j).first;
                int s = productions.get(i).get(j).second;

                if (f >= 'a' && f <= 'z' && s != ' ') {
                    productions.add(new ArrayList<>());
                    int next = productions.size() - 1 + 'A' + 100;
                    productions.get(i).get(j).first = next;
                    productions.get(productions.size() - 1).add(new pair(f, ' '));
                }

                if (s >= 'a' && s <= 'z') {
                    productions.add(new ArrayList<>());
                    int next = productions.size() - 1 + 'A' + 100;
                    productions.get(i).get(j).second = next;
                    productions.get(productions.size() - 1).add(new pair(s, ' '));
                }
            }
        }
        return productions;
    }

    private static boolean calc(ArrayList<ArrayList<pair>> productions, String check, int i, int j, int k) {
        if (h[i][j][k]) {
            return d[i][j][k];
        }
        h[i][j][k] = true;
        if (k == j) {
            int cur = check.charAt(j);
            for (int s = 0; s < productions.get(i).size(); s++) {
                if (cur == productions.get(i).get(s).first) {
                    d[i][j][k] = true;
                    break;
                }
            }
            return d[i][j][k];
        }

        for (int s = 0; s < productions.get(i).size(); s++) {
            int f = productions.get(i).get(s).first;
            int sc = productions.get(i).get(s).second;

            if (!(f >= 'a' && f <= 'z')) {
                for (int g = j; g < k; g++) {
                    d[i][j][k] = d[i][j][k] || calc(productions, check, f - 100 - 'A', j, g)
                            && calc(productions, check, sc - 'A' - 100, g + 1, k);
                }
            }
        }
        return d[i][j][k];
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

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("cf.in"));
        PrintWriter out = new PrintWriter("cf.out");

        int n = nextInt(in);
        char S = (char) in.read();

        for (int i = 0; i < 26; i++) {
            productions.add(new ArrayList<>());
        }

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
                productions.get(s - 'A').add("");
            } else {
                productions.get(s - 'A').add(cur.substring(5, cur.length()));
            }
        }


        ArrayList<ArrayList<pair>> lol = delLong(productions);
        for (int i = 0; i < lol.size(); i++) {
            if (lol.get(i).size() != 0) {
                System.out.println();
                System.out.print(i + " : ");
            }
            for (int j = 0; j < lol.get(i).size(); j++) {
                System.out.print((char) (lol.get(i).get(j).first) + "&" + (char) (lol.get(i).get(j).second) + " | ");
            }
        }
        System.out.println();
        System.out.println();

        lol = delEps(lol, S - 'A');

        for (int i = 0; i < lol.size(); i++) {
            if (lol.get(i).size() != 0) {
                System.out.println();
                System.out.print(i + " : ");
            }
            for (int j = 0; j < lol.get(i).size(); j++) {
                System.out.print((char) (lol.get(i).get(j).first) + "&" + (char) (lol.get(i).get(j).second) + " | ");
            }
        }

        lol = delChains(lol);
        System.out.println();
        System.out.println();

        for (int i = 0; i < lol.size(); i++) {
            if (lol.get(i).size() != 0) {
                System.out.println();
                System.out.print(i + " : ");
            }
            for (int j = 0; j < lol.get(i).size(); j++) {
                System.out.print((char) (lol.get(i).get(j).first) + "&" + (char) (lol.get(i).get(j).second) + " | ");
            }
        }

        lol = delExtraRules(lol, S - 'A');
        System.out.println();
        System.out.println();

        for (int i = 0; i < lol.size(); i++) {
            if (lol.get(i).size() != 0) {
                System.out.println();
                System.out.print(i + " : ");
            }
            for (int j = 0; j < lol.get(i).size(); j++) {
                System.out.print((char) (lol.get(i).get(j).first) + "&" + (char) (lol.get(i).get(j).second) + " | ");
            }
        }

        lol = delMyMind(lol);
        System.out.println();
        System.out.println();

        for (int i = 0; i < lol.size(); i++) {
            if (lol.get(i).size() != 0) {
                System.out.println();
                System.out.print(i + " : ");
            }
            for (int j = 0; j < lol.get(i).size(); j++) {
                System.out.print((char) (lol.get(i).get(j).first) + "&" + (char) (lol.get(i).get(j).second) + " | ");
            }
        }

        String check = in.readLine();

        int m = check.length();

        d = new boolean[lol.size()][m][m];
        h = new boolean[lol.size()][m][m];

        for (int i = 0; i < lol.size(); i++) {
            for (int j = 0; j < m; j++) {
                for (int k = 0; k < m; k++) {
                    d[i][j][k] = false;
                    h[i][j][k] = false;
                }
            }
        }

        System.out.println(S - 'A');
        System.out.println(lol.get(18).size());

        if (check.equals("")) {
            for (int i = 0; i < lol.get(S - 'A').size(); i++) {
                System.out.println(lol.get(S - 'A').get(i).first);
                if (lol.get(S - 'A').get(i).first == ' ') {
                    out.println(true);
                    out.close();
                    return;
                }
            }
            out.println(false);
            out.close();
            return;
        }

        System.out.println(calc(lol, check, S - 'A', 0, m - 1));

        System.out.close();
    }
}