package functions;

public class CosFunction implements Function {

    @Override
    public double f(double x) {
        return Math.cos(x) - x;
    }

    @Override
    public double df(double x) {
        return -Math.sin(x) - 1;
    }

    @Override
    public String getName() {
        return "cos(x) - x";
    }

    @Override
    public String toString() {
        return getName();
    }
}