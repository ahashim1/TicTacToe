public class NineBoardAdversarialSearch {
    State user;
    State bot;
    NineBoard nineBoard;
    private int maxDepth = 5;

    public NineBoardAdversarialSearch(State user, State bot, NineBoard nineBoard){
        this.user = user;
        this.bot = bot;
        this.nineBoard = nineBoard;
    }

    public Move getAIMove(){
        int activeBoard = nineBoard.getActiveBoard();
        int maxScore = -Integer.MAX_VALUE;
        int move = -1;

        if (activeBoard == -1){
            for (int i = 1; i<=9; i++){
                if (nineBoard.isBoardAvailable(i)){
                    Board board = nineBoard.getBoardWith(i);
                    for (int j = 1; j<=9; j++){
                        if (!board.isTileTaken(j)){
                            nineBoard.move(i, j, bot);
                            int score = minimaxWithAlphaBeta(nineBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
                            nineBoard.undoMove(i, j);
                            if (score > maxScore){
                                activeBoard = i;
                                move = j;
                                maxScore = score;
                            }

                        }
                    }
                }
            }
        }else{
            Board board = nineBoard.getBoardWith(activeBoard);
            for (int i = 1; i<=9; i++){
                if (!board.isTileTaken(i)){
                    nineBoard.move(activeBoard, i, bot);
                    int score = minimaxWithAlphaBeta(nineBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
                    nineBoard.undoMove(activeBoard, i);
                    if (score > maxScore){
                        move = i;
                        maxScore = score;
                    }

                }
            }
        }


        return new Move(activeBoard, move);
    }

    private int minimaxWithAlphaBeta(NineBoard nineBoard, int alpha, int beta, boolean isMax, int depth){

        if (nineBoard.isGameOver() || depth > maxDepth){
            return score(nineBoard);


        }
        if (isMax){
            return getMaxWithAlphaBeta(nineBoard, alpha, beta, depth);
        }else{
            return getMinWithAlphaBeta(nineBoard, alpha, beta, depth);
        }
    }

    private int getMaxWithAlphaBeta(NineBoard nineBoard, int alpha, int beta, int depth){
        int activeBoard = nineBoard.getActiveBoard();
        Board board = nineBoard.getBoardWith(activeBoard);

        for (int i = 1; i<=9; i++){
            if (!board.isTileTaken((i))){
                nineBoard.move(activeBoard, i, bot);
                int score = minimaxWithAlphaBeta(nineBoard, alpha, beta, false, depth++);

                nineBoard.undoMove(activeBoard, i);

                if (score > alpha){
                    alpha = score;
                }

                if (alpha >= beta){
                    return alpha;
                }
            }
        }

        return alpha;
    }

    private int getMinWithAlphaBeta(NineBoard nineBoard, int alpha, int beta, int depth){
        int activeBoard = nineBoard.getActiveBoard();
        Board board = nineBoard.getBoardWith(activeBoard);

        for (int i = 1; i<=9; i++){
            if (!board.isTileTaken(i)){
                nineBoard.move(activeBoard, i, user);

                int score = minimaxWithAlphaBeta(nineBoard, alpha, beta, true, depth++);

                nineBoard.undoMove(activeBoard, i);

                if (score < beta){
                    beta = score;
                }

                if (alpha >= beta){
                    return beta;
                }
            }
        }

        return beta;
    }

    private int score(NineBoard nineBoard){
        if (nineBoard.isGameOver()){

            if (nineBoard.getWinner() == bot){
                return 100000;
            }else if (nineBoard.getWinner() == user){
                return -100000;
            }else{

                return 0;

            }
        }else{
            return heuristic(nineBoard);
        }

    }

    private int heuristic(NineBoard nineBoard){

        int sum = 0;
        for (int boardInput = 1; boardInput<=9; boardInput++){

            Board board = nineBoard.getBoardWith(boardInput);
            for (int row = 0; row<3; row++){
                int botRowCount = 0;
                int botColCount = 0;
                int userRowCount = 0;
                int userColCount = 0;
                int botDiagCount = 0;
                int userDiagCount = 0;
                int botAntiDiagCount = 0;
                int userAntiDiagCount = 0;
                for (int col = 0; col<3; col++){
                    if (board.getTileValueWith(row, col) == bot){
                        botRowCount++;
                    }else if (board.getTileValueWith(row, col) == user){
                        userRowCount++;
                    }

                    if (board.getTileValueWith(col, row) == bot){
                        botColCount++;
                    }else if (board.getTileValueWith(col, row) == user){
                        botRowCount++;
                    }
                }

                // Diaganol

                if (board.getTileValueWith(row, row) == bot){
                    botDiagCount++;
                }else if (board.getTileValueWith(row, row) == user){
                    userDiagCount++;
                }

                if (board.getTileValueWith(row, 2-row) == bot){
                    botAntiDiagCount++;
                }else if (board.getTileValueWith(row, 2- row) == user){
                    userAntiDiagCount++;
                }



                if (row == 2){
                    if (botDiagCount>0 && userDiagCount==0){
                        sum += 100 * botDiagCount;
                    }else if (botDiagCount==0 && userDiagCount > 0){
                        sum -= 100 * botDiagCount;
                    }

                    if (botAntiDiagCount>0 && userAntiDiagCount==0){
                        sum += 100 * botAntiDiagCount;
                    }else if (botAntiDiagCount==0 && userAntiDiagCount > 0){
                        sum -= 100 * botAntiDiagCount;
                    }
                }


                if (botRowCount>0 && userRowCount==0){
                    sum += 100 * botRowCount;
                }else if (botRowCount==0 && userRowCount > 0){
                    sum -= 100 * botRowCount;
                }

                if (botColCount>0 && userColCount==0){
                    sum += 100 * botColCount;
                }else if (botColCount==0 && userColCount > 0){
                    sum -= 100 * botColCount;
                }
            }



        }

        return sum;
    }
}

