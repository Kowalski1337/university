import java.util.*;
import java.lang.*;
import java.io.*;
import java.security.MessageDigest;

public class Main {
    public static byte[] concat(byte[] first, byte[] second) {
        byte[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    public static void main(String[] args) throws Exception {
        byte[] zero = {0}, one = {1}, two = {2};

        BufferedReader r = new BufferedReader (new InputStreamReader(System.in));
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");

        int h = Integer.valueOf(r.readLine());
        ArrayList<HashMap<Integer, String>> nodes = new ArrayList<>();
        ArrayList<HashMap<Integer, byte[]>> hashes = new ArrayList<>();
        for (int i = 0; i < h; ++i) {
            nodes.add(new HashMap<>());
            hashes.add(new HashMap<>());
        }
        int q = Integer.valueOf(r.readLine());
        for (int i = 0; i < q; ++i) {
            String[] inp = r.readLine().split(" ");
            int id = Integer.valueOf(inp[0]);
            String data = inp[1];
            nodes.get(h - 1).put(id, data);
            hashes.get(h - 1).put(id, hasher.digest(concat(zero, Base64.getDecoder().decode(data.getBytes()))));
        }
        for (int i = h - 1; i > 0; --i) {
            Set<Map.Entry<Integer, byte[]>> set = hashes.get(i).entrySet();
            for (Map.Entry<Integer, byte[]> el : set) {
                int id = el.getKey();
                if (id % 2 == 0) {
                    byte[] left = el.getValue();
                    byte[] right;
                    if (hashes.get(i).containsKey(id + 1)) {
                        right = hashes.get(i).get(id + 1);
                    } else {
                        right = new byte[0];
                    }
                    hashes.get(i - 1).put(id / 2, hasher.digest(concat(concat(one, left), concat(two, right))));
                } else {
                    byte[] left;
                    byte[] right = el.getValue();
                    if (hashes.get(i).containsKey(id - 1)) {
                        left = hashes.get(i).get(id - 1);
                    } else {
                        left = new byte[0];
                    }
                    hashes.get(i - 1).put(id / 2, hasher.digest(concat(concat(one, left), concat(two, right))));
                }
            }
        }

    }
}