package com.ninh236.mybomberman.entities.enemies.ai;

import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.entities.Entity;
import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons;

import javax.swing.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons.*;

public class AI {

    private final ArrayList<Keymap.Buttons> buffer;
    private final Entity entity;
    private final Keymap keymap;
    private final Random random;
    private Timer timer;
    private int way = 1;
    private int time = -1;

    public AI(Entity entity) {
        this.entity = entity;
        this.keymap = entity.getKeymap();
        this.buffer = new ArrayList<>();
        random = new Random();
        determineAI();
    }

    private void determineAI() {
//        timer = entity.getSmart() == Entity.SMART_IMPOSSIBLE ? null : new Timer(100, e -> {
//            if (++time % 15 != 0) {
//                return;
//            }
//            if (entity.getSmart() == Entity.SMART_LOW) {
//                bufferProcess((way = random.nextInt(4)) == 0
//                        ? LEFT : way == 1 ? RIGHT : way == 2 ? UP : DOWN);
//            } else if (entity.getSmart() == Entity.SMART_MID || entity.getSmart() == Entity.SMART_HIGH) {
//                bufferProcess(random.nextInt(2) == 0 ? LEFT : RIGHT,
//                        random.nextInt(2) == 0 ? UP : DOWN);
//            }
//        });
    }

    public Timer getTimer() {
        return timer;
    }

    public void stop() {
        if (timer == null) {
            return;
        }
        timer.stop();
    }

    public void start() {
        if (timer == null) {
            return;
        }
        timer.start();
    }

    private void bufferProcess(Buttons... buttons) {
        bufferClear();
        buffer.addAll(Arrays.asList(buttons));
        bufferApply();
    }

    private void bufferClear() {
        buffer.forEach(key -> keymap.setPress(key, false));
        buffer.clear();
    }

    private void bufferApply() {
        buffer.forEach(key -> keymap.setPress(key, true));
    }

}
