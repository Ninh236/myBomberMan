package com.ninh236.mybomberman.engine.core.game.states.fire;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.gui.GameScreen;

import java.util.function.Supplier;

public class InitialState extends com.ninh236.mybomberman.engine.core.game.states.InitialState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (sprite.updateAnimation(elapsedTime)) {
            ((GameScreen) screen).getMap().delete(sprite);
            sprite.setCurrentState(NullState::new);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {

    }

}
