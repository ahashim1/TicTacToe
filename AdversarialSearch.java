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
        // Initializes values
        int maxScore = Integer.MIN_VALUE;
        Move move = null;

        // For testing runtime
        long start = System.currentTimeMillis();

        // Goes through possible moves
        for (Move test: board.getPossibleMoves()){
            // Copies the board to test moves
            Board copy = board.deepClone();
            // Moves to the position
            copy.move( test.getBoard(), test.getPosition(), bot);
            // evaluates the move
            int score = minimaxWithAlphaBeta(copy, Integer.MIN_VALUE, Integer.MAX_VALUE, false);

            if (score > maxScore){
                move = test;
                maxScore = score;
            }


        }

        // Stats
        long elapsed = System.currentTimeMillis() - start;
        System.err.println("Minimax Alpha Beta Stats");
        System.err.println("Elapsed Time (ms): " + elapsed);
        System.err.println("Nodes Visited: " + count);
        return move;
    }


    private int minimaxWithAlphaBeta(Board board, int alpha, int beta, boolean isMax){
        count++;

        // Test terminal State
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

        // If the score of the move is better than the previous best for max or the alpha, replace the alpha. 
        // If alpha >= beta, then you can prune
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

    private int getMinWithAlphaBeta(Board board, int alpha, int beta){

        // If the score of the move is better than the previous best for min or the beta, replace the beta. 
        // If alpha >= beta, then you can prune
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


    // Evaluates terminal state
    private int score(Board board){
        if (board.getWinner() == bot){
            return 1;
        }else if (board.getWinner() == user){
            return -1;
        }else{

            return 0;

        }
    }


    // These methods are unused, but were used for the first implementations of minimax. 
    // They are similar to the methods above, but don't keep track of alpha or beta
    private  int minimax(Board board, boolean isMax){
        count++;

        if (board.isGameOver()){

            return score(board);

        }

        if (isMax){

            return getMax(board);
        }else{
            return getMin(board);
        }
    }

    private  int getMax(Board board){
        int maxScore = Integer.MIN_VALUE;

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
        int minScore = Integer.MAX_VALUE;
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

//    //     regular minimax, This is commented out because alpha beta pruning is always used
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
