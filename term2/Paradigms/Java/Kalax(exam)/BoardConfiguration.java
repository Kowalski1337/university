import java.io.PrintWriter;

/**
 * Created by Анна on 29.05.2017.
 */
public class BoardConfiguration {
    //private PrintWriter System.out = new PrintWriter(System.System.outtem.System.out);
    private int[][] board;
    private int[] score;
    private int next;

    public boolean isEnd;

    public boolean WhoseTurn(){
        return next == 0;
    }
    
    public boolean correctChoice(int x){
        int m = board[next][x];
        return m != 0;
    }

    public void makeMove(int x) {
        if (!correctChoice(x)){
            System.out.println("Wrong choice, there is no stones! You can choose only: ");
            for (int i = 0; i < 6; i++) {
                if (board[next][i] != 0) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
            return;
        }
        int y = next;
        int m = board[y][x];
        board[y][x] = 0;
        for (int i = 0; i < m; i++) {
            if (x == 6) {
                x = 0;
                y++;
            } else {
                x++;
            }
            if (i == m - 1 && x <= 5 && board[y % 2][x] == 0 && board[(y + 1) % 2][5 - x] != 0) {
                score[next] += board[(y + 1) % 2][5 - x] + 1;
                board[(y + 1) % 2][5 - x] = 0;
                break;
            }

            if (x == 6) {
                if (next % 2 != y % 2) {
                    y++;
                    x = 0;
                    board[y % 2][x]++;
                    continue;
                }
                score[next]++;
            } else {
                board[y % 2][x]++;
            }
        }
        if (x != 6 || next != y % 2) {
            next++;
            next %= 2;
        }
        info();
    }

    private void congr() {
        System.out.println("First player's score: " + score[0]);
        System.out.println("Second player's score: " + score[1]);
        System.out.println((score[0] == score[1]) ? "Nobody wins..." : score[0] > score[1] ? "First player wins!" : "Second player wins");
    }

    private void cont() {
        System.out.println("First player's score: " + score[0]);
        System.out.println("Second player's score: " + score[1]);
        System.out.println("Current configuration: ");
        System.out.println("First player's holes: ");
        for (int i = 0; i < 6; i++) {
            System.out.print(board[0][i] + " ");
        }
        System.out.println("\nSecond player's holes: ");
        for (int i = 0; i < 6; i++) {
            System.out.print(board[1][5 - i] + " ");
        }
        System.out.println("\n" + (next == 0 ? "First" : "Second") + " player's turn.");
    }

    private void check() {
        int sum = 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                sum += board[i][j];
            }
        }
        System.out.println("Current sum: " + sum);
    }

    private void info() {
        for (int j = 0; j < 2; j++) {
            boolean isEnd = true;
            for (int i = 0; i < 6; i++) {
                if (board[j][i] != 0) {
                    isEnd = false;
                }
            }
            if (isEnd) {
                for (int i = 0; i < 6; i++) {
                    score[(j + 1) % 2] += board[(j + 1) % 2][i];
                }
                this.isEnd = true;
                congr();
                return;
            }
        }
        cont();
        System.out.println("________________________________________");
        //check();
    }

    BoardConfiguration() {
        isEnd = false;
        board = new int[2][6];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = 6;
            }
        }
        score = new int[2];
        score[0] = score[1] = 0;
        next = 0;
    }

}
