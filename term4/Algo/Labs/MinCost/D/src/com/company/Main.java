package com.company;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try (
                Scanner in = new Scanner(new FileInputStream("rps2.in"));
                PrintWriter out = new PrintWriter(new FileOutputStream("rps2.out"))) {
            long r1 = in.nextLong();
            long s1 = in.nextLong();
            long p1 = in.nextLong();
            long r2 = in.nextLong();
            long s2 = in.nextLong();
            long p2 = in.nextLong();

            out.println(Math.max(Math.max(0, r1 - r2 - p2), Math.max(p1 - p2 - s2, s1 - s2 - r2)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
