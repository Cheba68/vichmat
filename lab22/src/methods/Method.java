package methods;

import functions.Function;
import models.Result;

public interface Method {

    Result solve(Function function,
                 double a,
                 double b,
                 double eps);

    String getName();
}