import javax.swing.*;

public class PlotFrame {

    public static void show(Function f, double a, double b) {

        JFrame frame = new JFrame("График функции");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        frame.add(new PlotPanel(f, a, b));

        frame.setVisible(true);
    }
}