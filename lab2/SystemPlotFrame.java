import javax.swing.*;

public class SystemPlotFrame {

    public static void show(SystemFunction system, double x, double y) {
        JFrame frame = new JFrame("График системы");
        
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 600);
        
        frame.add(new SystemPlotPanel(system, x, y));
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}