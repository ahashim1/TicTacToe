public class UltimateTTTAdversarialSearch {
    Mark user;
    Mark bot;
    UltimateTTT ultimateTTT;
    private int maxDepth = 4;

    public UltimateTTTAdversarialSearch(Mark user, Mark bot, UltimateTTT ultimateTTT){
        this.user = user;
        this.bot = bot;
        this.ultimateTTT = ultimateTTT;
    }

    public Move getAIMove(){
        int maxScore = -Integer.MAX_VALUE;
        Move move = null;

        System.out.println(ultimateTTT.getPossibleMoves().size());
        for (Move test: ultimateTTT.getPossibleMoves()) {
            UltimateTTT copy = ultimateTTT.deepClone();
            copy.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(copy, -Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
//            ultimateTTT.undoMove(test.getBoard(), test.getPosition());
            if (score > maxScore) {
                move = test;
                maxScore = score;
            }
        }


        return move;
    }

    private int minimaxWithAlphaBeta(UltimateTTT ultimateTTT, int alpha, int beta, boolean isMax, int depth){

        if (ultimateTTT.isGameOver() || depth++ == maxDepth){
            return score(ultimateTTT);


        }
        if (isMax){
            return getMaxWithAlphaBeta(ultimateTTT, alpha, beta, depth);
        }else{
            return getMinWithAlphaBeta(ultimateTTT, alpha, beta, depth);
        }
    }

    private int getMaxWithAlphaBeta(UltimateTTT ultimateTTT, int alpha, int beta, int depth){
        for (Move test: ultimateTTT.getPossibleMoves()) {

            UltimateTTT copy = ultimateTTT.deepClone();
            copy.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(ultimateTTT, alpha, beta, false, depth);

//            ultimateTTT.undoMove(test.getBoard(), test.getPosition());

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

        for (Move test: ultimateTTT.getPossibleMoves()) {

            UltimateTTT copy = ultimateTTT.deepClone();
            copy.move(test.getBoard(), test.getPosition(), user);

            int score = minimaxWithAlphaBeta(ultimateTTT, alpha, beta, true, depth);

//            ultimateTTT.undoMove(test.getBoard(), test.getPosition());

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

        sum += 10 * evaluateSingleBoard(ultimateTTT.getGlobalBoard());

        for (int boardInput = 1; boardInput<=9; boardInput++) {
            if (ultimateTTT.isBoardAvailable(boardInput)) {

                Board board = ultimateTTT.getBoardWith(boardInput);
                sum += evaluateSingleBoard(board);

                int numberFreePlays = ultimateTTT.numberFreePlays(boardInput);
                if (ultimateTTT.getTurn() == user){
                    sum += 10 * numberFreePlays;
                }else{
                    sum -= 10 * numberFreePlays;
                }
            }
        }
        return sum;

    }

    private int evaluateSingleBoard(Board board){
        int score = 0;
        score += evaluateRows(board);
        score += evaluateCols(board);
        score += evaluateDiags(board);

        return score;
    }

    private int evaluateRows(Board board){
        int score = 0;

        for (int row = 0; row < 3; row++){
            int botCount = 0;
            int userCount = 0;

            for (int col = 0; col < 3; col++){
                if (board.getTileValueWith(row, col) == bot){
                    botCount++;
                }else if (board.getTileValueWith(row, col) == user){
                    userCount++;
                }


            }

            if (botCount > 0 && userCount == 0) {
                score += 2 ^ botCount;
            } else if (botCount == 0 && userCount > 0) {
                score += 2 ^ userCount;
            }
        }
        return score;
    }

    private int evaluateCols(Board board){
        int score = 0;
        int botCount = 0;
        int userCount = 0;
        for (int col = 0; col < 3; col++){

            for (int row = 0; row < 3; row++){
                if (board.getTileValueWith(row, col) == bot){
                    botCount++;
                }else if (board.getTileValueWith(row, col) == user){
                    userCount++;
                }
            }
        }

        if (botCount > 0 && userCount == 0){
            score += 2 ^ botCount;
        }else if (botCount == 0 && userCount > 0){
            score += 2 ^ userCount;
        }

        return score;
    }

    private int evaluateDiags(Board board){
        int score = 0;

        int botDiagCount = 0;
        int userDiagCount = 0;
        int botAntiDiagCount = 0;
        int userAntiDiagCount = 0;
        for (int row = 0; row < 3; row++){

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
                    score += 2 ^ botDiagCount;
                }else if (botDiagCount==0 && userDiagCount > 0){
                    score -= 2 ^ userDiagCount;
                }

                if (botAntiDiagCount>0 && userAntiDiagCount==0){
                    score += 2 ^ botAntiDiagCount;
                }else if (botAntiDiagCount==0 && userAntiDiagCount > 0){
                    score -= 2 ^ userAntiDiagCount;
                }
            }
        }



        return score;
    }
}
