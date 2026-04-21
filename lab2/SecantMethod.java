public class SecantMethod {

    public static Result solve(Function f, double a, double b, double eps) {

        if (eps <= 0) {
            throw new IllegalArgumentException("Погрешность должна быть > 0");
        }

        if (a >= b) {
            throw new IllegalArgumentException("Некорректный интервал");
        }

        double fa = f.f(a);
        double fb = f.f(b);

        if (fa * fb > 0) {
            throw new IllegalArgumentException("На интервале нет корня или их несколько");
        }

        double x0 = a;
        double x1 = b;
        double x2;

        int iterations = 0;

        do {
            double f0 = f.f(x0);
            double f1 = f.f(x1);

            if (Math.abs(f1 - f0) < 1e-12) {
                throw new ArithmeticException("Деление на ноль в методе секущих");
            }

            x2 = x1 - f1 * (x1 - x0) / (f1 - f0);

            x0 = x1;
            x1 = x2;

            iterations++;

        } while (Math.abs(x1 - x0) > eps);

        return new Result(x1, f.f(x1), iterations);
    }
}