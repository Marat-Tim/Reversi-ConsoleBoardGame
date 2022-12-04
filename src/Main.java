import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameBoard board = new GameBoard(8);
        Scanner in = new Scanner(System.in);
        Cell now = Cell.BLACK;
        do {
            try {
                System.out.println("Сейчас ходит: " + now);
                System.out.print(board.toString(now));
                System.out.print("Введите i, j, color: ");
                int i = in.nextInt();
                int j = in.nextInt();
                board.add(i, j, now);
                now = Cell.otherColor(now);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                System.out.println(ex.getClass());
            }
        } while (true);
    }
}