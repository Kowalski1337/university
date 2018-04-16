package fastscanner;

import java.util.*;
import java.io.*;

public class FastScanner implements AutoCloseable {
    private BufferedReader br;
    private StringTokenizer st;

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

    @Override
    public void close() throws IOException {
        br.close();
    }
}
