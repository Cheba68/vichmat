import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class InputHandler {

    public static InputData readFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {

            // фикс локали (чтобы работало 0.01)
            scanner.useLocale(Locale.US);

            System.out.println("Файл: " + new File(filename).getAbsolutePath());

            if (!scanner.hasNextInt()) {
                throw new RuntimeException("Ошибка чтения funcChoice");
            }
            int funcChoice = scanner.nextInt();

            if (!scanner.hasNextInt()) {
                throw new RuntimeException("Ошибка чтения methodChoice");
            }
            int methodChoice = scanner.nextInt();

            if (!scanner.hasNextDouble()) {
                throw new RuntimeException("Ошибка чтения a");
            }
            double a = scanner.nextDouble();

            if (!scanner.hasNextDouble()) {
                throw new RuntimeException("Ошибка чтения b");
            }
            double b = scanner.nextDouble();

            if (!scanner.hasNextDouble()) {
                throw new RuntimeException("Ошибка чтения eps");
            }
            double eps = scanner.nextDouble();

            System.out.println("Считано: a=" + a + " b=" + b + " eps=" + eps);

            return new InputData(funcChoice, methodChoice, a, b, eps);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения файла: " + e.getMessage(), e);
        }
    }
}