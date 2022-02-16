package Billiards_ex1_4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    private final ArrayList<Ball> balls = new ArrayList<>();
    private Hole holes;
    private final JLabel jLabelInPockets;

    public void add(Ball b){
        this.balls.add(b);
    }
    public void addPockets(Hole p){
        this.holes = p;
    }

    public BallCanvas (JLabel label){
        this.jLabelInPockets = label;
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        holes.draw(g2);
        for (Ball b : balls) {
            b.draw(g2);
        }
        int ballsInPocket = balls.stream().filter(x -> x.getColor() == Color.BLACK).toArray().length;
        jLabelInPockets.setText(Integer.toString(ballsInPocket));
    }
}
