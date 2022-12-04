import java.util.Scanner;

public class HumanPlayer implements Player{
    @Override
    public void makeMove(GameBoard board, Cell color) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координаты куда вы хотите поставить фишку: ");
        int i = in.nextInt();
        int j = in.nextInt();
        board.add(i, j, color);
    }
}
