public class InputData {
    public int funcChoice;
    public int methodChoice;
    public double a, b, eps;

    public InputData(int funcChoice, int methodChoice, double a, double b, double eps) {
        this.funcChoice = funcChoice;
        this.methodChoice = methodChoice;
        this.a = a;
        this.b = b;
        this.eps = eps;
    }
}