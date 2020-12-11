package com.anjg.audio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class SendingFile extends Thread{

    static String file = "Temp/Temp.wav";
    static String recFile = "Saved audio/Received.wav";

        public void run() {
            try(RandomAccessFile reader = new RandomAccessFile(file, "r");
                FileChannel readChannel = reader.getChannel())
            {
                int bufferSize = 1024;
                if (bufferSize > readChannel.size()){
                    bufferSize = (int) readChannel.size();
                }
                ByteBuffer buff = ByteBuffer.allocate(bufferSize);

                while (readChannel.read(buff) > 0){
                    buff.flip();
                    Network.sock.write(buff);
                    buff.clear();
                }
                Network.sock.shutdownOutput();
            }catch(IOException ex){ex.printStackTrace();}
        }


        public void receive(){
            try (RandomAccessFile writer = new RandomAccessFile(recFile, "rw");
            FileChannel writeChannel = writer.getChannel()
            ){
                ByteBuffer buff = ByteBuffer.allocate(1024);
                while(Network.sock.read(buff) > 0){
                    buff.flip();
                    writeChannel.write(buff);
                    buff.clear();
                }
                Network.sock.shutdownInput();
            }catch(IOException ex){ex.printStackTrace();}
            Gui.loadBox.addItem("Received");
        }


}



