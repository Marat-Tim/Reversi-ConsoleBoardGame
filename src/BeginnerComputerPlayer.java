import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class BeginnerComputerPlayer implements ComputerPlayer {
    private GameBoard board;

    @Override
    public void makeMove(GameBoard board, Cell color) {
        this.board = board;
        double maxWinning = -1;
        int iWithMaxWinning = -1, jWithMaxWinning = -1;
        for (int i = 0; i < board.size(); ++i) {
            for (int j = 0; j < board.size(); ++j) {
                if (board.canAdd(i, j, color)) {
                    double winning = countWinning(i, j, color);
                    if (winning > maxWinning) {
                        iWithMaxWinning = i;
                        jWithMaxWinning = j;
                        maxWinning = winning;
                    }
                }
            }
        }
        board.add(iWithMaxWinning, jWithMaxWinning, color);
    }

    private double countWinning(int i, int j, Cell color) {
        double winning = 0;
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if (k != 0 || l != 0) {
                    var point =
                            board.findChipThatCanMakeClosureOnLine(i + k, j + l, k, l, color);
                    if (point != null) {
                        winning += countWinningForSlice(i + k, j + l, point.i(), point.j());
                    }
                }
            }
        }
        winning += getWeight(i, j);
        return winning;
    }

    /**
     * Считает выигрыш для среза между данными точками.
     *
     * @param i1 Номер строки первой фишки.
     * @param j1 Номер столбца первой фишки.
     * @param i2 Номер строки второй фишки.
     * @param j2 Номер столбца второй фишки.
     * @return Выигрыш для данного среза.
     * @throws IllegalArgumentException Если нельзя взять срез по таким координатам.
     */
    public double countWinningForSlice(int i1, int j1, int i2, int j2) {
        double winning = 0;
        int iMin = Math.min(i1, i2);
        int iMax = Math.max(i1, i2);
        int jMin = Math.min(j1, j2);
        int jMax = Math.max(j1, j2);
        if (i1 == i2) {
            for (int j = jMin; j <= jMax; ++j) {
                winning += getWeightInClosure(i1, j);
            }
            return winning;
        }
        if (j1 == j2) {
            for (int i = iMin; i <= iMax; ++i) {
                winning += getWeightInClosure(i, j1);
            }
            return winning;
        }
        if (Math.abs(j1 - j2) == Math.abs(i1 - i2)) {
            for (int i = iMin, j = jMin; i <= iMax && j <= jMax; ++i, ++j) {
                winning += getWeightInClosure(i, j);
            }
            return winning;
        }
        throw new IllegalArgumentException(
                "It is not possible to take a slice according to these coordinates");
    }

    private double getWeightInClosure(int i, int j) {
        if (board.isOnEdge(i, j)) {
            return 2;
        }
        return 1;
    }

    private double getWeight(int i, int j) {
        if (board.isInTheCorner(i, j)) {
            return 0.8;
        }
        if (board.isOnEdge(i, j)) {
            return 0.4;
        }
        return 0;
    }
}
