package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Oneal extends Enemy {

    public Oneal(int x, int y) {
        super(Images.ONEAL, x, y, new Keymap());
        speed = SPEED_SLOW;
        smart = SMART_MID;
        score = 200;
        WALLPASS = false;
        id = "O";
        init();
    }

}
