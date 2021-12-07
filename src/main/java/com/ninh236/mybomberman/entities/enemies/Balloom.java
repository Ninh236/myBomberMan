package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Balloom extends Enemy {

    public Balloom(int x, int y) {
        super(Images.BALLOOM, x, y, new Keymap());
        speed = SPEED_SLOWEST;
        smart = SMART_LOW;
        score = 100;
        WALLPASS = false;
        id = "b";
        init();
    }
}
