package com.anjg.audio.gui;

public enum GuiButtons {
    RECORD("Record"),
    STOP("Stop"),
    PLAY("Play"),
    PAUSE("Pause"),
    SAVE("Save"),
    SEND("Send"),
    LOAD("Load");

    private String label;

    private GuiButtons(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
