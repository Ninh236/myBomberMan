package com.ninh236.mybomberman.entities.enemies.ai;

import com.ninh236.mybomberman.engine.core.game.states.bomberman.DeathState;
import com.ninh236.mybomberman.engine.core.input.Keymap;
import com.ninh236.mybomberman.engine.core.map.Map;
import com.ninh236.mybomberman.entities.Bomberman;
import com.ninh236.mybomberman.entities.Entity;
import com.ninh236.mybomberman.entities.blocks.Brick;
import com.ninh236.mybomberman.entities.blocks.items.Items;
import com.ninh236.mybomberman.entities.bomb.Bomb;
import com.ninh236.mybomberman.entities.enemies.ai.algorithm.BFS;
import com.ninh236.mybomberman.gui.GameScreen;

import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons;

import javax.swing.Timer;
import java.awt.*;
import java.util.*;

import static com.ninh236.mybomberman.engine.core.input.Keymap.Buttons.*;
import static com.ninh236.mybomberman.entities.Entity.*;

public class AI {

    private final ArrayList<Keymap.Buttons> buffer;
    private final Entity enemy;
    private final Bomberman player;
    private final Map map;
    private final Keymap keymap;
    private final Random random;
    private Timer timer;
    private int way = 1;
    private int time = -1;

    public AI(Entity enemy) {
        this.enemy = enemy;
        player = GameScreen.firstPlayer();
        keymap = enemy.getKeymap();
        buffer = new ArrayList<>();
        random = new Random();
        map = Map.getInstance();
        determineAI();
    }

    private void determineAI() {
        timer = enemy.getSmart() == SMART_IMPOSSIBLE ? null : new Timer(10, e -> {
            if (enemy.getSmart() == SMART_LOW) {
                if (time++ % 100 != 0) {
                    return;
                }
            } else {
                if (enemy.getSmart() == SMART_MID) {
                    if (time++ % 30 != 0) {
                        return;
                    }
                } else {
                    if (time++ % 25 != 0) {
                        return;
                    }
                }
            }

            switch (enemy.getSmart()) {
                case SMART_LOW -> SmartLowAI();
                case SMART_MID -> SmartMidAI();
                case SMART_HIGH -> SmartHighAI();
            }
        });
    }

    private void SmartLowAI() {
        bufferProcess((way = random.nextInt(4)) == 0 ? LEFT : way == 1 ? RIGHT : way == 2 ? UP : DOWN);
    }

    private void SmartMidAI() {
        if (Math.abs((enemy.getX() + 5) / enemy.getWidth() - (player.getX() + 5) / player.getWidth()) < 3
                && Math.abs((enemy.getY() + 5) / enemy.getHeight() - (player.getY() + 5) / player.getHeight()) < 3
                && !(player.getCurrentState() instanceof DeathState)) {
            boolean check = ChasingPlayer((enemy.getX() + 5) / enemy.getWidth()
                    , (enemy.getY() + 5) / enemy.getHeight()
                    , (player.getX() + 5) / player.getWidth()
                    , (player.getY() + 5) / player.getHeight());
            if (!check) {
                bufferProcess(random.nextInt(2) == 0 ? LEFT : RIGHT,
                        random.nextInt(2) == 0 ? UP : DOWN);
            }
        } else {
            bufferProcess(random.nextInt(2) == 0 ? LEFT : RIGHT,
                    random.nextInt(2) == 0 ? UP : DOWN);
        }
    }

    private void SmartHighAI() {
        if (Math.abs((enemy.getX() + 5) / enemy.getWidth() - (player.getX() + 5) / player.getWidth()) < 6
                && Math.abs((enemy.getY() + 5) / enemy.getHeight() - (player.getY() + 5) / player.getHeight()) < 6
                && !(player.getCurrentState() instanceof DeathState)) {
            boolean check = ChasingPlayer((enemy.getX() + 5) / enemy.getWidth()
                    , (enemy.getY() + 5) / enemy.getHeight()
                    , (player.getX() + 5) / player.getWidth()
                    , (player.getY() + 5) / player.getHeight());
            if (!check) {
                bufferProcess(random.nextInt(2) == 0 ? LEFT : RIGHT,
                        random.nextInt(2) == 0 ? UP : DOWN);
            }
        } else {
            bufferProcess(random.nextInt(2) == 0 ? LEFT : RIGHT,
                    random.nextInt(2) == 0 ? UP : DOWN);
        }
    }

