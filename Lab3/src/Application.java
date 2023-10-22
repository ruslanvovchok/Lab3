
import java.util.Scanner;

public class Application {

	static char[] board = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    static char currentPlayer = 'X';

    public static void main(String[] args) {
        while (true) {
            printBoard();
            if (currentPlayer == 'X') {
                playerMove();
            } else {
                aiMove();
            }
            if (checkWin()) {
                System.out.println("Player " + currentPlayer + " win!");
                break;
            }
            if (isBoardFull()) {
                System.out.println("Draw!");
                break;
            }
            togglePlayer();
        }
    }

    static void printBoard() {
        System.out.println(board[0] + "|" + board[1] + "|" + board[2]);
        System.out.println("-+-+-");
        System.out.println(board[3] + "|" + board[4] + "|" + board[5]);
        System.out.println("-+-+-");
        System.out.println(board[6] + "|" + board[7] + "|" + board[8]);
        System.out.println();
    }

    static void playerMove() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Your turn (1-9):");
        int move = scanner.nextInt() - 1;
        if (board[move] != ' ') {
            System.out.println("This cell was busy!");
            playerMove();
        } else {
            board[move] = currentPlayer;
        }
    }

    static void aiMove() {
        int bestScore = Integer.MIN_VALUE;
        int move = -1;
        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = currentPlayer;
                int score = minimax(board, 0, false);
                board[i] = ' ';
                if (score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        board[move] = currentPlayer;
    }

    static int minimax(char[] board, int depth, boolean isMaximizing) {
        if (checkWin()) {
            return isMaximizing ? -1 : 1;
        }

        if (isBoardFull()) {
            return 0;
        }

        int bestScore = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        char saveCurrentPlayer = currentPlayer;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == ' ') {
                board[i] = isMaximizing ? 'O' : 'X';
                currentPlayer = isMaximizing ? 'O' : 'X';
                int score = minimax(board, depth + 1, !isMaximizing);
                board[i] = ' ';
                currentPlayer = saveCurrentPlayer;

                bestScore = isMaximizing ?
                        Math.max(score, bestScore) :
                        Math.min(score, bestScore);
            }
        }
        return bestScore;
    }

    static boolean checkWin() {
        int[][] winConditions = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };
        for (int[] condition : winConditions) {
            if (board[condition[0]] != ' ' &&
                    board[condition[0]] == board[condition[1]] &&
                    board[condition[0]] == board[condition[2]]) {
                return true;
            }
        }
        return false;
    }

    static boolean isBoardFull() {
        for (char c : board) {
            if (c == ' ') {
                return false;
            }
        }
        return true;
    }

    static void togglePlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }
}
