import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.HashSet;

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
    public static void main(String[] args)throws Exception{
        BufferedReader in = new BufferedReader(new FileReader("problem1.in"));
        PrintWriter out = new PrintWriter("problem1.out");

        String s = in.readLine();
        int n = nextInt(in);
        int m = nextInt(in);
        int k = nextInt(in);

        HashSet<Integer> blackHoles = new HashSet<>();

        for (int i = 0; i < k; i++){
            blackHoles.add(nextInt(in));
        }

        int graph[][] = new int[n+1][26];
        for (int i = 1; i <= n; i++){
            for (int j = 0; j < 26; j++){
                graph[i][j] = 0;
            }
        }

        for (int i = 0; i < m; i++){
            int first = nextInt(in);
            int second = nextInt(in);
            int third = in.read() - 'a';

            graph[first][third] = second;
        }

        int cur = 1;

        for (int i = 0; i < s.length(); i++){
            int index = s.charAt(i) - 'a';
            if (graph[cur][index] != 0){
                cur = graph[cur][index];
            } else{
                out.print("Rejects");
                out.close();
                return;
            }
        }
        if (!blackHoles.contains(cur)){
            out.print("Rejects");
        } else {
            out.print("Accepts");
        }
        out.close();
    }
}
