import java.awt.event.*;
import java.util.ArrayList;
import java.awt.*;
import java.util.Collections;
import javax.swing.*;
import java.util.Random;

public class GamePanel extends JPanel {
    public static final int HEIGHT = 700, WIDTH = 700;
    public static final Rectangle BOUNDS = new Rectangle(HEIGHT, WIDTH);

    private InputPanel inputPanel;
    private ArrayList<Line> path;
    private ArrayList<Tower> towers;
    private ArrayList<Monster> monsters;
    private Timer gameTimer;
    private Timer waveTimer;
    private static int waveNumber;
    private boolean previouslyFastForward;


    public GamePanel(InputPanel input) {
        inputPanel = input;
        waveNumber = 0;

        towers = new ArrayList<Tower>();
        monsters = new ArrayList<Monster>();
        constructPath();
        setPreferredSize(new Dimension(HEIGHT, WIDTH));
        setBackground(Color.LIGHT_GRAY);

        addMouseListener(new ClickListener());

        int xStart = (int) path.get(0).getStart().getX();
        int yStart = (int) path.get(0).getStart().getY();

        waveTimer = new Timer(150, new WaveListener(xStart, yStart));
        inputPanel.assignWaveTimer(waveTimer);

        gameTimer = new Timer(30, new GameListener());
        gameTimer.start();

        previouslyFastForward = false;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(20));
        drawAll(g);
    }




    public void fireTowers() {
        for (int i = 0; i < towers.size(); i++) {
            for (int j = 0; j < monsters.size(); j++) {
                Monster curMonster = monsters.get(j);
                if (i < towers.size() && towers.get(i).canReach(curMonster)) {
                    towers.get(i).fire(curMonster);
                    i++;
                    j = -1;
                }
            }
        }
    }


    public int[] checkForDeath() {
        int[] stats = new int[2];
        for (int i = 0; i < monsters.size(); i++) {
            if (monsters.get(i).didReachEnd()) {
                stats[0] += monsters.get(i).getScoreLoss();
                monsters.remove(i);
                i--;
            } else if (monsters.get(i).isDead()) {
                stats[1] += monsters.get(i).getMoneyValue();
                monsters.remove(i);
                i--;
            }
        }
        return stats;
    }


    public void moveAll() {
        if (inputPanel.isInFastForward() && !previouslyFastForward) {
            gameTimer.stop();
            gameTimer = new Timer(1, new GameListener());
            previouslyFastForward = true;
            gameTimer.start();
        } else if (!inputPanel.isInFastForward() && previouslyFastForward) {
            gameTimer.stop();
            gameTimer = new Timer(30, new GameListener());
            previouslyFastForward = false;
            gameTimer.start();
        }
        if (inputPanel.getWaveNumber() > waveNumber) {
            waveNumber++;
            sendWave();
        }
        for (Monster monster : monsters) {
            monster.setBeingAttacked(false);
            monster.move(path);
        }
    }


    private void orderMonsters() {

        Collections.sort(monsters);
    }


    private void drawAll(Graphics g) {
        for (Tower tower : towers) {
            tower.drawReach(g);
        }

        for (Tower tower : towers) {
            tower.drawTower(g);
        }

        for (Line line : path) {
            line.draw(g);
        }

        for (Monster monster : monsters) {
            monster.draw(g);
        }
    }


    private void sendWave() {
        if (!(waveTimer != null && waveTimer.isRunning())) {
            waveTimer.start();
        }
    }


    private void constructPath() {
        path = new ArrayList<Line>(5);
        path.add(new Line(700, 600, 200, 600));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                new Point(200, 100)));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                new Point(600, 100)));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                new Point(600, 400)));
        path.add(new Line(path.get(path.size() - 1).getEnd(),
                new Point(0, 400)));
    }


    private class GameListener implements ActionListener {

        public void actionPerformed(ActionEvent e)  {
            if (inputPanel.isStillPlaying() || !monsters.isEmpty()) {
                moveAll();
                orderMonsters();


                fireTowers();
                inputPanel.updateStats(checkForDeath());
                repaint();
            } else {
                if (waveNumber <= 10 && monsters.isEmpty() && inputPanel.getScore() > 0) {
                    JOptionPane.showMessageDialog(null, "You won! "
                            + "Congratulations!");
                    System.exit(0);
                } else if (inputPanel.getScore() <= 0) {
                    JOptionPane.showMessageDialog(null, "You Lost! Better "
                            + "luck next time!");
                    System.exit(0);
                }
            }
        }
    }


    private class WaveListener implements ActionListener {

        private int waveComparator, xStart, yStart;
        private int numTimes;


        public WaveListener(int xStart, int yStart) {
            this.waveComparator = waveNumber;
            this.xStart = xStart;
            this.yStart = yStart;
            numTimes = 0;
        }


        public void actionPerformed(ActionEvent e) {
            numTimes++;
            waveComparator = waveNumber * 2;
            Random rand = new Random();
            if (numTimes > 10) {
                waveTimer.stop();
                numTimes = 0;
            } else {
                if (waveComparator < 6) {
                    numTimes += (10 - 2 * waveComparator);
                    monsters.add(new RegularMonster(xStart, yStart));
                } else {
                    int random = rand.nextInt(waveComparator) + 1;
                    if (random < 2 && waveNumber < 3) {
                        monsters.add(new RegularMonster(xStart, yStart));
                    } else if (random < 4 && waveNumber < 4) {
                        monsters.add(new FastMonster(xStart, yStart));
                    } else if (random < 6 && waveNumber < 7) {
                        monsters.add(new FasterMonster(xStart, yStart));
                    } else if (random < 10 && waveNumber < 6) {
                        numTimes++;
                        monsters.add(new HardMonster(xStart, yStart));
                    } else if (random < 16 && waveNumber < 8) {
                        numTimes++;
                        monsters.add(new IntenseMonster(xStart, yStart));
                    } else {
                        if (waveNumber < 9) {
                            numTimes += 5;
                        } else {
                            numTimes++;
                        }
                        monsters.add(new UltimateMonster(xStart, yStart));
                    }
                }
            }
        }
    }


    private class ClickListener extends MouseAdapter {


        public Tower createTower(Point p, int tower) {
            Tower result = null;
            if (tower == Tower.BASIC_TOWER) {
                result = new BasicTower(p);
            } else if (tower == Tower.ADVANCED_TOWER) {
                result = new AdvancedTower(p);
            }
            if (!result.canPlaceOnMap(towers)
                    || !inputPanel.removeFromMoney(result.getCost())) {
                return null;
            } else {
                return result;
            }
        }


        public void mousePressed(MouseEvent e) {
            Point originalLoc = e.getPoint();
            Point loc = new Point((int) originalLoc.getX() - 12,
                    (int) originalLoc.getY() - 12);
            int type = inputPanel.getCurrentTowerType();
            Tower newTower = createTower(loc, type);
            if (newTower != null) {
                towers.add(newTower);
            }
            repaint();
        }
    }
}