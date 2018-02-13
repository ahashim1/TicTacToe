// Author: Ali Hashim
// This class contains the regular minimax and minimaxWithAlphaBeta implementations for the basic TTT board.
public class AdversarialSearch {

    // Constructor for adversarial search keeps track of the user, bot, and board
    Mark user;
    Mark bot;
    Board board;

    public AdversarialSearch(Mark user, Mark bot, Board board){
        this.user = user;
        this.bot = bot;
        this.board = board;
    }

    // Count used for counting the number of nodes visited.
    int count = 0;


    
    // The only public method for this class, returns the AI's best move (which consists of a board and position)
    // The boardNumber from the move is ignored for regular TTT.
    
    public Move getAIMove(){
        int maxScore = -Integer.MAX_VALUE;
        Move move = null;
        long start = System.currentTimeMillis();
        for (Move test: board.getPossibleMoves()){
            Board copy = board.deepClone();
            copy.move( test.getBoard(), test.getPosition(), bot);
            int score = minimaxWithAlphaBeta(copy, -Integer.MAX_VALUE, Integer.MAX_VALUE, false);
            if (score > maxScore){
                move = test;
                maxScore = score;
            }


        }
        long elapsed = System.currentTimeMillis() - start;
        System.err.println("Minimax Alpha Beta Stats");
        System.err.println("Elapsed Time (ms): " + elapsed);
        System.err.println("Nodes Visited: " + count);
        return move;
    }


    private int minimaxWithAlphaBeta(Board board, int alpha, int beta, boolean isMax){
        count++;

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
        for (Move i: board.getPossibleMoves()){
            Board copy = board.deepClone();
            copy.move(i.getBoard(), i.getPosition(), bot);
            int score = minimaxWithAlphaBeta(copy, alpha, beta, false);

            if (score > alpha){
                alpha = score;
            }
            if (alpha >= beta){
                return alpha;
            }

        }
        return alpha;
    }

    public int getMinWithAlphaBeta(Board board, int alpha, int beta){

        for (Move i: board.getPossibleMoves()){
            Board copy = board.deepClone();
            copy.move(i.getBoard(), i.getPosition(), user);
            int score = minimaxWithAlphaBeta(copy, alpha, beta, true);
            if (score < beta){
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
        count++;

        if (isMax){

            return getMax(board);
        }else{
            return getMin(board);
        }
    }

    public  int getMax(Board board){
        int maxScore = -Integer.MAX_VALUE;

        for (Move i: board.getPossibleMoves()){
            Board copy = board.deepClone();


            copy.move(i.getBoard(), i.getPosition(), bot);
                int score = minimax(copy, false);



                if (score > maxScore){
                    maxScore = score;
                }


            }

        return maxScore;
    }

    public  int getMin(Board board){
        int minScore = 1000;
        for (Move i: board.getPossibleMoves()){

            Board copy = board.deepClone();

            copy.move(i.getBoard(), i.getPosition(), user);
                int score = minimax(copy, true);

                if (score < minScore){
                    minScore = score;
                }


        }

        return minScore;
    }

//    //     regular minimax
//
//    public Move getAIMove() {
//        int maxScore = -Integer.MAX_VALUE;
//        Move move = null;
//        long start = System.currentTimeMillis();
//        for (Move test : board.getPossibleMoves()) {
//            Board copy = board.deepClone();
//            copy.move(test.getBoard(), test.getPosition(), bot);
//            int score = minimax(copy, false);
//            if (score > maxScore) {
//                move = test;
//                maxScore = score;
//            }
//
//
//        }
//        long elapsed = System.currentTimeMillis() - start;
//        System.err.println("Minimax Stats");
//        System.err.println("Elapsed Time (ms): " + elapsed);
//        System.err.println("Nodes visited: " + count);
//        return move;
//
//
//    }

}
