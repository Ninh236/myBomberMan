package com.ninh236.mybomberman.gui;

import com.ninh236.mybomberman.engine.core.game.Game;
import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.input.PlayerKeyBoardController;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.engine.core.input.KeymapController;
import com.ninh236.mybomberman.fonts.Fonts;
import com.ninh236.mybomberman.gui.BombermanGame.Scene;
import com.ninh236.mybomberman.media.Images;
import com.ninh236.mybomberman.media.Sounds;
import com.ninh236.mybomberman.media.tools.ImageUtilities;

import java.awt.*;
import java.util.ArrayList;

public class MenuScreen extends Screen {

    public static final int START = 0;
    public static final int ULTIMATE = 1;
    public static final int QUIT = 2;
    private static MenuScreen instance;
    private final int optionsNumber = 3;
    private Image image;
    private Image selector;
    private ArrayList<Point> options;
    private int pointingOption = 0;
    private int selectedOption = -1;
    private Keymap keymap;
    private KeymapController keyController;
    private Font font;

    private MenuScreen() {
        init();
    }

    public static MenuScreen getInstance() {
        return instance == null ? (instance = new MenuScreen()) : instance;
    }

    private void init() {
        keyController = PlayerKeyBoardController.getInstance();
        image = ImageUtilities.createCompatibleVolatileImage(640, 560, Transparency.OPAQUE);
        font = Fonts.getInstance().getJoystixMonospace(24);
        selector = Images.SELECTOR;
        options = new ArrayList<>();
        keymap = new Keymap();
        addOptions();
    }

    private void start() {
        var graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(Color.BLACK);
        graphics2D.fillRect(0, 0, 640, 560);
        graphics2D.drawImage(Images.LOGO, 40, 20, 568, 347, null);
        drawStrings(graphics2D);
        drawSelector(graphics2D);
        graphics2D.dispose();
    }

    private void addOptions() {
        for (var i = 0; i < optionsNumber; i++) {
            options.add(getPoint(i));
        }
    }

    private Point getPoint(int optionPosition) {
        return optionPosition == 0 ? new Point(259, 415)
                : optionPosition == 1 ? new Point(259, 455)
                : new Point(259, 495);
    }

    private void drawStrings(Graphics2D graphics2D) {
        for (var i = 0; i < optionsNumber; i++) {
            drawString(graphics2D, getString(i), options.get(i));
        }
    }

    private void drawString(Graphics2D graphics2D, String string, Point point) {
        graphics2D.setColor(new Color(127, 127, 127));
        graphics2D.setFont(font);
        graphics2D.drawString(string, point.x + 2, point.y + 2);
        graphics2D.setColor(Color.WHITE);
        graphics2D.drawString(string, point.x, point.y);
    }

    private String getString(int i) {
        if (i == 0) return "START";
        if (i == 1) return "Ultimate Mode";
        return "QUIT";
    }

    private void drawSelector(Graphics2D g2d) {
        var point = options.get(pointingOption);
        g2d.drawImage(selector, point.x - 55, point.y - 17, 20, 20, null);
    }

    public void setSelectedOption() {
        selectedOption = pointingOption;
    }

    @Override
    public void update(final long elapsedTime, final Game game) {
        keyController.update(keymap);
        if (keymap.isPress(Keymap.Buttons.SELECT)) {
            nextOption();
        try {
            Thread.sleep(200);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        } else {
            if (keymap.isPress(Keymap.Buttons.START)) {
                setSelectedOption();
                Sounds.getInstance().stop(Sounds.TITLE_SCREEN);
                switch (selectedOption) {
                    case START:
                        game.setScreen(Scene.STAGE);
                        break;
                    case ULTIMATE:
                        game.setScreen(Scene.ULTIMATE);
                        break;
                    case QUIT:
                        game.setScreen(Scene.QUIT);
                        break;

                }
            }
        }
    }

    public void nextOption() {
        var graphics2D = (Graphics2D) image.getGraphics();
        graphics2D.setColor(Color.BLACK);
        var point = options.get(pointingOption);
        graphics2D.fillRect(point.x - 55, point.y - 17, 20, 20);
        pointingOption = pointingOption == optionsNumber - 1 ? 0 : ++pointingOption;
        drawSelector(graphics2D);
        graphics2D.dispose();
    }

    @Override
    public void draw(final Graphics2D graphics2D) {
        graphics2D.drawImage(image, 0, 0, null);
    }

    @Override
    public void restart() {
        selectedOption = 0;
        start();
    }

    @Override
    public void setSize(Dimension dimension) {

    }

}
