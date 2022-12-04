import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Player first = new HumanPlayer();
        Player second = new HumanPlayer();
        Game game = new Game(first, second);
        game.start();
    }
}