package com.ninh236.mybomberman.engine.core.input;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ninh236.mybomberman.engine.core.input.Keyboard.KeyState.PRESSED;
import static com.ninh236.mybomberman.engine.core.input.Keyboard.KeyState.RELEASED;


public final class Keyboard {

    private static Keyboard instance;
    private final HashMap<Integer, KeyState> keys;
    private final ArrayList<Consumer<Integer, KeyState, Void>> keyStateChanged;

    private Keyboard() {
        keys = new HashMap<>();
        keyStateChanged = new ArrayList<>();
    }

    public static Keyboard getInstance() {
        return instance == null ? (instance = new Keyboard()) : instance;
    }

    public void keyPress(int keyCode) {
        keys.put(keyCode, PRESSED);
        keyStateChanged.forEach(key -> key.apply(keyCode, PRESSED));
    }

    public void keyRelease(int keyCode) {
        keys.put(keyCode, RELEASED);
        keyStateChanged.forEach(key -> key.apply(keyCode, RELEASED));
    }

    public boolean isKeyPressed(int keyCode) {
        return keys.get(keyCode) == PRESSED;
    }

    public boolean isKeyPressed() {
        return keys.containsValue(PRESSED);
    }
    public boolean isKeyRelease(int keyCode) {
        return !isKeyPressed(keyCode);
    }

    public void keyChangedSubscribe(Consumer<Integer, KeyState, Void> function) {
        keyStateChanged.add(function);
    }

    public enum KeyState {
        PRESSED,
        RELEASED,
    }

    @java.lang.FunctionalInterface
    public interface Consumer<One, Two, Three> {

        Three apply(One one, Two two);
    }

}
