package com.equwece.kotask.view;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class TagCircle extends JPanel {
    private int diam;
    private Color color;

    public TagCircle(int diam, Color color) {
        this.diam = diam;
        this.color = color;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(this.color);
        g.fillRect(0, 0, this.diam, this.diam);
    }
}
