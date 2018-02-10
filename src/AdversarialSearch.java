public class AdversarialSearch {
    Mark user;
    Mark bot;
    Board board;
    public AdversarialSearch(Mark user, Mark bot, Board board){
        this.user = user;
        this.bot = bot;
        this.board = board;
    }
    public Move getAIMove(){
        int maxScore = -Integer.MAX_VALUE;
        Move move = null;
        long start = System.currentTimeMillis();
        for (Move test: board.getPossibleMoves()){
            board.move( test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(board, -Integer.MAX_VALUE, Integer.MAX_VALUE, false);
            board.undoMove(test.getBoard(), test.getPosition());
            if (score > maxScore){
                move = test;
                maxScore = score;
            }


        }
        long elapsed = System.currentTimeMillis() - start;
        System.out.println(elapsed);
        return move;
    }


    private int minimaxWithAlphaBeta(Board board, int alpha, int beta, boolean isMax){
        if (board.isGameOver()){

            return score(board);

        }
        if (isMax){
            return getMaxWithAlphaBeta(board, alpha, beta);
        }else{
            return getMinWithAlphaBeta(board, alpha, beta);
        }
    }

    private int getMaxWithAlphaBeta(Board board, int alpha, int beta){
        Move bestMove = null;
        for (Move i: board.getPossibleMoves()){
            Board copy = board.deepClone();
            copy.move(i.getBoard(), i.getPosition(), bot);
            int score = minimaxWithAlphaBeta(copy, alpha, beta, false);
//            board.undoMove(i.getBoard(), i.getPosition());

            if (score >= alpha){
                bestMove = i;
                alpha = score;
            }
            if (alpha >= beta){
                return alpha;
            }

        }
        return alpha;
    }

    public int getMinWithAlphaBeta(Board board, int alpha, int beta){
        Move bestMove = null;

        for (Move i: board.getPossibleMoves()){

            Board copy = board.deepClone();
            copy.move(i.getBoard(), i.getPosition(), user);
            int score = minimaxWithAlphaBeta(copy, alpha, beta, true);
//            board.undoMove(i.getBoard(), i.getPosition());

            if (score <= beta){
                bestMove = i;
                beta = score;
            }


                if (alpha >= beta){

                    return beta;
                }



        }
        return beta;
    }


    private int score(Board board){
        if (board.getWinner() == bot){
            return 1;
        }else if (board.getWinner() == user){
            return -1;
        }else{

            return 0;

        }
    }

        public  int minimax(Board board, boolean isMax){
        if (board.isGameOver()){

            return score(board);

        }

        if (isMax){
            return getMax(board);
        }else{
            return getMin(board);
        }
    }

    public  int getMax(Board board){
        int maxScore = -1000;

        for (Move i: board.getPossibleMoves()){


            board.move(i.getBoard(), i.getPosition(), bot);
                int score = minimax(board, false);



                if (score > maxScore){
                    maxScore = score;
                }
                board.undoMove(i.getBoard(), i.getPosition());


            }

        return maxScore;
    }

    public  int getMin(Board board){
        int minScore = 1000;
        for (Move i: board.getPossibleMoves()){


            board.move(i.getBoard(), i.getPosition(), user);
                int score = minimax(board, true);

                if (score < minScore){
                    minScore = score;
                }
                board.undoMove(i.getBoard(), i.getPosition());


        }

        return minScore;
    }

    //     regular minimax

//    public void getAIMove(){
//        int maxScore = -1000;
//        int move = -1;
//        long start = System.currentTimeMillis();
//
//        for (int i = 1; i<=9; i++){
//            if (!board.isTileTaken(i)){
//                board.move(i, bot);
//                int score = minimax(board, false);
//                board.undoMove(i);
//                if (score > maxScore){
//                    move = i;
//                    maxScore = score;
//                }
//            }
//
//        }
//        long elapsed = System.currentTimeMillis() - start;
//        System.out.println(elapsed);
//
//        board.move(move, bot);
//
//
//    }

}
