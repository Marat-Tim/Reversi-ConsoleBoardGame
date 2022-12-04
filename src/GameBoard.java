/**
 * Хранит игровое поле + предоставляет методы для работы с полем, которые учитывают правила игры.
 */
public class GameBoard {

    /**
     * Хранит данные о самом поле.
     */
    private final BoardData board;

    /**
     * Создает поле размера size * size и устанавливает в середине поля начальные фишки.
     *
     * @param size Размер поля.
     */
    public GameBoard(int size) {
        board = new BoardData(size);
        board.add(size / 2 - 1, size / 2 - 1, Cell.WHITE);
        board.add(size / 2, size / 2, Cell.WHITE);
        board.add(size / 2 - 1, size / 2, Cell.BLACK);
        board.add(size / 2, size / 2 - 1, Cell.BLACK);
    }

    /**
     * Возвращает размер поля.
     *
     * @return Размер поля.
     */
    public int size() {
        return board.size();
    }

    /**
     * Проверяет, можно ли поставить фишку такого цвета на какую-нибудь клетку. Если нет, то игра
     * закончена.
     *
     * @param color Цвет фишки.
     * @return Истина, если игра закончена, иначе - ложь.
     */
    public boolean isEndOfGame(Cell color) {
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                if (canAdd(i, j, color)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Возвращает ячейку на данной позиции.
     *
     * @param i Номер строки.
     * @param j Номер столбца.
     * @return Ячейка на данной позиции.
     * @throws IndexOutOfBoundsException Если ячейки с такой позицией не существует.
     */
    public Cell get(int i, int j) {
        return board.get(i, j);
    }

    /**
     * Добавляет по данным координатам фишку данного цвета.
     *
     * @param i     Номер строки.
     * @param j     Номер столбца.
     * @param color Цвет фишки.
     * @throws GameBoardException Если добавить фишку нельзя.
     */
    public void add(int i, int j, Cell color) {
        if (canAdd(i, j, color)) {
            board.add(i, j, color);
            makeClosure(i, j, color);
            return;
        }
        throw new GameBoardException(
                "It was not possible to add a chip of this color according to these coordinates");
    }

    /**
     * Проверят можно ли добавить фишку по таким координатам, учитывая все правила игры.
     *
     * @param i     Номер строки.
     * @param j     Номер столбца.
     * @param color Цвет фишки.
     * @return Истина, если можно, иначе - ложь.
     */
    private boolean canAdd(int i, int j, Cell color) {
        return board.isEmptyCell(i, j) &&
                areOpponentChipsNearby(i, j, color) &&
                canMakeClosure(i, j, color);
    }

    /**
     * Проверяет есть ли на соседних координатах фишка другого цвета.
     *
     * @param i     Номер строки.
     * @param j     Номер столбца.
     * @param color Цвет фишки.
     * @return Истина, если хотя бы одна фишка есть, иначе - ложь.
     */
    private boolean areOpponentChipsNearby(int i, int j, Cell color) {
        Cell otherColor = Cell.otherColor(color);
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if (!(k == 0 && j == 0) && board.safeGet(i + k, j + l) == otherColor) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Проверяет можно ли при добавлении фишки такого цвета по таким координатам замкнуть хотя бы
     * одну фишку противника.
     *
     * @param i     Номер строки.
     * @param j     Номер столбца.
     * @param color Цвет фишки.
     * @return Истина, если замкнуть фишки противника можно, иначе - ложь.
     */
    private boolean canMakeClosure(int i, int j, Cell color) {
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if (!(k == 0 && l == 0) &&
                        findChipThatCanMakeClosureOnLine(i + k, j + l, k, l, color) != null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Ищет все возможные замыкания, которые появляются при добавлении фишки такого цвета по таким
     * координатам и перекрашивает их.
     *
     * @param i     Номер строки.
     * @param j     Номер столбца.
     * @param color Цвет фишки.
     */
    private void makeClosure(int i, int j, Cell color) {
        for (int k = -1; k <= 1; ++k) {
            for (int l = -1; l <= 1; ++l) {
                if (k != 0 || l != 0) {
                    var point =
                            findChipThatCanMakeClosureOnLine(i + k, j + l, k, l, color);
                    if (point != null) {
                        board.changeColorForSlice(i + k, j + l, point.i(), point.j());
                    }
                }
            }
        }
    }

    /**
     * Ищет фишку, которая может сделать замыкание точки (i, j) до первой точки с фишкой другого
     * цвета на координатах (i + n * dI, j + n *dJ).
     *
     * @param i     Номер строки начальной точки.
     * @param j     Номер столбца начальной точки.
     * @param dI    В какую сторону идти(по строкам).
     * @param dJ    В какую сторону идти(по столбцам).
     * @param color Какой цвет поставили только что.
     * @return Координаты фишки, если она нашлась, иначе null.
     */
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

    /**
     * Возвращает текстовое представление поля, которое можно показать пользователю. Данный метод не
     * показывает подсказки.
     *
     * @return Красивое текстовое представление поля.
     */
    @Override
    public String toString() {
        //TODO посчитай размер
        //TODO полностью переделай
        StringBuilder sb = new StringBuilder();
        sb.append("  0 1 2 3  4 5 6 7\n");
        for (int i = 0; i < size(); ++i) {
            sb.append(i);
            for (int j = 0; j < size(); ++j) {
                sb.append(board.safeGet(i, j).toString());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Возвращает текстовое представление поля, которое можно показать пользователю. Также отображает
     * подсказки, то есть места куда можно поставить фишку данного цвета.
     *
     * @param color Для какого цвета нужно отображать подсказки.
     * @return Красивое текстовое представление поля.
     */
    public String toString(Cell color) {
        //TODO посчитай размер
        //TODO полностью переделай
        StringBuilder sb = new StringBuilder();
        sb.append("  0 1 2 3  4 5 6 7\n");
        for (int i = 0; i < size(); ++i) {
            sb.append(i);
            for (int j = 0; j < size(); ++j) {
                if (board.safeGet(i, j) == Cell.EMPTY && canAdd(i, j, color)) {
                    sb.append("\uD83D\uDFE2");
                } else {
                    sb.append(board.safeGet(i, j).toString());
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
