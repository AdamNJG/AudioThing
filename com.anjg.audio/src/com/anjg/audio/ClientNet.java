package com.anjg.audio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;


public class ClientNet extends Network{
    SendingFile sf;

    public String ip = "127.0.0.1";

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void run() {
        try {
            Gui.netStatus.setText("Trying to connect to " + ip);
            sock = SocketChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(ip, 9999);
            sock.connect(socketAddress);
            Gui.netStatus.setText("Connected");
            isClient = true;
        } catch (IOException ex) {
            isClient = false;
            Gui.netStatus.setText("Connection Failed");
            ex.printStackTrace();
        }

        sf = new SendingFile();
        sf.receive();
    }


}
