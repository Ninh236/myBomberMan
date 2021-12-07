package com.ninh236.mybomberman.engine.core.graphics;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.input.Keymap;

import java.awt.*;
import java.util.HashMap;
import java.util.function.Supplier;

public abstract class Sprite {

    protected final Image image;
    private final Point center;
    protected int speed;
    protected int xValue;
    protected int yValue;
    protected HashMap<Class< ? extends SpriteState>, AnimationWrapper> animations;
    protected SpriteState currentState;
    protected Keymap keymap;
    protected String id;
    private AnimationWrapper currentAnimationWrapper;

    protected Sprite(final Image image, final int xValue, final int yValue) {
        this.image = image;
        this.xValue = xValue;
        this.yValue = yValue;
        center = new Point();
    }

    public void update(final Screen screen, final long elapsedTime) {
        if (!(currentState instanceof NullState)) {
            if (image.isActive()) {
                image.update(currentAnimationWrapper.row, currentAnimationWrapper.animation.getCurrentFrame());
            }
            var supplier = currentState.handleInput(this, keymap);
            if (supplier != null) {
                currentState.onExit(this, screen);
                setCurrentState(supplier);
            }
            currentState.update(this, screen, elapsedTime);
        }
    }

    public final String getId() {
        return id;
    }

    public final SpriteState getCurrentState() {
        return currentState;
    }

    public final void setCurrentState(final Supplier<SpriteState> supplier) {
        currentState = supplier.get();
        currentAnimationWrapper = animations.get(currentState.getClass());
    }

    public void setLocation(int x, int y) {
        xValue = x;
        yValue = y;
    }

    public final int getX() {
        return xValue;
    }

    public final int getY() {
        return yValue;
    }

    public final int getWidth() {
        return image.getWidth();
    }

    public final int getHeight() {
        return image.getHeight();
    }

    protected final short getAxisHorizontal(int x) {
        return (short) (x / image.getWidth());
    }

    protected final short getAxisVertical(int y) {
        return (short) (y / image.getHeight());
    }

    public final void setAxisPosition(final int x, final int y) {
        setLocation(x * image.getWidth(), y * image.getHeight());
    }

    public final Point getCenter() {
        center.setLocation(getX() + image.getWidth() / 2, getY() + image.getHeight() / 2);
        return center;
    }

    public final void translate(final int x, final int y) {
        xValue += x;
        yValue += y;
    }

    public final boolean isActive() {
        return image.isActive();
    }

    public final void setActive(final boolean active) {
        image.setActiveState(active);
    }

    public final boolean updateAnimation(final long elapsedTime) {
        return currentAnimationWrapper.animation.update(elapsedTime);
    }

    public void draw(final Graphics2D graphics) {
        if (image.isActive() && !(currentState instanceof NullState)) {
            image.draw(graphics, xValue, yValue);
        }
    }

    public final Image getImage() {
        return image;
    }

    public final Keymap getKeymap() {
        return keymap;
    }

}
