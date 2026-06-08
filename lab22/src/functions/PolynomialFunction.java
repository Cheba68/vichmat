package functions;

public class PolynomialFunction implements Function {

    @Override
    public double f(double x) {
        return x * x * x - x - 2;
    }

    @Override
    public double df(double x) {
        return 3 * x * x - 1;
    }

    @Override
    public String getName() {
        return "x^3 - x - 2";
    }

    @Override
    public String toString() {
        return getName();
    }
}