// Author Ali Hashim
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

// This class defines the state for the basic TTT board.
public class Board implements State, Serializable {

    // boardSize of 3 for TTT
    private int boardSize = 3;

    // Main underlying data structure is a 2d array of Marks
    private Mark[][] board = new Mark[boardSize][boardSize];

    // First player is X
    private Mark turn = Mark.X;

    // Sets variables to default
    private boolean isGameOver = false;
    private Mark winner = Mark.BLANK;

    public Board(){
        initializeBoard();
    }


//    Deep Clone from: https://www.avajava.com/tutorials/lessons/how-do-i-perform-a-deep-clone-using-serializable.html
    public Board deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Board) ois.readObject();
        } catch (IOException e) {
            return null;
        } catch (ClassNotFoundException e) {
            return null;
        }
    }


    // gets the player's turn
    public Mark getTurn(){
        return turn;
    }

    // toggles which turn it is
    public void changeTurn(){
        turn = turn == Mark.X ? Mark.O: Mark.X;
    }

    // tests terminal state
    public  boolean isGameOver(){
        return isGameOver;
    }

    // initializes the board with blanks (initial state)
    public void initializeBoard(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                board[i][j] = Mark.BLANK;
            }
        }
    }

    // gets applicable actions
    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> arr = new ArrayList<>();
        for (int i = 1; i<=9; i++){
            if (!isTileTaken(i)){
                arr.add(new Move(0, i));
            }
        }
        return arr;
    }

    // helper to get applicable actions
    public boolean isTileTaken(int input){
        Mark tile = getTileValueWith(input);
        if (tile == Mark.BLANK){
            return false;
        }


        return true;
    }


    // Converts 1-9 input to row and col values and returns the value
    public Mark getTileValueWith(int input){
        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        return board[row][col];
    }

    public Mark getTileValueWith(int row, int col){
        return board[row][col];
    }


    // Action function
    public boolean move(int boardNo, int input, Mark player){

        if (isTileTaken(input)){
            return false;
        }

        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        board[row][col] = player;

        // checks goalState 
        if (checkColumn(col, player) || checkRow(row, player) || checkDiag(row, col, player) || checkAntiDiag(row, col, player)){
            isGameOver = true;
            winner = player;
            changeTurn();
            return true;
        }

        // checks draw state

        if (checkDraw()){
            isGameOver = true;
            winner = Mark.BLANK;
        }


        // toggles player
        changeTurn();
        return true;
    }

    private boolean checkColumn(int col, Mark mark){
        for (int i = 0; i < boardSize; i++){
            if (board[i][col] != mark){
                break;
            }

            if (i == boardSize - 1){
                return true;
            }
        }

        return false;
    }
    private boolean checkRow(int row, Mark mark){
        for (int i = 0; i < boardSize; i++){
            if (board[row][i] != mark){
                break;
            }

            if (i == boardSize - 1){
                return true;
            }
        }

        return false;

    }

    private boolean checkDiag(int row, int col, Mark mark){
        if (row == col){
            for (int i = 0; i < boardSize; i++){
                if(board[i][i] != mark){
                    break;
                }

                if(i == boardSize - 1){

                    return true;
                }
            }
        }

        return false;
    }

    private boolean checkAntiDiag(int row, int col, Mark mark){
        if (row + col == boardSize - 1){
            for (int i = 0; i < boardSize; i++){
                if(board[i][boardSize - 1 - i] != mark){
                    break;
                }

                if(i == boardSize - 1){

                    return true;
                }
            }
        }

        return false;

    }

    private boolean checkDraw(){
        for (int i = 1; i<=9; i++){
            if (!isTileTaken(i)){
                return false;
            }
        }
        return true;
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++){
            System.err.println("+---+---+---+");
            for (int j = 0; j < boardSize; j++){
                System.err.print("| ");

                if (board[i][j] == Mark.BLANK){
                    System.err.print("_");
                }else {
                    System.err.print(board[i][j]);
                }
                System.err.print(" ");
            }
            System.err.println("|");

        }
        System.err.println("+---+---+---+");
    }

    public Mark getWinner(){
        return winner;
    }






}

