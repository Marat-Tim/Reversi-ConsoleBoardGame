public enum Cell {
    BLACK, RED, EMPTY;

    public static Cell otherColor(Cell color) {
        if (color == EMPTY) {
            throw new IllegalArgumentException("Argument color must be BLACK or RED");
        }
        return color == BLACK ? RED : BLACK;
    }
}
