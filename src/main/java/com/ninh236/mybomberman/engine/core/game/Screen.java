package com.ninh236.mybomberman.engine.core.game;

import java.awt.*;

public abstract class Screen {

    public abstract void draw(final java.awt.Graphics2D graphics2D);

    public abstract void update(final long elapsedTime, Game game);

    public abstract void restart();

    public abstract void setSize(final Dimension dimension);
}
