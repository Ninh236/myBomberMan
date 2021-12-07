package com.ninh236.mybomberman.engine.core.map;

import com.ninh236.mybomberman.engine.core.graphics.Sprite;

public class SpritePosition {

    private final Sprite sprite;
    private Position currentPosition;
    private Position previousPosition;

    public SpritePosition(Sprite sprite) {
        var center = sprite.getCenter();
        this.sprite = sprite;
        currentPosition = new Position(center.y / sprite.getHeight(), center.x / sprite.getWidth());
        previousPosition = (Position) currentPosition.clone();
    }

    public boolean update() {
        var center = sprite.getCenter();
        var newCurrentPosition = new Position(center.y / sprite.getHeight(), center.x / sprite.getWidth());
        if (currentPosition.equals(newCurrentPosition)) {
            return false;
        }
        previousPosition = currentPosition;
        currentPosition = newCurrentPosition;
        return true;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public Position getPreviousPosition() {
        return previousPosition;
    }
}
