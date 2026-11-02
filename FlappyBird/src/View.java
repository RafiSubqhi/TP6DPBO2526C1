import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    int width = 360;
    int height = 640;
    private Logic logic;
    JLabel scoreLabel; // Sesuai spesifikasi TP nomor 3

    public View(Logic logic) {
        this.logic = logic;
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.cyan);

        // 1. Matikan layout bawaan agar kita bisa atur posisi manual
        setLayout(null);

        // 2. Inisialisasi JLabel
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 32));
        scoreLabel.setForeground(Color.WHITE);

        // 3. Atur koordinat dan ukuran Label (x, y, lebar, tinggi)
        scoreLabel.setBounds(0, 30, width, 50);
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // Rata tengah

        add(scoreLabel); // Tambahkan ke layar

        setFocusable(true);
        addKeyListener(logic);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        Player player = logic.getPlayer();
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        ArrayList<Pipe> pipes = logic.getPipes();
        if(pipes != null) {
            for(int i = 0; i < pipes.size(); i++) {
                Pipe pipe = pipes.get(i);
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeight(), null);
            }
        }

        // Update skor secara real-time
        scoreLabel.setText("Score: " + (int) logic.getScore());
    }
}