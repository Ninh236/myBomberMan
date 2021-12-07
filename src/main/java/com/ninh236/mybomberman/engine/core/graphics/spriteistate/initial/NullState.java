package com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.input.Keymap;

import java.util.function.Supplier;

public class NullState implements SpriteState {


    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {

    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {

    }
}
