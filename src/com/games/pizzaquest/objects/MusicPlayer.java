package com.games.pizzaquest.objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicPlayer {
    static final String filename = "/music/Tarantella.wav";
    private static Clip clip = null;

    public static void playMusic(){
        try (InputStream in = MusicPlayer.class.getResourceAsStream(filename)){
            InputStream bufferedIn = new BufferedInputStream(in);
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)){
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Plays music continuously
            }
        }catch (Exception e){
            System.out.println("Music file not found!!!");
        }
    }

    public static void stopMusic() {
        clip.stop();
    }
}