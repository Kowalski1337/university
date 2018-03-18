import com.sun.javafx.image.IntPixelGetter;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.io.*;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static ArrayList<Integer> used = new ArrayList<>();
    public static int begin, end;
    public static ArrayList<Integer> parent = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> g;
    public static ArrayList<HashSet<Integer>> gs;
    public static ArrayList<Integer> cycle;
    public static boolean isAns = false;

    public static void dfs(int v){
        used.set(v, 1);
        int k = g.get(v).size();
        for (int i = 0; i < k; i++){
            if (isAns) return;
            int cur = g.get(v).get(i);
            if (used.get(cur) == 0){
                parent.set(cur, v);
                dfs(cur);
            } else {
                if (used.get(cur) == 1){
                    begin = cur;
                    end = v;
                    isAns = true;
                    return;
                }
            }

        }
        used.set(v, 2);
    }

    public static ArrayList<Integer> firstSolution(int v){
        int n = cycle.size();
        int st = -1;
        for (int i = 0; i < n; i++){
            int cur = cycle.get(i);
            if (gs.get(cur).contains(v)){
                st = i;
                break;
            }
        }
        if (st == -1){
            return cycle;
        }
        int stt = -1;
        for (int i = st; i < st + n; i++){
            int j = i % n;
            int cur = cycle.get(j);
            if (gs.get(v).contains(cur)){
                stt = j;
                break;
            }
        }
        if (stt == -1){
            return cycle;
        }
        ArrayList<Integer> ans = new ArrayList<>();
        for (int i = 0; i < stt; i++){
            ans.add(cycle.get(i));
        }
        ans.add(v);
        for (int i = stt; i < n; i++){
            ans.add(cycle.get(i));
        }
        used.set(v, 1);
        return ans;
    }

    public static ArrayList<Integer> secondSolution(){
        int x = cycle.get(0);
        int n = gs.size();
        ArrayList<Integer> A, B;
        A = new ArrayList<>();
        B = new ArrayList<>();
        for (int i = 0; i < n; i++){
            if (used.get(i) == 0){
                if (gs.get(x).contains(i)){
                    A.add(i);
                } else {
                    B.add(i);
                }
            }
        }
        int ii = -1;
        int jj = -1;
        for (int i = 0; i < A.size() && ii == -1; i++){
            for (int j = 0; j < B.size() && ii == -1; j++){
                if (gs.get(A.get(i)).contains(B.get(j))){
                    ii = i;
                    jj = j;
                }
            }
        }
        ArrayList<Integer> ans = new ArrayList<>();
        ans.add(A.get(ii));
        ans.add(B.get(jj));
        used.set(A.get(ii), 1);
        used.set(B.get(jj), 1);
        for (int i = 0; i < cycle.size(); i++){
            ans.add(cycle.get(i));
        }
        return ans;
    }


    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new File("guyaury.in"));
        PrintWriter out = new PrintWriter("guyaury.out");

        gs = new ArrayList<>();
        g = new ArrayList<>();
        int n = in.nextInt();


        for (int i = 0; i < n; i++) {
            g.add(new ArrayList<>());
            gs.add(new HashSet<>());
            used.add(0);
            parent.add(0);
        }

        String s = in.nextLine();

        for (int i = 0; i <= n - 1; i++) {
            s = in.nextLine();
            for (int j = 0; j < i; j++) {
                int next = s.charAt(j) - '0';
                if (next == 1) {
                    g.get(i).add(j);
                    gs.get(i).add(j);
                } else {
                    g.get(j).add(i);
                    gs.get(j).add(i);
                }
            }
        }

        /*for (int i = 0; i < n; i++){
            for (int j = 0; j < g.get(i).size(); j++){
                System.out.print((g.get(i).get(j)+1) + " ");
            }
            System.out.println();
        }
        System.out.println();*/

        for (int i = 0; i < n; i++){
            if (used.get(i) == 0) {
                dfs(i);
            }
        }

        //System.out.println((begin+1) + " " + (end+1));
        for (int i = 0; i < n; i++){
            used.set(i, 0);
        }

        cycle = new ArrayList<>();
        ArrayList<Integer> x = new ArrayList<>();
        for (int v = end; v != begin; v = parent.get(v)){
            x.add(v);
            used.set(v, 1);
        }
        used.set(begin, 1);
        x.add(begin);

        for (int i = 0; i < x.size(); i++){
            cycle.add(x.get(x.size()-1-i));
        }

        /*for (int i = 0; i < cycle.size(); i++){
            out.println(cycle.get(i));
        }*/

        int k = 0;
        while (cycle.size() < n) {
            if (cycle.size() > k) {
                k = cycle.size();
                for (int i = 0; i < n; i++) {
                    if (used.get(i) == 0) {
                        cycle = firstSolution(i);
                    }
                }
            } else {
                cycle = secondSolution();
            }
        }

        for (int i = 0; i < n; i++){
            out.print((cycle.get(i)+1) + " ");
        }

        out.close();
    }
}
