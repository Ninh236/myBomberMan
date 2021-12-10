package com.ninh236.mybomberman.gui;

import com.ninh236.mybomberman.engine.core.game.Game;
import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.input.Keyboard;
import com.ninh236.mybomberman.fonts.Fonts;
import com.ninh236.mybomberman.gui.BombermanGame.Scene;
import com.ninh236.mybomberman.media.Sound;
import com.ninh236.mybomberman.media.Sounds;
import com.ninh236.mybomberman.media.tools.ImageUtilities;

import java.awt.*;

public class MessageScreen extends Screen {
    private static MessageScreen instance;
    private short level = 3, MAX_LEVEL = 50;
    private Image image;
    private Keyboard keyboard;
    private Sound sound;
    private Font font;
    private Color color;

    private MessageScreen() {
        init();
    }

    public static MessageScreen getInstance() {
        return instance == null ? (instance = new MessageScreen()) : instance;
    }

    private void init() {
        image = ImageUtilities.createCompatibleVolatileImage(640, 578, Transparency.OPAQUE);
        color = new Color(127, 127, 127);
        font = Fonts.getInstance().getJoystixMonospace(30);
        keyboard = Keyboard.getInstance();
    }

    public void increaseLevel() {
        if (level != MAX_LEVEL) {
            level++;
        }
    }

    public boolean endGame() {
        return level == MAX_LEVEL;
    }

    public short getLevel() {
        return level;
    }

    public void setLevel(short level) {
        this.level = level > 50 ? 50 : level < 1 ? 1 : level;
    }

    public short getMAX_LEVEL() {
        return MAX_LEVEL;
    }

    public  void setMAX_LEVEL(short MAX_LEVEL) {
        this.MAX_LEVEL = MAX_LEVEL > 50 ? 50 : MAX_LEVEL < 1 ? 1 : MAX_LEVEL;
    }

    public void startStageScreen() {
        drawString("STAGE  " + this.level);
        sound = Sounds.getInstance().play(Sounds.LEVEL_START);
    }

    public void startGameOverScreen() {
        drawString("GAME OVER");
        sound = Sounds.getInstance().play(Sounds.GAME_OVER);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.drawImage(image, 0, 0, null);
    }

    public void drawString(String string) {
        var graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setBackground(Color.BLACK);
        graphics2D.fillRect(0, 0, 640, 578);
        graphics2D.setColor(color);
        graphics2D.setFont(font);
        graphics2D.drawString(string, 199, 298);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, 197, 296);
        graphics2D.dispose();
    }

    @Override
    public void restart() {

    }

    @Override
    public void update(final long elapsedTime, final Game game) {
//      //Check sound.
//        if (sound != null && sound.isPlaying()) {
//            System.out.println("Sound: " + sound.getFramePosition() + " " + sound.getFrameLength());
//        }
        final var scene = (BombermanGame.Scene) game.getSelectedScene();
        switch (scene) {
            case STAGE:
                if (sound == null || !sound.isPlaying()) {
                    game.setScreen(Scene.GAME);
                }
                break;
            case GAME_OVER:
                if (keyboard.isKeyPressed()) {
                    game.setScreen(Scene.MENU);
                    Sounds.getInstance().stop(Sounds.GAME_OVER);
                }
                break;
        }
    }

    @Override
    public void setSize(Dimension dimension) {

    }
}
