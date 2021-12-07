package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Pontan extends Enemy {

    public Pontan(int x, int y) {
        super(Images.PONTAN, x, y, new Keymap());
        speed = SPEED_MID;
        smart = SMART_HIGH;
        score = 8000;
        WALLPASS = true;
        id = "p";
        init();
    }

}
