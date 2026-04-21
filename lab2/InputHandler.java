import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class InputHandler {

    public static InputData readFromFile(String filename) throws FileNotFoundException {

        Scanner scanner = new Scanner(new File(filename));

        int funcChoice = scanner.nextInt();
        int methodChoice = scanner.nextInt();
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        double eps = scanner.nextDouble();

        scanner.close();

        return new InputData(funcChoice, methodChoice, a, b, eps);
    }
}