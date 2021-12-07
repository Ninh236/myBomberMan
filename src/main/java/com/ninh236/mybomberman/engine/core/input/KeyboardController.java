package com.ninh236.mybomberman.engine.core.input;

import com.ninh236.mybomberman.engine.core.input.Keyboard.KeyState;
import com.ninh236.mybomberman.engine.core.input.Keymap.Buttons;

import java.util.HashMap;

import static com.ninh236.mybomberman.engine.core.input.Keyboard.KeyState.PRESSED;

public abstract class KeyboardController implements KeymapController {

    private final HashMap<Integer, Buttons> keyMapper;
    private final HashMap<Buttons, Boolean> buffer;

    protected KeyboardController(HashMap<Integer, Buttons> keyMapper) {
        if (keyMapper == null) {
            throw new IllegalArgumentException("Key mapper is null!!!");
        }
        this.keyMapper = keyMapper;
        this.buffer = new HashMap<>();
        Keyboard.getInstance().keyChangedSubscribe(this::keyChanged);
    }

    @Override
    public void update(Keymap keymap) {
        synchronized (buffer) {
            if (buffer.isEmpty()) {
                return;
            }
            buffer.forEach(keymap::setPress);
        }
    }

    private Void keyChanged(int keyCode, KeyState keyState) {
        if (keyMapper.containsKey(keyCode)) {
            synchronized (buffer) {
                buffer.put(keyMapper.get(keyCode), keyState == PRESSED);
            }
        }
        return null;
    }

}
