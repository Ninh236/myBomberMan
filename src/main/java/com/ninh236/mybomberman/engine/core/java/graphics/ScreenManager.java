package com.ninh236.mybomberman.engine.core.java.graphics;

import javafx.scene.image.Image;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

import static com.ninh236.mybomberman.media.Images.ICON;

public class ScreenManager {

    private final GraphicsDevice device;
    private JFrame jFrame;

    public ScreenManager() {
        var environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        device = environment.getDefaultScreenDevice();
    }

    public void setScreen(final DisplayMode displayMode) {
        if (jFrame == null) {
            jFrame = new JFrame();
        }
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setIgnoreRepaint(true);
        jFrame.setResizable(false);
        jFrame.setLocation(455, 155);
        jFrame.setIconImage(ICON);
        if (displayMode != null) {
            if (device.isDisplayChangeSupported()) {
                try {
                    device.setDisplayMode(displayMode);
                } catch (IllegalArgumentException exception) {
                    exception.printStackTrace();
                }
            }
            jFrame.setSize(displayMode.getWidth(), displayMode.getHeight());
        }
        jFrame.setVisible(true);
        try {
            EventQueue.invokeAndWait(
                    () -> jFrame.createBufferStrategy(2)
            );
        } catch (InterruptedException | InvocationTargetException exception) {
            exception.printStackTrace();
        }
    }

    public Graphics2D getGraphics() {
        final var window = device.getFullScreenWindow();
        final var strategy = window != null ? window.getBufferStrategy() : jFrame.getBufferStrategy();
        return (Graphics2D) strategy.getDrawGraphics();
    }

    public void update() {
        final var window = device.getFullScreenWindow();
        final var strategy= window != null ? window.getBufferStrategy() : jFrame.getBufferStrategy();
        if (!strategy.contentsLost()) {
            strategy.show();
        }
        Toolkit.getDefaultToolkit().sync();
    }

    public Window getScreenWindow() {
        final var window = device.getFullScreenWindow();
        return window != null ? window : jFrame;
    }

    public int getWidth() {
        final var window = device.getFullScreenWindow();
        if (window != null) {
            return window.getWidth();
        } else {
            return 0;
        }
    }

    public int getHeight() {
        final var window = device.getFullScreenWindow();
        if (window != null) {
            return window.getHeight();
        } else {
            return 0;
        }
    }

    public void restoreScreen() {
        final var window = device.getFullScreenWindow();
        if (window != null) {
            window.dispose();
        }
        device.setFullScreenWindow(null);
    }

    public void showFPS(int fps) {
        final var window = device.getFullScreenWindow();
        if (window == null) {
            jFrame.setTitle("(FPS: " + fps + ")");
        }
    }

}
