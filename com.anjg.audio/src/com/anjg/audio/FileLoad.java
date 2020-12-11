package com.anjg.audio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class FileLoad extends Thread{
    private static final File dir = new File("Saved audio");
    public String[] files;
    Path savePath;
    Path tempPath;

    public static FileLoad init(){

        FileLoad fileLoad = new FileLoad();
        fileLoad.savePath = Paths.get("Saved audio");
        fileLoad.tempPath = Paths.get("Temp");
        if(!Files.exists(fileLoad.savePath)){
            try{
            Files.createDirectory(fileLoad.savePath);
            System.out.println("Directory created");
            }catch(Exception e){e.printStackTrace();}
        } else {
            System.out.println("Directory already exists");
        }
        if(!Files.exists(fileLoad.tempPath)){
            try{
                Files.createDirectory(fileLoad.tempPath);
                System.out.println("Directory created");
            }catch(Exception e){e.printStackTrace();}
        } else {
            System.out.println("Directory already exists");
        }

        return fileLoad;
    }


    public static String[] lessExt(String[] inString) {
        if(inString.length > 0){
        for (int i = 0; i < inString.length; i++) {
            String trim = inString[i];
            String trimmed = trim.substring(0, trim.lastIndexOf('.'));
            inString[i] = trimmed;
        }} else {System.out.println("wavs not trimmed");}
        return inString;
    }

    public String[] getFiles(String[] bob) {
        files = null;
        files = lessExt(dir.list());
        return files;
    }

    public void run() {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        String loadFile = "Saved audio/" + Gui.loadBox.getSelectedItem().toString() + ".wav";
        File file = new File("Temp/Temp.wav");

        byte[] bArray = new byte[(int)1.2e7];

        try{
            fis = new FileInputStream(loadFile);
            fis.read(bArray);
            fis.close();
            System.out.println("File read");} catch (Exception e) {
            System.out.println("cannot find file to read");
        } try{
            fos = new FileOutputStream(file);
            fos.write(bArray);
            fos.close();
            System.out.println("file loaded");
        } catch (Exception e) {
            System.out.println("Saved audio/" + Gui.saveName.getText() + ".wav");
        }
    }

}
