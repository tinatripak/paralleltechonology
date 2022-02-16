package Billiards_ex1_4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 950;
    public static final int HEIGHT = 350;
    int[] xPocketsArray = new int[] {2, WIDTH/2, WIDTH-44, 2, WIDTH/2, WIDTH-44};
    int[] yPocketsArray = new int[] {2, 2, 2, HEIGHT-104, HEIGHT-104, HEIGHT-104};


    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Граємося кульками");

        JLabel jLabelFieldTitle = new JLabel("Синіх кульок:");
        JTextField textFieldBlueBallsNum = new JTextField(5);
        JLabel jLabelNumInPocketTitle = new JLabel("Кульок у 'лузі': ");
        JLabel jLabelNumInPocket = new JLabel("0");

        this.canvas = new BallCanvas(jLabelNumInPocket);
        System.out.println("Ім'я потоку - "
                + Thread.currentThread().getName());
        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.GRAY);


        Hole p = new Hole(canvas, Color.BLACK, xPocketsArray, yPocketsArray);
        canvas.addPockets(p);
        this.canvas.repaint();


        JButton buttonSimpleBall = new JButton("Сірі кульки");
        JButton buttonBlueBall = new JButton("Сині кульки");
        JButton buttonRedBall = new JButton("Червоні кульки");
        JButton buttonRedVSBlue = new JButton("Хто переможе");
        JButton buttonJoin = new JButton("Джойнити");
        JButton buttonStop = new JButton("Зупинити");



        // створюємо кульки
        buttonSimpleBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x, y;
                if(Math.random()<0.5){
                    x = new Random().nextInt(canvas.getWidth());
                    y = 0;
                }else{
                    x = 0;
                    y = new Random().nextInt(canvas.getHeight());
                }
                Ball b = new Ball(canvas, Color.darkGray, x, y);
                canvas.add(b);

                BallThread thread = new BallThread(b, xPocketsArray, yPocketsArray);
                thread.start();
                System.out.println("Назва потоку = " +
                        thread.getName());
            }
        });

        // створюємо сині з пріоритетом 1
        buttonBlueBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x, y;
                if(Math.random()<0.5)
                {
                    x = new Random().nextInt(canvas.getWidth());
                    y = 0;
                }
                else {
                    x = 0;
                    y = new Random().nextInt(canvas.getHeight());
                }
                Ball b = new Ball(canvas, Color.blue, x, y);
                canvas.add(b);
                BallThread thread = new BallThread(b, xPocketsArray, yPocketsArray);
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.start();
                System.out.println("Назва потоку = " + thread.getName());
            }
        });

        // створюємо червоні з пріоритетом 5
        buttonRedBall.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int x, y;
                if(Math.random()<0.5)
                {
                    x = new Random().nextInt(canvas.getWidth());
                    y = 0;
                }
                else{
                    x = 0;
                    y = new Random().nextInt(canvas.getHeight());
                }
                Ball b = new Ball(canvas, Color.red, x, y);
                canvas.add(b);
                BallThread thread = new BallThread(b, xPocketsArray, yPocketsArray);
                thread.setPriority(Thread.NORM_PRIORITY);
                thread.start();
                System.out.println("Назва потоку = " + thread.getName());
            }
        });

        // створюємо червоні з пріоритетом 10 та створюємо сині з пріоритетом 1
        buttonRedVSBlue.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                if ((!textFieldBlueBallsNum.getText().equals("")) && (Integer.parseInt(textFieldBlueBallsNum.getText()) > 0)) {
                    for (int i = 0; i < Integer.parseInt(textFieldBlueBallsNum.getText()); i++) {
                        Ball blueBall = new Ball(canvas, Color.blue, canvas.getWidth() / 2, canvas.getHeight() / 2);
                        canvas.add(blueBall);
                        BallThread threadBlue = new BallThread(blueBall, xPocketsArray, yPocketsArray);
                        threadBlue.setPriority(Thread.MIN_PRIORITY);
                        threadBlue.start();
                        System.out.println("Назва потоку (сині) = " +
                                threadBlue.getName());
                    }
                } else {
                    for (int i = 0; i < 5; i++) {
                        Ball blueBall = new Ball(canvas, Color.blue, canvas.getWidth() / 2, canvas.getHeight() / 2);
                        canvas.add(blueBall);
                        BallThread threadBlue = new BallThread(blueBall, xPocketsArray, yPocketsArray);
                        threadBlue.setPriority(Thread.MIN_PRIORITY);
                        threadBlue.start();
                        System.out.println("Назва потоку (сині) = " +
                                threadBlue.getName());
                    }
                }
                Ball redBall = new Ball(canvas, Color.red, canvas.getWidth() / 2, canvas.getHeight() / 2);
                canvas.add(redBall);
                BallThread threadRed = new BallThread(redBall, xPocketsArray, yPocketsArray);
                threadRed.setPriority(Thread.MAX_PRIORITY);
                threadRed.start();


                System.out.println("Назва потоку (червоні) = " +
                        threadRed.getName());
            }
        });

        buttonJoin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                joinVisualization();
            }
        });

        buttonStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        buttonPanel.add(buttonSimpleBall);
        buttonPanel.add(buttonBlueBall);
        buttonPanel.add(buttonRedBall);
        buttonPanel.add(jLabelFieldTitle);
        buttonPanel.add(textFieldBlueBallsNum);
        buttonPanel.add(buttonRedVSBlue);
        buttonPanel.add(jLabelNumInPocketTitle);
        buttonPanel.add(jLabelNumInPocket);
        buttonPanel.add(buttonJoin);
        buttonPanel.add(buttonStop);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void joinVisualization(){
        Ball ballGreen = new Ball(canvas, Color.green, WIDTH / 2 - 100, HEIGHT / 2);
        Ball ballYellow = new Ball(canvas, Color.yellow, WIDTH / 2 + 100, HEIGHT / 2);
        canvas.add(ballGreen);
        canvas.add(ballYellow);

        BallThread threadGreen = new BallThread(ballGreen, xPocketsArray, yPocketsArray, 500);
        BallThread threadYellow = new BallThread(ballYellow, xPocketsArray, yPocketsArray, 500);
        threadGreen.start();
        try {
            threadGreen.Join(threadYellow);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadYellow.start();
    }
}