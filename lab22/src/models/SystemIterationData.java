package models;

public class SystemIterationData {

    private final int step;

    private final double x;
    private final double y;

    private final double errorX;
    private final double errorY;

    public SystemIterationData(int step,
                               double x,
                               double y,
                               double errorX,
                               double errorY) {

        this.step = step;
        this.x = x;
        this.y = y;
        this.errorX = errorX;
        this.errorY = errorY;
    }

    public int getStep() {
        return step;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getErrorX() {
        return errorX;
    }

    public double getErrorY() {
        return errorY;
    }
}