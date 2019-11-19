import java.awt.BorderLayout;
import javax.swing.JFrame;


public class TowerDefenseGame {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tower Defense");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        InputPanel input = new InputPanel();
        frame.add(input, BorderLayout.WEST);
        frame.add(new GamePanel(input));
        frame.pack();
        frame.setVisible(true);
    }
}