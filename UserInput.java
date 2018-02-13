import java.util.Scanner;
public class UserInput {
    private Board board;
    private Scanner input = new Scanner(System.in);
    static Mark user;
    static Mark bot;
    public UserInput(){
        board = new Board();
    }

    public void start(){
        setupUser();
        while (true){
            if (user == board.getTurn()){
                getPlayerMove();
            }else{
                getAIMove();
            }

            board.printBoard();



            if (board.isGameOver()){
                break;
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
        System.err.println("Please enter the tile you wish to place your move via 1-9");
        int value = input.nextInt();
        if (!(value >= 1 && value <= 9)){
            System.err.println("Please enter a tile from 1-9");
            getPlayerMove();
        }else if (!board.move(0, value, user)){
            System.err.println("That tile is taken!");
            getPlayerMove();
        }



    }




    public void getAIMove(){
        AdversarialSearch search = new AdversarialSearch(user, bot, board);
        Move move = search.getAIMove();
        System.out.println(move.getPosition());
        board.move(move.getBoard(), move.getPosition(), bot);

    }



}
