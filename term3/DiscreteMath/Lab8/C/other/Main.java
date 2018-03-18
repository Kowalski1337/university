import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Scanner in = new Scanner(System.in);
    public static PrintWriter out = new PrintWriter(System.out);
    public static boolean check(int X, int Y){
        out.println(1 + " " +  (X+1) + " " + (Y+1));
        out.flush();
        String s = in.next();
        return s.equals("YES");
    }

    public static int ans[];
    public static void merge(int[] result, int[] left, int[] right) {
        int i1 = 0;
        int i2 = 0;

        for (int i = 0; i < result.length; i++) {
            if (i2 >= right.length || (i1 < left.length && check(left[i1],right[i2]))) {
                result[i] = left[i1];
                i1++;
            } else {
                result[i] = right[i2];
                i2++;
            }
        }
    }

    public static void mergeSort(int[] array) {
        if (array.length > 1) {
            int[] left = leftHalf(array);
            int[] right = rightHalf(array);

            mergeSort(left);
            mergeSort(right);

            merge(array, left, right);
        }
    }

    public static int[] leftHalf(int[] array) {
        int size1 = array.length / 2;
        int[] left = new int[size1];
        for (int i = 0; i < size1; i++) {
            left[i] = array[i];
        }
        return left;
    }

    public static int[] rightHalf(int[] array) {
        int size1 = array.length / 2;
        int size2 = array.length - size1;
        int[] right = new int[size2];
        for (int i = 0; i < size2; i++) {
            right[i] = array[i + size1];
        }
        return right;
    }

    public static void main(String[] args) {
        int n = in.nextInt();
        ans = new int[n];
        for (int i = 0; i < n; i++){
            ans[i] = i;
        }
        mergeSort(ans);
        out.print(0 + " ");
        for (int i = 0; i < n; i++){
            out.print((ans[i]+1) + " ");
        }
        out.close();
        return;
    }
}
