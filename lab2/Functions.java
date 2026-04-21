public class Functions {

    public static Function example1() {
        return new Function() {
            @Override
            public double f(double x) {
                return x * x * x - x - 2;
            }

            @Override
            public double df(double x) {
                return 3 * x * x - 1;
            }
        };
    }

    public static Function example2() {
        return new Function() {
            @Override
            public double f(double x) {
                return Math.sin(x) - 0.5;
            }

            @Override
            public double df(double x) {
                return Math.cos(x);
            }
        };
    }
}