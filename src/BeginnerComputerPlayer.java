import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class BeginnerComputerPlayer implements ComputerPlayer {
    private GameBoard board;

    @Override
    public void makeMove(GameBoard board, Cell color) {
        this.board = board;
        Dictionary<Point, Double> winnings = new Hashtable<>();
        for (int i = 0; i < board.size(); ++i) {
            for (int j = 0; j < board.size(); ++j) {
                if (board.canAdd(i, j, color)) {
                    winnings.put(new Point(i, j), countWinning(i, j, color));
                }
            }
        }
    }

    private Double countWinning(int i, int j, Cell color) {

    }

    private Point findChipThatCanMakeClosureOnLine(int i, int j, int dI, int dJ, Cell color) {
        if (board.safeGet(i, j) == color) {
            return null;
        }
        int i1 = i;
        int j1 = j;
        Cell otherColor = Cell.otherColor(color);
        while (board.safeGet(i1, j1) == otherColor) {
            i1 += dI;
            j1 += dJ;
        }
        if (board.safeGet(i1, j1) != Cell.EMPTY) {
            return new Point(i1 - dI, j1 - dJ);
        }
        return null;
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
