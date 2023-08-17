package com.anjg.audio.recorder;

import com.anjg.audio.Recording;

public class RecordingHandler {
    private static Recording instance;

    public static Recording getInstance() {
        if(instance == null) {
            instance = new Recording();
        }
        return instance;
    }
}
