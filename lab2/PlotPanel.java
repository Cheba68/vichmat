import java.awt.*;
import javax.swing.*;

public class PlotPanel extends JPanel {

    private Function function;
    private double a, b;

    public PlotPanel(Function function, double a, double b) {
        this.function = function;
        this.a = a;
        this.b = b;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        // === МАСШТАБ ПО Y (авто) ===
        double minY = Double.MAX_VALUE;
        double maxY = -Double.MAX_VALUE;

        for (int i = 0; i < width; i++) {
            double x = a + i * (b - a) / width;
            double y = function.f(x);

            if (!Double.isNaN(y) && !Double.isInfinite(y)) {
                minY = Math.min(minY, y);
                maxY = Math.max(maxY, y);
            }
        }

        if (Math.abs(maxY - minY) < 1e-6) {
            maxY += 1;
            minY -= 1;
        }

        double scaleX = width / (b - a);
        double scaleY = height / (maxY - minY);

        // === ОСИ ===
        int x0 = (int) ((0 - a) * scaleX);           // положение Y-оси
        int y0 = (int) (maxY * scaleY);              // положение X-оси

        g2.setColor(Color.BLACK);

        // ось X
        if (y0 >= 0 && y0 <= height) {
            g2.drawLine(0, y0, width, y0);
        }

        // ось Y
        if (x0 >= 0 && x0 <= width) {
            g2.drawLine(x0, 0, x0, height);
        }

        // === ПОДПИСИ ===
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        if (y0 >= 0 && y0 <= height) {
            g2.drawString("X", width - 20, y0 - 5);
        }

        if (x0 >= 0 && x0 <= width) {
            g2.drawString("Y", x0 + 5, 15);
        }

        // ноль
        if (x0 >= 0 && x0 <= width && y0 >= 0 && y0 <= height) {
            g2.drawString("0", x0 + 5, y0 + 15);
        }

        // границы интервала
        g2.drawString(String.format("%.2f", a), 5, height - 5);
        g2.drawString(String.format("%.2f", b), width - 50, height - 5);

        // === ГРАФИК ===
        g2.setColor(Color.BLUE);

        double prevX = a;
        double prevY = function.f(prevX);

        for (int i = 1; i < width; i++) {
            double x = a + i * (b - a) / width;
            double y = function.f(x);

            if (Double.isNaN(y) || Double.isInfinite(y) ||
                Double.isNaN(prevY) || Double.isInfinite(prevY)) {
                prevX = x;
                prevY = y;
                continue;
            }

            int x1 = (int) ((prevX - a) * scaleX);
            int y1 = (int) ((maxY - prevY) * scaleY);

            int x2 = (int) ((x - a) * scaleX);
            int y2 = (int) ((maxY - y) * scaleY);

            g2.drawLine(x1, y1, x2, y2);

            prevX = x;
            prevY = y;
        }
    }
}