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

        int width = getWidth();
        int height = getHeight();

        // центр координат
        int x0 = width / 2;
        int y0 = height / 2;

        // масштаб
        double scaleX = width / (b - a);
        double scaleY = height / 4.0; // можно регулировать

        // оси
        g.drawLine(0, y0, width, y0); // X
        g.drawLine(x0, 0, x0, height); // Y

        // рисуем график
        g.setColor(Color.BLUE);

        double prevX = a;
        double prevY = function.f(prevX);

        for (int i = 1; i < width; i++) {
            double x = a + i * (b - a) / width;
            double y = function.f(x);

            int x1 = (int) ((prevX - a) * scaleX);
            int y1 = (int) (y0 - prevY * scaleY);

            int x2 = (int) ((x - a) * scaleX);
            int y2 = (int) (y0 - y * scaleY);

            g.drawLine(x1, y1, x2, y2);

            prevX = x;
            prevY = y;
        }
    }
}