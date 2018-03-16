import java.util.Scanner;
import java.io.*;

class Node {
    int value;
    int a = 0;
    int b = 0;
    boolean calc = false;
}

public class Selectw {

    public static Node[] a = new Node[1000];

    public static int m(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    /*int choice(int n, Node*k, int curT) {

        if (k[curT].check == true) {
            return max(k[curT].a, k[curT].b);
        }
        k[curT].a = max(0, k[curT].value);
        //cout << k[curT].a << " " << curT << "      ";
        for (int i = 0; i < Tree[curT].size(); i++) {
            int t = choice(n, k, Tree[curT][i]);
            if (k[Tree[curT][i]].b > 0)
                k[curT].a += k[Tree[curT][i]].b;
        }
        //cout << k[curT].a << endl;
        for (int i = 0; i < Tree[curT].size(); i++) {
            k[curT].b += choice(n, k, Tree[curT][i]);
        }
        k[curT].check = true;
        //cout << " b "<< k[curT].b << endl;
        return max(k[curT].a, k[curT].b);
    }*/


    public static int SWAG(int[][] Tree, int[] sizes, int cur) throws Exception {
        if (a[cur].calc == true) {
            //System.out.println("lol");
            return m(a[cur].a, a[cur].b);
        }
        //System.out.println(a[cur].value);
        a[cur].a = m(0, a[cur].value);
        for (int i = 0; i < sizes[cur]; i++) {
            //System.out.println("?" + a[cur].a);
            int temp = SWAG(Tree, sizes, Tree[cur][i]);
            //System.out.println("??" + a[cur].a);
            if (a[Tree[cur][i]].b > 0) {
                a[cur].a += a[Tree[cur][i]].b;
            }
            //System.out.println("???" + a[cur].a);
        }
        for (int i = 0; i < sizes[cur]; i++) {
            a[cur].b += SWAG(Tree, sizes, Tree[cur][i]);
        }
        a[cur].calc = true;
        //System.out.println("????" + a[cur].a);

        //System.out.println("!" + m(a[cur].b, a[cur].b));

        return m(a[cur].a, a[cur].b);
    }

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(new File("selectw.in"));
        PrintWriter out = new PrintWriter("selectw.out");

        int n = in.nextInt();
        int[][] Tree = new int[n + 1][n + 1];
        int[] sizes = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            sizes[i] = 0;
        }


        for (int i = 1; i <= n; i++) {
            int parent = in.nextInt();
            int value = in.nextInt();

            a[i] = new Node();
            a[i].value = value;
            Tree[parent][sizes[parent]++] = i;
        }


        /*for (int i = 0; i <= n; i++) {
            for (int j = 0; j < sizes[i]; j++) {
                out.print(Tree[i][j] + " " + a[Tree[i][j]].value + " " + a[Tree[i][j]].calc + " ");
                out.println(SWAG(Tree, sizes, Tree[i][j]));
            }
        }*/
        out.println(SWAG(Tree, sizes, Tree[0][0]));
        out.close();
    }
}
