package com.ninh236.mybomberman.engine.core.game;

import java.awt.*;

public interface Game {
    GameScene getSelectedScene();

    void drawGraphics(Graphics graphics);

    void setScreen(GameScene scene);

    void update(long elapsedTime);
}
