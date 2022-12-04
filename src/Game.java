public class Game {
    private static final int BOARD_SIZE = 6;

    private final Player first, second;

    private final GameBoard board;

    public Game(Player first, Player second) {
        this.first = first;
        this.second = second;
        board = new GameBoard(BOARD_SIZE);
    }

    public void start() {
        Player player = first;
        Cell colorNow = Cell.BLACK;
        do {
            if (!(player instanceof ComputerPlayer)) {
                System.out.printf("Ходит: %s\n", colorNow);
                System.out.print(board.toString(colorNow));
            }
            player.makeMove(board, colorNow);
            colorNow = Cell.otherColor(colorNow);
            player = player == first ? second : first;
        } while (!board.isEndOfGame(colorNow));
        System.out.print(board);
        System.out.printf("Игра окончена. Победил - %s\n", Cell.otherColor(colorNow));
    }
}
