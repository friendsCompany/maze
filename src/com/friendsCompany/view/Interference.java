package com.friendsCompany.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class Interference {
    private int sizeOfRect = 1;
    private int speedOfInter = 10;

    public Interference(int sizeOfRect){
        this.sizeOfRect = sizeOfRect;
        initInterference();
    }

    public void initInterference(){
        new IntFrame();
    }

    public class IntFrame extends JFrame {
        public IntFrame(){
            setTitle("GameFrame");
            setSize(300, 300);
            setLocation(100, 100);
            setResizable(false);
            add(new Panel());
            setVisible(true);
        }
    }

    public class Panel extends JPanel implements ActionListener{
        Graphics2D g2;
        Timer timer = new Timer(speedOfInter,this);

        public Panel(){
            timer.start();
        }

        public void paint(Graphics g){
            g2 = (Graphics2D) g;

            for (int i = 0; i < 300/sizeOfRect; i++) {
                for (int j = 0; j < 300; j++) {
                    g2.setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));
                    g2.fillRect(j * sizeOfRect, i * sizeOfRect, sizeOfRect, sizeOfRect);
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
    }
}
