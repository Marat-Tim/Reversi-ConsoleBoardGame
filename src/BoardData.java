import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BoardData {
    private final List<List<Chip>> board;

    public BoardData(int size) {
        board = new ArrayList<>(size);
        for (int x = 0; x < size; ++x) {
            board.add(new ArrayList<>(size));
            for (int y = 0; y < size; ++y) {
                board.get(x).add(null);
            }
        }
    }

    int size() {
        return board.size();
    }

    public Chip get(int x, int y) {
        return board.get(x).get(y);
    }

    public void set(int x, int y, Chip chip) {
        board.get(x).set(y, chip);
    }

    public List<Chip> slice(int x1, int y1, int x2, int y2) {
        List<Chip> slice = new ArrayList<>();
        int xMin, xMax, yMin, yMax;
        xMin = Math.min(x1, x2);
        xMax = Math.max(x1, x2);
        yMin = Math.min(y1, y2);
        yMax = Math.max(y1, y2);
        if (x1 == x2) {
            for (int y = yMin; y <= yMax; ++y) {
                slice.add(get(x1, y));
            }
            return slice;
        }
        if (y1 == y2) {
            for (int x = xMin; x <= xMax; ++x) {
                slice.add(get(x, y1));
            }
            return slice;
        }
        if (Math.abs(y1 - y2) == Math.abs(x1 - x2)) {
            for (int x = xMin, y = yMin; x <= xMax && y <= yMax; ++x, ++y) {
                slice.add(get(x, y));
            }
            return slice;
        }
        throw new IllegalArgumentException(
                "It is not possible to take a slice according to these coordinates");
    }
}
