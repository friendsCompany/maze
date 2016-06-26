package com.friendsCompany.view;

import javax.swing.*;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameMenu {
    public void initMenu(){
        new MenuFrame();
    }
}

class MenuFrame extends JFrame{
    public MenuFrame(){
        setTitle("Menu");
        setLocation(500, 300);
        setSize(387, 83);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MenuPanel menuPanel = new MenuPanel();
        menuPanel.setLayout(null);
        menuPanel.setFocusable(true);
        add(menuPanel);
        setVisible(true);
    }
}

class MenuPanel extends JPanel{
    JButton start = new JButton("Start");
    JButton exit = new JButton("Exit");

    public MenuPanel(){
        setBackground(Color.DARK_GRAY);
        start.setBounds(0, 0, 190, 55);
        add(start);
        exit.setBounds(191, 0, 190, 55);
        add(exit);
        start.addActionListener(new StartAction());
    }

    private class StartAction implements ActionListener {//реализуем интерфейс отвечающий за события


        public void actionPerformed(ActionEvent event) {//реакция на нажатие кнопки-смена цвета панели
            new Interference().initInterference();
        }

    }


}
