package com.ninh236.mybomberman.media.tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;

public class ImageUtilities {

    static public BufferedImage invertImage(BufferedImage image) {
        if (image == null) {
            return null;
        }
        Color colorPixel;
        for (int i = 0, rgb; i < image.getWidth(); i++) {
            for (var j = 0; j < image.getHeight(); j ++) {
                colorPixel = new Color(image.getRGB(i, j));
                rgb = new Color(255 - colorPixel.getRed(), 255 - colorPixel.getGreen(), 255 - colorPixel.getBlue()).getRGB();
                image.setRGB(i, j, rgb);
            }
        }
        return image;
    }

    static public BufferedImage convertImage(Image image) {
        if (image == null) {
            return null;
        }
        var img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        img.createGraphics().drawImage(image, 0, 0, null);
        return img;
    }

    static public Image invertImage(Image image) {
        return invertImage(convertImage(image));
    }

    public static BufferedImage createCompatibleImage(final int width, final int height, final int transparency) {
        var graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        return graphicsConfiguration.createCompatibleImage(width, height, transparency);
    }

    public static BufferedImage createCompatibleImage(final int width, final int height) {
        return createCompatibleImage(width, height, Transparency.BITMASK);
    }

    public static BufferedImage createCompatibleImage(final BufferedImage image) {
        var graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        if (image.getColorModel().equals(graphicsConfiguration.getColorModel())) {
            System.out.println("Optimized");
            return image;
        }
        var newImage = graphicsConfiguration.createCompatibleImage(image.getWidth(), image.getHeight(), Transparency.BITMASK);
        var graphics = newImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        return newImage;
    }

    public static BufferedImage createCompatibleImage(final Image image) {
        var graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        var newImage = graphicsConfiguration.createCompatibleImage(image.getWidth(null), image.getHeight(null), Transparency.BITMASK);
        var graphics = newImage.createGraphics();
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        image.flush();
        return newImage;
    }

    public static VolatileImage createCompatibleVolatileImage(final int width, final int height, final int transparency) {
        var graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        var newImage = graphicsConfiguration.createCompatibleVolatileImage(width, height, transparency);
        if (transparency == Transparency.TRANSLUCENT) {
            var graphics = newImage.createGraphics();
            graphics.setComposite(AlphaComposite.Src);
            graphics.dispose();
        }
//        System.out.println(newImage.getCapabilities().isAccelerated());
        return newImage;
    }

    public static synchronized VolatileImage createCompatibleVolatileImage(Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        var graphicsConfiguration = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        var newImage = graphicsConfiguration.createCompatibleVolatileImage(width, height, Transparency.TRANSLUCENT);
        var graphics = newImage.createGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.clearRect(0, 0, width, height);
        graphics.drawImage(image, 0, 0, null);
        graphics.dispose();
        image.flush();
//        System.out.println(newImage.getCapabilities().isAccelerated());
        return newImage;
    }
}
