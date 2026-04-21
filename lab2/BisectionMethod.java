public class BisectionMethod {

    public static Result solve(Function f, double a, double b, double eps) {

        if (eps <= 0) {
            throw new IllegalArgumentException("Погрешность должна быть > 0");
        }

        if (a >= b) {
            throw new IllegalArgumentException("Левая граница должна быть меньше правой");
        }

        double fa = f.f(a);
        double fb = f.f(b);

        if (fa * fb > 0) {
            throw new IllegalArgumentException("На интервале нет корня или их чётное количество");
        }

        int iterations = 0;
        double x = 0;

        // 🔁 Основной цикл
        while (Math.abs(b - a) > eps) {
            x = (a + b) / 2.0;
            double fx = f.f(x);

            if (fa * fx < 0) {
                b = x;
                fb = fx;
            } else {
                a = x;
                fa = fx;
            }

            iterations++;
        }

        x = (a + b) / 2.0;

        return new Result(x, f.f(x), iterations);
    }
}