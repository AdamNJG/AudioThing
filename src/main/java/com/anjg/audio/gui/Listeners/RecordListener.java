package com.anjg.audio.gui.Listeners;

import com.anjg.audio.Audio;
import com.anjg.audio.Recording;
import com.anjg.audio.recorder.RecordingHandler;
import com.anjg.audio.gui.GuiButtons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecordListener implements ActionListener {
    JButton saveButton;
    JButton recordButton;
    Recording recorder;

    public RecordListener(JButton recordButton) {
        this.recordButton = recordButton;
        this.recorder = RecordingHandler.getInstance();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ((recordButton.getText()).contains(GuiButtons.STOP.getLabel())) {
            recorder.finish();
            recordButton.setText(GuiButtons.RECORD.getLabel());
        } else {
            recordButton.setText(GuiButtons.STOP.getLabel());
            recorder = new Recording();;
            Thread stopper = new Thread(() -> {
                try {
                    Thread.sleep(Audio.RECORD_TIME);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                recorder.finish();
                recordButton.setText(GuiButtons.RECORD.getLabel());
            });
            recorder.start();
            stopper.start();
        }
    }
}