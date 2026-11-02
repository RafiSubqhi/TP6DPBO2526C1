import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;
    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidth = 34;
    int playerHeight = 24;

    Timer gameLoop;
    int gravity = 1;
    View view;
    Image birdImage;
    Player player;

    int pipeStartPosX = frameWidth;
    int pipeStartPosY = 0;
    int pipeWidth = 64;
    int pipeHeight = 512;

    Timer pipesCooldown;
    int pipeVelocityX = -2;

    boolean gameOver = false;
    double score = 0;

    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    public Logic() {
        birdImage = new ImageIcon(getClass().getResource("assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidth, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                placePipes();
            }
        });
        pipesCooldown.start();

        gameLoop = new Timer(1000/60, this);
        gameLoop.start();
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes() {
        return pipes;
    }

    public double getScore() {
        return score;
    }

    public void placePipes() {
        // Typo garis strip sudah dihapus dan disatukan dalam satu baris
        int randomPosY = (int) (pipeStartPosY - (pipeHeight / 4) - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStartPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStartPosX, randomPosY + openingSpace + pipeHeight, pipeWidth, pipeHeight, lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for(int i = 0; i < pipes.size(); i++){
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            if (!pipe.passed && player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                score += 0.5;
                pipe.passed = true;
            }

            if (player.getPosX() < pipe.getPosX() + pipe.getWidth() &&
                    player.getPosX() + player.getWidth() > pipe.getPosX() &&
                    player.getPosY() < pipe.getPosY() + pipe.getHeight() &&
                    player.getPosY() + player.getHeight() > pipe.getPosY()) {
                gameOver = true;
            }
        }

        if (player.getPosY() > frameHeight) {
            gameOver = true;
        }

        if (gameOver) {
            gameLoop.stop();
            pipesCooldown.stop();
            App.stopBGM(); // Ini perintah agar musik berhenti saat nabrak
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if(view != null) {
            view.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!gameOver) {
                player.setVelocityY(-10);
            }
        }

        // Blok tombol R sudah disatukan agar tidak duplikat
        if (e.getKeyCode() == KeyEvent.VK_R) {
            if (gameOver) {
                player.setPosX(playerStartPosX);
                player.setPosY(playerStartPosY);
                player.setVelocityY(0);
                pipes.clear();
                gameOver = false;
                score = 0;
                gameLoop.start();
                pipesCooldown.start();
                App.playBGM();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}