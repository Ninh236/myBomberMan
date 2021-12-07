package com.ninh236.mybomberman.engine.core.graphics;

public class AnimationWrapper {

    public final int row;
    public final AnimationControl animation;

    public  AnimationWrapper(int row, String frames, long frameTime) {
        this.row = row;
        this.animation = new AnimationControl(frames, frameTime);
    }

}
