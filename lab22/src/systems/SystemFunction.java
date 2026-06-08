package systems;

public interface SystemFunction {

    double phi1(double x, double y);

    double phi2(double x, double y);

    double f1(double x, double y);

    double f2(double x, double y);

    double df1dx(double x, double y);

    double df1dy(double x, double y);

    double df2dx(double x, double y);

    double df2dy(double x, double y);

    String getName();
}