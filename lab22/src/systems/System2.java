package systems;

public class System2 implements SystemFunction {

    @Override
    public double phi1(double x, double y) {
        return Math.sqrt(4 - y * y);
    }

    @Override
    public double phi2(double x, double y) {
        return x - 1;
    }

    @Override
    public String getName() {
        return """
                x² + y² = 4
                x - y = 1
                """;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public double f1(double x, double y) {
        return x * x + y * y - 4;
    }

    @Override
    public double f2(double x, double y) {
        return x - y - 1;
    }

    @Override
    public double df1dx(double x, double y) {
        return 2 * x;
    }
    
    @Override
    public double df1dy(double x, double y) {
        return 2 * y;
    }
    
    @Override
    public double df2dx(double x, double y) {
        return 1;
    }
    
    @Override
    public double df2dy(double x, double y) {
        return -1;
    }
}