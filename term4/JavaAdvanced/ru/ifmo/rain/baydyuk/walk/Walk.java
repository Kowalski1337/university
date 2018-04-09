package ru.ifmo.rain.baydyuk.walk;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;

public class Walk {

    public static void main(String[] args) {

        if (args == null) {
            System.err.println("Invalid args");
            return;
        }

        if (args.length < 2) {
            System.err.println("No such args");
            return;
        }

        if (args[0] == null){
            System.err.println("Invalid input file");
            return;
        }

        if (args[1] == null){
            System.err.println("Invalid output file");
            return;
        }

        try (
                BufferedReader reader = Files.newBufferedReader(Paths.get(args[0]), Charset.forName("UTF-8"))
        ) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(args[1]), Charset.forName("UTF-8"))) {

                String path;
                try {
                    while ((path = reader.readLine()) != null) {
                        int hash;
                        try {
                            hash = CalcHash.getHashFaster(Paths.get(path));
                        } catch (IOException | InvalidPathException e) {
                            hash = 0;
                        }
                        try {
                            writer.write(String.format("%08x %s%n", hash, path));
                        } catch (IOException e) {
                            System.err.println("Problems with writing to " + args[1]);
                        }
                    }
                } catch (IOException e) {
                    System.err.println("Problems with reading from " + args[0]);
                }
            } catch (SecurityException e) {
                System.err.println("No access to write to file " + args[1]);
            } catch (NoSuchFileException e) {
                System.err.println("File " + args[1] + " not found");
            } catch (InvalidPathException e) {
                System.err.println(args[1] + " isn't name of file");
            } catch (IOException e) {
                System.err.println("Problems with opening or closing file " + args[1]);
            }
        } catch (SecurityException e) {
            System.err.println("No access to read file " + args[0]);
        } catch (InvalidPathException e) {
            System.err.println(args[0] + " isn't name of file");
        } catch (NoSuchFileException e) {
            System.err.println("File " + args[0] + " not found");
        } catch (IOException e) {
            System.err.println("Problems with opening or closing file " + args[0]);
        }


    }
}

