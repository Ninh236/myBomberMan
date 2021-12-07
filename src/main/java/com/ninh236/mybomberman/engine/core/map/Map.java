package com.ninh236.mybomberman.engine.core.map;

import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.entities.blocks.Wall;

import java.util.HashMap;

public class Map {

    public static final short COLUMNS = 31, ROWS = 13;
    private static Map instance;
    private final Tile[][] map;
    private final HashMap<Sprite, SpritePosition> mapper;

    private Map() {
        map = new Tile[ROWS][COLUMNS];
        mapper = new HashMap<>();
        init();
    }

    public static Map getInstance () {
        return instance == null ? (instance = new Map()) : instance;
    }

    private void init() {
        for (var i = 0; i < ROWS; i++) {
            for (var j = 0; j < COLUMNS; j++) {
                map[i][j] = new Tile();
                if (i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30) {
                    map[i][j].add(new Wall(0, 0));
                }
            }
        }
    }

    public Position getPosition(Sprite sprite) {
        return mapper.containsKey(sprite) ? mapper.get(sprite).getCurrentPosition() : null;
    }

    public boolean contains(final int row, final int column, Sprite... sprites) {
        return map[row][column].containsAny(sprites);
    }

    public boolean contains(final int row, final int column, final Class<?> classes) {
        return map[row][column].containsAny(classes);
    }

    public boolean contains(Sprite sprite, final Class<?>... classes) {
        var position = mapper.get(sprite).getCurrentPosition();
        return map[position.row][position.column].containsAny(classes);
    }

    public boolean isEmpty(int row, int column) {
        return map[row][column].isEmpty();
    }

    public void add(final Sprite sprite) {
        var spritePosition = new SpritePosition(sprite);
        var position = spritePosition.getCurrentPosition();
        map[position.row][position.column].add(sprite);
        mapper.put(sprite, spritePosition);
    }

    public Sprite[] getSprite(final int row, final int column, final Class<?>...classes) {
        return map[row][column].get(classes);
    }

    public boolean delete(final Sprite sprite) {
        if (mapper.get(sprite) == null) {
            return false;
        }
        var position = mapper.get(sprite).getCurrentPosition();
        return map[position.row][position.column].remove(sprite) && mapper.remove(sprite) != null;
    }

    public void reset() {
        init();
    }

    public void update(final Sprite sprite) {
        if (mapper.get(sprite) == null || !mapper.get(sprite).update()) {
            return;
        }
        final Position currentPosition = mapper.get(sprite).getCurrentPosition();
        final Position previousPosition = mapper.get(sprite).getPreviousPosition();
        map[previousPosition.row][previousPosition.column].remove(sprite);
        map[currentPosition.row][currentPosition.column].add(sprite);
    }

    public void show() {
        var stringBuilder = new StringBuilder();
        for (var i = 0; i < ROWS; i++) {
            for (var j = 0; j < COLUMNS; j++) {
                stringBuilder.append(map[i][j].length());
            }
            stringBuilder.append("\r\n");
        }
        System.out.println(stringBuilder);
    }

}
