package com.ninh236.mybomberman.gui;

import com.ninh236.mybomberman.engine.core.game.Game;
import com.ninh236.mybomberman.engine.core.game.GameScene;
import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.media.Sounds;

import java.awt.*;

public class BombermanGame implements Game {

    private Scene selectedScene;
    private Screen currentScreen;

    public BombermanGame() {
        init();
    }

    private void init() {
        currentScreen = MenuScreen.getInstance();
        setScreen(Scene.MENU);
        Sounds.getInstance().play(Sounds.TITLE_SCREEN);
    }

    public void drawGraphics(final Graphics graphics) {
        currentScreen.draw((Graphics2D) graphics);
    }

    public void setScreen(GameScene gameScene) {
        selectedScene = (Scene) gameScene;
        currentScreen.restart();
        switch (this.selectedScene) {
            case MENU:
                currentScreen = MenuScreen.getInstance();
                break;
            case STAGE:
                currentScreen = MessageScreen.getInstance();
                ((MessageScreen) currentScreen).startStageScreen();
                break;
            case GAME:
                currentScreen = GameScreen.getInstance();
                Sounds.getInstance().loop(Sounds.STAGE_THEME);
                ((GameScreen) currentScreen).enableAI();
                PanelInformation.getInstance().startCountdown();
                break;
            case GAME_OVER:
                currentScreen = MessageScreen.getInstance();
                ((MessageScreen) currentScreen).startGameOverScreen();
                break;
            case QUIT:
        }
    }

    public void update(long elapsedTime) {
        currentScreen.update(elapsedTime, this);
    }

    public void setSze(Dimension dimension) {
        currentScreen.setSize(dimension);
    }

    @Override
    public Scene getSelectedScene() {
        return selectedScene;
    }

    public enum Scene implements GameScene {
        MENU,
        STAGE,
        ULTIMATE,
        GAME,
        GAME_OVER,
        QUIT
    }
}
