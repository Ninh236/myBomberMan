package com.ninh236.mybomberman.engine.core;

import com.ninh236.mybomberman.engine.core.graphics.Sprite;

import java.awt.*;

public class Viewport {

    private final Rectangle position;
    private final Dimension levelSize;
    private final int quarter;
    private final int third;

    public Viewport(final Rectangle position, final Dimension levelSize) {
        this.position = position;
        this.levelSize = levelSize;
        quarter = levelSize.width / 4;
        third = 3 * quarter;
    }

    public void update(final Point instructPoint) {
        if (instructPoint.x > quarter && instructPoint.x < third) {
            position.x = instructPoint.x - quarter;
        }
        if (instructPoint.x > third && instructPoint.x < quarter) {
            position.x = quarter - instructPoint.x;
        }
        if (position.x < 0) {
            position.x = 0;
        }
        if (position.x + position.width > levelSize.width) {
            position.x = levelSize.width - position.width;
        }
    }

    private boolean contains(int xValue, int yValue, int width, int height) {
        return position.intersects(xValue, yValue, width, height);
    }

    public Rectangle getPosition() {
        return position;
    }

    public void restart() {
        position.x = 0;
        position.y = 0;
    }

    public boolean contains(final Sprite sprite) {
        return contains(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());
    }

}
