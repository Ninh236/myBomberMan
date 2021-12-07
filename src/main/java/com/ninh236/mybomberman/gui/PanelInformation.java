package com.ninh236.mybomberman.gui;

import com.ninh236.mybomberman.fonts.Fonts;
import com.ninh236.mybomberman.media.tools.ImageUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PanelInformation {

    private static PanelInformation instance;
    private final int optionsNumber = 3;
    private int score;
    private int remainingTime = 200, remainingLives = 2;
    private Image image;
    private Timer timer;
    private Dimension Size;
    private Color background;
    private Font font;
    private Point[] position;
    private boolean isChange;

    private PanelInformation() {
        super();
        init();
    }

    public static PanelInformation getInstance() {
        return instance == null ? (instance = new PanelInformation()) : instance;
    }

    private void init() {
        Size = new Dimension(640, 60);
        font = Fonts.getInstance().getJoystixMonospace(24);
        background = new Color(188, 188,188);
        position = new Point[]{new Point(20, 37), new Point(360, 37), new Point(480, 37)};
        image = ImageUtilities.createCompatibleVolatileImage(640, 60, Transparency.OPAQUE);
        var graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(background);
        graphics2D.fillRect(0, 0, 640, 60);
        graphics2D.dispose();
        timer = new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                decreaseCounter();
            }
        });
    }

    private void drawString(final Graphics graphics2D, final String string, final Point point) {
        graphics2D.setColor(background);
        graphics2D.fillRect(point.x, point.y - 25, 300, 30);
        graphics2D.setColor(Color.BLACK);
        graphics2D.setFont(font);
        graphics2D.drawString(string, point.x + 2, point.y + 2);
        graphics2D.setFont(font);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, point.x, point.y);
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int core) {
        score = core;
    }

    public void increaseScore(int addScore) {
        score += addScore;
    }

    public int getRemainingLives() {
        return remainingLives;
    }

    public void setRemainingLives(int remainingLives) {
        this.remainingLives = remainingLives;
    }

    public void decreaseRemainingLives() {
        remainingLives = remainingLives < 0 ? -1 : --this.remainingLives;
    }

    public int getHeight() {
        return Size.height;
    }

    private void drawStrings(Graphics graphics2D) {
        for (var i = 0; i < this.optionsNumber; i++) {
            drawString(graphics2D, getString(i), position[i]);
        }
    }

    private String getString(int i) {
        if (i == 0)
            return "TIME " + remainingTime();
        if (i == 1)
            return score();
        return "LEFT " + remainingLives;
    }

    private String score() {
        return score + "";
    }

    private String remainingTime() {
        return remainingTime + "";
    }

    public void startCountdown() {
        remainingTime = 200;
        isChange = true;
        timer.start();
    }

    public void stopCountdown() {
        timer.stop();
        isChange = false;
    }

    private void decreaseCounter() {
        if (remainingTime == 0) {
            stopCountdown();
            return;
        }
        remainingTime--;
        isChange = true;
    }

    public void setSize(Dimension dimension) {
        var y = (int) Math.round(dimension.height / 14.0);
        Size = new Dimension(dimension.width, y + y / 2);
        System.out.println(dimension + " " + Size + " " + y);
    }

    public void draw(final Graphics2D graphics2D) {
        if (isChange) {
            var newGraphics2D = (Graphics2D) image.getGraphics();
            drawStrings(newGraphics2D);
            graphics2D.dispose();
            isChange = false;
        }
        graphics2D.drawImage(image, 0, 15, null);
    }

}
