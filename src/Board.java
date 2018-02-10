import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
public class Board implements State, Serializable {
    private int boardSize = 3;
    private Mark[][] board = new Mark[boardSize][boardSize];
    private Mark turn = Mark.X;
    private boolean isGameOver = false;
    private Mark winner = Mark.BLANK;

    public Board(){
        initializeBoard();
    }

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


    public Mark getTurn(){
        return turn;
    }

    public void changeTurn(){
        turn = turn == Mark.X ? Mark.O: Mark.X;
    }



    public  boolean isGameOver(){
        return isGameOver;
    }
//    public  boolean isGameFull() {
//        return isGameOver && getWinner() == Mark.BLANK;
//    } public Mark getWinner(){
//        return winner;
//    }

    public void initializeBoard(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                board[i][j] = Mark.BLANK;
            }
        }
    }

    public ArrayList<Move> getPossibleMoves(){
        ArrayList<Move> arr = new ArrayList<>();
        for (int i = 1; i<=9; i++){
            if (!isTileTaken(i)){
                arr.add(new Move(0, i));
            }
        }
        return arr;
    }
    public boolean isTileTaken(int input){
        Mark tile = getTileValueWith(input);
        if (tile == Mark.BLANK){
            return false;
        }


        return true;
    }

    public Mark getTileValueWith(int input){
        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        return board[row][col];
    }

    public Mark getTileValueWith(int row, int col){
        return board[row][col];
    }

    public boolean move(int boardNo, int input, Mark player){

        if (isTileTaken(input)){
            return false;
        }

        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        board[row][col] = player;

        if (checkColumn(col, player) || checkRow(row, player) || checkDiag(row, col, player) || checkAntiDiag(row, col, player)){
            isGameOver = true;
            winner = player;
            changeTurn();
            return true;
        }



        if (checkDraw()){
            isGameOver = true;
            winner = Mark.BLANK;
        }


        changeTurn();
        return true;
    }

    public void undoMove(int boardInput, int input){

        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        winner = Mark.BLANK;
        isGameOver = false;
        changeTurn();
        board[row][col] = Mark.BLANK;
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

