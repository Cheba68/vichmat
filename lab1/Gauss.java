public class Gauss {

    private final double[][] a;
    private final double[][] original;
    private final int n;
    private int swapCount = 0;
    private static final double EPS = 1e-12;

    public Gauss(double[][] matrix) {
        this.n = matrix.length;
        this.a = new double[n][n + 1];
        this.original = new double[n][n + 1];

        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, a[i], 0, n + 1);
            System.arraycopy(matrix[i], 0, original[i], 0, n + 1);
        }
    }

    // Прямой ход
    public void forwardElimination() {
        for (int k = 0; k < n - 1; k++) {
            pivot(k);
            for (int i = k + 1; i < n; i++) {
                double m = a[i][k] / a[k][k];
                for (int j = k; j <= n; j++) {
                    a[i][j] -= m * a[k][j];
                }
            }
        }
    }

    // Выбор главного элемента
    private void pivot(int k) {
        int maxRow = k;
        double max = Math.abs(a[k][k]);

        for (int i = k + 1; i < n; i++) {
            if (Math.abs(a[i][k]) > max) {
                max = Math.abs(a[i][k]);
                maxRow = i;
            }
        }

        if (max < EPS) return;

        if (maxRow != k) {
            double[] tmp = a[k];
            a[k] = a[maxRow];
            a[maxRow] = tmp;
            swapCount++;
        }
    }

    // Проверка типа системы
    // -1 — нет решений, 0 — бесконечно много, 1 — единственное
    public int checkSystemType() {
        boolean zeroRow = false;

        for (int i = 0; i < n; i++) {
            boolean allZero = true;
            for (int j = 0; j < n; j++) {
                if (Math.abs(a[i][j]) > EPS) {
                    allZero = false;
                    break;
                }
            }

            if (allZero) {
                if (Math.abs(a[i][n]) > EPS) return -1;
                zeroRow = true;
            }
        }
        return zeroRow ? 0 : 1;
    }

    // Обратный ход
    public double[] solve() {
        double[] x = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            double sum = a[i][n];
            for (int j = i + 1; j < n; j++) {
                sum -= a[i][j] * x[j];
            }
            x[i] = sum / a[i][i];
        }
        return x;
    }

    // Определитель
    public double determinant() {
        double det = (swapCount % 2 == 0) ? 1 : -1;
        for (int i = 0; i < n; i++) {
            det *= a[i][i];
        }
        return det;
    }

    // Невязки
    public double[] residuals(double[] x) {
        double[] r = new double[n];

        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += original[i][j] * x[j];
            }
            r[i] = sum - original[i][n];
        }
        return r;
    }

    // Печать матрицы
    public void printUpperTriangularMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                System.out.printf("%10.4f ", a[i][j]);
            }
            System.out.println();
        }
    }
}