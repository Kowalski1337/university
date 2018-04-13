package fastscanner;

import java.util.*;
import java.io.*;

public class FastScanner implements AutoCloseable {
    BufferedReader br;
    StringTokenizer st;

    public FastScanner(File f) throws FileNotFoundException {
        br = new BufferedReader(new FileReader(f));
    }

    public String next() throws IOException {
        while (st == null || !st.hasMoreTokens()) {
            try {
                st = new StringTokenizer(br.readLine());
            } catch (NullPointerException e) {
                return null;
            }
        }
        return st.nextToken();
    }

    public int nextInt() throws IOException {
        return Integer.parseInt(next());
    }

    @Override
    public void close() throws IOException {
        br.close();
    }
}
