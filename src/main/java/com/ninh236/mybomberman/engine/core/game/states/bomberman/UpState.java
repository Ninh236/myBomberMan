package com.ninh236.mybomberman.engine.core.game.states.bomberman;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.SpriteState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.entities.Bomberman;
import com.ninh236.mybomberman.entities.Entity;
import com.ninh236.mybomberman.gui.GameScreen;
import com.ninh236.mybomberman.media.Sounds;

import java.util.function.Supplier;

import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons.*;

public class UpState implements SpriteState {

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    @Override
    public Supplier<SpriteState> handleInput(Sprite sprite, Keymap keymap) {
        left = keymap.isPress(LEFT);
        right = keymap.isPress(RIGHT);
        up = keymap.isPress(UP);
        down = keymap.isPress(DOWN);
        return left ? LeftState::new
                : right ? RightState::new
                : up ? null
                : down ? DownState::new : null;
    }

    @Override
    public void update(Sprite sprite, Screen screen, long elapsedTime) {
        if (up) {
            sprite.updateAnimation(elapsedTime);
            ((Entity) sprite).moveUp((GameScreen) screen);
        }
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().play(Sounds.UP, up);
        }
    }

    @Override
    public void onExit(Sprite sprite, Screen screen) {
        if (sprite instanceof Bomberman) {
            Sounds.getInstance().stop(Sounds.UP);
        }
    }

}
