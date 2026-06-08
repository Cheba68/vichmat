package models;

import java.util.List;

public class SystemResult {

    private final double x;
    private final double y;

    private final int iterations;

    private final List<SystemIterationData> table;

    public SystemResult(double x,
                        double y,
                        int iterations,
                        List<SystemIterationData> table) {

        this.x = x;
        this.y = y;
        this.iterations = iterations;
        this.table = table;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIterations() {
        return iterations;
    }

    public List<SystemIterationData> getTable() {
        return table;
    }
}