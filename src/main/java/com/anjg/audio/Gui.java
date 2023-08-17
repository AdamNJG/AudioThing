package com.anjg.audio;

import com.anjg.audio.gui.GuiButtons;
import com.anjg.audio.gui.components.AudioButtonPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;


public class Gui{
    boolean isPlaying;
    static JButton recButton;
    JButton playButton;
    static JButton saveButton;
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
    volatile Recording recorder;
    volatile Playback player;
    static JComboBox loadBox;
    FileLoad fileLoad;
    ClientNet clientNet;
    Network hostNet;
    SendingFile sf;

    public Gui(){
        isPlaying = false;
        recorder = new Recording();
        player = new Playback();
    }

    public void run() {
        fileLoad = FileLoad.init();
        JFrame frame = new JFrame();
        JPanel mainPanel = new JPanel();

        mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.Y_AXIS));

        mainPanel.add(new AudioButtonPanel());
        addPersistanceButtons(mainPanel);
        addNetworkButtons(mainPanel);

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

    private void addPersistanceButtons(JPanel mainPanel) {
        saveButton = new JButton(GuiButtons.SAVE.getLabel());
        saveButton.addActionListener(new SaveListener());
        saveButton.setEnabled(false);
        saveName = new JTextField("Save file name",20);
        loadButton = new JButton(GuiButtons.LOAD.getLabel());
        loadButton.addActionListener(new LoadListener());
        loadBox = new JComboBox(fileLoad.getFiles(fileLoad.files));
        loadBox.setPrototypeDisplayValue("                      ");
        delButton = new JButton("Delete");
        delButton.addActionListener(new DelListener());

        saveName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {saveName.setText("");}

            @Override
            public void focusLost(FocusEvent e) {}
        });

        JPanel persistPanel = new JPanel();
        persistPanel.add(loadBox);
        persistPanel.add(loadButton);
        persistPanel.add(delButton);
        persistPanel.add(saveName);
        persistPanel.add(saveButton);
        persistPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        mainPanel.add(persistPanel);
    }

    private void addNetworkButtons(JPanel mainPanel){
        hostButton = new JButton("Host");
        netStatus = new JTextArea(1, 20);
        hostButton.addActionListener(new HostListener());
        resetButton = new JButton("Reset Network");
        resetButton.addActionListener(new ResetListener());

        connectButton = new JButton("Connect");
        connectButton.addActionListener(new ConnectListener());

        ipField = new JTextField("Host IP address",15);
        ipField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                ipField.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {}
        });

        sendButton = new JButton(GuiButtons.SEND.getLabel());
        sendButton.addActionListener(new SendListener());

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
            String ipPattern = "^(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.(?!$)|$)){4}$";
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
            playButton.setText(GuiButtons.PLAY.getLabel());
            loadButton.setEnabled(true);
            player.stopClip();
            player = null;
            isPlaying = false;
        }
    }

    public class LoadListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            saveButton.setEnabled(true);
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
