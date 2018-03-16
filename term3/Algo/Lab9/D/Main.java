import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String s = in.readLine();
            int m = s.length();

            s = s + "@" + in.readLine();
            int n = s.length();
            int[] p = new int[n];
            p[0] = 0;

            for (int i = 1; i < n; i++){
                int j = p[i-1];
                while (j > 0 && s.charAt(i) != s.charAt(j)){
                    j = p[j-1];
                }
                if (s.charAt(i) == s.charAt(j)){
                    j++;
                }
                p[i] = j;
            }

            ArrayList<String> answer = new ArrayList<>();

            for (int i = m + 1; i < n; i++){
                if (p[i] == m){
                    answer.add(Integer.toString(i - 2*m + 1) + " ");
                }
            }
            out.write(Integer.toString(answer.size()) + '\n');
            for (int i = 0; i < answer.size(); i++){
                out.write(answer.get(i));
            }
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
