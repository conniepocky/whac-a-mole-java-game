import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class WhacAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Whac-A-Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel(); 

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currentMole;
    JButton currentPlant;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;

    int score = 0;
    boolean isGameOver = false;

    WhacAMole() {
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Score: " + Integer.toString(score));
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);		
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        frame.add(boardPanel);

        Image plantImg = new ImageIcon(getClass().getResource("./plant.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(getClass().getResource("./mole.png")).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH));

        for (int i = 0; i < 9; i++) {
            JButton tile = new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);

            tile.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {    
                    JButton tile = (JButton) e.getSource();
                    
                    if (tile == currentMole) {
                        score += 10;

                        textLabel.setText("Score: " + Integer.toString(score));
                    } else if (tile == currentPlant) {
                        textLabel.setFont(new Font("Arial", Font.PLAIN, 25));
                        textLabel.setText("<html>Oops! You hit a plant. Game Over. <br>Score: " + Integer.toString(score) + ", click me to restart.</html>");

                        setMoleTimer.stop();
                        setPlantTimer.stop();

                        for (int i = 0; i < 9; i++) {
                            board[i].setEnabled(false);
                        }

                        isGameOver = true;
                    }
                }
            });
        }

        textPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isGameOver) {
                    isGameOver = false;
                    
                    score = 0;
                    textLabel.setFont(new Font("Arial", Font.PLAIN, 50));
                    textLabel.setText("Score: " + Integer.toString(score));

                    setMoleTimer.start();
                    setPlantTimer.start();

                    for (int i = 0; i < 9; i++) {
                        board[i].setEnabled(true);
                    }
                }
            }
        });

        setMoleTimer = new Timer(600, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentMole != null) {
                    currentMole.setIcon(null);
                    currentMole = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if (currentPlant == tile) {
                    return;
                }

                currentMole = tile;
                currentMole.setIcon(moleIcon);
            }
        });

        setPlantTimer = new Timer(1200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (currentPlant != null) {
                    currentPlant.setIcon(null);
                    currentPlant = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if (currentMole == tile) {
                    return;
                }

                currentPlant = tile;
                currentPlant.setIcon(plantIcon);
            }
        });

        setMoleTimer.start();
        setPlantTimer.start();

        frame.setVisible(true);
    }
}
