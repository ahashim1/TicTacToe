// Author: Ali Hashim

// Class to define a move object with the board and position.
public class Move {
    private int board;
    private int position;

    public Move(int board, int position){
        this.board = board;
        this.position = position;
    }

    public int getBoard(){
        return board;

    }

    public int getPosition(){
        return position;
    }
}
