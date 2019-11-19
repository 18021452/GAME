import java.awt.Color;
import java.awt.Graphics;


public class HardMonster extends Monster {


    public HardMonster(int x, int y) {
        super(x, y, -3, 0, 5, 2000, 1);
    }


    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}