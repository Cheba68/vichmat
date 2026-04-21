public class NewtonMethod {

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

        double x0;
        if (fa * f.df(a) > 0) {
            x0 = a;
        } else {
            x0 = b;
        }

        double x = x0;
        double prev;
        int iterations = 0;

        do {
            prev = x;

            double dfx = f.df(x);

            if (Math.abs(dfx) < 1e-12) {
                throw new ArithmeticException("Производная близка к нулю");
            }

            x = x - f.f(x) / dfx;

            iterations++;

        } while (Math.abs(x - prev) > eps);

        return new Result(x, f.f(x), iterations);
    }
}