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
            return "\uD83D\uDD34";
        }
        if (this == BLACK) {
            return "⚫";
        }
        return "⚪";
    }
}
