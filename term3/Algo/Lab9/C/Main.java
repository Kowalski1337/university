import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String s= in.readLine();
            int n = s.length();
            int[] z = new int[n];

            z[0] = 0;
            int left = 0;
            int right = 0;
            for (int i = 1; i < n; i++){
                if (i <= right){
                    z[i] = Math.min(z[i-left], right-i+1);
                }
                while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])){
                    z[i]++;
                }
                if (i + z[i] - 1 > right){
                    left = i;
                    right = i + z[i] - 1;
                }
            }
            for (int i = 1; i < n; i++){
                out.write(Integer.toString(z[i]) + " ");
            }
            out.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
