package FastScanner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MyReader {
    private BufferedReader reader;
    private int ch;
    private boolean EOF;

    MyReader(String file) throws IOException {
        reader = new BufferedReader(new FileReader(file));
        ch = reader.read();
        EOF = (ch == -1);
    }

    int getNext() throws IOException {
        int ans = ch;
        EOF = ((ch = reader.read()) == 1);
        return ans;
    }

    boolean hasNext() {
        return !EOF;
    }
}
