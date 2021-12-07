package com.ninh236.mybomberman.engine.core.game.states.bomberman;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.entities.Bomberman;
import com.ninh236.mybomberman.media.Sounds;

import java.util.function.Supplier;

public class DeathState extends com.ninh236.mybomberman.engine.core.game.states.DeathState {

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        return null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (!sprite.updateAnimation(elapsedTime)) {
            return;
        }
        sprite.setCurrentState(NullState::new);
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().stop();
            Sounds.getInstance().play(Sounds.JUST_DIED);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {

    }

}
