package com.ninh236.mybomberman.entities;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.input.PlayerKeyBoardController;
import com.ninh236.mybomberman.engine.core.game.states.bomberman.*;
import com.ninh236.mybomberman.engine.core.graphics.AnimationWrapper;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.engine.core.input.Keymap.Buttons;
import com.ninh236.mybomberman.entities.blocks.Brick;
import com.ninh236.mybomberman.entities.blocks.items.Items;
import com.ninh236.mybomberman.entities.bomb.Bomb;
import com.ninh236.mybomberman.entities.enemies.Enemy;
import com.ninh236.mybomberman.gui.GameScreen;
import com.ninh236.mybomberman.media.Images;
import com.ninh236.mybomberman.media.Sounds;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashMap;

public class Bomberman extends Entity {

    private boolean SPEEDUP;
    private boolean DETONATOR;
    private boolean FLAMEPASS;
    private boolean MYSTERY;
    private int FLAMES, BOMBS;
    private boolean enteredThePortal;
    private int createdPump;
    private final PropertyChangeSupport changes = new PropertyChangeSupport(this);

    public Bomberman(final int x, final int y) {
        super(new Image(Images.BOMBERMAN, 6, 6, (float) 2.5), x, y);
        keymap = new Keymap();
        keyController = PlayerKeyBoardController.getInstance();
        speed = SPEED_MID;
        BOMBS = 1;
        FLAMES = 1;
        SPEEDUP = false;
        DETONATOR = false;
        WALLPASS = false;
        FLAMEPASS = false;
        BOMBPASS = false;
        MYSTERY = false;
        id = "B";
        init();
    }

    public final void init() {
        super.animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "1", 4000 / 60));
                put(UpState.class, new AnimationWrapper(1, "2,1,0,1", 4000 / 60));
                put(DownState.class, new AnimationWrapper(2, "2,1,0,1", 4000 / 60));
                put(RightState.class, new AnimationWrapper(3, "2,1,0,1", 4000 / 60));
                put(LeftState.class, new AnimationWrapper(4, "2,1,0,1", 4000 / 60));
                put(DeathState.class, new AnimationWrapper(5, "0,1,2,3,4", 300));
            }
        };
        setCurrentState(LeftState::new);
    }

    @Override
    public void update(final Screen screen, final long elapsedTime) {
        var gameScreen = (GameScreen) screen;
        keyController.update(keymap);
        checkKeyState(gameScreen);
        super.update(gameScreen, elapsedTime);
        checkDeath(gameScreen);
    }

    public void restart(int x, int y) {
        setCurrentState(InitialState::new);
        setAxisPosition(x, y);
        createdPump = 0;
        image.setActiveState(true);
        enteredThePortal = false;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changes.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        changes.removePropertyChangeListener(listener);
    }

    public void die() {
        Sounds.getInstance().stop(Sounds.UP, Sounds.DOWN, Sounds.LEFT, Sounds.RIGHT);
        Sounds.getInstance().play(Sounds.DEATH);
        setCurrentState(DeathState::new);
    }

    private void checkDeath(GameScreen gameScreen) {
        if (currentState instanceof DeathState || currentState instanceof NullState || !gameScreen.getMap().contains(this, Enemy.class)) {
            return;
        }
        die();
    }

    public void setSPEEDUP(boolean SPEEDUP) {
        this.SPEEDUP = SPEEDUP;
        if (SPEEDUP) {
            speed = SPEED_FAST;
        } else {
            speed = SPEED_MID;
        }
    }

    public void increaseBombs(final int addBomb) {
        BOMBS += addBomb;
    }

    public void increaseFlames(final int addFlame) {
        FLAMES += addFlame;
    }

    public int getBOMBS() {
        return BOMBS;
    }

    public boolean isMYSTERY() {
        return MYSTERY;
    }

    public void setMYSTERY(boolean MYSTERY) {
        this.MYSTERY = MYSTERY;
    }

    public int getFLAMES() {
        return FLAMES;
    }

    public boolean hasDETONATOR() {
        return DETONATOR;
    }

    public void setDETONATOR(boolean DETONATOR) {
        this.DETONATOR = DETONATOR;
    }

    public boolean hasFLAMEPASS() {
        return FLAMEPASS;
    }

    public void setFLAMEPASS(boolean FLAMEPASS) {
        this.FLAMEPASS = FLAMEPASS;
    }

    public void plantBomb() {
        if (centralCollision(Bomb.class)) {
            insideBomb = true;
            return;
        }
        if (!centralCollision(Brick.class, Items.class) && createdPump < BOMBS) {
            final var bomb = new Bomb(getCenter().x / image.getWidth() * image.getWidth(), getCenter().y / image.getHeight() * image.getHeight(), this);
            insideBomb = true;
            createdPump++;
            changes.firePropertyChange(Events.ADD_BOMB.name(), null, bomb);
        }
    }

    public void decreaseCreatedPump() {
        if (createdPump > 0) {
            createdPump--;
        }
    }

    public void checkKeyState(final GameScreen gameScreen) {
        if (currentState instanceof DeathState || currentState instanceof NullState) {
            return;
        }
        if (keymap.isPress(Buttons.BOMB)) {
            plantBomb();
        }
        if (keymap.isPress(Buttons.DETONATE) && DETONATOR) {
            detonatorBomb(gameScreen);
        }
    }

    public void detonatorBomb(final GameScreen screen) {
        screen.eraseBomb(this);
    }

    public boolean isEnteredThePortal() {
        return enteredThePortal;
    }

    public void setEnteredThePortal(boolean enteredThePortal) {
        this.enteredThePortal = enteredThePortal;
    }

    public enum Events {
        ADD_BOMB,
    }

}
