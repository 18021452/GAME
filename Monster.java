import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;


public abstract class Monster implements Comparable<Monster> {

    private int x, y, moneyValue, health, speed, pathIndex, distanceTraveled;
    private Point vector;
    private boolean beingAttacked, reachedEnd;


    public Monster(int x, int y) {
        this(x, y, -3, 0, 5, 150, 1);
    }


    public Monster(int x, int y, int vecX, int vecY, int moneyValue, int health,
                   int speed) {
        this.x = x;
        this.y = y;
        this.vector = new Point(speed * vecX, speed * vecY);
        this.health = health;
        this.moneyValue = moneyValue;
        this.speed = speed;

        pathIndex = 0;
        distanceTraveled = 0;
        beingAttacked = false;
        reachedEnd = false;
    }


    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }


    public int getMoneyValue() {
        return moneyValue;
    }


    public int getDistanceTraveled() {
        return distanceTraveled;
    }


    public int getScoreLoss() {
        if (this instanceof UltimateMonster) {
            return 30;
        } else if (this instanceof IntenseMonster) {
            return 20;
        } else {
            return 10;
        }
    }


    public boolean isBeingAttacked() {
        return beingAttacked;
    }


    public boolean didReachEnd() {
        return reachedEnd;
    }


    public void setBeingAttacked(boolean beingAttacked) {
        this.beingAttacked = beingAttacked;
    }


    public void takeHit(int damage) {

        health -= damage;
        beingAttacked = true;
    }


    public void move(ArrayList<Line> path) {
        Random rand = new Random();
        if (this instanceof UltimateMonster && rand.nextInt(2) == 0) {
            return;
        }
        int vecX = (int) vector.getX();
        int vecY = (int) vector.getY();
        distanceTraveled += (int) Math.sqrt(vecX * vecX + vecY * vecY);
        x += vecX;
        y += vecY;
        if (!path.get(pathIndex).contains(x, y)) {
            pathIndex++;
            if (pathIndex >= path.size()) {
                reachedEnd = true;
                drainHealth();
            } else {
                //removed speed for now
                vector = path.get(pathIndex).getVector(speed);
                x = (int) path.get(pathIndex).getStart().getX();
                y = (int) path.get(pathIndex).getStart().getY();
            }
        }
    }


    public void drainHealth() {
        health = 0;
    }


    public boolean isDead() {
        return health <= 0;
    }


    public int compareTo(Monster other) {
        if (other.equals(this)) {
            return 0;
        } else {
            return other.getDistanceTraveled() - distanceTraveled;
        }
    }


    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof Monster)) {
            return false;
        }
        Monster monster = (Monster) other;
        return monster.getDistanceTraveled() == distanceTraveled;
    }


    public int hashCode() {
        return super.hashCode() + moneyValue;
    }


    public abstract void draw(Graphics g);

}