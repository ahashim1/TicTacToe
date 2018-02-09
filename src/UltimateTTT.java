public class UltimateTTT extends NineBoard {

    private NineBoard nineBoard;
    private Board globalBoard;

    public UltimateTTT(){
        this.nineBoard = new NineBoard();
        this.globalBoard = new Board();
    }

    public boolean isGameOver(){
        return globalBoard.isGameOver();
    }

    public boolean move(int board, int position, State user){
        if (!nineBoard.move(board, position, user)){
            return false;
        }


        Board boardToCheck = nineBoard.getBoardWith(board);
        if (boardToCheck.isGameOver() && boardToCheck.getWinner() != State.BLANK){
            globalBoard.move(board, boardToCheck.getWinner());
            globalBoard.printBoard();
        }

        changeTurn();
        return true;

    }

    @Override public void printBoard(){
        nineBoard.printBoard();
    }


}
