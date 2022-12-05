import java.util.Scanner;

public class HumanPlayer implements Player{
    @Override
    public void makeMove(GameBoard board, Cell color) {
        Scanner in = new Scanner(System.in);
        System.out.print("Введите координаты куда вы хотите поставить фишку: ");
        String inText = in.nextLine();
        if (Constants.STOP_WORD.equals(inText)) {
            throw new StopGameException();
        }
        int i = Integer.parseInt(inText.split(" ")[0]);
        int j = Integer.parseInt(inText.split(" ")[1]);
        board.add(i, j, color);
    }
}
