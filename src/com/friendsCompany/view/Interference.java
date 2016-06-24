package com.friendsCompany.view;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Interference {

    public void initInterference(){
        new Frame();
    }

    public class Frame extends JFrame {
        public Frame(){
            setTitle("GameFrame");
            setSize(300, 300);
            setLocation(100, 100);
            setResizable(false);
            add(new Panel());
            setVisible(true);
        }
    }

    public class Panel extends JPanel{
        Graphics2D g2;

        public Panel(){

        }

        public void paint(Graphics g){
            g2 = (Graphics2D) g;

            for (int i = 0; i < 300; i++) {
                for (int j = 0; j < 300; j++) {
                    g2.setColor(new Color(new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)));

                    g2.fillRect(j * 1, i * 1, 1, 1);
                }
            }
        }
    }
}
