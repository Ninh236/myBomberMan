package com.ninh236.mybomberman;
import com.ninh236.mybomberman.engine.core.java.resources.ImageResource;
import com.ninh236.mybomberman.media.tools.FileManager;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static com.ninh236.mybomberman.engine.core.java.resources.ImageResource.VOLATILE;

public class Test {

    public static void main(String[] args) {
//        play("src/main/resources/com/ninh236/mybomberman/Stage_Theme.wav");
        ImageResource imageResource = new ImageResource();
        imageResource.load("Stage_Theme.wav", VOLATILE);
    }

    public static void play(String filename)
    {
            try
            {
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new File(filename)));
                clip.start();
            }
            catch (Exception exc)
            {
                exc.printStackTrace(System.out);
            }
        }
}
