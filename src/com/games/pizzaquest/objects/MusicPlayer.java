package com.games.pizzaquest.objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class MusicPlayer {
    static final String filename = "/music/Tarantella.wav";
    static Clip clip = null;

    public static void playMusic(int volume){

        try (InputStream in = MusicPlayer.class.getResourceAsStream(filename)){
            InputStream bufferedIn = new BufferedInputStream(in);
            try (AudioInputStream audioIn = AudioSystem.getAudioInputStream(bufferedIn)){
                clip = AudioSystem.getClip();
                clip.open(audioIn);
                setVolume(volume);
                clip.loop(Clip.LOOP_CONTINUOUSLY); // Plays music continuously
            }
        }catch (Exception e){
            System.out.println("Music file not found!!!");
        }
    }
    public float getVolume() {
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return (float) Math.pow(10f, gainControl.getValue() / 20f);
    }

    public static void setVolume(float volume) {
        volume *= 0.01;
        if (volume < 0f || volume > 1f)
            throw new IllegalArgumentException("Volume not valid: " + volume);
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(40f * (float) Math.log10(volume));
    }

    public static void stopMusic() {
        clip.stop();
    }
}