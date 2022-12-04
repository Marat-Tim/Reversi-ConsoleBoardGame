import java.util.ArrayList;
import java.util.List;

/**
 * Инкапсулированный двумерный массив для хранения игрового поля. Позволяет только добавлять фишки на
 * поле или перекрашивать их, но не убирать. Правила игры при добавлении не учитываются. Оси координат
 * идут из левого верхнего угла вниз и вправо, (ВАЖНО!!!)при этом вниз идет ось I, а вправо ось J.
 */
public class BoardData {
    /**
     * Двумерный массив в котором хранится поле игры.
     */
    private final List<List<Cell>> board;

    /**
     * Создает поле размера size * size.
     *
     * @param size Размер поля.
     */
    public BoardData(int size) {
        board = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            board.add(new ArrayList<>(size));
            for (int j = 0; j < size; ++j) {
                board.get(i).add(Cell.EMPTY);
            }
        }
    }

    /**
     * Возвращает размер поля(размеры по вертикали и горизонтали одинаковые).
     *
     * @return Размер поля.
     */
    int size() {
        return board.size();
    }

    /**
     * Возвращает ячейку по данным координатам. Если координаты выходят за пределы поля, то возвращает
     * Cell.EMPTY(считается что поле бесконечное).
     *
     * @param i Номер строки.
     * @param j Номер столбца.
     * @return Ячейка по данным координатам.
     */
    public Cell safeGet(int i, int j) {
        try {
            return board.get(i).get(j);
        } catch (IndexOutOfBoundsException ex) {
            return Cell.EMPTY;
        }
    }

    /**
     * Возвращает ячейку по данным координатам. При выходе за границу поля выбрасывает исключение.
     *
     * @param i Номер строки.
     * @param j Номер столбца.
     * @return Ячейка по данным координатам.
     * @throws IndexOutOfBoundsException Если ячейки по таким координатам не существует.
     */
    public Cell get(int i, int j) {
        return board.get(i).get(j);
    }

    /**
     * Добавляет по данным координатам фишку.
     *
     * @param i     Номер строки.
     * @param j     Номер столбца.
     * @param color Цвет добавляемой фишки(BLACK или RED).
     * @throws IllegalArgumentException Если color = null или color = Cell.EMPTY.
     */
    public void set(int i, int j, Cell color) {
        if (color == null) {
            throw new IllegalArgumentException("Argument color must be non-null");
        }
        if (color == Cell.EMPTY) {
            throw new IllegalArgumentException("Argument color must be RED or BLACK, not EMPTY");
        }
        board.get(i).set(j, color);
    }

    /**
     * Меняет цвет фишки по данным координатам. Если там нет фишки, то выбрасывает исключение.
     *
     * @param i Номер строки.
     * @param j Номер столбца.
     * @throws IllegalArgumentException Если по данным координатам нет фишки.
     */
    public void changeColor(int i, int j) {
        if (safeGet(i, j) == Cell.EMPTY) {
            throw new IllegalArgumentException(String.format(
                    "The field cell with coordinates x=%d j=%d is empty", i, j));
        }
        set(i, j, Cell.otherColor(safeGet(i, j)));
    }

    /**
     * Проверят пустая ли клетка поля по данным координатам.
     *
     * @param i Номер строки.
     * @param j Номер столбца.
     * @return Истина, если клетка пустая, иначе - ложь.
     */
    public boolean isEmptyCell(int i, int j) {
        return safeGet(i, j) == Cell.EMPTY;
    }

    /**
     * Перекрашивает срез фишек находящихся либо на одной линии, либо на диагонали между двумя точками.
     *
     * @param i1 Номер строки первой фишки.
     * @param j1 Номер столбца первой фишки.
     * @param i2 Номер строки второй фишки.
     * @param j2 Номер столбца второй фишки.
     * @throws IllegalArgumentException Если нельзя взять срез по таким координатам.
     */
    public void changeColorForSlice(int i1, int j1, int i2, int j2) {
        int iMin = Math.min(i1, i2);
        int iMax = Math.max(i1, i2);
        int jMin = Math.min(j1, j2);
        int jMax = Math.max(j1, j2);
        if (i1 == i2) {
            for (int j = jMin; j <= jMax; ++j) {
                changeColor(i1, j);
            }
            return;
        }
        if (j1 == j2) {
            for (int i = iMin; i <= iMax; ++i) {
                changeColor(i, j1);
            }
            return;
        }
        if (Math.abs(j1 - j2) == Math.abs(i1 - i2)) {
            for (int i = iMin, j = jMin; i <= iMax && j <= jMax; ++i, ++j) {
                changeColor(i, j);
            }
            return;
        }
        throw new IllegalArgumentException(
                "It is not possible to take a slice according to these coordinates");
    }
}
