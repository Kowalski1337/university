import java.util.Random;
import java.util.Scanner;

/**
 * Created by vadim on 01.06.2017.
 */
public class ComputerPlayer implements Player {
    public void makeMove(BoardConfiguration board) {
        Random rand = new Random();
        int x = -1;
        while (x == -1 || !board.correctChoice(x)) {
            x = Math.abs(rand.nextInt()) % 6;
        }
        System.out.println("Your opponent choose: " + x);
        board.makeMove(x);
    }
}
