import java.io.*;

public class Main {
    private static final int p = 3;
    private static long[] hash;

    private static long getHas(int l, int r) {
        return l > 0 ? ((hash[r] - hash[l - 1])) : hash[r];
    }

    private static int nextInt(BufferedReader in) throws IOException {
        int ans = 0;
        int ch = in.read();
        while (ch < '0' || ch > '9') {
            ch = in.read();
        }
        while (ch >= '0' && ch <= '9') {
            ans = 10 * ans + ch - '0';
            ch = in.read();
        }
        return ans;
    }

    public static void main(String[] args) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("test.in"));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));

            String s = in.readLine();
            int n = s.length();

            hash = new long[n];

            hash[0] = s.charAt(0);

            long[] pows = new long[n];
            pows[0] = 1;
            for (int i = 1; i < n; i++) {
                pows[i] = pows[i - 1] * p;
            }


            for (int i = 1; i < n; i++) {
                hash[i] = hash[i - 1] + s.charAt(i) * pows[i];
            }

            for (int i = 0; i < n; i++){
                out.write(Long.toString(hash[i]) + '\n');
            }

            int m = nextInt(in);


            for (int i = 0; i < m; i++) {
                int a1 = nextInt(in) - 1;
                int a2 = nextInt(in) - 1;
                int a3 = nextInt(in) - 1;
                int a4 = nextInt(in) - 1;
                long hash1 = getHas(a1, a2);
                long hash2 = getHas(a3, a4);
                out.write(a1 < a3 && hash1 * pows[a3 - a1] == hash2 || a1 >= a3 && hash1 == hash2 * pows[a1 - a3] ? "Yes" : "No");
                out.write('\n');
            }
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
