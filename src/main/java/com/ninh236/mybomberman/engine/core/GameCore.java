package com.ninh236.mybomberman.engine.core;

import com.ninh236.mybomberman.engine.core.java.graphics.ScreenManager;

import java.awt.*;

public abstract class GameCore {

    private static final DisplayMode MODE = new DisplayMode(647, 585, 32, 0);
    protected ScreenManager screenManger;
    private boolean isRunning;
    private long loopTime;
    private long fpsTime;
    private int fps;
    private int sleepTime;

    public void run() {
        try {
            init();
            gameLoop();
        } finally {
            screenManger.restoreScreen();
            lazilyExit();
        }
    }

    public void init() {
        screenManger = new ScreenManager();
        screenManger.setScreen(MODE);
        Window window = screenManger.getScreenWindow();
        window.setBackground( Color.BLACK );
        loopTime = 1000000000 / 5000;
        sleepTime = 12;
        isRunning = true;
    }

    public void gameLoop() {
        var previousTime = System.nanoTime();
        while (isRunning) {
            var now = System.nanoTime();
            var elapsedTime = now - previousTime;
            showFPS(now);
            if (elapsedTime > loopTime) {
                previousTime = now;
                update(elapsedTime / 1000000);
                var graphics2D = screenManger.getGraphics();
                draw(graphics2D);
                graphics2D.dispose();
                screenManger.update();
                ++fps;
            }
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }

    public abstract void update(final long elapsedTime);

    public abstract void draw(final Graphics2D graphics2D);

    public void stop() {
        isRunning = false;
    }

    public void lazilyExit() {
        var thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException exception) {
                exception.printStackTrace();
            }
            System.exit(0);
        });
        thread.setDaemon(false);
        thread.start();
    }

    private void showFPS(long currentTime) {
        if (currentTime - fpsTime <= 1000000000) {
            return;
        }
        fpsTime = currentTime;
        screenManger.showFPS(fps);
        fps = 0;
    }

}
