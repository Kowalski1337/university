import java.io.*;

public class Main {

    static class MyReaderWriter {
        private BufferedReader in;
        private BufferedWriter out;

        MyReaderWriter(String read, String write) throws IOException {
            in = new BufferedReader(new FileReader(read));
            out = new BufferedWriter(new FileWriter(write));
        }

        MyReaderWriter() {
            in = new BufferedReader(new InputStreamReader(System.in));
            out = new BufferedWriter(new OutputStreamWriter(System.out));
        }

        String nextLine() throws IOException {
            return in.readLine();
        }

        int nextInt() throws IOException {
            int ans = 0;
            boolean isNegate = false;
            int ch = in.read();
            while ((ch < '0' || ch > '9') && ch != '-') {
                ch = in.read();
            }
            if (ch == '-') {
                isNegate = true;
                ch = in.read();
            }
            while (ch >= '0' && ch <= '9') {
                ans = ans * 10 + ch - '0';
                ch = in.read();
            }
            return isNegate ? -ans : ans;
        }

        public void print(int what) throws IOException {
            out.write(Integer.toString(what));
        }

        public void println(int what) throws IOException {
            out.write(Integer.toString(what) + '\n');
        }

        public void print(long what) throws IOException {
            out.write(Long.toString(what));
        }

        public void println(long what) throws IOException {
            out.write(Long.toString(what) + '\n');
        }

        public void print(String what) throws IOException {
            out.write(what);
        }

        public void println(String what) throws IOException {
            out.write(what + '\n');
        }

        public void close() throws IOException {
            in.close();
            out.close();
        }
    }

    public static void main(String[] args) {
        try {
            MyReaderWriter rw = new MyReaderWriter("cyclic.in", "cyclic.out");

            String t = rw.nextLine();
            String s = t + t;
            int n = s.length();
            int[] z = new int[n];

            z[0] = 0;
            int left = 0;
            int right = 0;
            for (int i = 1; i < t.length(); i++) {
                if (i <= right) {
                    z[i] = Math.min(z[i - left], right - i + 1);
                }

                while (i + z[i] < n && s.charAt(z[i]) == s.charAt(i + z[i])) {
                    z[i]++;
                }
                if (i + z[i] - 1 > right) {
                    left = i;
                    right = i + z[i] - 1;
                }
            }

            s += t.charAt(0);

            int ans = t.length();
            for (int i = 1; i < t.length(); i++) {
                if (s.charAt(z[i]) <= s.charAt(i + z[i])) {
                    ans--;
                }
            }
            rw.println(ans);
            rw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
