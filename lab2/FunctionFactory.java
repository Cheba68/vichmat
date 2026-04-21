public class FunctionFactory {

    public static Function getFunction(int choice) {
        switch (choice) {
            case 1:
                return Functions.example1();
            case 2:
                return Functions.example2();
            default:
                throw new IllegalArgumentException("Неверный выбор функции");
        }
    }

    public static void printFunctions() {
        System.out.println("Выберите функцию:");
        System.out.println("1: x^3 - x - 2");
        System.out.println("2: sin(x) - 0.5");
    }
}