package com.anjg.audio;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

public class Gui{

    final String RECORD = "Record";
    final String STOP = "Stop";
    final String PLAY = "Play";
    final String PAUSE = "Pause";
    final String SAVE = "Save";
    final String SEND = "Send";
    final String LOAD = "Load";

    boolean isPlaying = false;
    JButton recButton;
    JButton playButton;
    JButton saveButton;
    JButton sendButton;
    JButton connectButton;
    JButton hostButton;
    JButton resetButton;
    JButton stopButton;
    JButton loadButton;
    JButton delButton;
    static JTextField ipField;
    static JTextField saveName;
    static JTextArea netStatus;
    volatile Recording recorder = new Recording();
    volatile Playback player = new Playback();
    static JComboBox loadBox;
    FileLoad fileLoad;


    String ipPattern = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";

    ClientNet clientNet;
    Network hostNet;
    SendingFile sf;


    public void go() {
        fileLoad = FileLoad.init();
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();

        recButton = new JButton(RECORD);
        recButton.addActionListener(new recListener());
        playButton = new JButton(PLAY);
        playButton.addActionListener(new playListener());
        sendButton = new JButton(SEND);
        sendButton.addActionListener(new SendListener());
        saveButton = new JButton(SAVE);
        saveButton.addActionListener(new SaveListener());
        saveButton.setEnabled(false);
        saveName = new JTextField("File name",20);
        stopButton = new JButton(STOP);
        stopButton.addActionListener(new StopListener());
        loadButton = new JButton(LOAD);
        loadButton.addActionListener(new LoadListener());
        hostButton = new JButton("Host");
        netStatus = new JTextArea(1, 20);
        hostButton.addActionListener(new HostListener());
        resetButton = new JButton("Reset Network");
        resetButton.addActionListener(new ResetListener());
        delButton = new JButton("Delete");
        delButton.addActionListener(new DelListener());
        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectListener());
        loadBox = new JComboBox(fileLoad.getFiles(fileLoad.files));
        loadBox.setPrototypeDisplayValue("                      ");
        ipField = new JTextField("Host IP address",15);
        ipField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ipField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });
        saveName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

                saveName.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {

            }
        });

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        JPanel audioPanel = new JPanel();
        audioPanel.add(recButton);
        audioPanel.add(playButton);
        audioPanel.add(stopButton);
        audioPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(audioPanel);

        JPanel loadPanel = new JPanel();
        loadPanel.add(loadBox);
        loadPanel.add(loadButton);
        loadPanel.add(delButton);
        loadPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(loadPanel);

        JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.add(saveName);
        savePanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(savePanel);

        JPanel connectPanel = new JPanel();
        connectPanel.add(connectButton);
        connectPanel.add(ipField);
        connectPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(connectPanel);

        JPanel netPanel = new JPanel();
        netPanel.add(hostButton);
        netPanel.add(netStatus);
        netPanel.add(resetButton);
        mainPanel.add(netPanel);

        JPanel sendPanel = new JPanel();
        sendPanel.add(sendButton);
        mainPanel.add(sendPanel);


        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
        frame.setSize(400, 450);
        frame.setVisible(true);
    }


    public void disConButtons() {
        connectButton.setEnabled(false);
        hostButton.setEnabled(false);
    }

    public void resConButtons() {
        connectButton.setEnabled(true);
        hostButton.setEnabled(true);
    }


    public class recListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            saveButton.setEnabled(true);
            if ((recButton.getText()).contains(STOP)) {
                recorder.finish();
                recButton.setText(RECORD);
            } else {
                recButton.setText(STOP);
                recorder = new Recording();;
                Thread stopper = new Thread(() -> {
                    try {
                        Thread.sleep(recorder.RECORD_TIME);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    recorder.finish();
                    recButton.setText(RECORD);
                });
                recorder.start();
                stopper.start();

            }

        }
    }

    public class playListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            loadButton.setEnabled(false);

            if (playButton.getText().contains(PLAY) && !isPlaying){
                playButton.setText(PAUSE);
                player = new Playback();
                player.start();
                isPlaying = true;
            } else if(playButton.getText().contains(PAUSE)){
                playButton.setText(PLAY);
                player.pause();
            } else if(playButton.getText().contains(PLAY) && isPlaying){
                playButton.setText(PAUSE);
                player.cont();
            }

        }
            }

    public class SaveListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            FileSave fs = new FileSave();
            fs.start();
            loadBox.addItem(Gui.saveName.getText());
        }
    }

    public class ConnectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            clientNet = new ClientNet();
            if(ipField.getText().matches(ipPattern)){
                String newIp = ipField.getText();
                clientNet.setIp(newIp);
                clientNet.start();
                disConButtons();
            ipField.setText(""); } else {
                clientNet.start();
                disConButtons();
            }
        }
    }



    public class HostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
                hostNet = new HostNet();
                hostNet.start();
                disConButtons();
        }
    }

    public class SendListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {

            sf = new SendingFile();
            sf.start();
            try {
                Thread.sleep(30);
                sf = null;
            }catch(Exception e){e.printStackTrace();}

        }
    }

    public class ResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            clientNet = null;
            Network.closeServPort();
            hostNet = null;
            sf =null;
            resConButtons();
            netStatus.setText("");
        }
    }

    public class StopListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playButton.setText(PLAY);
            loadButton.setEnabled(true);
            player.stopClip();
            player = null;
            isPlaying = false;
        }
    }

    public class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            FileLoad fl = new FileLoad();
            fl.start();
        }
    }

    public class DelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            String delName = "Saved audio/" + Gui.loadBox.getSelectedItem().toString() + ".wav";
            File delFile = new File(delName);
            delFile.delete();
            loadBox.removeItem(loadBox.getSelectedItem());
        }
    }
}
