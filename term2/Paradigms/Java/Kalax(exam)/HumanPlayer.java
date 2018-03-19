/**
 * Created by Анна on 29.05.2017.
 */

import java.util.Scanner;

public class HumanPlayer implements Player {
    Scanner in = new Scanner(System.in);

    public void makeMove(BoardConfiguration board) {
        System.out.println("Enter your move (place where to start)");
        int x = in.nextInt();
        board.makeMove(x);
    }
}


