package com.anjg.audio;

import javax.sound.sampled.*;
import java.io.IOException;

public class Playback extends Audio{
    AudioInputStream stream;
    DataLine.Info info;
    volatile Clip clip;
    volatile long clipTime;

    public void run() {
        try{
            stream = AudioSystem.getAudioInputStream(tempFile);
            info = new DataLine.Info(Clip.class, getAudioFormat());
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
        } catch(LineUnavailableException lex) {lex.printStackTrace();
        } catch(UnsupportedAudioFileException uex) {uex.printStackTrace();
        } catch(IOException iex) {iex.printStackTrace();}
    }

    public void pause() {
        clipTime = clip.getMicrosecondPosition();
        clip.stop();
    }

    public void cont() {
        clip.setMicrosecondPosition(clipTime);
        clip.start();
    }

    public void stopClip() {
        clip.close();
        clip = null;
    }
}


