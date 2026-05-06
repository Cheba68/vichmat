public class SimpleIterationMethod {

    public static Result solve(PhiFunction phi, double a, double b, double eps) {
        if (eps <= 0) {
            throw new IllegalArgumentException("Погрешность должна быть > 0");
        }

        if (a >= b) {
            throw new IllegalArgumentException("Некорректный интервал");
        }

        double maxDerivative = Math.max(
                Math.abs(phi.dphi(a)),
                Math.abs(phi.dphi(b))
        );

        if (maxDerivative >= 1) {
            throw new IllegalArgumentException("Условие сходимости не выполнено |φ'(x)| < 1");
        }

        double x = (a + b) / 2.0;
        double prev;
        int iterations = 0;
        int maxIterations = 10000;
        do {
            prev = x;
            x = phi.phi(x);
            iterations++;
            if (iterations > maxIterations) {
                throw new ArithmeticException("Превышено максимальное количество итераций");
            }

        } while (Math.abs(x - prev) > eps);

        return new Result(x, 0, iterations);
    }
}