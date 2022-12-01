
public class Chip {
    private Color color;

    public Chip(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void changeColor() {
        if (color == Color.BLACK) {
            color = Color.RED;
        } else {
            color = Color.BLACK;
        }
    }

    @Override
    public String toString() {
        return color == Color.BLACK ? "X" : "O";
    }
}
