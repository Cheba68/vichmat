package methods;

import functions.Function;
import models.IterationData;
import models.Result;

import java.util.ArrayList;
import java.util.List;

public class NewtonMethod implements Method {

    @Override
    public Result solve(Function function,
                        double a,
                        double b,
                        double eps) {

        double x = (a + b) / 2.0;

        List<IterationData> table = new ArrayList<>();

        int step = 0;

        while (true) {

            double fx = function.f(x);
            double dfx = function.df(x);

            if (Math.abs(dfx) < 1e-12) {
                throw new RuntimeException(
                        "Производная близка к нулю"
                );
            }

            double nextX = x - fx / dfx;

            double error = Math.abs(nextX - x);

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

    @Override
    public String getName() {
        return "Метод Ньютона";
    }

    @Override
    public String toString() {
        return getName();
    }
}