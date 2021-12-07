package com.ninh236.mybomberman.engine.core.java.resources;

import com.ninh236.mybomberman.engine.core.Resource;
import com.ninh236.mybomberman.media.tools.FileManager;

import java.awt.*;

public class ImageResource implements Resource<Image> {

    public static final int VOLATILE = 0, BUFFERED = 2;

    @Override
    public Image load(String path, int type) {
        final var fileManager = FileManager.getInstance();
        return type == VOLATILE ? fileManager.loadVolatileImageJAR(path) : fileManager.loadBufferedImageJAR(path);
    }
}
