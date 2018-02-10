import java.util.ArrayList;

public interface State {
    public Mark getTurn();
    public void changeTurn();
    public boolean isGameOver();
    public boolean move(int board, int input, Mark player);
    public void printBoard();
    public Mark getWinner();
    public ArrayList<Move> getPossibleMoves();
}
