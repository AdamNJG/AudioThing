package com.anjg.audio;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import java.io.File;

public class Audio extends Thread{

    static final long RECORD_TIME = 60000;
    AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    File tempFile = new File("Temp/Temp.wav");

    AudioFormat getAudioFormat() {
        float sampleRate = 42000;
        int sampleSizeInBits = 16;
        int channels = 2;
        boolean signed = true;
        boolean bigEndian = true;
        AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits,
                channels, signed, bigEndian);
        return format;
    }
    void finish(){}

    public void run(){}
}
