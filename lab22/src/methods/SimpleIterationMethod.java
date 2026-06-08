package methods;

import functions.Function;
import models.IterationData;
import models.Result;

import java.util.ArrayList;
import java.util.List;

public class SimpleIterationMethod implements Method {

    @Override
    public Result solve(Function function,
                        double a,
                        double b,
                        double eps) {

        double lambda =
                calculateLambda(function, a, b);

        if (!checkConvergence(
                function,
                lambda,
                a,
                b
        )) {

            throw new RuntimeException(
                    "Метод простой итерации не сходится на данном интервале"
            );
        }

        double x = chooseInitialApproximation(
                function,
                a,
                b
        );

        List<IterationData> table =
                new ArrayList<>();

        int step = 0;

        while (true) {

            double nextX =
                    phi(function, lambda, x);

            double error =
                    Math.abs(nextX - x);

            table.add(
                    new IterationData(
                            ++step,
                            nextX,
                            function.f(nextX),
                            error
                    )
            );

            if (error < eps) {

                return new Result(
                        nextX,
                        function.f(nextX),
                        step,
                        table
                );
            }

            x = nextX;
        }
    }

    private double phi(Function function,
                       double lambda,
                       double x) {

        return x + lambda * function.f(x);
    }

    private double calculateLambda(Function function,
                                   double a,
                                   double b) {

        double maxDerivative = 0;

        for (double x = a;
             x <= b;
             x += 0.01) {

            maxDerivative = Math.max(
                    maxDerivative,
                    Math.abs(function.df(x))
            );
        }

        return -1.0 / maxDerivative;
    }

    private boolean checkConvergence(Function function,
                                     double lambda,
                                     double a,
                                     double b) {

        double q = 0;

        for (double x = a;
             x <= b;
             x += 0.01) {

            double value = Math.abs(
                    1 + lambda * function.df(x)
            );

            q = Math.max(q, value);
        }

        return q < 1;
    }

    private double chooseInitialApproximation(
            Function function,
            double a,
            double b
    ) {

        if (function.f(a) * function.df(a) > 0) {
            return a;
        }

        return b;
    }

    @Override
    public String getName() {
        return "Простая итерация";
    }

    @Override
    public String toString() {
        return getName();
    }
}