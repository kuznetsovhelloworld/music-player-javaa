package mp3;

import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

/**
 * The MusicPlayerUI class represents the user interface of a music player application.
 * It extends JPanel and  1implements ActionListener to handle user actions.
 */
public class MusicPlayerUI extends JPanel implements ActionListener {
    private JLabel songName;
    private JButton select;
    private JButton play;
    private JButton pause;
    private JButton resume;
    private JButton stop;
    private JFileChooser fileChooser;
    private FileInputStream fileInputStream;
    private BufferedInputStream bufferedInputStream;
    private File myFile;
    private String filename;
    private long totalLength;
    private long pauseLength;
    private Player player;
    private Thread playThread;
    private Thread resumeThread;

    /**
     * Constructs a MusicPlayerUI object.
     * Initializes the UI components and sets up the action events.
     */
    public MusicPlayerUI() {
        setLayout(new BorderLayout());
        initComponents();
        addActionEvents();
        playThread = new Thread(runnablePlay);
        resumeThread = new Thread(runnableResume);
    }

    /**
     * Initializes the UI components.
     */
    private void initComponents() {
        JPanel playerPanel = new JPanel();
        JPanel controlPanel = new JPanel();

        songName = new JLabel("", SwingConstants.CENTER);
        select = new JButton("Select Mp3");
        play = new JButton(new ImageIcon("/Users/bigmag/Downloads/music-player-java-code/Mp3MusicPlayer/music-player-icons/play-button.png"));
        pause = new JButton(new ImageIcon("/Users/bigmag/Downloads/music-player-java-code/Mp3MusicPlayer/music-player-icons/pause-button.png"));
        resume = new JButton(new ImageIcon("/Users/bigmag/Downloads/music-player-java-code/Mp3MusicPlayer/music-player-icons/resume-button.png"));
        stop = new JButton(new ImageIcon("/Users/bigmag/Downloads/music-player-java-code/Mp3MusicPlayer/music-player-icons/stop-button.png"));

        playerPanel.setLayout(new GridLayout(2, 1));
        playerPanel.add(select);
        playerPanel.add(songName);

        controlPanel.setLayout(new GridLayout(1, 4));
        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(resume);
        controlPanel.add(stop);

        play.setBackground(Color.WHITE);
        pause.setBackground(Color.WHITE);
        resume.setBackground(Color.WHITE);
        stop.setBackground(Color.WHITE);

        add(playerPanel, BorderLayout.NORTH);
        add(controlPanel, BorderLayout.SOUTH);
    }

    /**
     * Sets up the action events for UI components.
     */
    private void addActionEvents() {
        select.addActionListener(this);
        play.addActionListener(this);
        pause.addActionListener(this);
        resume.addActionListener(this);
        stop.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(select)) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("/Users/bigmag"));
            fileChooser.setDialogTitle("Select Mp3");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(select) == JFileChooser.APPROVE_OPTION) {
                myFile = fileChooser.getSelectedFile();
                filename = fileChooser.getSelectedFile().getName();
                String filePath = fileChooser.getSelectedFile().getPath();
                songName.setText("File Selected: " + filename);
            }
        }
        if (e.getSource().equals(play)) {
            if (filename != null) {
                playThread.start();
                songName.setText("Now playing: " + filename);
            } else {
                songName.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(pause)) {
            if (player != null && filename != null) {
                try {
                    pauseLength = fileInputStream.available();
                    player.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        if (e.getSource().equals(resume)) {
            if (filename != null) {
                resumeThread.start();
            } else {
                songName.setText("No File was selected!");
            }
        }
        if (e.getSource().equals(stop)) {
            if (player != null) {
                player.close();
                songName.setText("");
            }
        }
    }

    private Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                totalLength = fileInputStream.available();
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
            try {
                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(totalLength - pauseLength);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
