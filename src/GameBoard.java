import java.util.Optional;

public class GameBoard {

    private final BoardData board;

    public GameBoard(int size) {
        board = new BoardData(size);
    }

    public int size() {
        return board.size();
    }

    private void checkThatAddIsCorrect(int x, int y, Cell color) {
        if (color == null) {
            throw new IllegalArgumentException("Argument color must be non-null");
        }
        if (board.get(x, y) != Cell.EMPTY) {
            throw new CellOccupiedException(
                    String.format(
                            "The field cell with coordinates x=%d y=%d is already occupied", x, y));
        }
//        Cell otherColor = color == Cell.BLACK ? Cell.RED : Cell.BLACK;
//        if (Optional.of(board.get(x + 1, y)).orElse(null).getColor() != otherColor &&
//                Optional.of(board.get(x - 1, y)).orElse(null).getColor() != otherColor &&
//                Optional.of(board.get(x, y + 1)).orElse(null).getColor() != otherColor &&
//                Optional.of(board.get(x, y - 1)).orElse(null).getColor() != otherColor) {
//            throw new NoEnemyChipNearException("There should be an enemy chip next to the cell");
//        }

    }

    public void add(int x, int y, Cell color) {
        checkThatAddIsCorrect(x, y, color);
        board.set(x, y, color);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size() * (size() + 1));
        for (int x = 0; x < size(); ++x) {
            for (int y = 0; y < size(); ++y) {
                if (board.get(x, y) != null) {
                    sb.append(board.get(x, y).toString());
                } else {
                    sb.append("█");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
