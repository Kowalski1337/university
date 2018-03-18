import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by vadim on 10.04.2017.
 */
public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new File("exam.in"));
        PrintWriter out = new PrintWriter("exam.out");

        int k = in.nextInt();
        int n = in.nextInt();

        float res = 0;
        float s;
        for (int i = 0; i < k; i++){
            res += in.nextInt() * in.nextInt();
        }
        s = res*(float)0.01/n;
        out.print(s);
        out.close();
    }
}
