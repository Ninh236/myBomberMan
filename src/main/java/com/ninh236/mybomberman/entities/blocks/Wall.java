package com.ninh236.mybomberman.entities.blocks;

import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.media.Images;


public class Wall extends Sprite {

    public Wall(int xValue, int yValue) {
        super(new Image(Images.WALL, 1, 1, 1), xValue, yValue);
    }

}
