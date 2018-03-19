import javafx.scene.input.ScrollEvent;

/**
 * Created by Анна on 29.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        BoardConfiguration board = new BoardConfiguration();
        ComputerPlayer p1 = new ComputerPlayer();
        HumanPlayer p2 = new HumanPlayer();

        while (!board.isEnd) {
            if (board.WhoseTurn()){
                p2.makeMove(board);
            }
            else {
                p1.makeMove(board);
            }
        }
    }
}
