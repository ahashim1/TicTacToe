import java.io.*;
import java.util.ArrayList;

public class UltimateTTT implements State, Serializable {

    private NineBoard nineBoard;
    private Board globalBoard;
    private Mark turn = Mark.X;
    private Mark winner = Mark.BLANK;
    private int activeBoard = -1;

    public UltimateTTT(){
        this.nineBoard = new NineBoard();
        this.globalBoard = new Board();
    }

    public UltimateTTT deepClone(){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (UltimateTTT) ois.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public boolean isGameOver(){
        if (globalBoard.isGameOver()){
            winner = globalBoard.getWinner();
            return true;
        }

        return false;
    }

    public boolean move(int board, int position, Mark user){
        if (globalBoard.isTileTaken(board)){
            activeBoard = -1;
            return false;
        }
        if (!nineBoard.move(board, position, user)){
            return false;
        }


        Board boardToCheck = nineBoard.getBoardWith(board);
        if (boardToCheck.isGameOver() && boardToCheck.getWinner() != Mark.BLANK){
            globalBoard.move(0, board, boardToCheck.getWinner());
        }


        changeTurn();
        activeBoard = position;
        if (globalBoard.isTileTaken(activeBoard)) {
            activeBoard = -1;
        }
        return true;

    }

    public Mark getTurn(){
        return turn;
    }

    public void changeTurn(){
        turn = turn == Mark.X ? Mark.O: Mark.X;
    }

    public Mark getWinner(){
        return winner;
    }

    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> arr = new ArrayList<>();

        if (activeBoard != -1){
            if (!nineBoard.isBoardAvailable(activeBoard) || globalBoard.isTileTaken(activeBoard)){
                activeBoard = -1;
            }
        }

        if (activeBoard == -1){
            for (int boardNo = 1; boardNo<=9; boardNo++){
                if (nineBoard.isBoardAvailable(boardNo)){
                    for (int positionNo = 1; positionNo<=9;positionNo++){
                        if (!nineBoard.getBoardWith(boardNo).isTileTaken(positionNo)){
                            arr.add(new Move(boardNo, positionNo));
                        }
                    }
                }
            }
        }else{
            for (int positionNo = 1; positionNo<=9;positionNo++){
                if (!nineBoard.getBoardWith(activeBoard).isTileTaken(positionNo)){
                    arr.add(new Move(activeBoard, positionNo));
                }
            }
        }

        return arr;

    }
    public void printBoard(){
        globalBoard.printBoard();
        nineBoard.printBoard();
    }

    public int getActiveBoard(){
        return activeBoard;
    }

    public Board getBoardWith(int input){
        return nineBoard.getBoardWith(input);
    }

    public Board getGlobalBoard(){
        return globalBoard;
    }

    // If global board spot is taken or if there is the ability to play 3 in a row increase number of free plays after state.
    public int numberFreePlays(int boardInput){
        int freePlays = 0;

        for (int i = 1; i<=9; i++){
            if (!nineBoard.getBoardWith(boardInput).isTileTaken(i)){
                 if (globalBoard.isTileTaken(i)){
                     freePlays++;
                 }
            }
        }


        return freePlays;
    }

    public boolean isBoardAvailable(int input){
        return nineBoard.isBoardAvailable(input);
    }



}
