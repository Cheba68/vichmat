import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.commons.math3.linear.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            int choice = readChoice(sc);

            int n;
            double[][] matrix;

            if (choice == 1) {
                n = readDimension(sc);
                matrix = readMatrixFromKeyboard(sc, n);
            } else if (choice == 2) {
                matrix = readMatrixFromFile(sc);
                n = matrix.length;
            } else {
                matrix = generateRandomMatrix(sc);
                n = matrix.length;
            }

            try {
                Gauss solver = new Gauss(matrix);

                solver.forwardElimination();

                System.out.println("\nТРЕУГОЛЬНАЯ МАТРИЦА (ГАУСС)");
                solver.printUpperTriangularMatrix();

                int type = solver.checkSystemType();

                if (type == -1) {
                    System.out.println("\nСистема не имеет решений.");
                } else if (type == 0) {
                    System.out.println("\nСистема имеет бесконечно много решений.");
                } else {
                    double det = solver.determinant();
                    System.out.println("\nОпределитель (Гаусс) = " + det);

                    double[] x = solver.solve();
                    System.out.println("\nВЕКТОР НЕИЗВЕСТНЫХ");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("x%d = %.6f%n", i + 1, x[i]);
                    }

                    double[] r = solver.residuals(x);
                    System.out.println("\nВЕКТОР НЕВЯЗОК");
                    for (int i = 0; i < n; i++) {
                        System.out.printf("r%d = %.6e%n", i + 1, r[i]);
                    }

                    solveWithLibrary(matrix);
                }

            } catch (Exception e) {
                System.out.println("Ошибка вычислений: " + e.getMessage());
            }
        }
    }

    private static int readIntLine(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.\n");
            }
        }
    }

    private static int readChoice(Scanner sc) {
        while (true) {
            int choice = readIntLine(
                    sc,
                    "Выберите способ ввода:\n" +
                    "1 - с клавиатуры\n" +
                    "2 - из файла\n" +
                    "3 - случайная генерация\n> "
            );
            if (choice >= 1 && choice <= 3) return choice;
            System.out.println("Ошибка: введите 1, 2 или 3.\n");
        }
    }

    private static int readDimension(Scanner sc) {
        while (true) {
            int n = readIntLine(sc, "Введите размерность n (1 ≤ n ≤ 20): ");
            if (n >= 1 && n <= 20) return n;
            System.out.println("Ошибка: n должно быть от 1 до 20.\n");
        }
    }

    private static double[][] readMatrixFromKeyboard(Scanner sc, int n) {
        double[][] matrix = new double[n][n + 1];

        System.out.println("\nВведите матрицу построчно:");
        System.out.println("Формат: a1 a2 ... an b");

        for (int i = 0; i < n; i++) {
            while (true) {
                System.out.print("Строка " + (i + 1) + ": ");
                String[] parts = sc.nextLine().trim().split("\\s+");

                if (parts.length != n + 1) {
                    System.out.println("Ошибка: несоотвествие с размерностью матрицы");
                    continue;
                }

                try {
                    for (int j = 0; j <= n; j++) {
                        matrix[i][j] = parseDouble(parts[j]);
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка: вводите числа.");
                }
            }
        }
        return matrix;
    }

    private static double[][] readMatrixFromFile(Scanner sc) {
        while (true) {
            System.out.print("Введите имя файла: ");
            String filename = sc.nextLine().trim();

            try (Scanner file = new Scanner(new File(filename))) {

                int n = Integer.parseInt(file.nextLine().trim());
                if (n < 1 || n > 20)
                    throw new IllegalArgumentException("Некорректная размерность");

                double[][] matrix = new double[n][n + 1];

                for (int i = 0; i < n; i++) {
                    String[] parts = file.nextLine().trim().split("\\s+");
                    for (int j = 0; j <= n; j++) {
                        matrix[i][j] = parseDouble(parts[j]);
                    }
                }
                return matrix;

            } catch (FileNotFoundException e) {
                System.out.println("Ошибка: файл не найден.\n");
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage() + "\n");
            }
        }
    }

    private static double[][] generateRandomMatrix(Scanner sc) {

        int n = readDimension(sc);
        double[][] matrix = new double[n][n + 1];

        double min = -10.0;
        double max = 10.0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                matrix[i][j] = min + Math.random() * (max - min);
            }
        }

        System.out.println("\nСлучайно сгенерированная матрица:");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.printf("%10.4f ", matrix[i][j]);
            }
            System.out.println();
        }

        return matrix;
    }

    private static void solveWithLibrary(double[][] augmentedMatrix) {

        int n = augmentedMatrix.length;
        double[][] A = new double[n][n];
        double[] b = new double[n];

        for (int i = 0; i < n; i++) {
            System.arraycopy(augmentedMatrix[i], 0, A[i], 0, n);
            b[i] = augmentedMatrix[i][n];
        }

        RealMatrix matrixA = new Array2DRowRealMatrix(A);
        RealVector vectorB = new ArrayRealVector(b);

        LUDecomposition lu = new LUDecomposition(matrixA);

        if (!lu.getSolver().isNonSingular()) {
            System.out.println("\n=== БИБЛИОТЕКА ===");
            System.out.println("Система вырождена.");
            return;
        }

        RealVector solution = lu.getSolver().solve(vectorB);

        System.out.println("\nРЕШЕНИЕ С ПОМОЩЬЮ БИБЛИОТЕКИ");
        for (int i = 0; i < n; i++) {
            System.out.printf("x%d = %.6f%n", i + 1, solution.getEntry(i));
        }

        System.out.println("\nОпределитель (библиотека) = " + lu.getDeterminant());
    }

    private static double parseDouble(String s) {
        return Double.parseDouble(s.replace(',', '.'));
    }
}