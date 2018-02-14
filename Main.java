// Author: Ali Hashim

// Main driver to run tic tac toe. See README.txt for instructions on running.
public class Main {

    public static void main(String[] args) {
        if (args.length == 0){
            UserInput game = new UserInput();
            game.start();
        }else if (args[0].equals("NineBoardTTT")){
            NineBoardUserInput game = new NineBoardUserInput();
            game.start();
        }else if (args[0].equals("UltimateTTT")){
            UltimateTTTUserInput game = new UltimateTTTUserInput();
            game.start();
        }else{
            System.err.println("Not a valid command line argument.");
        }
    }




}
