package com.ninh236.mybomberman.entities.bomb;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.states.bomb.ExplosionState;
import com.ninh236.mybomberman.engine.core.game.states.bomb.InitialState;
import com.ninh236.mybomberman.engine.core.graphics.AnimationWrapper;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.entities.Bomberman;
import com.ninh236.mybomberman.entities.Entity;
import com.ninh236.mybomberman.gui.GameScreen;
import com.ninh236.mybomberman.media.Images;
import com.ninh236.mybomberman.media.Sounds;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Bomb extends Entity {

    private final Bomberman bomberman;
    private boolean detonate;
    private Fire fire;
    private Timer timer;

    public Bomb(final int x, int y, final Bomberman player) {
        super(new Image(Images.BOMB, 1, 3, (float) 2.5), x, y);
        bomberman = player;
        id = "X";
        init();
        timer = player.hasDETONATOR() ? null : new Timer(2250, e -> {
            detonate = true;
            timer.stop();
        });
        if (timer != null) {
            timer.start();
        }
    }

    public final void init() {
        super.animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0,1,2", 400));
            }
        };
        setCurrentState(InitialState::new);
    }

    @Override
    public void update(Screen screen, long elapsedTime) {
        super.update(screen, elapsedTime);
        if (fire != null) {
            fire.update(screen, elapsedTime);
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        super.draw(graphics2D);
        if (fire != null) {
            fire.draw(graphics2D);
        }
    }

    public void detonate(final Screen screen) {
        if (!isActive()) {
            return;
        }
        setActive(false);
        setCurrentState(ExplosionState::new);
        bomberman.decreaseCreatedPump();
        fire = new Fire(xValue, yValue, bomberman.getFLAMES(), (GameScreen) screen);
        var sound = Sounds.getInstance().getNewSound(Sounds.EXPLOSION_1);
        if (sound != null) {
            sound.play();
        }
    }

    public boolean isExplosionEnded() {
        return fire != null && fire.getCurrentState() instanceof NullState;
    }

    public boolean isTimeOver() {
        return detonate;
    }

    public boolean hasDetonated() {
        return !isActive();
    }

    public boolean belongsTo(Bomberman bomberman) {
        return this.bomberman == bomberman;
    }

}
