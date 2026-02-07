import java.util.ArrayList;
import java.util.Scanner;

abstract class Player {
    public abstract TicTacToe chooseMove(TicTacToe board);

    public double boardValue(TicTacToe board) {
        if (board.checkWin(this)) return 1.0;
        if (board.checkLose(this)) return -1.0;
        return 0.0;
    }
}

class AIPlayer extends Player {
    private String name;
    private Player opponent;

    public AIPlayer(String name, Player opponent) {
        this.name = name;
        this.opponent = opponent;
    }

    public Player getOpponent() {
        return opponent;
    }

    public void setOpponent(Player opponent) {
        this.opponent = opponent;
    }

    public String toString() {
        return name + " (AI)";
    }

    public TicTacToe chooseMove(TicTacToe board) {
        TicTacToe[] options = board.possibleMoves(this);
        double best = Double.NEGATIVE_INFINITY;
        TicTacToe bestMove = null;

        for (TicTacToe opt : options) {
            double val = minValue(opt);
            if (val > best) {
                best = val;
                bestMove = opt;
            }
        }
        return bestMove;
    }

    public double maxValue(TicTacToe board) {
        if (board.checkWin(this)) return 1.0;
        if (board.checkLose(this)) return -1.0;
        if (board.checkDraw()) return 0.0;

        double best = Double.NEGATIVE_INFINITY;
        for (TicTacToe opt : board.possibleMoves(this))
            best = Math.max(best, minValue(opt));
        return best;
    }

    public double minValue(TicTacToe board) {
        if (board.checkWin(this)) return 1.0;
        if (board.checkLose(this)) return -1.0;
        if (board.checkDraw()) return 0.0;

        double worst = Double.POSITIVE_INFINITY;
        for (TicTacToe opt : board.possibleMoves(opponent))
            worst = Math.min(worst, maxValue(opt));
        return worst;
    }

    public double boardValue(TicTacToe board) {
        return maxValue(board);
    }
}

class UserPlayer extends Player {
    private String name;
    private Scanner input;

    public UserPlayer(Scanner input, String name) {
        this.input = input;
        this.name = name;
    }

    public String toString() {
        return name;
    }

    public TicTacToe chooseMove(TicTacToe board) {
        System.out.println("Current Board:\n" + board);
        TicTacToe[] options = board.possibleMoves(this);

        for (int i = 0; i < options.length; i++) {
            System.out.println("Option " + i + ":\n" + options[i]);
        }

        System.out.print("Choose move index: ");
        int choice = input.nextInt();
        return options[choice];
    }
}

class TicTacToe {
    private char[][] board;
    private Player x;
    private Player o;

    public TicTacToe(Player x, Player o) {
        this.x = x;
        this.o = o;
        board = new char[3][3];

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = '_';
    }

    public void setBoard(char[][] inputBoard) {
        board = new char[3][3];
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                board[i][j] = inputBoard[i][j];
    }

    public int countBlanks() {
        int count = 0;
        for (char[] row : board)
            for (char c : row)
                if (c == '_') count++;
        return count;
    }

    public char markerForPlayer(Player p) {
        return (p == x) ? 'X' : 'O';
    }

    public boolean checkWin(Player p) {
        char m = markerForPlayer(p);

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == m && board[i][1] == m && board[i][2] == m) return true;
            if (board[0][i] == m && board[1][i] == m && board[2][i] == m) return true;
        }

        return (board[0][0] == m && board[1][1] == m && board[2][2] == m) ||
               (board[0][2] == m && board[1][1] == m && board[2][0] == m);
    }

    public boolean checkLose(Player p) {
        return checkWin((p == x) ? o : x);
    }

    public boolean checkDraw() {
        return countBlanks() == 0 && !checkWin(x) && !checkWin(o);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : board) {
            for (char c : row) sb.append(c);
            sb.append('\n');
        }
        return sb.toString();
    }

    public TicTacToe[] possibleMoves(Player p) {
        char mark = markerForPlayer(p);
        ArrayList<TicTacToe> moves = new ArrayList<>();

        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == '_') {
                    TicTacToe copy = new TicTacToe(x, o);
                    char[][] newBoard = new char[3][3];

                    for (int r = 0; r < 3; r++)
                        for (int c = 0; c < 3; c++)
                            newBoard[r][c] = board[r][c];

                    newBoard[i][j] = mark;
                    copy.setBoard(newBoard);
                    moves.add(copy);
                }

        return moves.toArray(new TicTacToe[0]);
    }
}

public class TicTacToeGame {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        UserPlayer human = new UserPlayer(input, "Human");
        AIPlayer ai = new AIPlayer("Computer", human);

        TicTacToe game = new TicTacToe(human, ai);

        Player current = human;

        while (true) {
            game = current.chooseMove(game);

            if (game.checkWin(current)) {
                System.out.println(game);
                System.out.println(current + " wins!");
                break;
            }

            if (game.checkDraw()) {
                System.out.println(game);
                System.out.println("Draw.");
                break;
            }

            current = (current == human) ? ai : human;
        }

        input.close();
    }
}
