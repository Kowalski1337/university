import java.io.*;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String s = in.readLine();

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

            for (int i = 0; i < n; i++){
                out.write(Integer.toString(p[i]) + " ");
            }
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
