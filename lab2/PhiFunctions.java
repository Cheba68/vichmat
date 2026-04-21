public class PhiFunctions {

    public static PhiFunction example1() {
        return new PhiFunction() {
            @Override
            public double phi(double x) {
                return Math.cbrt(x + 2);
            }

            @Override
            public double dphi(double x) {
                return 1.0 / (3 * Math.pow(x + 2, 2.0 / 3.0));
            }
        };
    }
}