import java.io.File;
import java.io.PrintWriter;
import java.util.PriorityQueue;

/**
 * Created by vadim on 12.05.2017.
 */
public class Check {
    public static void main(String[] args) throws Exception{
        PrintWriter out = new PrintWriter(new File("check.out"));
        Heap myHeap = new Heap(5);
        //myHeap.changeValue(0, 8);
        myHeap.changeValue(3, 4);

        myHeap.changeValue(2, 5);

        Node lol = myHeap.top();
        out.printf("%d %.0f\n",  lol.key, lol.value);

        myHeap.changeValue(4, 1);
        myHeap.changeValue(2, 0);
        myHeap.changeValue(1, -2);

        out.println(myHeap.size());
        /*for (int i = 0; i < myHeap.size(); i++){
            out.println(myHeap.heap.get(i).key + " " + myHeap.heap.get(i).value);
        }*/

        while (myHeap.size() != 0){
            Node cur = myHeap.top();
            out.printf("%d %.0f\n", cur.key, cur.value);
        }
        out.close();
    }
}
