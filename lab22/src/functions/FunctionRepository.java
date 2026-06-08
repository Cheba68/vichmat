package functions;

import java.util.List;

public class FunctionRepository {

    public static List<Function> getFunctions() {

        return List.of(
                new PolynomialFunction(),
                new SinFunction(),
                new CosFunction()
        );
    }
}