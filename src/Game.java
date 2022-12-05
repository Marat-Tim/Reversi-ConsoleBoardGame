import java.util.InputMismatchException;
import java.util.Stack;

public class Game {
    private final Player first, second;

    private final GameBoard board;

    public Game(Player first, Player second) {
        this.first = first;
        this.second = second;
        board = new GameBoard(Constants.BOARD_SIZE);
    }

    public void start() {
        Player player = first;
        Cell colorNow = Cell.BLACK;
        do {
            printTurnInfo(player, colorNow);
            while (true) {
                try {
                    player.makeMove(board, colorNow);
                    break;
                } catch (GameBoardException ex) {
                    System.out.println(
                            "Установить фишку вашего цвета по этими координатами не удалось");
                } catch (InputMismatchException ex) {
                    System.out.printf(
                            "Неправильный формат ввода, нужно ввести 2 числа от 0 до %d\n",
                            board.size() - 1);
                } catch (StopGameException ex) {
                    System.out.println("Завершение игры");
                    return;
                }
            }
            colorNow = Cell.otherColor(colorNow);
            player = player == first ? second : first;
        } while (!board.isEndOfGame(colorNow));
        printEndOfGameInfo();
    }

    public int humanBestScore() {
        int scoreOfFirst = -1;
        if (first.getClass() == HumanPlayer.class) {
            scoreOfFirst = board.getChipCount(Cell.BLACK);
        }
        int scoreOfSecond = -1;
        if (second.getClass() == HumanPlayer.class) {
            scoreOfSecond = board.getChipCount(Cell.WHITE);
        }
        return Math.max(scoreOfFirst, scoreOfSecond);
    }

    private void printEndOfGameInfo() {
        Cell winner = board.whatColorWin();
        System.out.print(board);
        System.out.printf(
                "Игра окончена. Победил - %s\n",
                winner == null ? "никто, у вас ничья" : winner);
    }

    private void printTurnInfo(Player player, Cell colorNow) {
        if (!(player instanceof ComputerPlayer)) {
            System.out.printf("Ходит: %s\n", colorNow);
            System.out.print(board.toString(colorNow));
        }
    }
}
