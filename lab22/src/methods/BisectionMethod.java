package methods;

import functions.Function;
import models.IterationData;
import models.Result;

import java.util.ArrayList;
import java.util.List;

public class BisectionMethod implements Method {

    @Override
    public Result solve(Function function,
                        double a,
                        double b,
                        double eps) {

        List<IterationData> table = new ArrayList<>();

        int step = 0;
        double x = 0;

        while (Math.abs(b - a) > eps) {

            x = (a + b) / 2.0;

            double fx = function.f(x);

            table.add(
                    new IterationData(
                            ++step,
                            x,
                            fx,
                            Math.abs(b - a)
                    )
            );

            if (function.f(a) * fx < 0) {
                b = x;
            } else {
                a = x;
            }
        }

        return new Result(
                x,
                function.f(x),
                step,
                table
        );
    }

    @Override
    public String getName() {
        return "Метод бисекции";
    }

    @Override
    public String toString() {
        return getName();
    }
}