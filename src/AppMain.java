import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import snake.App;
import minesweeper.App1;
import flappybird.App2;

public class AppMain {
    public static void main(String[] args) {
        int boardWidth = 1200;
        int boardHeight = 750;

        JFrame frame = new JFrame("Retro Games");
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);

        // Create a custom panel with a background image
        JPanel backgroundPanel = new JPanel() {
            Image backgroundImage = new ImageIcon("C:\\Users\\HPP\\Desktop\\JavaProjects\\Homepage\\src\\background.png").getImage(); 
            // Load image
         
        @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        //backgroundPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 50));
        backgroundPanel.setLayout(new BorderLayout());

        // Create a container panel to hold the heading and text1 labels
        JPanel headingPanel = new JPanel();
        headingPanel.setLayout(new BoxLayout(headingPanel, BoxLayout.Y_AXIS)); // Stack components vertically
        headingPanel.setOpaque(false);

        // Create heading label
        JLabel headingLabel = new JLabel("Retro Games", JLabel.CENTER);
        headingLabel.setFont(new Font("Arial", Font.BOLD, 100));
        headingLabel.setForeground(Color.YELLOW); 
        headingLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        //Adding text
        JLabel text1 = new JLabel("Discover your nostalgia!", JLabel.CENTER);
        text1.setFont(new Font("Arial", Font.ITALIC, 40));
        text1.setForeground(Color.WHITE);
        text1.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        headingPanel.add(headingLabel);
        headingPanel.add(text1);

        /*  Create game labels panel for clickable text links
        JPanel gamePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 200, 350));
           */

        JPanel gamePanel = new JPanel();
        //gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 80, 100));
        //gamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        //gamePanel.setLayout(new GridLayout(2, 3, 150, -200)); // 2 rows (images and names), 3 columns
        gamePanel.setLayout(new GridBagLayout());
        gamePanel.setOpaque(false);
        //gamePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 60, 10, 60); // Add padding between elements
        gbc.gridx = 0;
        gbc.gridy = 0;

        // Create clickable labels for games
        JLabel snakeImageLabel = createScaledImageLabel("C:\\Users\\HPP\\Desktop\\JavaProjects\\Homepage\\src\\snake.png", 270, 270); // Provide the correct image path
        JLabel snakeGameLabel = createClickableLabel("Snake Game", () -> {
            try {
                snake.App.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        JLabel minesweeperImageLabel = createScaledImageLabel("C:\\Users\\HPP\\Desktop\\JavaProjects\\Homepage\\src\\Minesweeper.png", 270, 270);
        JLabel minesweeperLabel = createClickableLabel("Minesweeper", () -> {
            try {
                minesweeper.App1.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    
        JLabel flappyBirdImageLabel = createScaledImageLabel("C:\\Users\\HPP\\Desktop\\JavaProjects\\Homepage\\src\\flappyBird.png", 270, 270);
        JLabel FlappyBirdLabel = createClickableLabel("Flappy Bird", () -> {
            try {
                flappybird.App2.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        //Adding text on foot

        JPanel EndingPanel = new JPanel();
        EndingPanel.setLayout(new BoxLayout(EndingPanel, BoxLayout.Y_AXIS)); // Stack components vertically
        EndingPanel.setOpaque(false);

        JLabel text2 = new JLabel("share your feedback at:", JLabel.CENTER);
        text2.setFont(new Font("Arial", Font.PLAIN, 20));
        text2.setBorder(new EmptyBorder(0, 0, 4, 0));
        text2.setForeground(Color.WHITE);
        text2.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        JLabel text3 = new JLabel("kaur.noorpreet14@gmail.com or KaurManpreetM@outlook.com", JLabel.CENTER);
        text3.setFont(new Font("Arial", Font.PLAIN, 20));
        text3.setBorder(new EmptyBorder(0, 0, 5, 0));
        text3.setForeground(Color.WHITE);
        text3.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally

        EndingPanel.add(text2);
        EndingPanel.add(text3);

        gamePanel.add(snakeImageLabel, gbc);
        gbc.gridy = 1;
        gamePanel.add(snakeGameLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gamePanel.add(minesweeperImageLabel, gbc);
        gbc.gridy = 1;
        gamePanel.add(minesweeperLabel, gbc);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gamePanel.add(flappyBirdImageLabel, gbc);
        gbc.gridy = 1;
        gamePanel.add(FlappyBirdLabel, gbc);

        backgroundPanel.add(headingPanel, BorderLayout.PAGE_START);
        backgroundPanel.add(gamePanel, BorderLayout.CENTER);
        backgroundPanel.add(EndingPanel, BorderLayout.PAGE_END);

        frame.add(backgroundPanel); // Add panel to the frame
        frame.setVisible(true); // Set visibility after adding components
    }

    private static JLabel createClickableLabel(String text, Runnable onClickAction) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Add mouse listener for click events
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                onClickAction.run(); // Trigger the action when clicked
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                label.setForeground(Color.YELLOW);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                label.setForeground(Color.WHITE);
            }
        });

        return label;
    }

    private static JLabel createScaledImageLabel(String imagePath, int width, int height) {
        ImageIcon icon = new ImageIcon(imagePath);
        Image scaledImage = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(scaledImage));
    }

}