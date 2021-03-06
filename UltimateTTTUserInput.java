import java.util.Scanner;

// Author: Ali Hashim
// Quite similar to NineBoardUserInput. Could probably use extends.
public class UltimateTTTUserInput {
    private UltimateTTT ultimateTTT;
    private Scanner input = new Scanner(System.in);
    static Mark user;
    static Mark bot;

    public UltimateTTTUserInput(){
        ultimateTTT = new UltimateTTT();
    }


    public void start(){
        setupUser();
        while (true){
            if (user == ultimateTTT.getTurn()){
                getPlayerMove();
            }else{
                getAIMove();
            }

            ultimateTTT.printBoard();


            if (ultimateTTT.isGameOver()){
                if (ultimateTTT.getWinner() == Mark.BLANK){
                    System.out.println("Draw");
                    ultimateTTT = new UltimateTTT();
                    start();
                }



                System.out.println(ultimateTTT.getWinner() + " Won!");
                ultimateTTT = new UltimateTTT();
                start();
            }
        }
    }


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
    public void getPlayerMove(){
        int boardNumber = ultimateTTT.getActiveBoard();

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


        if (!ultimateTTT.move(boardNumber, positionNumber, user)){
            System.err.println("That tile is taken!");
            getPlayerMove();
        }





    }

    public void getAIMove(){

        UltimateTTTAdversarialSearch search = new UltimateTTTAdversarialSearch(user, bot, ultimateTTT);

        Move move = search.getAIMove();
        System.out.println(move.getBoard() + " " + move.getPosition());
        ultimateTTT.move(move.getBoard(), move.getPosition(), bot);


    }

}
