import java.awt.Color;
import java.awt.Graphics;


public class RegularMonster extends Monster {


    public RegularMonster(int x, int y) {
        super(x, y);
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillOval(getX() - 10, getY() - 10, 20, 20);
        if (isBeingAttacked()) {
            g.setColor(Color.WHITE);
            g.fillOval(getX() - 4, getY() - 4, 8, 8);
        }
    }
}