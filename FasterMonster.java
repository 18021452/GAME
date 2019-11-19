import java.awt.Color;
import java.awt.Graphics;


public class FasterMonster extends Monster {


    public FasterMonster(int x, int y) {
        super(x, y, -3, 0, 5, 500, 5);
    }


    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}