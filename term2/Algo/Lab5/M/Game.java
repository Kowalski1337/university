import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by vadim on 21.04.2017.
 */
public class Game {
    public static boolean used[];
    public static boolean isWin[];

    public static void dfs(ArrayList<Integer> g[], int v){
        used[v] = true;
        isWin[v] = false;
        for (int i = 0; i < g[v].size(); i++){
            int next = g[v].get(i);
            if (!used[next]){
                dfs(g, next);
            }
            if (!isWin[next]){
                isWin[v] = true;
            }
        }
    }
    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("game.in"));
        PrintWriter out = new PrintWriter("game.out");

        int n = in.nextInt();
        int m = in.nextInt();
        int s = in.nextInt();

        ArrayList<Integer> g[] = new ArrayList[n+1];

        for (int i = 0; i <=n; i++){
            g[i] = new ArrayList<>();
        }

        for (int i = 0; i < m; i++){
            int first = in.nextInt();
            int second = in.nextInt();

            g[first].add(second);
        }
        used = new boolean[n+ 1];
        isWin = new boolean[n+1];
        for (int i =1; i <=n; i++){
            used[i] = false;
            //isWin[i] = false;
        }
        dfs(g,s);

        if (isWin[s]){
            out.print("First player wins");
        }else{
            out.print("Second player wins");
        }
        out.close();

    }
}
