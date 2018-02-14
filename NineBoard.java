import java.io.*;
import java.util.ArrayList;

// Author: Ali Hashim
// Class that keeps track of the NineBoard state. A good amount of code is redundant from the Board.java class
public class NineBoard implements State, Serializable{

    private int numBoards = 9;
    private Board[][] nineBoard = new Board[numBoards/3][numBoards/3];
    private Mark turn = Mark.X;
    private int activeBoard = -1;
    private Mark winner = Mark.BLANK;


    public NineBoard(){
        initializeBoard();
    }

    //    Deep Clone from: https://www.avajava.com/tutorials/lessons/how-do-i-perform-a-deep-clone-using-serializable.html
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


    // Gets the active board or the board the player has to play in. returns -1 if there is a free play.
    public int getActiveBoard(){
        return activeBoard;
    }

    // Helper to return the Board with an input from 1-9
    public Board getBoardWith(int input){
        int boardRow = (input - 1)/3;
        int boardCol = (input - 1)%3;
        return nineBoard[boardRow][boardCol];
    }

    // Initializes the 9-board by initializing the array with Boards
    public void initializeBoard(){
        for (int i = 0; i < numBoards/3; i++){
            for (int j = 0; j<numBoards/3; j++){
                nineBoard[i][j] = new Board();
            }
        }
    }

    // Ugly method to print the board.
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

    // helper function for possible moves
    public boolean isBoardAvailable(int boardInput){
        Board board = getBoardWith(boardInput);

        // If the board is drawn return false.
        if (board.isGameOver() && board.getWinner() == Mark.BLANK){
            return false;
        }

        return true;
    }

    // Tests terminal state
    public boolean isGameOver(){

        // Tests draw case.
        for (int i = 0; i<(numBoards/3); i++){
            for (int j = 0; j<(numBoards/3); j++){
                if (nineBoard[i][j].isGameOver() && nineBoard[i][j].getWinner() == Mark.BLANK){
                    if (i == 2 && j == 2){
                        winner = Mark.BLANK;
                        return true;
                    }
                }else{
                    break;
                }
            }
        }

        for (int i = 0; i<(numBoards/3); i++){

            for (int j = 0; j<(numBoards/3); j++){
                Board board = nineBoard[i][j];
                // someone won
                if (board.isGameOver() && board.getWinner() != Mark.BLANK){
                    winner = board.getWinner();
                    return true;
                }

            }
        }
        return false;
    }


    // Action for 9 board
    public boolean move(int board, int position, Mark user){
        int boardRow = (board - 1)/3;
        int boardCol = (board - 1)%3;

        // Moves in an individual board
        if (!nineBoard[boardRow][boardCol].move(activeBoard, position, user)){
            return false;
        }

        // Changes the activeBoard
        activeBoard = position;
        // Toggles turn
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

    // Applicability
    public ArrayList<Move> getPossibleMoves() {
        ArrayList<Move> arr = new ArrayList<Move>();

        // free move
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

    
    public int numberFreePlays(int boardInput){
        int freePlays = 0;

        for (int i = 1; i<=9; i++){

            if (!getBoardWith(boardInput).isTileTaken(i)){
                if (getBoardWith(i).isGameOver()){
                    freePlays++;
                }
            }
        }


        return freePlays;
    }

}
