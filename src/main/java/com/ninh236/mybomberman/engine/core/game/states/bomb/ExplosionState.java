package com.ninh236.mybomberman.engine.core.game.states.bomb;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.states.DeathState;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.entities.bomb.Bomb;

import java.util.function.Supplier;

public class ExplosionState extends DeathState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        return ((Bomb) sprite).isExplosionEnded() ? NullState::new : null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {

    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {

    }

}
