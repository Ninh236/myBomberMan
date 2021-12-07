package com.ninh236.mybomberman.engine.core.map;

import com.ninh236.mybomberman.engine.core.graphics.Sprite;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Tile {

    private final ArrayList<Sprite> sprites;

    public Tile() {
        sprites = new ArrayList<>();
    }

    public boolean add(final Sprite sprite) {
        return sprites.add(sprite);
    }

    Sprite[] get(final Class<?>... classes) {
        return sprites.stream().filter((first) -> Stream.of(classes).anyMatch(second -> second.isInstance(first))).toArray(Sprite[]::new);
    }

    public boolean remove(final Sprite sprite) {
        return sprites.remove(sprite);
    }

    public boolean containsAny(final Class<?>... classes) {
        for (var sprite : sprites) {
            for (var _class : classes) {
                if (_class.isInstance(sprite)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean containsAny(final Sprite... sprites) {
        return Stream.of(sprites).anyMatch(this.sprites::contains);
    }

    public boolean isEmpty() {
        return sprites.isEmpty();
    }

    public int length() {
        return sprites.size();
    }

}
