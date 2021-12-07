package com.ninh236.mybomberman.entities.blocks;

import com.ninh236.mybomberman.engine.core.game.Screen;
import com.ninh236.mybomberman.engine.core.game.states.brick.DeathState;
import com.ninh236.mybomberman.engine.core.graphics.AnimationWrapper;
import com.ninh236.mybomberman.engine.core.graphics.Image;
import com.ninh236.mybomberman.engine.core.graphics.Sprite;
import com.ninh236.mybomberman.engine.core.graphics.spriteistate.initial.EmptyState;
import com.ninh236.mybomberman.engine.core.map.Map;
import com.ninh236.mybomberman.entities.blocks.items.Items;
import com.ninh236.mybomberman.media.Images;

import java.awt.*;
import java.util.HashMap;

public class Brick extends Sprite {

    private final int type;
    private boolean hasItem;
    private Items item;

    public Brick(final int xValue, final int yValue, final int type) {
        super(new Image(Images.BRICK, 6, 6, (float) 2.5), xValue, yValue);
        id = "L";
        this.type = type;
        if (type != -1) {
            hasItem = true;
        }
        init();
    }

    public final void init() {
        super.animations = new HashMap<>() {
            {
                put(EmptyState.class, new AnimationWrapper(0, "0", 4000/60));
                put(DeathState.class, new AnimationWrapper(5, "0,1,2,3,4,5", 4000 / 60));
            }
        };
        setCurrentState(EmptyState::new);
    }

    public Items getItem() {
        return item;
    }

    public boolean hasItem() {
        return hasItem && !item.isRemovedStatus();
    }

    @Override
    public void update (Screen screen, long elapsedTime) {
        super.update(screen, elapsedTime);
        if (item != null) {
            item.update(screen, elapsedTime);
        }
    }

    @Override
    public void draw(final Graphics2D graphics2D) {
        super.draw(graphics2D);
        if (item != null) {
            item.draw(graphics2D);
        }
    }

    public void activateItem() {
        if (!hasItem) {
            return;
        }
        var map = Map.getInstance();
        item = new Items(xValue, yValue, type);
        map.delete(this);
        id = "I";
        map.add(item);
    }

    public void destroy() {
        setCurrentState(DeathState::new);
    }

}
