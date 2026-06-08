package methods;

import functions.Function;
import models.IterationData;
import models.Result;

import java.util.ArrayList;
import java.util.List;

public class SecantMethod implements Method {

    @Override
    public Result solve(Function function,
                        double a,
                        double b,
                        double eps) {

        double x0 = a;
        double x1 = b;

        List<IterationData> table = new ArrayList<>();

        int step = 0;

        while (true) {

            double f0 = function.f(x0);
            double f1 = function.f(x1);

            if (Math.abs(f1 - f0) < 1e-12) {
                throw new RuntimeException(
                        "Деление на ноль"
                );
            }

            double x2 =
                    x1 - f1 * (x1 - x0) / (f1 - f0);

            double error = Math.abs(x2 - x1);

            table.add(
                    new IterationData(
                            ++step,
                            x2,
                            function.f(x2),
                            error
                    )
            );

            if (error < eps) {

                return new Result(
                        x2,
                        function.f(x2),
                        step,
                        table
                );
            }

            x0 = x1;
            x1 = x2;
        }
    }

    @Override
    public String getName() {
        return "Метод секущих";
    }

    @Override
    public String toString() {
        return getName();
    }
}