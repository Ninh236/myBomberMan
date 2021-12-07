package com.ninh236.mybomberman.engine.core.java.resources;

import com.ninh236.mybomberman.engine.core.Resource;
import com.ninh236.mybomberman.media.tools.FileManager;

import javax.sound.sampled.*;
import java.io.IOException;

public class SoundResource  implements Resource<Clip> {

    public static final int WAV = 1;

    @Override
    public Clip load (String path, int type) {
        if (type == WAV) {
            return FileManager.getInstance().loadClipJar(path);
        }
        Clip clip = null;
        try {
            var inputStream = AudioSystem.getAudioInputStream(getClass().getResource(path));
            AudioInputStream otherInputStream;
            var baseFormat = inputStream.getFormat();
            var decodedFormat = new AudioFormat(baseFormat.getSampleRate(), 16, baseFormat.getChannels(), false, false);
            otherInputStream = AudioSystem.getAudioInputStream(decodedFormat, inputStream);
            clip = AudioSystem.getClip();
            clip.open(otherInputStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException exception) {
            System.err.println(exception.getMessage());
        }
        return clip;
    }
}
