package systems;

import java.util.ArrayList;
import java.util.List;
import models.SystemIterationData;
import models.SystemResult;

public class NewtonSystemMethod {

    public SystemResult solve(SystemFunction system,
                              double x,
                              double y,
                              double eps) {

        List<SystemIterationData> table =
                new ArrayList<>();

        int step = 0;

        while (true) {

            double f1 =
                    system.f1(x, y);

            double f2 =
                    system.f2(x, y);

            //  Якобиан 

            double df1dx =
                    system.df1dx(x, y);
                    
            double df1dy =
                    system.df1dy(x, y);
                    
            double df2dx =
                    system.df2dx(x, y);
                    
            double df2dy =
                    system.df2dy(x, y);
                
            double determinant =
                    df1dx * df2dy
                            -
                            df1dy * df2dx;

            if (Math.abs(determinant)
                    < 1e-10) {

                throw new RuntimeException(
                        "Якобиан вырожден"
                );
            }

            //  Метод Крамера 

            double deltaX =
                    (-f1 * df2dy
                            +
                            df1dy * f2)
                            / determinant;

            double deltaY =
                    (-df1dx * f2
                            +
                            f1 * df2dx)
                            / determinant;

            double nextX =
                    x + deltaX;

            double nextY =
                    y + deltaY;

            double errorX =
                    Math.abs(deltaX);

            double errorY =
                    Math.abs(deltaY);

            table.add(
                    new SystemIterationData(
                            ++step,
                            nextX,
                            nextY,
                            errorX,
                            errorY
                    )
            );

            if (Math.max(errorX, errorY)
                    < eps) {

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