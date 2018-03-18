import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by vadim on 26.04.2017.
 */
public class Main {
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
        BufferedReader in = new BufferedReader(new FileReader("automaton.in"));
        PrintWriter out = new PrintWriter("automaton.out");


        int n = nextInt(in);

        char Start = (char) in.read();

        ArrayList<ArrayList<ArrayList<Integer>>> graph = new ArrayList<>();

        for (int i = 0; i <= 26; i++) {
            graph.add(new ArrayList<>());
            for (int j = 0; j < 26; j++) {
                graph.get(i).add(new ArrayList<>());
            }
        }

        String line = in.readLine();

        for (int i = 0; i < n; i++) {
            line = in.readLine();
            char cur = line.charAt(0);
            //System.out.println(s.length());
            String lol = line.substring(5, line.length());
            //System.out.println(lol);
            if (lol.length() == 1) {
                int a = lol.charAt(0);
                graph.get(cur - 'A').get(a - 'a').add(26);
            } else {
                char a = lol.charAt(0);
                char b = lol.charAt(1);
                graph.get(cur - 'A').get(a - 'a').add(b - 'A');
            }
        }

     /*   for (int i = 0; i < 26; i++){
            for (int j = 0; j < 26; j++){
                for (int k = 0; k < graph.get(i).get(j).size(); k++){
                    out.println((char) (i + 'A') + " -> " + (char) (j + 'a') + (char) (graph.get(i).get(j).get(k) + 'A'));
                }
            }
        }*/
       // out.println();

        LinkedList<Integer> cur = new LinkedList<>();

        int q = nextInt(in);

        line = in.readLine();

        for (int w = 0; w < q; w++) {
            cur.clear();
            //System.out.println(Start -'A');
            cur.add(Start - 'A');
            String s = in.readLine();

            for (int i = 0; i < s.length(); i++) {
                int index = s.charAt(i) - 'a';
                int l = cur.size();
                /*if (l == 0) {
                    out.print("no");
                }*/
                LinkedList<Integer> currr = new LinkedList<>();
                for (int j = 0; j < l; j++) {
                    int curr = cur.getFirst();
                    cur.removeFirst();
                    for (int g = 0; g < graph.get(curr).get(index).size(); g++) {
                        if (!currr.contains(graph.get(curr).get(index).get(g))) {
                            currr.addLast(graph.get(curr).get(index).get(g));
                        }
                    }
                }
                cur = currr;
            }
            boolean is = false;
            for (Integer i : cur) {
                if (i == 26) {
                    is = true;
                    break;
                }
            }
            out.println(is ? "yes": "no");
        }
        out.close();
    }
}