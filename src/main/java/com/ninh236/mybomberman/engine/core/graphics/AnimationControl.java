package com.ninh236.mybomberman.engine.core.graphics;

import java.util.ArrayList;

public class AnimationControl {

    private final ArrayList<Integer> frames;
    private final long frameTime;
    private long elapsedTime;
    private int step;

    public AnimationControl(String frames, long frameTime) {
        this.frameTime = frameTime;
        final var framesList = frames.split(",");
        this.frames = new ArrayList<>(framesList.length + 1);
        for (var frame : framesList) {
            this.frames.add(Integer.parseInt(frame));
        }
        this.frames.add(-1);
    }

    public boolean update(final long totalElapsedTime) {
        elapsedTime += totalElapsedTime;
        if (elapsedTime > frameTime) {
            elapsedTime = 0;
            if (frames.get(++step) == -1) {
                step = 0;
                return true;
            }
        }
        return false;
    }

    public void restart() {
        elapsedTime = 0;
        step = 0;
    }

    public int getCurrentFrame() {
        return frames.get(step);
    }
}
