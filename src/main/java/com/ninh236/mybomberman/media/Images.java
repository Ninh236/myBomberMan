package com.ninh236.mybomberman.media;

import com.ninh236.mybomberman.engine.core.java.resources.ImageResource;

import java.awt.*;

import static com.ninh236.mybomberman.engine.core.java.resources.ImageResource.VOLATILE;

public class Images {
    public static final Image LOGO;
    public static final Image SELECTOR;
    public static final Image GROUND;
    public static final Image WALL;
    public static final Image BOMB;
    public static final Image FIRE;
    public static final Image BOMBERMAN;
    public static final Image BALLOOM;
    public static final Image ONEAL;
    public static final Image DOLL;
    public static final Image MINVO;
    public static final Image KONDORIA;
    public static final Image PASS;
    public static final Image OVAPI;
    public static final Image PONTAN;
    public static final Image BRICK;
    public static final Image ITEMS;
    public static final Image ICON;

    static {
        final var root = new Root();
        final var imageResource = new ImageResource();
        LOGO = imageResource.load(root.LOGO, VOLATILE);
        SELECTOR = imageResource.load(root.SELECTOR, VOLATILE);
        WALL = imageResource.load(root.WALL, VOLATILE);
        GROUND = imageResource.load(root.FLOOR, VOLATILE);
        ITEMS = imageResource.load(root.ITEMS, VOLATILE);
        BRICK = imageResource.load(root.BRICK, VOLATILE);
        BOMB = imageResource.load(root.BOMB, VOLATILE);
        FIRE = imageResource.load(root.FIRE, VOLATILE);
        BOMBERMAN = imageResource.load(root.BOMBERMAN, VOLATILE);
        BALLOOM = imageResource.load(root.BALLOOM, VOLATILE);
        ONEAL = imageResource.load(root.ONEAL, VOLATILE);
        DOLL = imageResource.load(root.DOLL, VOLATILE);
        MINVO = imageResource.load(root.MINVO, VOLATILE);
        KONDORIA = imageResource.load(root.KONDORIA, VOLATILE);
        OVAPI = imageResource.load(root.OVAPI, VOLATILE);
        PASS = imageResource.load(root.PASS, VOLATILE);
        PONTAN = imageResource.load(root.PONTAN, VOLATILE);
        ICON = imageResource.load(root.ICON, VOLATILE);
    }

}
