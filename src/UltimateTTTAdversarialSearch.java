public class UltimateTTTAdversarialSearch {
    Mark user;
    Mark bot;
    UltimateTTT ultimateTTT;
    private int maxDepth = 5;

    public UltimateTTTAdversarialSearch(Mark user, Mark bot, UltimateTTT ultimateTTT){
        this.user = user;
        this.bot = bot;
        this.ultimateTTT = ultimateTTT;
    }

    public Move getAIMove(){
        int maxScore = -Integer.MAX_VALUE;
        Move move = null;

        System.out.println(ultimateTTT.getPossibleMoves().size());
        int count = 0;
        for (Move test: ultimateTTT.getPossibleMoves()) {
            System.out.println(count);
            ultimateTTT.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(ultimateTTT, -Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
            ultimateTTT.undoMove(test.getBoard(), test.getPosition());
            if (score > maxScore) {
                move = test;
                maxScore = score;
            }
            count++;
        }


        return move;
    }

    private int minimaxWithAlphaBeta(UltimateTTT ultimateTTT, int alpha, int beta, boolean isMax, int depth){

        if (ultimateTTT.isGameOver() || depth > maxDepth){
            return score(ultimateTTT);


        }
        if (isMax){
            return getMaxWithAlphaBeta(ultimateTTT, alpha, beta, depth);
        }else{
            return getMinWithAlphaBeta(ultimateTTT, alpha, beta, depth);
        }
    }

    private int getMaxWithAlphaBeta(UltimateTTT ultimateTTT, int alpha, int beta, int depth){
        System.out.println(ultimateTTT.getPossibleMoves().size());
        for (Move test: ultimateTTT.getPossibleMoves()) {

            ultimateTTT.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(ultimateTTT, alpha, beta, false, depth++);

            ultimateTTT.undoMove(test.getBoard(), test.getPosition());

            if (score > alpha){
                alpha = score;
            }

            if (alpha >= beta){
                return alpha;
            }

        }

        return alpha;
    }

    private int getMinWithAlphaBeta(UltimateTTT ultimateTTT, int alpha, int beta, int depth){
        System.out.println(ultimateTTT.getPossibleMoves().size());

        for (Move test: ultimateTTT.getPossibleMoves()) {

            ultimateTTT.move(test.getBoard(), test.getPosition(), user);

            int score = minimaxWithAlphaBeta(ultimateTTT, alpha, beta, true, depth++);

            ultimateTTT.undoMove(test.getBoard(), test.getPosition());

            if (score < beta){
                beta = score;
            }

            if (alpha >= beta){
                return beta;
            }
        }


        return beta;
    }

    private int score(UltimateTTT ultimateTTT){
        if (ultimateTTT.isGameOver()){

            if (ultimateTTT.getWinner() == bot){
                return 100000;
            }else if (ultimateTTT.getWinner() == user){
                return -100000;
            }else{

                return 0;

            }
        }else{
            return heuristic(ultimateTTT);
        }

    }

    private int heuristic(UltimateTTT ultimateTTT){

        int sum = 0;
        for (int boardInput = 1; boardInput<=9; boardInput++){

            Board board = ultimateTTT.getBoardWith(boardInput);
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
