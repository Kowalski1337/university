public class SumLong {
    public static void main(String[] args) {
        long result = 0;
        for (int i = 0; i < args.length; i++) {
            int first = 0;
            boolean start = false;

            for (int j = 0; j < args[i].length(); j++) {
                if (start == false) {
                    if (!Character.isWhitespace(args[i].charAt(j))) {
                        start = true;
                        first = j;
                    }
                } else {
                    if (Character.isWhitespace(args[i].charAt(j))) {
                        start = false;
                        if (j > first) {
                            result += Long.valueOf(args[i].substring(first, j));
                        }
                    }


                }
                if (j == args[i].length() - 1 && start == true) {
                    result += Long.valueOf(args[i].substring(first, j + 1));
                    start = false;
                }

            }
        }
        System.out.println(result);


    }
}