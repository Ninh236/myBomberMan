package com.ninh236.mybomberman.media;

import com.ninh236.mybomberman.engine.core.Resource;
import com.ninh236.mybomberman.engine.core.input.Keyboard;
import com.ninh236.mybomberman.engine.core.java.resources.SoundResource;

import javax.sound.sampled.Clip;
import java.util.ArrayList;

public class Sounds {
    public static final int PAUSE = 20;
    public static final int TITLE_SCREEN = 0, LEVEL_START = 1, STAGE_THEME = 2, FIND_THE_PORTAL = 3, GAME_OVER = 4, INVINCIBILITY_THEME = 5, JUST_DIED = 6, ENDING_THEME = 7, LEVEL_COMPLETE = 8, ULTIMATE = 9, BOMB_PLANT = 10, EXPLOSION_1 = 11, EXPLOSION_2 = 12, KICK = 13, POWER_UP_2 = 14, DEATH = 15, LEFT = 16, RIGHT = 17, UP = 18, DOWN = 19;
    private static Sounds instance;
    final int soundsLength = PAUSE + 1;
    private final ArrayList<Sound> sounds;

    private Sounds() {
        this.sounds = new ArrayList<>();
        initComponents();
    }

    public static Sounds getInstance() {
        return instance == null ? (instance = new Sounds()) : instance;
    }

    private void initComponents() {
        for (var i = 0; i < soundsLength; i++) {
            this.sounds.add(new Sound(getClip(i)));
        }
    }

    private Clip getClip(int i) {
        String path;
        Resource<Clip> resource = new SoundResource();
        var root = new Root();
        if (i == TITLE_SCREEN) path = root.TITLE_SCREEN;
        else if (i == LEVEL_START) path = root.STAGE;
        else if (i == STAGE_THEME) path = root.STAGE_THEME;
        else if (i == FIND_THE_PORTAL) path = root.FIND_THE_PORTAL;
        else if (i == GAME_OVER) path = root.GAME_OVER;
        else if (i == INVINCIBILITY_THEME) path = root.INVINCIBILITY_THEME;
        else if (i == JUST_DIED) path = root.JUST_DIED;
        else if (i == ENDING_THEME) path = root.ENDING_THEME;
        else if (i == LEVEL_COMPLETE) path = root.LEVEL_COMPLETE;
        else if (i == BOMB_PLANT) path = root.BOMB_PLANT;
        else if (i == EXPLOSION_1) path = root.EXPLOSION_1;
        else if (i == EXPLOSION_2) path = root.EXPLOSION_2;
        else if (i == KICK) path = root.KICK;
        else if (i == POWER_UP_2) path = root.POWER_UP_2;
        else if (i == DEATH) path = root.DEATH;
        else if (i == LEFT) path = root.LEFT;
        else if (i == RIGHT) path = root.RIGHT;
        else if (i == UP) path = root.UP;
        else if (i == DOWN) path = root.DOWN;
        else path = root.PAUSE;
        return (Clip) resource.load(path, SoundResource.WAV);
    }

    public Sound getNewSound(int sound) {
        return sound < sounds.size() ? sounds.get(sound) : null;
    }

    public Sound get(int soundName) {
        return soundName < sounds.size() ? sounds.get(soundName) : null;
    }

    public Sound play(int sound) {
        if (sounds.size() <= sound) {
            return null;
        }
        var newSound = sounds.get(sound);
        newSound.play();
        return  newSound;
    }

    public void play(int sound, boolean play) {
        if (play) {
            play(sound);
        } else {
            stop(sound);
        }
    }

    public void loop (int sound) {
        if (sound < sounds.size()) {
            sounds.get(sound).loop();
        }
    }

    public void change(int current, int newSound) {
        stop(current);
        play(newSound);
    }

    public void change(int current, int newSound, boolean asLoop) {
        stop(current);
        if (asLoop) {
            loop(newSound);
        } else {
            play(newSound);
        }
    }

    public boolean isPlaying(int sound) {
        return sound < sounds.size() && sounds.get(sound).isPlaying();
    }

    public void pause() {
        for (var sound : sounds) {
            sound.pause();
        }
    }

    public void stop() {
        for (var sound : sounds) {
            sound.stop();
        }
    }

    public void stop(int... sounds) {
        if (this.sounds.isEmpty()) {
            return;
        }
        for (var index : sounds) {
            var newSound = this.sounds.get(index);
            if (newSound.isPlaying() || newSound.isLoop()) {
                newSound.stop();
            }
        }
    }

    public void resume() {
        for (var sound : sounds) {
            if (sound.isAlive()) {
                sound.play();
            }
        }
    }
}
