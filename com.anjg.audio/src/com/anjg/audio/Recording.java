package com.anjg.audio;

import javax.sound.sampled.*;


public class Recording extends Audio{

    TargetDataLine line;


    public void run() {
        try {

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, getAudioFormat());

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                Gui.recButton.setText("No Line");
                Gui.recButton.setEnabled(false);
                Gui.saveButton.setEnabled(false);
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(getAudioFormat());
            line.start();

            System.out.println("Start capturing...");

            AudioInputStream ais = new AudioInputStream(line);

            System.out.println("Start recording...");

            AudioSystem.write(ais, fileType,tempFile);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    void finish() {
        line.stop();
        line.flush();
        line.close();
        System.out.println("Finished recording");
    }



    public static void main(String[] args) {
        final Recording recorder = new Recording();

        Thread stopper = new Thread(() -> {
            try {
                Thread.sleep(RECORD_TIME);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            recorder.finish();
        });

        recorder.run();
        stopper.start();
    }
}

