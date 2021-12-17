package com.ninh236.mybomberman.media.tools;

import com.ninh236.mybomberman.Bomberman;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class FileManager {

    private static FileManager instance;

    private FileManager() {
    }

    public static FileManager getInstance() {
        return instance == null ? (instance = new FileManager()) : instance;
    }

    public boolean saveFile(File file, String string) {
        try {
            try (var printWriter = new PrintWriter(file)) {
                printWriter.print(string);
            }
            return true;
        } catch (Exception exception) {
            System.err.println(exception.getMessage());
        }
        return false;
    }

    public String loadFile(File file) {
        try {
            var bufferedReader = new BufferedReader(new FileReader(file));
            var stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                if (bufferedReader.ready())
                    stringBuilder.append((System.lineSeparator()));
            }
            return stringBuilder.toString();
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public String loadFile(String path) {
        return loadFile(new File(path));
    }

    public BufferedImage loadBufferedImage(File file) {
        if (file == null || !file.exists()) {
            return null;
        }
        try {
            return ImageUtilities.createCompatibleImage(ImageIO.read(file));
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
        }
        return null;
    }

    public BufferedImage loadBufferedImage(String path) {
        return loadBufferedImage(new File(path));
    }

    public Image loadImage(File file) {
        return loadBufferedImage(file);
    }

    public Image loadImage(String path) {
        return loadImage(new File(path));
    }

    public Icon loadIcon(File file) {
        return loadImage(file) == null ? null : new ImageIcon(loadImage(file));
    }

    public Icon loadIcon(String path) {
        return loadIcon(new File(path));
    }

    public boolean saveImage(BufferedImage image, String formatName, File file) {
        try {
            ImageIO.write(image, formatName, file);
        } catch (IOException exception) {
            System.err.println(exception.getMessage());
            return false;
        }
        return true;
    }

    public boolean saveImage(BufferedImage image, File file) {
        return saveImage(image, file.getPath().substring(file.getPath().length() - 3), file);

    }

    public boolean saveImage(BufferedImage image, String path) {
        return saveImage(image, new File(path));
    }

    public boolean saveImage(Image image, String formatName, File file) {
        return saveImage(ImageUtilities.convertImage(image), formatName, file);
    }

    public synchronized BufferedImage loadBufferedImageJAR(String path) {
        return ImageUtilities.createCompatibleImage(loadImageJAR(path));
    }

    public synchronized VolatileImage loadVolatileImageJAR(String path) {
        return ImageUtilities.createCompatibleVolatileImage(loadImageJAR(path));
    }

    public ImageIcon loadImageIconJAR(String path) {
        return new ImageIcon(Objects.requireNonNull(Bomberman.class.getResource(path)));
    }

    public Image loadImageJAR(String path) {
        return loadImageIconJAR(path).getImage();
    }

    public Icon loadIconJAR(String path) {
        return loadImageIconJAR(path);
    }

    public Clip loadClipJar(String path) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Objects.requireNonNull(Bomberman.class.getResource(path))));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
            System.err.println(exception.getMessage());
        }
        return clip;
    }

    public Clip loadClip(String path) {
        Clip clip = null;
        try {
            clip = loadClip(new URL(path));
        } catch (MalformedURLException exception) {
            System.err.println(exception.getMessage());
        }
        return clip;
    }

    public Clip loadClip(URL path) {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(path));
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
            System.err.println(exception.getMessage());
        }
        return clip;
    }
}
