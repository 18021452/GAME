import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;





public class InputPanel extends JPanel {

    private int score = 10;
    private int money = 200;
    private boolean stillPlaying, inFastForward;

    private int waveNumber;

    private JLabel scoreLabel, moneyLabel, waveLabel;
    private int currentTowerType;
    private Timer waveTimer;


    public InputPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, GamePanel.HEIGHT));
        waveNumber = 0;

        add(Box.createRigidArea(new Dimension(60, 100)));
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        moneyLabel = new JLabel("Money: " + money);
        moneyLabel.setFont(new Font("Serif", Font.PLAIN, 25));
        waveLabel = new JLabel("Wave Number: " + waveNumber);
        waveLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        add(waveLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(scoreLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));
        add(moneyLabel);
        add(Box.createRigidArea(new Dimension(0, 50)));

        ButtonGroup radios = new ButtonGroup();
        JRadioButton basic = new JRadioButton("Basic Tower: 75");
        basic.addActionListener(new RadioListener(Tower.BASIC_TOWER));
        basic.setSelected(true);

        JRadioButton advanced = new JRadioButton("Advanced Tower: 150");
        advanced.addActionListener(new RadioListener(Tower.ADVANCED_TOWER));
        basic.setFont(new Font("Serif", Font.PLAIN, 15));
        advanced.setFont(new Font("Serif", Font.PLAIN, 15));

        radios.add(basic);
        radios.add(advanced);

        add(basic);
        add(advanced);
        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton wave = new JButton("Start wave");
        wave.addActionListener(new WaveButtonListener());
        add(wave);
        add(Box.createRigidArea(new Dimension(0, 50)));

        JButton fastForward = new JButton("Fast Forward");
        fastForward.addActionListener(new FastForwardButtonListener());
        add(fastForward);

        currentTowerType = Tower.BASIC_TOWER;
        stillPlaying = true;
        inFastForward = false;
        waveTimer = null;
    }


    public void assignWaveTimer(Timer waveTimer) {
        this.waveTimer = waveTimer;
    }


    public int getCurrentTowerType() {
        return currentTowerType;
    }


    public int getWaveNumber() {
        return waveNumber;
    }


    public int getScore() {
        return score;
    }


    public void updateStats(int[] stats) {
        takeFromScore(stats[0]);
        addToMoney(stats[1]);
    }


    public boolean isStillPlaying() {
        return stillPlaying;
    }


    public boolean isInFastForward() {
        return inFastForward;
    }


    public boolean removeFromMoney(int spent) {
        if (money - spent < 0) {
            return false;
        } else {
            money -= spent;
            moneyLabel.setText("Money: " + money);
            return true;
        }
    }

    private void takeFromScore(int scoreLoss) {
        score -= scoreLoss;
        if (score <= 0) {
            score = 0;
            stillPlaying = false;
        }
        scoreLabel.setText("Score: " + score);
    }

    private void addToMoney(int moneyAddition) {
        money += moneyAddition;
        moneyLabel.setText("Money: " + money);
    }

    private void endGame() {
        stillPlaying = false;
    }

    private class RadioListener implements ActionListener {

        private int tower;

        public RadioListener(int tower) {
            this.tower = tower;
        }

        public void actionPerformed(ActionEvent e) {
            if (!(currentTowerType == tower) || !(currentTowerType == tower)) {
                currentTowerType = tower;
            }
        }
    }

    private class WaveButtonListener implements ActionListener {


        public void actionPerformed(ActionEvent e) {
            if (waveNumber == 10) {
                waveNumber++;
                endGame();
            }
            if (!waveTimer.isRunning() && waveNumber < 10) {
                waveNumber++;
                waveLabel.setText("Wave Number: " + waveNumber);
            }
        }
    }

    private class FastForwardButtonListener implements ActionListener {


        public void actionPerformed(ActionEvent e) {
            inFastForward = !inFastForward;
        }
    }
}