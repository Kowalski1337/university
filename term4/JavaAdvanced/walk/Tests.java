package ru.ifmo.rain.baydyuk.walk;

public class Tests {

    private static final String SECURITY_FILE = "C:\\asdsd";
    private static final String INVALID_PATH = "?\\cs";
    private static final String PRESENT_READABLE_FILE = "file.txt";
    private static final String NOT_PRESENT_FILE = "notFile.txt";

    private static void print(String message) {
        System.out.println();
        System.out.println("_______________________");
        System.out.println("Test " + message);
        System.out.println("_______________________");
    }

    private static void testNullArgs() {
        print("null args");
        Walk.main(null);
    }

    private static void testNoSuchArgs() {
        print("no such args");
        Walk.main(new String[]{PRESENT_READABLE_FILE});
    }

    private static void testFiles(String inputFileName, String outPutFileName) {
        Walk.main(new String[]{inputFileName, outPutFileName});
    }

    private static void testFirstNull() {
        print("null first arg");
        testFiles(null, "fileName");
    }

    private static void testSecondNull() {
        print("null second arg");
        testFiles("fileName", null);
    }

    public static void testInputFileNotFound(String fileName1, String fileName2) {
        print("input file(" + fileName1 + ") not found");
        testFiles(fileName1, fileName2);
    }

    private static void testInvalidInputPath(String fileName1, String fileName2) {
        print("invalid path(" + fileName1 + ") of input file");
        testFiles(fileName1, fileName2);
    }

    private static void testInvalidOutputPath(String fileName1, String fileName2) {
        print("invalid path(" + fileName2 + ") of input file");
        testFiles(fileName1, fileName2);
    }

    private static void testSecurityInputFile(String fileName1, String fileName2) {
        print("security input file(" + fileName1 + ")");
        testFiles(fileName1, fileName2);
    }

    private static void testSecurityOutputFile(String fileName1, String fileName2) {
        print("security input file(" + fileName2 + ")");
        testFiles(fileName1, fileName2);
    }

    public static void main(String[] args) {
        testNullArgs();
        testNoSuchArgs();
        testFirstNull();
        testSecondNull();
        testInputFileNotFound(NOT_PRESENT_FILE, PRESENT_READABLE_FILE);
        testInvalidInputPath(INVALID_PATH, PRESENT_READABLE_FILE);
        testInvalidOutputPath(PRESENT_READABLE_FILE, INVALID_PATH);
        testSecurityInputFile(SECURITY_FILE, PRESENT_READABLE_FILE);
        testSecurityOutputFile(PRESENT_READABLE_FILE, SECURITY_FILE);
    }

}
