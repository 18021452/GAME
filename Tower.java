import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;


public abstract class Tower {

    public static final int BASIC_TOWER = 0;
    public static final int ADVANCED_TOWER = 1;
    private final int towerHeight = 24;
    private final int towerWidth = 24;
    private int x, y, damage, radius, cost;
    protected Rectangle drawRectangle;


    public Tower(Point p) {

        this((int) p.getX(), (int) p.getY());
    }


    public Tower(int x, int y) {

        this(x, y, 5, 100, 75);
    }


    public Tower(int x, int y, int damage, int radius, int cost) {
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.radius = radius;
        this.cost = cost;
        this.drawRectangle = new Rectangle(x, y, towerWidth, towerHeight);
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public Rectangle getDrawRectangle() {
        return drawRectangle;
    }


    public int getRadius() {
        return radius;
    }


    public int getCost() {
        return cost;
    }


    public boolean intersectsWith(Tower other) {

        return other.getDrawRectangle().intersects(drawRectangle);
    }


    public boolean canPlaceOnMap(ArrayList<Tower> towers) {
        if (!GamePanel.BOUNDS.contains(this.x, this.y)) {
            return false;
        }
        for (Tower tower : towers) {
            if (tower.intersectsWith(this)) {
                return false;
            }
        }
        return true;
    }


    public void fire(Monster monster) {


        monster.takeHit(damage);

    }

    public void fire(Graphics g) {

    }


    public boolean canReach(Monster monster) {
        int x1 = monster.getX();
        int y1 = monster.getY();
        return Math.sqrt((x1 - x) * (x1 - x) + (y1 - y) * (y1 - y)) <= radius;
    }


    public abstract void drawReach(Graphics g);



    public abstract void drawTower(Graphics g);
}