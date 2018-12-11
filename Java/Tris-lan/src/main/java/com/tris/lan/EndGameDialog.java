package com.tris.lan;

import javax.swing.JOptionPane;
import javax.swing.JFrame;

import java.awt.event.ActionListener;
import java.util.EventListener;

import javax.swing.Icon;

public class EndGameDialog {
    JFrame frame;
    int xPoints;
    int oPoints;
    Icon xIcon;
    Icon oIcon;

    public Runnable onContinue, onEndGame, onSaveAndClose;

    public EndGameDialog(JFrame frame, int xPoints, int oPoints, Icon xIcon, Icon oIcon) {
        this.frame = frame;
        this.xPoints = xPoints;
        this.oPoints = oPoints;
        this.xIcon = xIcon;
        this.oIcon = oIcon;
    }
    
    public void showEndGameDialog() {
        Object[] options = {"Continue",
                    "End game",
                    "Save score and close"};
    
        int result = JOptionPane.showOptionDialog(frame,
            "Punti X: " + xPoints + "\nPunti O: " + oPoints,
            "The winner is: " + (xPoints > oPoints ? "X" : "O"),
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            (xPoints > oPoints ? xIcon : oIcon),
            options,
            options[2]);

        if(result == 0) onContinue.run();
        else if(result == 1) onEndGame.run();
        else onSaveAndClose.run();
    }
}