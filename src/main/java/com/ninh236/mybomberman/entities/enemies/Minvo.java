package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Minvo extends Enemy {

    public Minvo(int x, int y) {
        super(Images.MINVO, x, y, new Keymap());
        speed = SPEED_MID;
        smart = SMART_MID;
        score = 800;
        WALLPASS = false;
        id = "M";
        init();
    }

}
