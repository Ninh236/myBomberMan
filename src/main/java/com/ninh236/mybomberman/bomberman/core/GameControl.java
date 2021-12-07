package com.ninh236.mybomberman.bomberman.core;

import com.ninh236.mybomberman.Bomberman;
import com.ninh236.mybomberman.entities.blocks.Brick;
import com.ninh236.mybomberman.entities.enemies.Enemy;
import com.ninh236.mybomberman.gui.GameScreen;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameControl {

    private final GameScreen gameScreen;
    private CopyOnWriteArrayList<Enemy> enemies;
    private CopyOnWriteArrayList<Brick> bricks;
    private Bomberman[] players;

    public GameControl(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void update() {

    }

    public void draw(Graphics2D graphics2D) {

    }

    public boolean hasCollision(Rectangle rectangle) {
        return false;
    }

}
