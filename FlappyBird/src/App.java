import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.*;
import java.io.File;

public class App {

    public static Clip bgmClip; // Jadikan variabel global agar bisa diakses Logic.java

    public static void playBGM() {
        try {
            // Gunakan getResource agar jalurnya selalu akurat dari dalam program
            java.net.URL audioUrl = App.class.getResource("/assets/Flappy Bird Theme Song.wav");

            if (audioUrl != null) {
                if (bgmClip == null) {
                    AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioUrl);
                    bgmClip = AudioSystem.getClip();
                    bgmClip.open(audioStream);
                }
                bgmClip.setFramePosition(0); // Mulai lagu dari detik ke-0
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                System.out.println("File audio tidak ditemukan di dalam folder build!");
            }
        } catch (Exception ex) {
            System.out.println("Gagal memutar audio: " + ex.getMessage());
        }
    }

    public static void stopBGM() {
        if (bgmClip != null && bgmClip.isRunning()) {
            bgmClip.stop(); // Hentikan pemutaran musik
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FlappyBird");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        JPanel mainMenu = new JPanel();
        mainMenu.setPreferredSize(new Dimension(360, 640));
        mainMenu.setLayout(new GridBagLayout());
        mainMenu.setBackground(Color.cyan);

        JButton startButton = new JButton("Mulai Permainan");
        JButton exitButton = new JButton("Keluar");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainMenu.add(startButton, gbc);

        gbc.gridy = 1;
        mainMenu.add(exitButton, gbc);

        exitButton.addActionListener(e -> System.exit(0));

        startButton.addActionListener(e -> {
            frame.remove(mainMenu);

            playBGM(); // Putar musik pertama kali

            Logic logika = new Logic();
            View tampilan = new View(logika);
            logika.setView(tampilan);

            frame.add(tampilan);
            frame.pack();
            tampilan.requestFocus();
        });

        frame.add(mainMenu);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}