import javafx.util.Pair;

import java.io.*;
import java.util.*;

public class Main {

    public static int timer = 1, lg;
    public static int [] parents, tin, tout, weight, pathNum, pathPos;
    public static ArrayList<Integer> paths = new ArrayList<>();
    public static ArrayList<ArrayList<Integer>> child = new ArrayList<>(), /*otr = new ArrayList<>(),*/ ver = new ArrayList<>();
    public static int [][] up, otr;

    public static int nextInt(InputStreamReader sc) throws Exception {
        int a = 0;
        int b = sc.read();
        while (b < '0' || b > '9') b = sc.read();
        while (b >= '0' && b <= '9') {
            a = a * 10 + (b - '0');
            b = sc.read();
        }
        return a;
    }

    public static int nextChar(InputStreamReader sc) throws Exception {
        int b = sc.read();
        while (b != 'P' && b != 'Q') b = sc.read();
        return b;
    }

    public static void dfs (int n, int prev) {
        weight[n] = 1;
        tin[n] = timer;
        timer++;
        for (int i = 0; i < child.get(n).size(); i++) {
            int k = child.get(n).get(i);
            if (tin[k] == 0 && k != prev) {
                parents[k] = n;
                dfs (k, n);
                weight[n] += weight[k];
            }
        }
        tout[n] = timer;
        timer++;
    }

    public static void hlddfs (int n, int path, int prev) {
        ver.get(path).add(n);
        //otr.get(path).add(0);
        //if (otr.get(path).size() != 1) otr.get(path).add(n);
        pathNum[n] = path;
        for (int i = 0; i < child.get(n).size(); i++) {
            int k = child.get(n).get(i);
            if (k != prev) {
                if (weight[k] * 2 >= weight[n]) {
                    hlddfs(k, path, n);
                }
                else {
                    paths.add(k);
                    //otr.add(new ArrayList<>());
                    ver.add(new ArrayList<>());
                    ver.get(paths.size() - 1).add(n);
                    hlddfs(k, paths.size() - 1, n);
                }
            }
        }
    }

    public static boolean isParent (int a, int b) {
        return (tin[a] <= tin[b] && tout[a] >= tout[b]);
    }

    public static int getLCA (int a, int b) {
        if (isParent(a, b)) {
            return a;
        }
        if (isParent(b, a)) {
            return b;
        }
        int cur = a;
        for (int j = lg - 1; j > -1; j--) {
            if (!isParent(up[cur][j], b)) cur = up[cur][j];
        }
        return up[cur][0];
    }

    public static void addBushes (int a, int b) {
        //System.out.println (pathNum[a] + "!!" + pathNum[b]);
        if (a == b) return;
        int cur = a;
        while (true) {
            ArrayList<Integer> listPaths = ver.get(pathNum[cur]);
            int[] list = otr[pathNum[cur]];
            int size = list.length;
            int k = parents[paths.get(pathNum[cur])];
            int a1 = pathPos[cur] + 1;
            if (pathNum[cur] == 0) a1--;
            if (pathNum[cur] == pathNum[b] || b == k) {
                //System.out.println (a);
                //System.out.println (b);
                //int b1 = listPaths.indexOf(b);
                int b1;
                if (b == k) b1 = 0;
                else if (pathNum[cur] == 0) b1 = pathPos[b];
                else b1 = pathPos[b] + 1;
                //if (b1 == -1) b1++;
                /*if (a1 > b1) {
                    int t = a1;
                    a1 = b1;
                    b1 = t;
                }*/
                addBush(pathNum[cur], size - 1, b1, a1 - 1, 0, (size + 1)/2 - 1);
                break;
            }
            addBush(pathNum[cur], size - 1, 0, a1 - 1, 0, (size + 1)/2 - 1);
            cur = k;
        }
    }

    public static void addBush (int path, int cur, int a, int b, int L, int R) {
        int[] list = otr[path];
        //System.out.println (otr.get(path));
        //System.out.println (ver.get(path));

        //System.out.println (path + " " + cur + " " + a + " " + b + " " + L + " " + R);
        if (a <= L && b >= R) {
            //int z = list[cur];
            //System.out.println ("Увеличено значение в вершине " + cur);
            otr[path][cur]++;
        }
        else if (a <= R && b >= L){
            int m = (L+R)/2;
            addBush(path, 2*cur - list.length - 1, a, b, L, m);
            addBush(path, 2*cur - list.length, a, b, m+1, R);
        }
    }

