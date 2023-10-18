package mp3;

import javax.swing.*;
import java.awt.*;


public class MusicPlayer {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame();
            frame.setTitle("Music Player");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Set the layout and add the MusicPlayerUI panel to the frame
            frame.getContentPane().setLayout(new BorderLayout());
            frame.getContentPane().add(new MusicPlayerUI(), BorderLayout.CENTER);

            // Set the frame properties
            frame.setSize(400, 200);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
