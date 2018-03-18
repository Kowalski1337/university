import java.io.File;
import java.io.PrintWriter;
import java.net.SecureCacheResponse;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Integer> ans = new ArrayList<>();

    public static boolean check(int X, int Y, Scanner in, PrintWriter out){
        out.println(1 + " " +  (X+1) + " " + (Y+1));
        out.flush();
        String s = in.next();
        return s.equals("YES");
    }
//    public static void sort(int l, int r, Scanner in, PrintWriter out){
//        if (l >= r){
//            return;
//        }
//
//        int m = l + (r-l)/2;
//        int key = ans.get(m);
//
//        int i = l;
//        int j = r;
//        while (i <= j){
//            while (check(ans.get(i), key, in, out)){
//                i++;
//            }
//            while (check(key, ans.get(j), in, out)){
//                j--;
//            }
//            if (i <= j){
//               swap(i, j);
//               i++;
//               j--;
//            }
//        }
//    }

//    public static void swap(int i, int j){
//        int a = ans.get(i);
//        ans.set(i, ans.get(j));
//        ans.set(j, a);
//        return;
//    }

    public static int poiskDashi(int key, Scanner in, PrintWriter out){
        int left = -1;
        int right = ans.size();
        int mid = 0;

        while (right > left + 1) {
            //System.out.print("lol");
            mid = left + (right - left) / 2;
            if (check(ans.get(mid), key, in, out)) {
                left = mid;
            } else {
                right = mid;
              //  System.out.println("lol");
            }

        }
        //System.out.println("done");

        return right;
    }

    public static void main(String[] args) throws Exception{
        Scanner in = new Scanner(System.in);
        PrintWriter out = new PrintWriter(System.out);

        int n = in.nextInt();

        ans.add(0);

        for (int i = 1; i < n; i++){
            int index = poiskDashi(i, in, out);
            ans.add(index, i);

        }

        //sort(0, n-1, in, out);
        out.print(0 + " ");
        for (int i = 0; i < n; i++){
            out.print((ans.get(i)+1) + " ");
        }

        out.close();
    }
}
