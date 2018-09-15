import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int getKeyPart(int number) {
        if (number >= ('e' - 'a')) {
            return number - ('e' - 'a');
        }
        return number + 'z' - 'e' + 1;
    }

    private static int getNew(char old, int shift) {
        if (old - 'a' >= shift) {
            return old - shift;
        }
        return 'z' - (shift - (old - 'a')) + 1;
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(new FileReader("cipher"));

        String cipher = in.nextLine();
        for (int i = 6; i <= 10; i++) {
            int[][] stats = new int[i][26];

            for (int j = 0; j < cipher.length(); j++) {
                stats[j % i][cipher.charAt(j) - 'a']++;
            }

            int[] key = new int[i];

            ArrayList<Character> newText = new ArrayList<>();

            for (int j = 0; j < i; j++) {
                int max = 0;
                int maxNum = 0;
                for (int jj = 0; jj < 26; jj++) {
                    if (stats[j][jj] > max) {
                        max = stats[j][jj];
                        maxNum = jj;
                    }
                }
                key[j] = getKeyPart(maxNum);
            }

            for (int j = 0; j < cipher.length(); j++) {
                newText.add((char) (getNew(cipher.charAt(j), key[j % i])));
            }

            PrintWriter out = new PrintWriter(new FileWriter(i + ""));

            newText.forEach(out::print);
            out.close();
        }
    }
}
