package com.ninh236.mybomberman.engine.core.java.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class KeyboardListener extends KeyAdapter {
    private static KeyboardListener instance;

    private KeyboardListener() {
    }

    public static KeyboardListener getInstance() {
        return instance == null ? (instance = new KeyboardListener()) : instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        com.ninh236.mybomberman.engine.core.input.Keyboard.getInstance().keyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        com.ninh236.mybomberman.engine.core.input.Keyboard.getInstance().keyRelease(e.getKeyCode());
    }
}
