package com.ninh236.mybomberman.entities.bomb;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.states.fire.InitialState;
import com.ninh236.mybomberman.engine.core.graphics.AnimationWrapper;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.map.Position;
import com.ninh236.mybomberman.entities.Bomberman;
import com.ninh236.mybomberman.entities.Entity;
import com.ninh236.mybomberman.entities.blocks.Brick;
import com.ninh236.mybomberman.entities.blocks.Wall;
import com.ninh236.mybomberman.entities.blocks.items.Items;
import com.ninh236.mybomberman.entities.enemies.Enemy;
import com.ninh236.mybomberman.gui.GameScreen;
import com.ninh236.mybomberman.media.Images;

import java.awt.*;
import java.util.HashMap;

public class Fire extends Entity {

    private final int length;
    private int[] lengthDirections;
    private Point[] position;
    private Image[] images;

    public Fire(final int x, final int y, final int length, final GameScreen gameScreen) {
        super(new Image(Images.FIRE, 7, 4, (float) 2.5), x, y);
        this. length = length;
        init(gameScreen);
        burnPlayer(gameScreen);
        createSprites();
    }

    public final void init(GameScreen gameScreen) {
        super.animations = new HashMap<>() {
            {
                put(InitialState.class, new AnimationWrapper(0, "0,1,2,3", 4000 / 60));
            }
        };
        lengthDirections = new int[4];
        setCurrentState(InitialState::new);
        gameScreen.getMap().add(this);
    }

    @Override
    public void update(final Screen screen, final long elapsedTime) {
        super.update(screen, elapsedTime);
        if (currentState instanceof NullState) {
            return;
        }
        final var i = animations.get(InitialState.class).animation.getCurrentFrame();
        for (final var sprite : images) {
            sprite.update(i);
        }
    }

    @Override
    public void draw(final Graphics2D graphics2D) {
        if (!(currentState instanceof NullState) && isActive()) {
            for (var i = 0; i < images.length; i++) {
                images[i].draw(graphics2D, position[i].x, position[i].y);
            }
        }
    }

    private Point getPositionSprite(final Direction direction, int adjust) {
        return direction == Direction.LEFT ? new Point(xValue - adjust * image.getWidth(), yValue)
                : direction == Direction.RIGHT ? new Point(xValue + adjust * image.getWidth(), yValue)
                : direction == Direction.UP ? new Point(xValue, yValue - adjust * image.getHeight())
                : new Point(xValue, yValue + adjust * image.getHeight());
    }

    private void createSprites() {
        var check = new boolean[4];
        short index = 0;
        images = new Image[lengthDirections[0] + lengthDirections[1] + lengthDirections[2] + lengthDirections[3] + 1];
        position = new Point[lengthDirections[0] + lengthDirections[1] + lengthDirections[2] + lengthDirections[3] + 1];
        position[index] = new Point(xValue, yValue);
        images[index++] = new Image(image.getImage(), 7, 4, (float) 2.5, 0);
        for (var i = 1; i <= length; i++) {
            for (var value : Direction.values()) {
                if (check[value.ordinal()]) {
                    continue;
                }
                final var position = getPositionSprite(value, i);
                if (i <= lengthDirections[value.ordinal()] && i != length) {
                    this.position[index] = position;
                    images[index++] = new Image(image.getImage(), 7, 4, (float) 2.5, value == Direction.UP || value == Direction.DOWN ? 6 : 5);
                }
                if (i < lengthDirections[value.ordinal()] || lengthDirections[value.ordinal()] == 0) {
                    continue;
                }
                if (i == length) {
                    this.position[index] = position;
                    images[index++] = new Image(image.getImage(), 7, 4, (float) 2.5, value.ordinal() + 1);
                }
                check[value.ordinal()] = true;
            }
        }
    }

    private void burnPlayer(final GameScreen gameScreen) {
        var check = new boolean[4];
        var position = gameScreen.getMap().getPosition(this);
        for (var i = 1; i <= length; i++) {
            if (!check[Direction.LEFT.ordinal()] && checkObstacle(gameScreen, Direction.LEFT, i, position.row, position.column - i)) {
                check[Direction.LEFT.ordinal()] = true;
            }
            if (!check[Direction.RIGHT.ordinal()] && checkObstacle(gameScreen, Direction.RIGHT, i, position.row, position.column + 1)) {
                check[Direction.RIGHT.ordinal()] = true;
            }
            if (!check[Direction.UP.ordinal()] && checkObstacle(gameScreen, Direction.UP, i, position.row - 1, position.column)) {
                check[Direction.UP.ordinal()] = true;
            }
            if (!check[Direction.DOWN.ordinal()] && checkObstacle(gameScreen, Direction.DOWN, i, position.row + i, position.column)) {
                check[Direction.DOWN.ordinal()] = true;
            }
        }
        killPlayer(gameScreen, position);
    }

    private boolean checkObstacle(final GameScreen gameScreen, final Direction direction, final int adjust, final int row, final int column) {
        var map = gameScreen.getMap();
        boolean checkWalls;
        boolean checkLivingEntities;
        if (direction == Direction.UP || direction == Direction.DOWN) {
            checkWalls = collisionY(map, row, Wall.class, Brick.class, Bomb.class, Items.class);
            checkLivingEntities = collisionY(map, row, Bomberman.class, Enemy.class);
        } else {
            checkWalls = collisionX(map, column, Wall.class, Brick.class, Bomb.class, Items.class);
            checkLivingEntities = collisionX(map, column, Bomberman.class, Enemy.class);
        }
        if (checkWalls) {
            gameScreen.eraseBrick(row, column);
            gameScreen.eraseBomb(row, column);
        }
        if (checkLivingEntities) {
            gameScreen.eraseEnemy(row, column);
            if (!gameScreen.firstPlayer().hasFLAMEPASS()) {
                gameScreen.erasePlayer(row, column);
            }
        }
        if (!checkWalls && length != adjust) {
            return false;
        }
        lengthDirections[direction.ordinal()] = checkWalls ? adjust - 1 : adjust;
        return true;
    }

    private void killPlayer(GameScreen gameScreen, Position position) {
        if (gameScreen.getMap().contains(position.row, position.column, Enemy.class)) {
            gameScreen.eraseEnemy(position.row, position.column);
        }
        if (centralCollision(Bomberman.class) && !gameScreen.firstPlayer().hasFLAMEPASS()) {
            gameScreen.erasePlayer(position.row, position.column);
        }
    }

    private enum Direction {
        UP,
        DOWN,
        RIGHT,
        LEFT
    }

}
