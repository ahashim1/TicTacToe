public class NineBoard extends Board {

    private int numBoards = 9;
    private Board[][] nineBoard = new Board[numBoards/3][numBoards/3];
    protected State turn = State.X;
    protected int activeBoard = -1;
    protected int previousBoard = -1;

    public NineBoard(){
        initializeBoard();
    }

    public int getActiveBoard(){
        return activeBoard;
    }
    public Board getBoardWith(int input){
        int boardRow = (input - 1)/3;
        int boardCol = (input - 1)%3;
        return nineBoard[boardRow][boardCol];
    }
    @Override public void initializeBoard(){
        for (int i = 0; i < numBoards/3; i++){
            for (int j = 0; j<numBoards/3; j++){
                nineBoard[i][j] = new Board();
            }
        }
    }
    @Override public void printBoard(){

        for (int boardRow = 0; boardRow<3; boardRow++){

            for (int positionRow = 0; positionRow < 3; positionRow++){

                System.err.print(" ");

                for (int boardCol = 0; boardCol<numBoards/3; boardCol++){
                    System.err.print("|");

                    for (int positionCol = 0; positionCol<numBoards/3; positionCol++){

                        State mark = nineBoard[boardRow][boardCol].getTileValueWith(positionRow, positionCol);
                        if (mark == State.BLANK) {
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
        if (board.isGameOver() && board.getWinner() == State.BLANK){
            return false;
        }

        return true;
    }

    @Override public boolean isGameOver(){


        for (int i = 0; i<(numBoards/3); i++){

            for (int j = 0; j<(numBoards/3); j++){
                Board board = nineBoard[i][j];
                // someone won
                if (board.isGameOver() && board.getWinner() != State.BLANK){
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

    public void undoMove(int activeBoard, int position){
        Board board = getBoardWith(activeBoard);
        board.undoMove(position);
        changeTurn();
        this.activeBoard = previousBoard;
        previousBoard = -1;
    }


    public boolean move(int board, int position, State user){
        int boardRow = (board - 1)/3;
        int boardCol = (board - 1)%3;
        if (!nineBoard[boardRow][boardCol].move(position, user)){
            return false;
        }
        previousBoard = activeBoard;
        activeBoard = position;
        changeTurn();
        return true;
    }

}
