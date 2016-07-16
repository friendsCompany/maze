package com.friendsCompany.view;

import com.friendsCompany.models.PlayerSphere;
import com.sun.j3d.utils.applet.MainFrame;

import javax.swing.*;
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
        setSize(387, 155);
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
    JButton setSize = new JButton("Set Size");
    JButton player = new JButton("Player");
    JTextField textField = new JTextField();
    private int sizeOfRect = 1;

    public MenuPanel(){
        setBackground(Color.DARK_GRAY);

        start.setBounds(0, 0, 190, 55);
        add(start);

        exit.setBounds(191, 0, 190, 55);
        add(exit);

        textField.setBounds(0, 56, 190, 20);
        add(textField);

        setSize.setBounds(191, 56, 190, 20);
        add(setSize);

        player.setBounds(0, 77, 381, 50);
        add(player);

        start.addActionListener(new StartAction());
        setSize.addActionListener(new SetSizeAction());
        exit.addActionListener(new ExitAction());
        player.addActionListener(new PlayerAction());

    }

    private class StartAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            new Interference(sizeOfRect);
        }
    }

    private class ExitAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            System.exit(0);
        }
    }

    private class PlayerAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            PlayerSphere.initPlayer();
        }
    }

    private class SetSizeAction implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            try {
                sizeOfRect = Integer.parseInt(textField.getText());
            } catch (Exception e) {
                System.err.println("Error!!! Try again!");
                e.printStackTrace();
            }
        }
    }
}
