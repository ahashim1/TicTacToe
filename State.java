import java.util.ArrayList;

// Author: Ali Hashim
// Interface defining state functions.
public interface State {
    Mark getTurn();
    void changeTurn();
    boolean isGameOver();
    boolean move(int board, int input, Mark player);
    void printBoard();
    Mark getWinner();
    ArrayList<Move> getPossibleMoves();
}
