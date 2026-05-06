import java.util.Scanner;

public class Main {

    public static double readDouble(Scanner scanner) {
        String input = scanner.next();
        input = input.replace(",", ".");
        return Double.parseDouble(input);
    }

    private static boolean hasRoot(Function f, double a, double b) {
        return f.f(a) * f.f(b) <= 0;
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\n==============================");
                System.out.println("1: Решить нелинейное уравнение");
                System.out.println("2: Решить систему уравнений");
                System.out.println("0: Выход");
                System.out.print("Ваш выбор: ");

                int mainChoice = scanner.nextInt();

                if (mainChoice == 0) {
                    System.out.println("Выход из программы.");
                    break;
                }

                //УРАВНЕНИЯ
                if (mainChoice == 1) {

                    System.out.println("\nВыберите способ ввода:");
                    System.out.println("1: С клавиатуры");
                    System.out.println("2: Из файла");

                    int inputChoice = scanner.nextInt();
                    InputData data;
                    double a = 0;
                    double b = 0;
                    double eps = 0;

                    if (inputChoice == 1) {

                        FunctionFactory.printFunctions();
                        int funcChoice = scanner.nextInt();

                        System.out.println("Выберите метод:");
                        System.out.println("1: Бисекция");
                        System.out.println("2: Ньютон");
                        System.out.println("3: Секущие");
                        System.out.println("4: Простая итерация");

                        int methodChoice = scanner.nextInt();

                        System.out.print("Введите a: ");
                        a = readDouble(scanner);

                        System.out.print("Введите b: ");
                        b = readDouble(scanner);

                        if (a >= b) {
                            throw new IllegalArgumentException("Некорректный интервал");
                        }

                        System.out.print("Введите eps: ");
                        eps = readDouble(scanner);

                        if (eps <= 0) {
                            throw new IllegalArgumentException("Погрешность должна быть > 0");
                        }   

                        data = new InputData(funcChoice, methodChoice, a, b, eps);

                    } else {
                        data = InputHandler.readFromFile("input.txt");
                        System.out.println("Данные считаны из файла.");
                        a = data.a;
                        b = data.b;
                        eps = data.eps;
                    }
                    
                    Function f = FunctionFactory.getFunction(data.funcChoice);
                    Result result = null;

                    if (!hasRoot(f, a, b)) {
                        System.out.println(" На данном интервале нет корня или их четное количество.");
                        return;
                    }


                    switch (data.methodChoice) {
                        case 1:
                            result = BisectionMethod.solve(f, data.a, data.b, data.eps);
                            break;
                        case 2:
                            result = NewtonMethod.solve(f, data.a, data.b, data.eps);
                            break;
                        case 3:
                            result = SecantMethod.solve(f, data.a, data.b, data.eps);
                            break;
                        case 4:
                            PhiFunction phi = PhiFunctions.example1();
                            result = SimpleIterationMethod.solve(phi, data.a, data.b, data.eps);
                            break;
                        default:
                            System.out.println("Неверный метод");
                            continue;
                    }

                    System.out.println("\nКуда вывести результат?");
                    System.out.println("1: В консоль");
                    System.out.println("2: В файл");

                    int outputChoice = scanner.nextInt();

                    if (outputChoice == 1) {
                        System.out.println("\nРЕЗУЛЬТАТ:");
                        System.out.println("Корень: " + result.root);
                        System.out.println("f(x): " + result.value);
                        System.out.println("Итерации: " + result.iterations);
                    } else {
                        OutputHandler.writeToFile("output.txt", result);
                        System.out.println("Результат записан в файл");
                    }
                    System.out.println("\nПостроить график? (1 - да / 0 - нет)");
                    int plotChoice = scanner.nextInt();

                    if (plotChoice == 1) {
                        PlotFrame.show(f, data.a, data.b);
                    }
                }

                //СИСТЕМЫ УРАВНЕНИЙ
                else if (mainChoice == 2) {

                    System.out.println("\nВыберите систему:");
                    System.out.println("1: sin(y) + 2x = 2;  y + cos(x-1) = 0.7");
                    System.out.println("2: x + sin(y) = -0.4; 2y - cos(x+1) = 0");
                    System.out.println("3: y - cos(x) = 2; x + cos(y-1) = 0.8");

                    int systemChoice = scanner.nextInt();

                    SystemFunction sys;

                    switch (systemChoice) {
                        case 1:
                            sys = SystemFunctions.system13();
                            break;
                        case 2:
                            sys = SystemFunctions.system12();
                            break;
                        case 3:
                            sys = SystemFunctions.system16();
                            break;
                        default:
                            System.out.println("Неверный выбор системы");
                            continue;
                    }

                    System.out.print("Введите начальное x0: ");
                    double x0 = readDouble(scanner);

                    System.out.print("Введите начальное y0: ");
                    double y0 = readDouble(scanner);

                    System.out.print("Введите eps: ");
                    double eps = readDouble(scanner);

                    double[] result = SystemSimpleIteration.solve(sys, x0, y0, eps);

                    System.out.println("\nПостроить график системы? (1 - да / 0 - нет)");
                    int plotChoice = scanner.nextInt();

                    if (plotChoice == 1) {
                        SystemPlotFrame.show(sys, result[0], result[1]);
                    }
                }

            } catch (IllegalArgumentException e) {
                System.out.println(" Ошибка ввода: " + e.getMessage());
            } catch (ArithmeticException e) {
                System.out.println(" Ошибка вычислений: " + e.getMessage());
            } catch (Exception e) {
                System.out.println(" Неизвестная ошибка: " + e.getMessage());
            }
        }

        scanner.close();
    }
}