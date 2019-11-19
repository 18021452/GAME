import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class BasicTower extends Tower {


    public BasicTower(Point p) {
        super(p);
    }


    public BasicTower(int x, int y) {
        super(x, y);
    }


    public void drawReach(Graphics g) {
        int r = getRadius();
        g.setColor(new Color(232, 227, 227));
        g.fillOval(getX() - r + 12, getY() - r + 12, r * 2, r * 2);
    }


    public void drawTower(Graphics g) {
        g.setColor(new Color(10,150,150));
        g.fillRect(getX(), getY(), (int) drawRectangle.getHeight(),
                (int) drawRectangle.getWidth());
    }


}