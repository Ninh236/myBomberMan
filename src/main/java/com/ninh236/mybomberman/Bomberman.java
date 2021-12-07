package com.ninh236.mybomberman;

import com.ninh236.mybomberman.engine.core.GameCore;
import com.ninh236.mybomberman.engine.core.java.controllers.KeyboardListener;
import com.ninh236.mybomberman.gui.BombermanGame;

import java.awt.*;

public class Bomberman extends GameCore {

    private BombermanGame mainGame;

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        new Bomberman().run();
    }

    @Override
    public void init() {
        super.init();
        screenManger.getScreenWindow().addKeyListener(KeyboardListener.getInstance());
        mainGame = new BombermanGame();
    }

    @Override
    public void update(long elapsedTime) {
        mainGame.update(elapsedTime);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        mainGame.drawGraphics(graphics2D);
    }
}
