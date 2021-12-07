package com.ninh236.mybomberman.fonts;

import java.awt.*;
import java.io.IOException;

public class Fonts {

    private static Font calibri;
    private static Font joystixMonospace;
    private static Fonts instance;

    static {
        try {
            calibri = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("com/ninh236/mybomberman/fonts/calibri.ttf"));
            joystixMonospace = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getClassLoader().getResourceAsStream("com/ninh236/mybomberman/fonts/joystix_monospace.otf"));
        } catch (FontFormatException | IOException event) {
            System.out.println("Error loadings fonts");
            event.printStackTrace();
        }
    }

    private Fonts() {

    }

    public static Fonts getInstance() {
        return instance == null ? instance = new Fonts() : instance;
    }

    public Font getCalibri(float size) {
        return calibri != null ? calibri.deriveFont(Font.PLAIN, size) : null;
    }

    public Font getJoystixMonospace(float size) {
        return joystixMonospace != null ? joystixMonospace.deriveFont(Font.CENTER_BASELINE, size) : null;
    }
}
