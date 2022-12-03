import java.awt.*;
import java.util.Optional;

public class GameBoard {

    private final BoardData board;

    public GameBoard(int size) {
        board = new BoardData(size);
    }

    public int size() {
        return board.size();
    }

    public void add(int i, int j, Cell color) {
        board.add(i, j, color);
        checkThatAddIsCorrect(i, j, color);
        paintOverCellsAccordingRules(i, j, color);
    }

    private void paintOverCellsAccordingRules(int i, int j, Cell color) {


    }

    /**
     * Закрашивает линию фишек от точки (i, j) до первой точки с фишкой другого цвета на координатах
     * (i + n * dI, j + n *dJ).
     * @param i Номер строки начальной точки.
     * @param j Номер столбца начальной точки.
     * @param dI В какую сторону идти(по строкам).
     * @param dJ В какую сторону идти(по столбцам).
     * @param color
     * @return
     */
    private boolean paintOverCellsOnOneLine(int i, int j, int dI, int dJ, Cell color) {
        int i1, j1;
        i1 = i;
        j1 = j;
        while (board.get(i1, j1) == color) {
            i1 += dI;
            j1 += dJ;
        }
        if (board.get(i, j) != Cell.EMPTY) {
            board.changeColorForSlice(i, j, i1, j1);
            return true;
        }
        return false;
    }

    private void checkThatAddIsCorrect(int i, int j, Cell color) {
        Cell otherColor = Cell.otherColor(color);
        if (board.get(i + 1, j) != otherColor &&
                board.get(i - 1, j) != otherColor &&
                board.get(i, j + 1) != otherColor &&
                board.get(i, j - 1) != otherColor) {
            throw new NoEnemyChipNearException(
                    "You can only add a chip to the cell next to the opponent");
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(size() * (size() + 1));
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                if (board.get(i, j) != null) {
                    sb.append(board.get(i, j).toString());
                } else {
                    sb.append("█");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
