import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by vadim on 27.04.2017.
 */
class way {
    int e, w;
}

public class Main {
    public static final int inf = (int)Math.pow(10, 9) + 7;
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

    public static int color[];

    public static boolean isCycle = false;

    public static int sort[];

    public static int curTime = 0;

    public static void dfs(ArrayList<Integer> g[], int v) {
        color[v] = 1;
        for (int i = 0; i < g[v].size(); i++) {
            int next = g[v].get(i);
            if (color[next] == 0) {
                dfs(g, next);
            }
        }
        color[v] = 2;
    }

    public static void topSort(ArrayList<Integer> g[], int v){
        if (isCycle){
            return;
        }
        color[v] = 1;
        for (int i = 0; i < g[v].size(); i++){
            int next = g[v].get(i);
            if (color[next] == 1) {
                isCycle = true;
                return;
            }
            if (color[next] == 2){
                topSort(g, next);
            }
        }
        color[v] = 0;
        sort[curTime++] = v;
    }

    public static int number[];

    public static void main(String[] args) throws Exception {
        BufferedReader in = new BufferedReader(new FileReader("problem3.in"));
        PrintWriter out = new PrintWriter("problem3.out");

        int n = nextInt(in);
        int m = nextInt(in);
        int k = nextInt(in);

        HashSet<Integer> blackHoles = new HashSet<>();

        for (int i = 0; i < k; i++) {
            blackHoles.add(nextInt(in));
        }

        ArrayList<Integer> graph[] = new ArrayList[n + 1];
        ArrayList<Integer> trans[] = new ArrayList[n + 1];

        for (int i = 0; i <= n; i++) {
            trans[i] = new ArrayList<>();
            graph[i] = new ArrayList<>();
        }


        for (int i = 0; i < m; i++) {
            int first = nextInt(in);
            int second = nextInt(in);
            int third = in.read() - 'a';

            graph[first].add(second);
            trans[second].add(first);
        }


        color = new int[n + 1];
        sort = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            color[i] = 0;
        }

        for (int i = 1; i <= n; i++) {
            if (blackHoles.contains(i) && color[i] == 0) {
                dfs(trans, i);
            }
        }

        /*for (int i = 1; i <= n; i++){
            out.println(color[i]);
        }
        out.println();*/

        topSort(graph, 1);

        number = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            number[i] = 0;
        }
        number[1] = 1;

        /*out.println(isCycle);

        for (int i = curTime - 1; i >= 0; i--){
            out.println(sort[i]);
        }
        out.println();*/

        if (isCycle){
            out.print(-1);
            out.close();
            return;
        }

        for (int i = curTime - 1; i >= 0; i--){
            int next = sort[i];
            for (int j = 0; j < graph[next].size(); j++){
                int cur = graph[next].get(j);
                number[cur] += number[next] % inf;
                number[cur] %= inf;
            }
        }

        int solve = 0;

        for (int i = 1; i <= n; i++){
            if(blackHoles.contains(i)){
                solve += number[i] % inf;
                solve %= inf;
            }
        }
        out.print(solve);
        out.close();
    }
}
