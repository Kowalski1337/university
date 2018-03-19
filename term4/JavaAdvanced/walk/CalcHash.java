package ru.ifmo.rain.baydyuk.walk;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

class CalcHash {

    private static final int a = 0x811c9dc5;
    private static final int b = 0x01000193;
    private static final int c = 0xff;

    static int getHashNotEnoughSpace(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            int h = a;
            int ch;
            while ((ch = is.read()) != -1) {
                h = (h * b) ^ (ch & c);
            }
            return h;
        }
    }

    static int getHashFaster(Path path) throws IOException {
        try (InputStream is = Files.newInputStream(path)) {
            int h = a;
            byte[] ch = new byte[1000];
            int count;
            while ((count = is.read(ch)) != -1) {
                for (int i = 0; i < count; i++) {
                    h = (h * b) ^ (ch[i] & c);
                }
            }
            return h;
        }
    }
}
