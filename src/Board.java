public class Board {

    private final BoardData board;

    public Board(int size) {
        board = new BoardData(size);
    }

    public int size() {
        return board.size();
    }

    public void add(int x, int y, Color color) {
        if (color == null) {
            throw new IllegalArgumentException("Argument color must be non-null");
        }
        if (board.get(x, y) != null) {
            throw new CellOccupiedException(
                    String.format(
                            "The field cell with coordinates x=%d y=%d is already occupied", x, y));
        }
        board.set(x, y, new Chip(color));
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
