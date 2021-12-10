package com.ninh236.mybomberman.gui;

import com.ninh236.mybomberman.bomberman.core.GameControl;
import com.ninh236.mybomberman.engine.core.Viewport;
import com.ninh236.mybomberman.engine.core.game.Game;
import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.constants.Objects;
import com.ninh236.mybomberman.engine.core.game.states.DeathState;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.NullState;
import com.ninh236.mybomberman.engine.core.map.Map;
import com.ninh236.mybomberman.entities.Bomberman;
import com.ninh236.mybomberman.entities.Entity;
import com.ninh236.mybomberman.entities.blocks.Brick;
import com.ninh236.mybomberman.entities.blocks.Wall;
import com.ninh236.mybomberman.entities.blocks.items.Items;
import com.ninh236.mybomberman.entities.bomb.Bomb;
import com.ninh236.mybomberman.entities.enemies.Enemy;
import com.ninh236.mybomberman.gui.BombermanGame.Scene;
import com.ninh236.mybomberman.media.Images;
import com.ninh236.mybomberman.media.Sounds;
import com.ninh236.mybomberman.media.tools.ImageUtilities;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Stream;

public class GameScreen extends Screen implements PropertyChangeListener {

    private static GameScreen instance;
    private final int xValue;
    private final int yValue;
    private final Map map;
    private final Image buffer;
    private static Bomberman[] players;
    private final ArrayList<Brick> bricks;
    private final ArrayList<Enemy> enemies;
    private final ArrayList<Bomb> bombs;
    private final Viewport window;
    private final GameControl gameControl;
    private final PanelInformation panelInformation;
    private final double internalScaleX;
    private boolean defeated;
    private boolean powerUp;
    private boolean portal;
    private Dimension Size;

    private GameScreen() {
        Size = new Dimension(1240, 520);
        internalScaleX = ((double) 640 / (Size.width >> 1));
        window = new Viewport(new Rectangle(Size.width >> 1, Size.height), Size);
        buffer = ImageUtilities.createCompatibleVolatileImage(Size.width, Size.height, Transparency.OPAQUE);
        xValue = buffer.getWidth(null) / Map.COLUMNS;
        yValue = buffer.getHeight(null) / Map.ROWS;
        players = new Bomberman[1];
        enemies = new ArrayList<>();
        bricks = new ArrayList<>();
        bombs = new ArrayList<>();
        map = Map.getInstance();
        var graphic2D = (Graphics2D) buffer.getGraphics();
        drawBaseMap(graphic2D);
        graphic2D.dispose();
        players[0] = new Bomberman(40, 40);
        gameControl = new GameControl(this);
        panelInformation = PanelInformation.getInstance();
        restart();
    }

    public static GameScreen getInstance() {
        return instance == null ? (instance = new GameScreen()) : instance;
    }

