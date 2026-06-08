package utils;

import functions.Function;

public class RootChecker {

    public static int countRoots(Function function,
                                 double a,
                                 double b) {

        double fa = function.f(a);
        double fb = function.f(b);

        if (fa * fb > 0) {
        
            throw new RuntimeException(
                    "На интервале отсутствует смена знака функции"
            );

        }
        return 1;
    }
}