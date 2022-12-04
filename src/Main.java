import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.printf("a = %s", null);
        Player first = new HumanPlayer();
        Player second = new HumanPlayer();
        Game game = new Game(first, second);
        game.start();
    }
}