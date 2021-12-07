package com.ninh236.mybomberman.entities;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.input.KeymapController;
import com.ninh236.mybomberman.engine.core.map.Map;
import com.ninh236.mybomberman.entities.blocks.Brick;
import com.ninh236.mybomberman.entities.blocks.Wall;
import com.ninh236.mybomberman.entities.bomb.Bomb;
import com.ninh236.mybomberman.entities.enemies.ai.AI;
import com.ninh236.mybomberman.gui.GameScreen;

import static com.ninh236.mybomberman.entities.Entity.Direction.HORIZONTAL;
import static com.ninh236.mybomberman.entities.Entity.Direction.VERTICAL;

public abstract class Entity extends Sprite {

    public static final int SPEED_SLOWEST = 1, SPEED_SLOW = 2, SPEED_MID = 4, SPEED_FAST = 5, SMART_LOW = 1, SMART_MID = 2, SMART_HIGH = 3, SMART_IMPOSSIBLE = 4;
    protected final int varX = 3;
    protected final int varY = 3;
    protected int smart;
    protected AI ai;
    protected KeymapController keyController;
    protected boolean insideBomb;
    protected boolean WALLPASS;
    protected boolean BOMBPASS;

    protected Entity (final Image image, final int x, final int y) {
        super(image, x, y);
    }

    @Override
    public void update(final Screen screen, final long elapsedTime) {
        super.update(screen, elapsedTime);
    }

    public final void moveLeft(final GameScreen gameScreen) {
        speed = -Math.abs(speed);
        updateX(gameScreen);
    }

    public final void moveRight(final GameScreen gameScreen) {
        speed = Math.abs(speed);
        updateX(gameScreen);
    }

    public final void moveUp(final GameScreen gameScreen) {
        speed = -Math.abs(speed);
        updateY(gameScreen);
    }

    public final void moveDown(final GameScreen gameScreen) {
        speed = Math.abs(speed);
        updateY(gameScreen);
    }

    private void updateX(final GameScreen gameScreen) {
        if (!centralCollision(Bomb.class)) {
            insideBomb = false;
        }
        var adjustment = getAdjustmentX(gameScreen.getMap());
        if (adjustment != 0) {
            translate(adjustment, 0);
        }
    }

    private void updateY(final GameScreen gameScreen) {
        if (!centralCollision(Bomb.class)) {
            insideBomb = false;
        }
        var adjustment = getAdjustmentY(gameScreen.getMap());
        if (adjustment != 0) {
            translate(0, adjustment);
        }
    }

    private boolean collision(final Map map, final Direction direction, final int val, final Class<?>... classes) {
        for (final var _class : classes) {
            if (direction == VERTICAL && (map.contains(val, getAxisHorizontal(getX() + 2 * varX), _class) || map.contains(val, getAxisHorizontal(getX() + image.getWidth() - 2 * varX), _class))
                    || direction == HORIZONTAL && (map.contains(getAxisVertical(getY() + 2 * varY), val, _class) || map.contains(getAxisVertical(getY() + image.getHeight() - 2 * varY), val, _class))) {
                return true;
            }
        }
        return false;
    }

    protected final boolean collisionX(final Map map, final int x, final Class<?>... classes) {
        return collision(map, HORIZONTAL, x, classes);
    }

    protected final boolean collisionY(final Map map, final int y, final Class<?>... classes) {
        return collision(map, VERTICAL, y, classes);
    }

    protected final boolean centralCollision(final Class<?>... classes) {
        return Map.getInstance().contains(this, classes);
    }

    private int getAdjustmentX(final Map map) {
        var adjustment = 0;
        int position = speed < 0 ? getAxisHorizontal(getX() + speed) : getAxisHorizontal(getX() + image.getWidth() + speed);
        if (collisionX(map, position, Wall.class)
                || !WALLPASS && collisionX(map, position, Brick.class)
                || !BOMBPASS && !insideBomb && collisionX(map, position, Bomb.class)) {
            adjustment = speed < 0
                    ? position * image.getWidth() + image.getWidth() - (getX() + speed)
                    : position * image.getWidth() - (image.getWidth() + getX() + Math.abs(speed) + 1);
        }
        return speed + adjustment;
    }

    private int getAdjustmentY(final Map map) {
        var adjustment = 0;
        int position = speed < 0 ? getAxisVertical(getY() + speed) : getAxisVertical(getY() + image.getHeight() + speed);
        if (collisionY(map, position, Wall.class)
                || !WALLPASS && collisionY(map, position, Brick.class)
                || !BOMBPASS && !insideBomb && collisionY(map, position, Bomb.class)) {
            adjustment = speed < 0
                    ? position * image.getHeight() + image.getHeight() - (getY() + speed)
                    : position * image.getHeight() - (image.getHeight() + getY() + Math.abs(speed) + 1);
        }
        return speed + adjustment;
    }

    public void startAI() {
        ai = new AI(this);
        ai.start();
    }

    public void stopAI() {
        if (ai == null) {
            return;
        }
        ai.stop();
        ai = null;
    }

    public final AI getAI() {
        return ai;
    }

    public void setWALLPASS(boolean WALLPASS) {
        this.WALLPASS = WALLPASS;
    }

    public void setBOMBPASS(boolean BOMBPASS) {
        this.BOMBPASS = BOMBPASS;
    }

    public int getSmart() {
        return smart;
    }

    public enum Direction {
        HORIZONTAL,
        VERTICAL
    }

}
