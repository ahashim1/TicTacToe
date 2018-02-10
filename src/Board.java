import java.util.ArrayList;
public class Board {
    private int boardSize = 3;
    private State[][] board = new State[boardSize][boardSize];
    private State turn = State.X;
    private boolean isGameOver = false;
    public State winner = State.BLANK;

    public Board(){
        initializeBoard();
    }



    public State getTurn(){
        return turn;
    }

    public void changeTurn(){
        turn = turn == State.X ? State.O: State.X;
    }



    public  boolean isGameOver(){
        return isGameOver;
    }
    public  boolean isGameFull() {
        return isGameOver && getWinner() == State.BLANK;
    } public State getWinner(){
        return winner;
    }

    public void initializeBoard(){
        for (int i = 0; i < boardSize; i++){
            for (int j = 0; j<boardSize; j++){
                board[i][j] = State.BLANK;
            }
        }

//        board[0][0] = State.X;
//        board[0][1] = State.O;
//        board[0][2] = State.BLANK;
//        board[1][0] = State.BLANK;
//        board[1][1] = State.O;
//        board[1][2] = State.BLANK;
//        board[2][0] = State.O;
//        board[2][1] = State.X;
//        board[2][2] = State.X;

    }

    public ArrayList<Integer> getPossibleMoves(){
        ArrayList<Integer> arr = new ArrayList<>();
        for (int i = 1; i<=9; i++){
            if (!isTileTaken(i)){
                arr.add(i);
            }
        }
        return arr;
    }
    private boolean isTileTaken(int input){
        State tile = getTileValueWith(input);
        if (tile == State.BLANK){
            return false;
        }


        return true;
    }

    public State getTileValueWith(int input){
        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        return board[row][col];
    }

    public State getTileValueWith(int row, int col){
        return board[row][col];
    }

    public boolean move(int input, State player){

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
            winner = State.BLANK;
        }

        changeTurn();
        return true;
    }

    public void undoMove(int input){

        int row = (input - 1) / boardSize;
        int col = (input - 1) % boardSize;
        winner = State.BLANK;
        isGameOver = false;
        changeTurn();
        board[row][col] = State.BLANK;
    }


    private boolean checkColumn(int col, State mark){
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
    private boolean checkRow(int row, State mark){
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

    private boolean checkDiag(int row, int col, State mark){
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

    private boolean checkAntiDiag(int row, int col, State mark){
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
//                This is more efficient. doing above for testing purposes
//        return moveCount == boardSize * boardSize ;

    }
    public void printBoard() {
        for (int i = 0; i < boardSize; i++){
            System.err.println("+---+---+---+");
            for (int j = 0; j < boardSize; j++){
                System.err.print("| ");

                if (board[i][j] == State.BLANK){
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







}

