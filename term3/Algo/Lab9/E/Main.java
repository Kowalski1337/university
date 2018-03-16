import java.io.*;
import java.util.ArrayList;

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

        String nextLine() throws Exception {
            return in.readLine();
        }

        public void print(int what) throws IOException {
            out.write(Integer.toString(what));
        }

        public void println(int what) throws IOException {
            out.write(Integer.toString(what) + '\n');
        }

        public void println(long what) throws IOException {
            out.write(Long.toString(what) + '\n');
        }

        public void close() throws IOException {
            in.close();
            out.close();
        }
    }

    public static void main(String[] args) {
        try {
            MyReaderWriter rw = new MyReaderWriter();
            String s = rw.nextLine();
            ArrayList<Integer> ans = new ArrayList<>();
            ans.add(0);
            int n = s.length();
            for (int i = 0; i < n - 1; i++) {
                int temp = ans.get(i);
                while (temp > 0 && s.charAt(i + 1) != s.charAt(temp)) {
                    temp = ans.get(temp - 1);
                }
                if (s.charAt(i + 1) == s.charAt(temp)) {
                    temp++;
                }
                ans.add(temp);
            }

            rw.println(n % (n - ans.get(ans.size() - 1)) == 0
                    ? n - ans.get(ans.size() - 1) : n);

            rw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
