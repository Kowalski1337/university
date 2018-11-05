import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Scanner;

public class Main {

    private static final byte[] one = {1}, two = {2}, zero = {0};

    private static String getHash(MessageDigest md, String s1, String s2) {
        if (s1.equals("") && s2.equals("")) {
            return "";
        }
        md.update("1".concat(s1).concat("2".concat(s2)).getBytes());
        return new String(md.digest());
    }

    private static byte[] concat(byte[] first, byte[] second) {
        byte[] answer = new byte[first.length + second.length];
        for (int i = 0; i < first.length; i++) {
            answer[i] = first[i];
        }
        for (int i = 0; i < second.length; i++) {
            answer[first.length + i] = second[i];
        }
        return answer;
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, FileNotFoundException {
        Scanner in = new Scanner(System.in);
        int h = in.nextInt();
        in.nextLine();
        String hash = in.nextLine();
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        int n = in.nextInt();
        in.nextLine();
        for (int i = 0; i < n; i++) {
            int id = in.nextInt();
            String data = in.nextLine();
            data = data.substring(1);

            byte[] curBytes = "null".equals(data) ? new byte[0] : md.digest(concat(zero, Base64.getDecoder().decode(data)));

            for (int j = 0; j < h; j++) {
                String brother = in.nextLine();
                byte[] brotherBytes = "null".equals(brother) ? new byte[0] : Base64.getDecoder().decode(brother);
                if (curBytes.length == 0 && brotherBytes.length == 0) {
                    curBytes = new byte[0];
                } else {
                    if (id % 2 == 0) {
                        curBytes = md.digest(concat(concat(one, curBytes), concat(two, brotherBytes)));
                    } else {
                        curBytes = md.digest(concat(concat(one, brotherBytes), concat(two, curBytes)));
                    }
                }
                id /= 2;
            }
            System.out.println(Arrays.equals(Base64.getDecoder().decode(hash), curBytes) ? "YES" : "NO");
        }


    }
}
