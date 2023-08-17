package com.anjg.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileSave extends Thread{
    public void run() {

        File file = new File("Temp/Temp.wav");
        File saveFile = new File("Saved audio/" + Gui.saveName.getText() + ".wav");

        byte[] bArray = new byte[(int)1.2e7];

        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(file);
            fis.read(bArray);
            fis.close();
            System.out.println("File read");} catch (Exception e) {
            System.out.println("cannot find file to read");
        } try{
            fos = new FileOutputStream(saveFile);
            fos.write(bArray);
            fos.close();
            System.out.println("file saved");
        } catch (Exception e) {
            System.out.println("Saved audio/" + Gui.saveName.getText() + ".wav");
        }

    }
}
