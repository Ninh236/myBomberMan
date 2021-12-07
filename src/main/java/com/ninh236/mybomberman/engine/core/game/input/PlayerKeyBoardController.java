package com.ninh236.mybomberman.engine.core.game.input;

import com.ninh236.mybomberman.engine.core.input.KeyboardController;
import com.ninh236.mybomberman.engine.core.input.KeymapController;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons.*;

public class PlayerKeyBoardController extends KeyboardController {

    private static PlayerKeyBoardController instance;

    private PlayerKeyBoardController() {
        super(new HashMap<>() {
            {
                put(KeyEvent.VK_DOWN, DOWN);
                put(KeyEvent.VK_UP, UP);
                put(KeyEvent.VK_RIGHT, RIGHT);
                put(KeyEvent.VK_LEFT, LEFT);
                put(KeyEvent.VK_SPACE, BOMB);
                put(KeyEvent.VK_SHIFT, DETONATE);
                put(KeyEvent.VK_BACK_SPACE, SELECT);
                put(KeyEvent.VK_ENTER, START);
            }
        });
    }

    public static KeymapController getInstance() {
        return instance == null ? instance = new PlayerKeyBoardController(): instance;
    }

}
