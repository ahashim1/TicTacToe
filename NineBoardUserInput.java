import java.util.Scanner;

// Author: Ali Hashim
// Class that gets the user's input for NineBoard. Similar to UserInput
public class NineBoardUserInput {

    private NineBoard nineBoard;
    private Scanner input = new Scanner(System.in);
    static Mark user;
    static Mark bot;

    // Initializes the nineboard.
    public NineBoardUserInput(){
        nineBoard = new NineBoard();
    }


    // Same as before
    public void start(){
        setupUser();
        while (true){
            if (user == nineBoard.getTurn()){
                getPlayerMove();
            }else{
                getAIMove();
            }

            nineBoard.printBoard();


            if (nineBoard.isGameOver()){
                if (nineBoard.getWinner() == Mark.BLANK){
                    System.out.println("Draw");
                    nineBoard = new NineBoard();
                    start();
                }



                System.out.println(nineBoard.getWinner() + " Won!");
                nineBoard = new NineBoard();
                start();
            }
        }
    }

    // Same as before

    public void setupUser(){
        System.err.println("Please enter X or O to start the game.");

        char value = input.next().trim().charAt(0);

        if (Character.toLowerCase(value) == 'x'){
            user = Mark.X;
            bot = Mark.O;
        }else if (Character.toLowerCase(value) == 'o'){
            user = Mark.O;
            bot = Mark.X;
        }else{
            System.err.println("The character that was inputted was not an X or an O.");
            setupUser();
        }
    }

    // Now has input for board when the user has a free play

    public void getPlayerMove(){
        int boardNumber = nineBoard.getActiveBoard();
        int positionNumber;
        if (boardNumber == -1){
            System.err.println("Please enter the board and tile you wish to place your move via 1-9");
            boardNumber = input.nextInt();
            if (!(boardNumber >= 1 && boardNumber <= 9)) {
                System.err.println("Please enter a tile from 1-9");
                getPlayerMove();
            }

            positionNumber = input.nextInt();
            if (!(positionNumber >= 1 && positionNumber <= 9)) {
                System.err.println("Please enter a tile from 1-9");
                getPlayerMove();
            }
        }else{
            System.err.println("Please enter the tile you wish to place your move in " + boardNumber + " via 1-9");
            positionNumber = input.nextInt();
            if (!(positionNumber >= 1 && positionNumber <= 9)) {
                System.err.println("Please enter a tile from 1-9");
                getPlayerMove();
            }
        }


        if (!nineBoard.move(boardNumber, positionNumber, user)){
            System.err.println("That tile is taken!");
            getPlayerMove();
        }





    }

    // Gets move and prints it out
    public void getAIMove(){

        NineBoardAdversarialSearch search = new NineBoardAdversarialSearch(user, bot, nineBoard);

        Move move = search.getAIMove();
        System.out.println(move.getBoard() + " " + move.getPosition());
        nineBoard.move(move.getBoard(), move.getPosition(), bot);


    }








}
