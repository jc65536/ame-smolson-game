package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import main.*;

public class TitleScreen extends Screen {

    private JLabel title = new JLabel("Smolson Valley");
    private JButton testButton = new JButton("testButton");

    public TitleScreen() {
        add(title);
        add(testButton);
        setBackground(new Color(0xffcccc));

        testButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Main.switchScreen(Main.GAME_SCREEN);
            }

        });

    }

}
