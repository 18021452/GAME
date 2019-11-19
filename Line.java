import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;


public class Line {
    private Point start, end;


    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }


    public Line(int x1, int y1, int x2, int y2) {
        start = new Point(x1, y1);
        end = new Point(x2, y2);
    }

    public Point getEnd() {
        return end;
    }

    public Point getStart() {
        return start;
    }


    public boolean contains(int x, int y) {
        int largeX, smallX, largeY, smallY;

        int x1 = (int) start.getX();
        int y1 = (int) start.getY();
        int x2 = (int) end.getX();
        int y2 = (int) end.getY();
        if (Math.abs(x1 - x2) > 0) {
            if (x1 > x2) {
                largeX = x1;
                smallX = x2;
            } else {
                smallX = x1;
                largeX = x2;
            }
            return x >= smallX && x <= largeX;

        } else {
            if (y1 > y2) {
                largeY = y1;
                smallY = y2;
            } else {
                smallY = y1;
                largeY = y2;
            }
            return y >= smallY && y <= largeY;
        }
    }


    public Point getVector(int speed) {
        int x1 = (int) start.getX();
        int y1 = (int) start.getY();
        int x2 = (int) end.getX();
        int y2 = (int) end.getY();

        if (x2 > x1) {
            return new Point(speed * 3, 0);
        } else if (x2 < x1) {
            return new Point(speed * -3, 0);
        } else if (y2 > y1) {
            return new Point(0, speed * 3);
        } else {
            return new Point(0, speed * -3);
        }
    }


    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(),
                (int) end.getY());
    }
}