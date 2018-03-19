 class BinarySearchSpan {
    public static void main(String[] args) {

        int[] arr = new int[args.length - 1];
        int key = Integer.valueOf(args[0]);

        for (int i = 1; i < args.length; i++) {
            arr[i - 1] = Integer.valueOf(args[i]);
        }

        int answer1 = BinarySearch_Left(arr, key);

        if (answer1 < 0){
            System.out.print(-answer1-1 + " 0");
        }
        else {
            int answer2 = BinarySearch_Right(arr, key, -1, arr.length);
            System.out.print(answer1 + " ");
            System.out.println(answer2 - answer1);
        }


    }

    //Pre:  (array[i] >= array[i+1]: 0 <= i < array.length-1)
    static int BinarySearch_Left(int[] array, int key) {
        int left = -1;
        int right = array.length;
        int mid = 0;
        //Pre: (mid, left, right - int) && (left == array.first - 1) && (right == array.last + 1)
        //I: (right' >= left' + 1 >= 0) && (right' == a.length || key > a[right']) && (left' == -1 || key <= a[left'])
        // array[i]' == array[i] i = [left', right'] && [0, array.length]
        while (right > left + 1) {
            mid = left + (right - left) / 2;
            //Pre: (mid' - left' == right' - mid') || (mid' - left' == right' - mid' - 1)
            if (array[mid] > key) {
                left = mid;
            } else {
                right = mid;
            }
            // Post: right' - left' <= (last_right - last_left)/2+1

        }
        //Post: (right == 0 || array[right - 1] > key) && (right == array.length || array[right] <= key)

        //Pre: last_post
        if (right == array.length || key != array[right]) {
            return (-right - 1);
        } else {
            return (right);
        }
        // Post: ((0 <= -ans-1 <= a.length)  && (-ans-1 == a.length || key > array[-ans-1]) &&
        //  (-ans-1 == 0 || key < array[-ans-2])) || (array[ans] == key)

    }
    // Post: ((0 <= -ans-1 <= a.length)  && (-ans-1 == a.length || key > array[-ans-1]) &&
    //  (-ans-1 == 0 || key < array[-ans-2])) || (array[ans] == key)

    // Pre: (array.length >= 0) &&(array[i] >= array[i+1]: 0 <= i < array.length-1) &&
    // (right == array.length || key >= array[right]) && (left == -1 || key < array[left]) && (right >= left + 1 >= 0)
    static int BinarySearch_Right(int[] array, int key, int left, int right) {
        if (right <= left)) {
            return right;
            // Post: ((ans == a.length || key > array[ans]) &&
            //  (ans == -1 || key <= array[ans - 1]))
        } else {
            int mid = left + (right - left) / 2;
            //Pre: (mid - left == right - mid) || (mid - left == right - mid - 1)
            // array[i]' == array[i] (i = [left, right] && [0, array.length])
            if (array[mid] >= key) {
                return BinarySearch_Right(array, key, mid, right);
            } else {
                return BinarySearch_Right(array, key, left, mid);

            }
            // Post: ((ans == a.length || key > array[ans]) &&
            //  (ans == -1 || key <= array[ans - 1]))
        }

    }
    // Post: ((ans == a.length || key > array[ans]) &&
    //  (ans == -1 || key <= array[ans - 1]))
}