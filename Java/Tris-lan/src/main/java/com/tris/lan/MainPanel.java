package com.tris.lan;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Image;

public class MainPanel extends JPanel {

    public MainPanel() {
        super();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawLine(200, 0, 200, 625);
        g.drawLine(400, 0, 400, 625);
        g.drawLine(0, 200, 625, 200);
        g.drawLine(0, 400, 625, 400);

        g.dispose();
    }

    public void addImage(Image image, int x, int y, int width, int height) {
        Graphics g = getGraphics();

        g.drawImage(image, x, y, width, height, null);

        g.dispose();
    }

    public void clearScreen() {
        repaint();
        
        Graphics g = getGraphics();

        g.drawLine(200, 0, 200, 625);
        g.drawLine(400, 0, 400, 625);
        g.drawLine(0, 200, 625, 200);
        g.drawLine(0, 400, 625, 400);

        g.dispose();
    }
}