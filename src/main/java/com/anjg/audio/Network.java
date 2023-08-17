package com.anjg.audio;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public abstract class Network extends Thread{
    static ServerSocketChannel serverSock;
    static SocketChannel sock;
    boolean isHost;
    boolean isClient;

    public static void closeServPort() {
        try {
            serverSock.close();
        }catch(Exception e){e.printStackTrace();}
    }
}
