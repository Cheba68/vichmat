public class SystemFunctions {

    public static SystemFunction system13() {
        return new SystemFunction() {

            @Override
            public double phi1(double x, double y) {
                return (2 - Math.sin(y)) / 2.0;
            }

            @Override
            public double phi2(double x, double y) {
                return 0.7 - Math.cos(x - 1);
            }
        };
    }
    
    public static SystemFunction system12() {
        return new SystemFunction() {
            public double phi1(double x, double y) {
                return -0.4 - Math.sin(y);
            }

            public double phi2(double x, double y) {
                return Math.cos(x + 1) / 2.0;
            }
        };
    }

    public static SystemFunction system16() {
        return new SystemFunction() {
            public double phi1(double x, double y) {
                return Math.cos(x) + 2;
            }

            public double phi2(double x, double y) {
                return 0.8 - Math.cos(y - 1);
            }
        };
    }
}