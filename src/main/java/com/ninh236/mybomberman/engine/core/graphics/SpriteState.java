package com.ninh236.mybomberman.engine.core.graphics;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.input.Keymap;

import java.util.function.Supplier;

public interface SpriteState {

    Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap);

    void update(Sprite sprite, Screen screen, long elapsedTime);

    void onExit(Sprite sprite, Screen screen);
}
