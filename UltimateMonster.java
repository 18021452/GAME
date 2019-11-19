import java.awt.Color;
import java.awt.Graphics;

public class UltimateMonster extends Monster {


    public UltimateMonster(int x, int y) {
        super(x, y, -3, 0, 75, 10000, 1);
    }


    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(getX() - 25, getY() - 25, 50, 50);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}