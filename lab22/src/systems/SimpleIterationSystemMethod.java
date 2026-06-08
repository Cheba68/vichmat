package systems;
//no use
import java.util.ArrayList;
import java.util.List;
import models.SystemIterationData;
import models.SystemResult;

public class SimpleIterationSystemMethod {

    private static final int MAX_ITERATIONS = 10000;

    public SystemResult solve(SystemFunction system,
                              double x0,
                              double y0,
                              double eps) {

        List<SystemIterationData> table =
                new ArrayList<>();

        int step = 0;
        
        double x = x0;  // текущие значения
        double y = y0;

        while (true) {

            double nextX =
                    system.phi1(x, y);

            double nextY =
                    system.phi2(x, y);

            double errorX =
                    Math.abs(nextX - x);

            double errorY =
                    Math.abs(nextY - y);

            step++;
            
            table.add(
                    new SystemIterationData(
                            step,
                            nextX,
                            nextY,
                            errorX,
                            errorY
                    )
            );

            // ПРОВЕРКА НА ПРЕВЫШЕНИЕ ЛИМИТА ИТЕРАЦИЙ
            if (step >= MAX_ITERATIONS) {
                throw new RuntimeException(
                    "Метод не сошелся за " + MAX_ITERATIONS + 
                    " итераций."
                );
            }

            if (Math.max(errorX, errorY) < eps) {

                return new SystemResult(
                        nextX,
                        nextY,
                        step,
                        table
                );
            }

            x = nextX;
            y = nextY;
        }
    }
}