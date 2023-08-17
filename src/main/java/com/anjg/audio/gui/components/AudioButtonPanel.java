package com.anjg.audio.gui.components;

import com.anjg.audio.gui.GuiButtons;
import com.anjg.audio.gui.Listeners.RecordListener;

import javax.swing.*;
import java.awt.*;

public class AudioButtonPanel extends JPanel {
    JButton recordButton;
    JButton playButton;
    JButton stopButton;
    public AudioButtonPanel() {
        recordButton = new JButton(GuiButtons.RECORD.getLabel());
        playButton = new JButton(GuiButtons.PLAY.getLabel());
        //playButton.addActionListener(new Gui.playListener());
        stopButton = new JButton(GuiButtons.STOP.getLabel());
        //stopButton.addActionListener(new Gui.StopListener());
        recordButton.addActionListener(new RecordListener(recordButton));

        this.add(recordButton);
        this.add(playButton);
        this.add(stopButton);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
    }
}