    @Override
    public final void restart() {
        eraseEnemies();
        bricks.clear();
        map.reset();
        window.restart();
        firstPlayer().restart(1, 1);
        firstPlayer().addPropertyChangeListener(this);
        bombs.clear();
        powerUp = false;
        portal = false;
        generateMap();
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public void setSize(final Dimension dimension) {
        panelInformation.setSize(dimension);
        var x1 = (int) Math.round(dimension.width / 16.0);
        var y1 = (int) Math.round(dimension.height / 14.0);
        Size = new Dimension(dimension.width * 2 - x1, dimension.height - y1);
    }

    @Override
    public void draw(final Graphics2D graphics2D) {
        panelInformation.draw((graphics2D));
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        var position = window.getPosition();
        graphics2D.translate(0, panelInformation.getHeight());
        graphics2D.scale(internalScaleX, 1);
        graphics2D.drawImage(buffer, 0, 0, position.width, position.height, position.x, position.y, position.x + position.width, position.y + position.height, null);
        graphics2D.translate(-position.x, position.y);
        drawScene(graphics2D);
        graphics2D.dispose();
    }

    private void drawScene(final Graphics2D graphics2D) {
        drawBombs(graphics2D);
        drawBricks(graphics2D);
        drawPlayers(graphics2D);
        gameControl.draw(graphics2D);
    }

    private void drawBaseMap(final Graphics2D graphics2D) {
        Color ground = new Color(80, 160, 0);
        graphics2D.setColor(ground);
        graphics2D.fillRect(xValue, yValue, Size.width - xValue * 2, Size.height - yValue * 2);
        for (short i = 0; i < Map.ROWS; i++) {
            for (short j = 0; j < Map.COLUMNS; j++) {
                if (map.contains(i, j, Wall.class)) {
                    graphics2D.drawImage(Images.WALL, j * xValue, i * yValue, xValue, yValue, null);
                }
            }
        }
    }

    public void drawPlayers(final Graphics2D graphics2D) {
        for (final var enemy:enemies) {
            if (window.contains(enemy)) {
                enemy.draw(graphics2D);
            }
        }
        firstPlayer().draw(graphics2D);
    }

    private void drawBricks(Graphics2D graphics2D) {
        for (final var brick : bricks) {
            if (window.contains(brick)) {
                brick.draw(graphics2D);
            }
        }
    }

    private void drawBombs(Graphics2D graphics2D) {
        for (final var bomb : bombs) {
            if (window.contains(bomb)) {
                bomb.draw(graphics2D);
            }
        }
    }

    public void erasePlayer(final int row, final int column) {
        if (!map.contains(row, column, firstPlayer())) {
            return;
        }
        firstPlayer().die();
        enemies.forEach(Entity::stopAI);
    }

    public void eraseEnemy(final int row, final int column) {
        for (final var enemy: enemies) {
            if (map.contains(row, column, enemy)) {
                enemy.death(this);
                panelInformation.increaseScore(enemy.getScore());
            }
        }
    }

    public void eraseEnemies() {
        for (var enemy : enemies) {
            enemy.stopAI();
        }
        enemies.clear();
    }

    public void eraseBrick(final int row, final int column) {
        var sprite = map.getSprite(row, column, Brick.class, Items.class);
        Stream.of(sprite).forEach((i) -> {
            if (i instanceof Brick && !(i.getCurrentState() instanceof NullState)) {
                ((Brick) i).destroy();
                return;
            }
            if (!(i instanceof Items)) {
                return;
            }
            var j = (Items) i;
            j.createEnemies(this);
            if (!j.isPortal()) {
                j.removePowerUp();
                map.delete(j);
            }
        });
    }

    public void eraseBomb(final int row, final int column) {
        for (final var bomb : bombs) {
            if (!(bomb.getCurrentState() instanceof DeathState) && map.contains(row, column, bomb)) {
                bomb.detonate(this);
                return;
            }
        }
    }

    public void eraseBomb(final Bomberman bomberman) {
        bombs.stream().filter(bomb -> (!bomb.hasDetonated() && bomb.belongsTo(bomberman))).forEachOrdered((bomb) -> bomb.detonate(this));
    }

    public int getEnemiesNumber() {
        return enemies.size();
    }

    public void enableAI() {
        for (final var enemy : enemies) {
            enemy.getAI().start();
        }
    }

    public static Bomberman firstPlayer() {
        return players[0];
    }

    @Override
    public void update(final long elapsedTime, final Game game) {
        window.update(firstPlayer().getCenter());
        updatePlayer(elapsedTime, game);
        updateBombs(elapsedTime);
        updateEnemies(elapsedTime);
        updateBricks(elapsedTime);
        gameControl.update();
    }
    private void updatePlayer(final long elapsedTime, final Game game) {

        final var player = firstPlayer();
        if (!player.isEnteredThePortal()) {
            player.update(this, elapsedTime);
        }
        map.update(player);
        if (player.getCurrentState() instanceof NullState) {
            map.delete(player);
            if (Sounds.getInstance().isPlaying(Sounds.JUST_DIED)) {
                return;
            }
            firstPlayer().removePropertyChangeListener(this);
            panelInformation.decreaseRemainingLives();
            panelInformation.stopCountdown();
            if (panelInformation.getRemainingLives() < 0) {
                panelInformation.setRemainingLives(2);
                panelInformation.setScore(0);
                MessageScreen.getInstance().setLevel((short) 1);
                players[0] = (Bomberman) Objects.getInstance("B");
                game.setScreen(Scene.GAME_OVER);
            } else {
                game.setScreen(Scene.STAGE);
            }
        } else {
            if (player.isEnteredThePortal()) {
                firstPlayer().removePropertyChangeListener(this);
                if (Sounds.getInstance().isPlaying(Sounds.LEVEL_COMPLETE)) {
                    return;
                }
                panelInformation.stopCountdown();
                MessageScreen.getInstance().increaseLevel();
                if (!MessageScreen.getInstance().endGame()) {
                    game.setScreen(Scene.STAGE);
                }
            }
        }
    }

    public void updateEnemies(long elapsedTime) {
        for (var i = 0; i < enemies.size(); i++) {
            final var enemy = enemies.get(i);
            enemy.update(this, elapsedTime);
            if (enemy.getCurrentState() instanceof NullState) {
                enemies.remove(i--);
                if (enemies.isEmpty()) {
                    if (!defeated) {
                        Sounds.getInstance().play(Sounds.PAUSE);
                    }
                    defeated = true;
                } else  {
                    defeated = false;
                }
            } else {
                if (!(enemy.getCurrentState() instanceof DeathState)) {
                    map.update(enemy);
                }
            }
        }
    }

    public void updateBricks(long elapseTime) {
        for (var i = 0; i < bricks.size(); i ++) {
            final var brick = bricks.get(i);
            brick.update(this, elapseTime);
            if (brick.getCurrentState() instanceof  NullState && !brick.hasItem()) {
                if (!map.delete(brick)) {
                    map.delete(brick.getItem());
                }
                bricks.remove(i--);
            }
        }
    }

    private void updateBombs(final long elapsedTime) {
        for (var i = 0; i < bombs.size(); i++) {
            var bomb = bombs.get(i);
            bomb.update(this, elapsedTime);
            if (bomb.getCurrentState() instanceof NullState) {
                this.map.delete(bomb);
                bombs.remove(i--);
            }
        }
    }

    private void generateMap() {
        int level = MessageScreen.getInstance().getLevel();
        final var random = new Random();
        int row;
        int column;
        int type;
        int enemyNumber = 5 + level / 10;
        int enemyTypeNumber = level <= 3 ? level
                : level < 7 ? 4
                : level < 10 ? 5
                : level < 14 ? 6
                : level < 48 ? 7 : 8;
        int enemyTypeIgnore = level <= 27 ? 0
                : level <= 32 ? 1
                : level <= 41 ? 2
                : level <= 44 ? 3 : 4;
        map.add(firstPlayer());
        for (var i = 0; i < 55; i++)
            do {
                row = random.nextInt(12);
                column = random.nextInt(30);
                if (column < 3 && row == 1 || column == 1 && row < 3)
                    continue;
                if (map.isEmpty(row, column)) {
                    addTile(random, "L", row, column);
                    break;
                }
            } while (true);
        for (var i = 0; i < enemyNumber; i++)
            do {
                row = random.nextInt(10) + 2;
                column = random.nextInt(28) + 2;
                type = random.nextInt(enemyTypeNumber - enemyTypeIgnore) + enemyTypeIgnore;
                if (map.isEmpty(row, column)) {
                    addTile(random, determinateEnemy(type), row, column);
                    break;
                }
            } while (true);
//        map.show();
    }

    public Map getMap() {
        return map;
    }

    private void addTile(final Random random, final String object, final int i, final int j) {
        var type = - 1;
        if (!portal) {
            portal = true;
            type = 8;
        } else {
            if (!powerUp) {
                powerUp = true;
                type = random.nextInt(6);
            }
        }
        final var sprite = object.equals("L") ? new Brick(j * xValue, i * yValue, type) : determinateEnemy(i, j, object);
        map.add(sprite);
        if (object.equals("L")) {
            bricks.add((Brick) sprite);
        } else {
            enemies.add((Enemy) sprite);
        }
    }

    public Enemy determinateEnemy(final int i, final int j, final String type) {
        final var enemy = Objects.getInstance(type);
        if (enemy != null) {
            enemy.setLocation(j * xValue, i * yValue);
        }
        return (Enemy) enemy;
    }

    public String determinateEnemy(final int id) {
        return id > 6 ? Objects.PONTAN.getValue() : Objects.getEnemies()[id].getValue();
    }

    public void addEnemy(int row, int column, String enemy) {
        var sprite = determinateEnemy(row, column, enemy);
        enemies.add(sprite);
        map.add(sprite);
        sprite.startAI();
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        var name = event.getPropertyName();
        var value = event.getNewValue();
        if (name.equals(Bomberman.Events.ADD_BOMB.name())) {
            Sounds.getInstance().play(Sounds.BOMB_PLANT);
            this.map.add((Sprite) value);
            this.bombs.add((Bomb) value);
        }
    }

}
