package methods;

import functions.Function;
import models.IterationData;
import models.Result;

import java.util.ArrayList;
import java.util.List;

public class ChordMethod implements Method {

    @Override
    public Result solve(Function function,
                        double a,
                        double b,
                        double eps) {

        List<IterationData> table = new ArrayList<>();

        int step = 0;

        double x = a;

        while (true) {

            double fa = function.f(a);
            double fb = function.f(b);

            x = a - fa * (b - a) / (fb - fa);

            double fx = function.f(x);

            table.add(
                    new IterationData(
                            ++step,
                            x,
                            fx,
                            Math.abs(fx)
                    )
            );

            if (Math.abs(fx) < eps) {

                return new Result(
                        x,
                        fx,
                        step,
                        table
                );
            }

            if (fa * fx < 0) {
                b = x;
            } else {
                a = x;
            }
        }
    }

    @Override
    public String getName() {
        return "Метод хорд";
    }

    @Override
    public String toString() {
        return getName();
    }
}