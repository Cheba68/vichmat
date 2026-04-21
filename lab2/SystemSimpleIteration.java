public class SystemSimpleIteration {

    public static void solve(SystemFunction sys, double x0, double y0, double eps) {

        double x = x0;
        double y = y0;

        double prevX, prevY;

        int iterations = 0;

        System.out.println("\nИтерационный процесс:");

        do {
            prevX = x;
            prevY = y;

            x = sys.phi1(prevX, prevY);
            y = sys.phi2(prevX, prevY);

            iterations++;

            System.out.printf("Итерация %d: x=%.5f y=%.5f |dx|=%.5f |dy|=%.5f\n",
                    iterations, x, y,
                    Math.abs(x - prevX),
                    Math.abs(y - prevY));

        } while (Math.max(Math.abs(x - prevX), Math.abs(y - prevY)) > eps);

        System.out.println("\nРЕЗУЛЬТАТ:");
        System.out.println("x = " + x);
        System.out.println("y = " + y);
        System.out.println("Итераций: " + iterations);

        double f1 = Math.sin(y) + 2 * x - 2;
        double f2 = y + Math.cos(x - 1) - 0.7;

        System.out.println("\nПроверка:");
        System.out.println("f1 = " + f1);
        System.out.println("f2 = " + f2);
    }
}