package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Ovapi extends Enemy {

    public Ovapi(int x, int y) {
        super(Images.OVAPI, x, y, new Keymap());
        speed = SPEED_SLOW;
        smart = SMART_MID;
        score = 2000;
        WALLPASS = true;
        id = "o";
        init();
    }

}
