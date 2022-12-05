public enum Cell {
    BLACK, WHITE, EMPTY;

    public static Cell otherColor(Cell color) {
        if (color == EMPTY) {
            throw new IllegalArgumentException("Argument color must be BLACK or RED");
        }
        return color == BLACK ? WHITE : BLACK;
    }

    @Override
    public String toString() {
        if (this == WHITE) {
            return Constants.WHITE_CELL;
        }
        if (this == BLACK) {
            return Constants.BLACK_CELL;
        }
        return Constants.EMPTY_CELL;
    }
}
