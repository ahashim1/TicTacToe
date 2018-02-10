public class NineBoardAdversarialSearch {
    Mark user;
    Mark bot;
    NineBoard nineBoard;
    private int maxDepth = 5;

    public NineBoardAdversarialSearch(Mark user, Mark bot, NineBoard nineBoard){
        this.user = user;
        this.bot = bot;
        this.nineBoard = nineBoard;
    }

    public Move getAIMove(){
        int maxScore = -Integer.MAX_VALUE;
        Move move = null;

        for (Move test: nineBoard.getPossibleMoves()) {
            nineBoard.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(nineBoard, -Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
            nineBoard.undoMove(test.getBoard(), test.getPosition());
            if (score > maxScore) {
                move = test;
                maxScore = score;
            }
        }


        return move;
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
        for (Move test: nineBoard.getPossibleMoves()) {

            nineBoard.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(nineBoard, alpha, beta, false, depth++);

            nineBoard.undoMove(test.getBoard(), test.getPosition());

            if (score > alpha){
                alpha = score;
            }

            if (alpha >= beta){
                return alpha;
            }

        }

        return alpha;
    }

    private int getMinWithAlphaBeta(NineBoard nineBoard, int alpha, int beta, int depth){
        for (Move test: nineBoard.getPossibleMoves()) {

            nineBoard.move(test.getBoard(), test.getPosition(), user);

            int score = minimaxWithAlphaBeta(nineBoard, alpha, beta, true, depth++);

            nineBoard.undoMove(test.getBoard(), test.getPosition());

            if (score < beta){
                beta = score;
            }

            if (alpha >= beta){
                return beta;
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

