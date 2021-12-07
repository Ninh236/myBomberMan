package com.ninh236.mybomberman.engine.core.game.states.bomberman;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.input.Keymap;

import java.util.function.Supplier;

public class InitialState extends com.ninh236.mybomberman.engine.core.game.states.InitialState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        return LeftState::new;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        throw new UnsupportedOperationException("Not support yet!!!");
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {

    }
}
