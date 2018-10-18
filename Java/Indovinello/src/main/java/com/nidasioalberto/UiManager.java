package com.nidasioalberto;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.Color;
import java.lang.Math;

class UiManager extends JFrame {
    int casuale;
    JTextField input;
    JButton bottone;

    UiManager() {
        casuale = (new Random()).nextInt(100);

        JLabel indovinello = new JLabel("Indovinello !");
        input = new JTextField();
        bottone = new JButton("Controlla");

        JPanel panel = new JPanel();

        this.add(panel);

        GroupLayout layout = new GroupLayout(panel);
        layout.setVerticalGroup(layout.createSequentialGroup()
            .addComponent(indovinello)
            .addComponent(input)
            .addComponent(bottone));
        layout.setHorizontalGroup(layout.createParallelGroup()
            .addComponent(indovinello)
            .addComponent(input)
            .addComponent(bottone));

        panel.setLayout(layout);

        this.pack();
        this.setVisible(true);
        
        bottone.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try {
                    int numeroUtente = Integer.parseInt(input.getText());

                    if(casuale == numeroUtente) {
                        bottone.setText("Hai indovinato !");
                        bottone.setForeground(Color.green);
                    } else if(Math.abs(casuale - numeroUtente) < 10) {
                        bottone.setText("Ci sei vicino !");
                        bottone.setForeground(Color.orange);
                    } else {
                        bottone.setText("Riprova !");
                        bottone.setForeground(Color.red);
                    }
                } catch(Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}