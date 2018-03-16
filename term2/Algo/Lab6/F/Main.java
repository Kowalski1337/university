import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by vadim on 08.05.2017.
 */
public class Main {
    public static final int inf = (int)Math.pow(10, 9);
    static class edge{
        int a, b, w;
        edge(int a, int b,int w){
            this.a = a;
            this.b = b;
            this.w = w;
        }
    }
    public static void main(String[]args) throws Exception{
        Scanner in = new Scanner(new File("negcycle.in"));
        PrintWriter out = new PrintWriter("negcycle.out");

        int n = in.nextInt();

        ArrayList<edge> ways = new ArrayList<>();

        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                int next = in.nextInt();
                if (next != inf){
                    ways.add(new edge(i, j, next));
                }
            }
        }
        
        int[] d = new int[n];

        int[] p = new int[n];
        
        //d[0] = 0;
        p[0] = -1;
        for (int i = 1; i < n; i++){
            p[0] = -1;
        }
        
        int x = 0;
        for (int i=0; i<n; ++i) {
            x = -1;
            for (int j= 0; j< ways.size(); j++)
                if (d[ways.get(j).b] > d[ways.get(j).a] + ways.get(j).w) {
                    d[ways.get(j).b] = Math.max(-inf, d[ways.get(j).a] + ways.get(j).w);
                    p[ways.get(j).b] = ways.get(j).a;
                    x = ways.get(j).b;
                }
        }

        if (x == -1){
            out.print("NO");
            out.close();
            return;
        }

        int y = x;
        for (int i=0; i<n; ++i)
                y = p[y];

        ArrayList<Integer> path = new ArrayList<>();
            for (int cur=y; ; cur=p[cur]) {
                path.add(cur);
                if (cur == y && path.size() > 1)  break;
            }
        Collections.reverse(path);

        out.println("YES");
        out.println(path.size());
            for (int i=0; i< path.size(); ++i)
                out.print((path.get(i)+1) + " ");


            out.close();

    }
}
