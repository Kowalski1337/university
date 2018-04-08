import fastscanner.FastScanner;
import parser.Parser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class HW0 {
    public static void main(String[] args) throws IOException {
        try (FastScanner scanner = new FastScanner(new File("input.txt"));
             PrintWriter writer = new PrintWriter("output.txt")) {
            StringBuilder sb = new StringBuilder();
            new Parser().parse(scanner.next()).write(sb);
            writer.write(sb.toString());
        }

    }
}
