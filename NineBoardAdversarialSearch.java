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
            NineBoard copy = nineBoard.deepClone();
            copy.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(copy, -Integer.MAX_VALUE, Integer.MAX_VALUE, false, 0);
            if (score > maxScore) {
                move = test;
                maxScore = score;
            }
        }


        return move;
    }

    private int minimaxWithAlphaBeta(NineBoard nineBoard, int alpha, int beta, boolean isMax, int depth){

        if (nineBoard.isGameOver() || depth++ == maxDepth){
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
            NineBoard copy = nineBoard.deepClone();

            copy.move(test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(copy, alpha, beta, false, depth);


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
            NineBoard copy = nineBoard.deepClone();

            copy.move(test.getBoard(), test.getPosition(), user);

            int score = minimaxWithAlphaBeta(copy, alpha, beta, true, depth);


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


        for (int boardInput = 1; boardInput<=9; boardInput++) {
            if (nineBoard.isBoardAvailable(boardInput)) {

                Board board = nineBoard.getBoardWith(boardInput);
                sum += evaluateSingleBoard(board);

                int numberFreePlays = nineBoard.numberFreePlays(boardInput);
                if (nineBoard.getTurn() == user){
                    sum += 2 * numberFreePlays;
                }else{
                    sum -= 2 * numberFreePlays;
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

