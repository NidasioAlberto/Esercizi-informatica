package com.tris.lan;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.net.InetAddress;
import java.net.UnknownHostException;

class ControlPanel extends JPanel {

    static final int BEFORE_GAME_MODE = 1;
    static final int SERVER_MODE = 2;
    static final int INGAME_MODE = 3;

    JButton hostButton;
    JButton connectButton;
    JTextField ipTextField;
    JLabel ipLabel;
    JButton stopGameButton;

    public ControlPanel(final ConnectionCommandsListener connectionCommandsListener) {
        hostButton = new JButton("Host");
        hostButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                connectionCommandsListener.onHostGame();
            }
        });

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                connectionCommandsListener.onConnectToServer(ipTextField.getText());
            }
        });

        ipTextField = new JTextField();
        ipLabel = new JLabel();
        stopGameButton = new JButton("Stop game");
        stopGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                connectionCommandsListener.onStopGame();
            }
        });

        this.setLayout(createPaneLayout(BEFORE_GAME_MODE));
    }

    GroupLayout createPaneLayout(int mode) {
        GroupLayout groupLayout = new GroupLayout(this);

        if(mode == SERVER_MODE) {
            try {
                ipLabel.setText(InetAddress.getLocalHost().getHostAddress());
            } catch(UnknownHostException e) {
                e.printStackTrace();
            }

            groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(stopGameButton)
                .addComponent(ipLabel));
            groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(stopGameButton)
                .addComponent(ipLabel));
        } else if(mode == INGAME_MODE) {
            groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(stopGameButton));
            groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(stopGameButton));
        } else{
            groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup()
                .addComponent(hostButton)
                .addComponent(connectButton)
                .addComponent(ipTextField));
            groupLayout.setVerticalGroup(groupLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(hostButton)
                .addComponent(connectButton)
                .addComponent(ipTextField));
        }

        return(groupLayout);
    }
}