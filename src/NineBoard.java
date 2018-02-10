import java.io.*;
import java.util.ArrayList;

public class NineBoard implements State, Serializable{

    private int numBoards = 9;
    private Board[][] nineBoard = new Board[numBoards/3][numBoards/3];
    private Mark turn = Mark.X;
    private int activeBoard = -1;
    private int previousBoard = -1;
    private Mark winner = Mark.BLANK;


    public NineBoard(){
        initializeBoard();
    }

    public NineBoard deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (NineBoard) ois.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    public int getActiveBoard(){
        return activeBoard;
    }
    public Board getBoardWith(int input){
        int boardRow = (input - 1)/3;
        int boardCol = (input - 1)%3;
        return nineBoard[boardRow][boardCol];
    }

    public void initializeBoard(){
        for (int i = 0; i < numBoards/3; i++){
            for (int j = 0; j<numBoards/3; j++){
                nineBoard[i][j] = new Board();
            }
        }
    }
    public void printBoard(){

        for (int boardRow = 0; boardRow<3; boardRow++){

            for (int positionRow = 0; positionRow < 3; positionRow++){

                System.err.print(" ");

                for (int boardCol = 0; boardCol<numBoards/3; boardCol++){
                    System.err.print("|");

                    for (int positionCol = 0; positionCol<numBoards/3; positionCol++){

                        Mark mark = nineBoard[boardRow][boardCol].getTileValueWith(positionRow, positionCol);
                        if (mark == Mark.BLANK) {
                            System.err.print("_");
                        }else{
                            System.err.print(mark);

                        }

                        System.err.print("|");
                    }
                    System.err.print("  ");


                }

                System.err.println();



            }


            System.err.println();
        }
    }

    public boolean isBoardAvailable(int boardInput){
        Board board = getBoardWith(boardInput);
        if (board.isGameOver() && board.getWinner() == Mark.BLANK){
            return false;
        }

        return true;
    }

    public boolean isGameOver(){


        for (int i = 0; i<(numBoards/3); i++){

            for (int j = 0; j<(numBoards/3); j++){
                Board board = nineBoard[i][j];
                // someone won
                if (board.isGameOver() && board.getWinner() != Mark.BLANK){
                    winner = board.getWinner();
                    return true;
                }
//
//                // Available spots
//                if (!board.isGameFull()){
//                    return false;
//                }
            }
        }
        // draw
        return false;
    }

//    public void undoMove(int activeBoard, int position){
//        Board board = getBoardWith(activeBoard);
//        board.undoMove(activeBoard, position);
//        changeTurn();
//        this.activeBoard = previousBoard;
//        previousBoard = -1;
//    }


    public boolean move(int board, int position, Mark user){
        int boardRow = (board - 1)/3;
        int boardCol = (board - 1)%3;
        if (!nineBoard[boardRow][boardCol].move(activeBoard, position, user)){
            return false;
        }
        previousBoard = activeBoard;
        activeBoard = position;
        changeTurn();
        return true;
    }

    public Mark getWinner(){
        return winner;
    }

    public Mark getTurn(){
        return turn;
    }

    public void changeTurn(){
        turn = turn == Mark.X ? Mark.O : Mark.X;
    }

    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> arr = new ArrayList<Move>();

        if (activeBoard != -1){
            if (!isBoardAvailable(activeBoard)){
                activeBoard = -1;
            }
        }

        if (activeBoard == -1){
            for (int boardNo = 1; boardNo<=9; boardNo++){
                if (isBoardAvailable(boardNo)){
                    for (int positionNo = 1; positionNo<=9;positionNo++){
                        if (!getBoardWith(boardNo).isTileTaken(positionNo)){
                            arr.add(new Move(boardNo, positionNo));
                        }
                    }
                }
            }
        }else{
            for (int positionNo = 1; positionNo<=9;positionNo++){
                if (!getBoardWith(activeBoard).isTileTaken(positionNo)){
                    arr.add(new Move(activeBoard, positionNo));
                }
            }
        }

        return arr;
    }
}
