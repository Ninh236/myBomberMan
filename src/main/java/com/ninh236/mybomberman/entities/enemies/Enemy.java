package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.game.states.bomberman.*;
import com.ninh236.mybomberman.engine.core.graphics.AnimationWrapper;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.entities.Entity;
import com.ninh236.mybomberman.entities.enemies.ai.AI;
import com.ninh236.mybomberman.gui.GameScreen;

import java.awt.*;
import java.util.HashMap;

public class Enemy extends Entity {

    public int score;

    public Enemy(final java.awt.Image image, final int x, final int y, final Keymap keymap) {
        super(new Image(image, 6, 5, (float) 2.5), x, y);
        this.keymap = keymap;
        init();
    }

    final void init() {
        animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0", 4000 / 60));
                put(UpState.class, new AnimationWrapper(1, "0,1,2", 4000 / 60));
                put(DownState.class, new AnimationWrapper(2, "0,1,2", 4000 / 60));
                put(RightState.class, new AnimationWrapper(3, "0,1,2", 4000 / 60));
                put(LeftState.class, new AnimationWrapper(4, "0,1,2", 4000 / 60));
                put(DeathState.class, new AnimationWrapper(5, "0,1,2,3,4", 500));
            }
        };
        setCurrentState(LeftState::new);
        ai = new AI(this);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public void draw(final Graphics2D graphics2D) {
        super.draw(graphics2D);
        if (currentState instanceof DeathState) {
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawString("" + score, getX() + image.getWidth() / 5, getCenter().y);
        }
    }

    public void death(GameScreen gameScreen) {
        setCurrentState(DeathState::new);
        gameScreen.getMap().delete(this);
        stopAI();
    }

}
