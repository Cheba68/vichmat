package systems;

public class System1 implements SystemFunction {

    @Override
    public double phi1(double x, double y) {
        return (2 - Math.cos(y)) / 2.0;
    }

    @Override
    public double phi2(double x, double y) {
        return Math.sin(x + 1) - 1.2;
    }

    @Override
    public String getName() {

        return """
                sin(x+1)-y=1.2
                2x+cos(y)=2
                """;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public double f1(double x, double y) {
    
        return Math.sin(x + 1)
                - y
                - 1.2;
    }
    
    @Override
    public double f2(double x, double y) {
    
        return 2 * x
                + Math.cos(y)
                - 2;
    }

    @Override
    public double df1dx(double x, double y) {
        return Math.cos(x + 1);
    }
    
    @Override
    public double df1dy(double x, double y) {
        return -1;
    }
    
    @Override
    public double df2dx(double x, double y) {
        return 2;
    }
    
    @Override
    public double df2dy(double x, double y) {
        return -Math.sin(y);
    }
}