    public void SmartImpossible() {

    }

    private boolean ChasingPlayer(int xPosition, int yPosition, int xDestination, int yDestination) {
        int[][] map = new int[13][31];
        for(int i = 0; i < 13; i++) {
            for (int j = 0; j < 31; j++) {
                if (i % 2 != 1 && j % 2 != 1 || i == 0 || i == 12 || j == 0 || j == 30
                        || (this.map.contains(i, j, Brick.class) && !this.map.contains(i, j, Items.class) && enemy.getSmart() < SMART_HIGH)
                        || this.map.contains(i, j, Bomb.class)) {
                    map[i][j] = 1;
                } else {
                    map [i][j] = 0;
                }
            }
        }
        Point[] path = BFS.getInstance().findPath(map, new Point(xPosition, yPosition), new Point(xDestination, yDestination));
        if (path != null) {
            if (!(player.getCurrentState() instanceof DeathState)) {
                if (path.length == 1) {
                    if (enemy.getX() > player.getX()) {
                        bufferProcess(LEFT);
                    }
                    if (enemy.getX() < player.getX()) {
                        bufferProcess(RIGHT);
                    }
                    if (enemy.getY() > player.getY()) {
                        bufferProcess(UP);
                    }
                    if (enemy.getY() < player.getY()) {
                        bufferProcess(DOWN);
                    }
                    return true;
                }
                if (path[0].x > path[1].x) {
                    if (((enemy.getX() + 1) % 40 <= 5) && ((enemy.getY() + 1) % 40 <= 5)) {
                        bufferProcess(LEFT);
                    } else {
                        if (enemy.getY() > player.getY()) {
                            bufferProcess(LEFT, UP);
                        } else {
                            bufferProcess(LEFT, DOWN);
                        }
                    }
                }
                if (path[0].x < path[1].x) {
                    if (((enemy.getX() + 1) % 40 <= 5) && ((enemy.getY() + 1) % 40 <= 5)) {
                        bufferProcess(RIGHT);
                    } else {
                        if (enemy.getY() > player.getY()) {
                            bufferProcess(RIGHT, UP);
                        } else {
                            bufferProcess(RIGHT, DOWN);
                        }
                    }
                }
                if (path[0].y > path[1].y) {
                    if (((enemy.getX() + 1) % 40 <= 5) && ((enemy.getY() + 1) % 40 <= 5)) {
                        bufferProcess(UP);
                    } else {
                        if (enemy.getX() > player.getX()) {
                            bufferProcess(UP, LEFT);
                        } else {
                            bufferProcess(UP, RIGHT);
                        }
                    }
                }
                if (path[0].y < path[1].y) {
                    if (((enemy.getX() + 1) % 40 <= 5) && ((enemy.getY() + 1) % 40 <= 5)) {
                        bufferProcess(DOWN);
                    } else {
                        if (enemy.getX() > player.getX()) {
                            bufferProcess(DOWN, LEFT);
                        } else {
                            bufferProcess(DOWN, RIGHT);
                        }
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public Timer getTimer() {
        return timer;
    }

    public void stop() {
        if (timer == null) {
            return;
        }
        timer.stop();
    }

    public void start() {
        if (timer == null) {
            return;
        }
        timer.start();
    }

    private void bufferProcess(Buttons... buttons) {
            bufferClear();
            buffer.addAll(Arrays.asList(buttons));
            bufferApply();
    }

    private void bufferClear() {
        buffer.forEach(key -> keymap.setPress(key, false));
        buffer.clear();
    }

    private void bufferApply() {
        for (int i = 0; i < 40; i++) {
            buffer.forEach(key -> keymap.setPress(key, true));
        }
    }

}
