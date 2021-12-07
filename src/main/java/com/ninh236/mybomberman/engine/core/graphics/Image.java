package com.ninh236.mybomberman.engine.core.graphics;

import java.awt.*;

public final class Image {

    private java.awt.Image image;
    private final int rows;
    private final int columns;
    private final int width;
    private final int height;
    private final Rectangle rectangle;
    private int currentState;
    private boolean activeState;
    private Color color;
    private float scale;
    private int scaledWidth;
    private int scaledHeight;

    public Image(final java.awt.Image image, int rows, int columns, final float scale) {
        this.image = image;
        this. rows = rows;
        this.columns = columns;
        activeState = true;
        width = this.image.getWidth(null) /columns;
        height = this.image.getHeight(null) / rows;
        rectangle = new Rectangle(0, 0, width, height);
        setScale(scale);
    }

    public Image(final java.awt.Image image, final int rows, final int columns, final float scale, final int initialState) {
        this(image, rows, columns, scale);
        currentState = initialState;
    }


    public void update(int currentFrame) {
        if (activeState) {
//            if (currentFrame < 0 || currentFrame > columns) {
//                System.err.println("Invalid Frame: " + currentFrame + "!!");
//            }
            rectangle.setBounds(currentFrame * width, currentState * height, width, height);
        }
    }

    public void update(int state, int currentFrame) {
        currentState = state;
        update(currentFrame);
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public int getWidth() {
        return scaledWidth;
    }

    public int getHeight() {
        return scaledHeight;
    }

    public boolean isActive() {
        return activeState;
    }

    public java.awt.Image getImage() {
        return image;
    }

    public void setScale(float scale) {
        this.scale = scale;
        scaledWidth = (int) (width * this.scale);
        scaledHeight = (int) (height * this.scale);
    }

    public void setActiveState(boolean activeState) {
        this.activeState = activeState;
    }

    public void draw(final Graphics2D graphics, final int x, final int y) {
        if (activeState) {
            graphics.drawImage(image, x, y, x + scaledWidth, y + scaledHeight, rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, color, null);
        }
    }
}
