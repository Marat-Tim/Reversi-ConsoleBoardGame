import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {
    private static final Scanner in = new Scanner(System.in);

    private static final String GREETING = String.format("""
            Игра "РЕВЕРСИ".
                            
            Правила:
            ...
                            
            Интерфейс:
            %s - пустая клетка
            %s - клетка с черной фишкой
            %s - клетка с белой фишкой
            %s - клетка куда текущий игрок может поставить фишку
            
            Во время хода вы должны ввести 2 числа - номер строки и номер столбца куда вы хотите поставить фишку
            
            Если вы хотите досрочно завершить игру, то введите %s
            
            Меню:
            1. Начать новую игру
            2. Посмотреть статистику
            0. Выход
            """,
            Constants.EMPTY_CELL,
            Constants.BLACK_CELL,
            Constants.WHITE_CELL,
            Constants.BOARD_HINT,
            Constants.STOP_WORD);

    public static final String PLAYER_SELECTION = """
            Выберите %s игрока
            1. Реальный игрок
            2. Компьютер новичок
            3. Компьютер профи
            0. Назад к меню
            """;

    private static int maxScore = -1;

    public static void mainMenu() {
        int command;
        do {
            System.out.println(GREETING);
            command = getCommand(2);
            if (command == 1) {
                newGame();
            }
            if (command == 2) {
                statistics();
            }
        } while (command != 0);
    }

    private static void newGame() {
        Player first = selectPlayer("первого");
        Player second = selectPlayer("второго");
        if (first == null || second == null) {
            return;
        }
        Game game = new Game(first, second);
        game.start();
        if (game.humanBestScore() > maxScore) {
            maxScore = game.humanBestScore();
        }
    }

    private static Player selectPlayer(String playerNumber) {
        int command;
        do {
            System.out.printf(PLAYER_SELECTION, playerNumber);
            System.out.println();
            command = getCommand(3);
            if (command == 0) {
                return null;
            }
            if (command == 1) {
                return new HumanPlayer();
            }
            if (command == 2) {
                return new BeginnerComputerPlayer();
            }
            if (command == 3) {
                System.out.println("Продвинутый бот пока не реализован\n");
            }
        } while (true);
    }

    private static void statistics() {
        if (maxScore < 0) {
            System.out.println("Вы еще не сыграли ни одной игры");
        } else {
            System.out.printf("Лучший результат игрока: %d\n\n", maxScore);
        }
    }

    private static int getCommand(int end) {
        int command;
        while (true) {
            try {
                System.out.print("> ");
                command = in.nextInt();
                if (command < 0 || command > end) {
                    continue;
                }
                System.out.println();
                return command;
            } catch (InputMismatchException ex) {
                System.out.printf("Неправильный формат ввода. Введите число от %d до %d\n", 0, end);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
