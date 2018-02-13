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
            System.err.println("Not a valid input.");
        }
    }




}
