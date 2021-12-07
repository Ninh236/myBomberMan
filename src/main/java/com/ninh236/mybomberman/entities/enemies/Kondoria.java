package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Kondoria extends Enemy {

    public Kondoria(int x, int y) {
        super(Images.KONDORIA, x, y, new Keymap());
        speed = SPEED_SLOWEST;
        smart = SMART_HIGH;
        score = 1000;
        WALLPASS = true;
        id = "K";
        init();
    }

}
