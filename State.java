import java.util.ArrayList;

public interface State {
    Mark getTurn();
    void changeTurn();
    boolean isGameOver();
    boolean move(int board, int input, Mark player);
    void printBoard();
    Mark getWinner();
    ArrayList<Move> getPossibleMoves();
}
