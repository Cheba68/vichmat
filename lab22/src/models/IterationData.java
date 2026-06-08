package models;

public class IterationData {

    private final int step;
    private final double x;
    private final double fx;
    private final double error;

    public IterationData(int step,
                         double x,
                         double fx,
                         double error) {

        this.step = step;
        this.x = x;
        this.fx = fx;
        this.error = error;
    }

    public int getStep() {
        return step;
    }

    public double getX() {
        return x;
    }

    public double getFx() {
        return fx;
    }

    public double getError() {
        return error;
    }
}