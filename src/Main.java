import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Chip chip;
        Scanner in = new Scanner(System.in);
        if (in.nextInt() == 0) {
            chip = null;
        } else {
            chip = new Chip(Color.RED);
        }
        System.out.println(Optional.ofNullable(chip).orElse(null).getColor() == Color.BLACK);
    }
}