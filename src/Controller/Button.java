package Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class Button {

    public JButton createButton(String text, ActionListener action, int x, int y, int width, int height) {
        JButton button = new JButton(text);

        button.setBackground(new Color(0, 153, 255)); 
        button.setForeground(Color.WHITE);          
        button.setFocusPainted(false);           
        button.setFont(new Font("Montserrat", Font.BOLD, 16));
        button.setBounds(x, y, width, height);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 102, 204)); 
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 153, 255));
            }
        });

        button.addActionListener(action);

        return button;
    }
}
