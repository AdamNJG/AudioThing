package com.anjg.audio;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;


public class HostNet extends Network{

    InputStream inputStream;




    public void run() {

        try {
            InetAddress inetAddress = InetAddress.getLocalHost();
            serverSock = ServerSocketChannel.open();
            serverSock.socket().bind(new InetSocketAddress(9999));
            Gui.netStatus.setText("Listening for clients @" + inetAddress.getHostAddress());
            System.out.println("Listening for clients @" + inetAddress.getHostAddress());
            sock = serverSock.accept();
            isHost = true;
            Gui.netStatus.setText("Hosting");
        } catch(IOException ex) {
            isHost = false;
            Gui.netStatus.setText("Connection Failed");
            ex.printStackTrace(); }

        SendingFile sf = new SendingFile();

        sf.receive();

    }



}
