import java.awt.Color;
import java.awt.Graphics;


public class IntenseMonster extends Monster {


    public IntenseMonster(int x, int y) {
        super(x, y, -3, 0, 10, 4000, 1);
    }


    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        g.fillOval(getX() - 15, getY() - 15, 30, 30);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}