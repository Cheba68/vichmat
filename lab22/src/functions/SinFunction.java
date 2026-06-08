package functions;

public class SinFunction implements Function {

    @Override
    public double f(double x) {
        return Math.sin(x) - 0.5;
    }

    @Override
    public double df(double x) {
        return Math.cos(x);
    }

    @Override
    public String getName() {
        return "sin(x) - 0.5";
    }

    @Override
    public String toString() {
        return getName();
    }
}