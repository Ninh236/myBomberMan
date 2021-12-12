package com.ninh236.mybomberman.entities.blocks.items;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.states.bomberman.DeathState;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.map.Position;
import com.ninh236.mybomberman.gui.GameScreen;
import com.ninh236.mybomberman.media.Images;
import com.ninh236.mybomberman.media.Sounds;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

public class Items extends Sprite {

    private final int type;
    private final AtomicInteger enemiesNumber;
    private Timer timer;
    private boolean removedStatus;
    private Position position;

    public Items (final int xValue, final int yValue, int type) {
        super(new Image(Images.ITEMS, 1, 9, (float) 2.5), xValue, yValue);
        image.update(type);
        this.type = type;
        this.enemiesNumber = new AtomicInteger();
    }

    @Override
    public void update(Screen screen, long elapsedTime) {
        var gameScreen = (GameScreen) screen;
        if (GameScreen.firstPlayer().isEnteredThePortal()) {
            return;
        }
        if (isPortal() && !(GameScreen.firstPlayer().getCurrentState() instanceof DeathState)) {
            Sounds.getInstance().change(Sounds.STAGE_THEME, Sounds.FIND_THE_PORTAL, true);
        }
        if (GameScreen.firstPlayer().getCurrentState() instanceof DeathState || GameScreen.firstPlayer().getCurrentState() instanceof NullState) {
            Sounds.getInstance().stop(Sounds.FIND_THE_PORTAL);
        }
        addPendingEnemies(gameScreen, 3);
        var point = gameScreen.getMap().getPosition(GameScreen.firstPlayer());
        if (point != null && point.equals(gameScreen.getMap().getPosition(this))) {
            if (type != getPortal() && !removedStatus) {
                determineSkill();
                Sounds.getInstance().play(Sounds.POWER_UP_2);
                GameScreen.getInstance().setUsePowerUp(true);
                removePowerUp();
            } else {
                if (gameScreen.getEnemiesNumber() == 0) {
                    Sounds.getInstance().stop();
                    Sounds.getInstance().play(Sounds.LEVEL_COMPLETE);
                    GameScreen.firstPlayer().setEnteredThePortal(true);
//                  //Check level passed.
//                    System.out.println("Go to next level with Portal");
                }
            }
        }
    }

    private int getPortal() {
        return 8;
    }

    public boolean isPortal() {
        return type == getPortal();
    }

    public void removePowerUp() {
        if (type != getPortal()) {
            removedStatus = true;
        }
    }

    public void determineSkill() {
        switch (type) {
            case 0 -> GameScreen.firstPlayer().increaseFlames(1);
            case 1 -> GameScreen.firstPlayer().increaseBombs(1);
            case 2 -> GameScreen.firstPlayer().setDETONATOR(true);
            case 3 -> GameScreen.firstPlayer().setSPEEDUP(true);
            case 4 -> GameScreen.firstPlayer().setBOMBPASS(true);
            case 5 -> GameScreen.firstPlayer().setWALLPASS(true);
            case 6 -> GameScreen.firstPlayer().setFLAMEPASS(true);
            case 7 -> GameScreen.firstPlayer().setMYSTERY(true);
        }
    }

    public void createEnemies (final GameScreen gameScreen) {
        position = gameScreen.getMap().getPosition(this);
        if (timer != null && timer.isRunning()) {
            return;
        }
        timer = new Timer(500, new AbstractAction() {
            int time = 5;

            @Override
            public void actionPerformed(ActionEvent event) {
                increaseCounterEnemies();
                if (--time < 0) {
                    timer.stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private void increaseCounterEnemies() {
        if (enemiesNumber.get() < 7) {
            enemiesNumber.incrementAndGet();
        }
    }

    private void addPendingEnemies(GameScreen gameScreen, int type) {
        for (int i = 0, n = enemiesNumber.get(); i < n; i++) {
            gameScreen.addEnemy(position.row, position.column, gameScreen.determinateEnemy(type));
        }
        enemiesNumber.set(0);
    }

    public boolean isRemovedStatus() {
        return removedStatus;
    }

}
