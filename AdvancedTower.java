import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class AdvancedTower extends Tower {


    public AdvancedTower(Point p) {
        super((int) p.getX(), (int) p.getY(), 15, 60, 150);
    }


    public AdvancedTower(int x, int y) {
        super(x, y, 4, 30, 125);
    }


    public void drawReach(Graphics g) {
        int r = getRadius();
        g.setColor(new Color(232, 227, 227));
        g.fillOval(getX() - r + 12, getY() - r + 12, r * 2, r * 2);
    }


    public void drawTower(Graphics g) {
        g.setColor(new Color(20,100,100));
        g.fillRect(getX(), getY(), (int) drawRectangle.getHeight(),
                (int) drawRectangle.getWidth());
    }
}