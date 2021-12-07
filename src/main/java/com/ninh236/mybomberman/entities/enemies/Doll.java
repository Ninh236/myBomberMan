package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Doll extends Enemy {

    public Doll(int x, int y) {
        super(Images.DOLL, x, y, new Keymap());
        speed = SPEED_SLOW;
        smart = SMART_LOW;
        score = 400;
        WALLPASS = false;
        id = "D";
        init();
    }

}
