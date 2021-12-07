package com.ninh236.mybomberman.engine.core.input;

import java.util.HashMap;

import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons.NONE;

public class Keymap {
    private final HashMap<Buttons, ButtonState> buttons = new HashMap<>() {
        {
            for (var value : Buttons.values()) {
                put(value, new ButtonState(false, true));
            }
            put(NONE, new ButtonState(true, true));
        }
    };

    public boolean isPress(Buttons buttons) {
        return this.buttons.get(buttons).isPressed();
    }

    public void setPress(Buttons buttons, boolean pressed) {
        if (!this.buttons.get(buttons).isEnabled()) {
            return;
        }
        this.buttons.get(buttons).setPressed(pressed);
    }

    public enum Buttons {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        BOMB,
        DETONATE,
        SELECT,
        START,
        NONE
    }

    private class ButtonState {

        private boolean pressed, enabled;

        public ButtonState(boolean pressed, boolean enabled) {
            this.pressed = pressed;
            this.enabled = enabled;
        }

        public boolean isPressed() {
            return pressed;
        }

        public void setPressed(boolean pressed) {
            this.pressed = pressed;
        }

        public boolean isEnabled() {
            return enabled;
        }

    }
}
