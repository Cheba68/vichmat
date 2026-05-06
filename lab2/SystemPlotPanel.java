import java.awt.*;
import javax.swing.*;

public class SystemPlotPanel extends JPanel {
    private double centerX, centerY;
    private double range = 2; // масштаб
    private SystemFunction system;
    private double xMin = -5, xMax = 5;
    private double yMin = -5, yMax = 5;

    public SystemPlotPanel(SystemFunction system, double centerX, double centerY) {
        this.system = system;
        this.centerX = centerX;
        this.centerY = centerY;
        
        this.xMin = centerX - range;
        this.xMax = centerX + range;
        this.yMin = centerY - range;
        this.yMax = centerY + range;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        double scaleX = width / (xMax - xMin);
        double scaleY = height / (yMax - yMin);

        // координаты нуля
        int x0 = (int) ((0 - xMin) * scaleX);
        int y0 = (int) ((yMax - 0) * scaleY);

        // оси
        g2.setColor(Color.BLACK);

        if (y0 >= 0 && y0 <= height)
            g2.drawLine(0, y0, width, y0);

        if (x0 >= 0 && x0 <= width)
            g2.drawLine(x0, 0, x0, height);

        g2.drawString("X", width - 20, y0 - 5);
        g2.drawString("Y", x0 + 5, 15);

        // === ГРАФИК f1(x,y)=0 ===
        g2.setColor(Color.RED);

        for (int i = 0; i < width; i++) {
            double x = xMin + i * (xMax - xMin) / width;

            for (double y = yMin; y <= yMax; y += 0.01) {
                if (Math.abs(system.phi1(x, y)) < 0.05) {
                    int px = (int) ((x - xMin) * scaleX);
                    int py = (int) ((yMax - y) * scaleY);
                    g2.fillRect(px, py, 1, 1);
                }
            }
        }

        // === ГРАФИК f2(x,y)=0 ===
        g2.setColor(Color.BLUE);

        for (int i = 0; i < width; i++) {
            double x = xMin + i * (xMax - xMin) / width;

            for (double y = yMin; y <= yMax; y += 0.01) {
                if (Math.abs(system.phi2(x, y)) < 0.05) {
                    int px = (int) ((x - xMin) * scaleX);
                    int py = (int) ((yMax - y) * scaleY);
                    g2.fillRect(px, py, 1, 1);
                }
            }
        }
    }
}