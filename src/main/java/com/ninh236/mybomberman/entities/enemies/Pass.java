package com.ninh236.mybomberman.entities.enemies;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.media.Images;

public class Pass extends Enemy {

    public Pass(int x, int y) {
        super(Images.PASS, x, y, new Keymap());
        speed = SPEED_MID;
        smart = SMART_HIGH;
        score = 4000;
        WALLPASS = true;
        id = "P";
        init();
    }

}