    public static int get (int path, int a, int b) {
        int index = pathPos[a] /*+ 1*/;
        int[] list = otr[path];
        if (path == 0) index--;
        //System.out.println(index + " " + a + " " + b + " " + path);
        //index = 2 * index - 1;
        int size = list.length, cur = index, res = 0;
        res += list[cur];
        while (cur != size - 1) {
            //System.out.println (cur);
            if (cur % 2 == 0) cur++;
            cur = (cur + size) / 2;
            res += list[cur];
        }
        res += list[cur];
        return res;
    }

    public static void main(String[] arg) {
        try {
            InputStreamReader sc = new InputStreamReader(System.in);

            int n = nextInt(sc);
            int m = nextInt(sc);


            lg = (int)Math.ceil(Math.log(n)/Math.log(2));
            if (lg == 0) lg = 1;
            for (int i = 0; i < n + 1; i++) child.add(new ArrayList<>());
            parents = new int [n + 1];
            tin = new int [n + 1];
            weight = new int [n + 1];
            pathNum = new int [n + 1];
            pathPos = new int [n + 1];
            for (int i = 0; i < n + 1; i++) tin[i] = 0;
            tout = new int [n + 1];
            tout[0] = 2*n+1;
            up = new int[n + 1][lg];
            for (int i = 0; i < n + 1; i++) for (int j = 0; j < lg; j++) up[i][j] = 0;
            parents[1] = 0;
            for (int i = 2; i < n + 1; i++) {
                int a = nextInt(sc);
                int b = nextInt(sc);
                child.get(a).add(b);
                child.get(b).add(a);
            }
            dfs(1, 0);
            for (int i = 1; i < n + 1; i++) {
                up[i][0] = parents[i];
            }
            for (int i = 1; i < lg; i++) {
                for (int j = 1; j < n + 1; j++) {
                    up[j][i] = up[up[j][i - 1]][i - 1];
                }
            }
            //otr.add(new ArrayList<>());
            ver.add(new ArrayList<>());
            paths.add(1);
            hlddfs(1, 0, 0);
            /*for (int i = 0; i < paths.size(); i++) {
                int size = otr.get(i).size();
                if (otr.get(i).get(size - 1).equals(otr.get(i).get(size - 2))) otr.get(i).remove(size - 1);
            }*/
            int [] e = new int [20];
            e[0] = 1;
            for (int i = 1; i < 20; i++) e[i] = e[i - 1]*2;
            for (int i = 0; i < ver.get(0).size(); i++) pathPos[ver.get(0).get(i)] = i;
            otr = new int [paths.size()][];
            for (int i = 0; i < paths.size(); i++) {
                ArrayList<Integer> list = ver.get(i);
                int l = (int)(Math.ceil(Math.log(list.size())/Math.log(2)));
                if (i != 0) {
                    for (int j = 1; j < list.size(); j++) {
                        pathPos[list.get(j)] = j - 1;
                    }
                }
                otr[i] = new int [e[l + 1] - 1];
                Arrays.fill(otr[i], 0);
            }
            //for (int i = 0; i < pathPos.length; i++) System.out.print (pathPos[i] + " ");
            /*for (int i = 0; i < otr.size(); i++) System.out.println(otr.get(i));
            for (int i = 0; i < otr.size(); i++) System.out.println(ver.get(i));*/
            for (int i = 0; i < m; i++) {
                int c = nextChar(sc);
                int a = nextInt(sc);
                int b = nextInt(sc);
                if (c == 'P') {
                    int d = getLCA(a, b);
                    if (a == d) addBushes(b, d);
                    else if (b == d) addBushes(a, d);
                    else {
                        addBushes(a, d);
                        addBushes(b, d);
                    }
                }
                else if (c == 'Q') {
                    if (parents[a] != b) {
                        int t = a;
                        a = b;
                        b = t;
                    }
                    System.out.println(get (pathNum[a], a, b));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}