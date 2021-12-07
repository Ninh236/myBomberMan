package com.ninh236.mybomberman.engine.core.game.states.bomb;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.entities.bomb.Bomb;
import com.ninh236.mybomberman.gui.GameScreen;

import java.util.function.Supplier;

public class InitialState extends com.ninh236.mybomberman.engine.core.game.states.InitialState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        update((Bomb) sprite, (GameScreen) screen, elapsedTime);
    }

    public void update(Bomb bomb, GameScreen gameScreen, long elapsedTime) {
        bomb.updateAnimation(elapsedTime);
        if (bomb.isTimeOver()) {
            bomb.detonate(gameScreen);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {

    }

}
