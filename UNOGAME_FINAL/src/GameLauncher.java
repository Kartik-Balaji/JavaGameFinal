import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

// Main Game Launcher
public class GameLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Game Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new GridLayout(5, 1));

            JLabel title = new JLabel("Choose Your Game", SwingConstants.CENTER);
            title.setFont(new Font("Arial", Font.BOLD, 24));

            JButton connect4Btn = new JButton("Connect 4");
            JButton unoBtn = new JButton("UNO");
            JButton ticTacToeBtn = new JButton("Tic-Tac-Toe");
            JButton MemGameBtn = new JButton("Mem Game");
            MemGameBtn.addActionListener(e -> new MemoryGame());
            connect4Btn.addActionListener(e -> new Connect4Game());
            unoBtn.addActionListener(e -> new UnoGame());
            ticTacToeBtn.addActionListener(e -> new TicTacToeGame());

            frame.add(title);
            frame.add(connect4Btn);
            frame.add(unoBtn);
            frame.add(ticTacToeBtn);
            frame.add(MemGameBtn);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

// Connect4 Game Implementation
class Connect4Game extends JFrame {
    private static final int ROWS = 7;
    private static final int COLS = 7;
    private final JButton[][] board = new JButton[ROWS][COLS];
    private boolean isPlayer1Turn = true;
    private final Color EMPTY = Color.WHITE;
    private final Color P1_COLOR = Color.RED;
    private final Color P2_COLOR = Color.YELLOW;

    public Connect4Game() {
        setTitle("Connect 4");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(ROWS, COLS));

        // Initialize the board
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                JButton button = new JButton();
                button.setBackground(EMPTY);
                final int currentCol = col;
                button.addActionListener(e -> dropPiece(currentCol));
                board[row][col] = button;
                add(button);
            }
        }

        setSize(700, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void dropPiece(int col) {
        // Find the lowest empty row
        for (int row = ROWS - 1; row >= 0; row--) {
            if (board[row][col].getBackground() == EMPTY) {
                board[row][col].setBackground(isPlayer1Turn ? P1_COLOR : P2_COLOR);
                if (checkWin(row, col)) {
                    JOptionPane.showMessageDialog(this,
                            (isPlayer1Turn ? "Player 1" : "Player 2") + " wins!");
                    dispose();
                }
                isPlayer1Turn = !isPlayer1Turn;
                break;
            }
        }
    }

    private boolean checkWin(int row, int col) {
        Color currentColor = board[row][col].getBackground();
        return false;
    }
}


class TicTacToeGame extends JFrame {
    private final JButton[][] board = new JButton[3][3];
    private boolean isPlayer1Turn = true;

    public TicTacToeGame() {
        setTitle("Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                board[row][col] = new JButton("");
                board[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                board[row][col].setFocusPainted(false);
                final int r = row, c = col;
                board[row][col].addActionListener(e -> makeMove(r, c));
                add(board[row][col]);
            }
        }

        setSize(400, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void makeMove(int row, int col) {
        if (!board[row][col].getText().equals("")) {
            return; 
        }

        board[row][col].setText(isPlayer1Turn ? "X" : "O");
        board[row][col].setForeground(isPlayer1Turn ? Color.RED : Color.BLUE);

        if (checkWin()) {
            JOptionPane.showMessageDialog(this,
                    "Player " + (isPlayer1Turn ? "1 (X)" : "2 (O)") + " wins!");
            dispose();
            return;
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "It's a draw!");
            dispose();
            return;
        }

        isPlayer1Turn = !isPlayer1Turn;
    }

    private boolean checkWin() {
        String currentPlayerSymbol = isPlayer1Turn ? "X" : "O";

        
        for (int i = 0; i < 3; i++) {
            if (board[i][0].getText().equals(currentPlayerSymbol) &&
                    board[i][1].getText().equals(currentPlayerSymbol) &&
                    board[i][2].getText().equals(currentPlayerSymbol)) return true;

            if (board[0][i].getText().equals(currentPlayerSymbol) &&
                    board[1][i].getText().equals(currentPlayerSymbol) &&
                    board[2][i].getText().equals(currentPlayerSymbol)) return true;
        }

        
        if (board[0][0].getText().equals(currentPlayerSymbol) &&
                board[1][1].getText().equals(currentPlayerSymbol) &&
                board[2][2].getText().equals(currentPlayerSymbol)) return true;

        if (board[0][2].getText().equals(currentPlayerSymbol) &&
                board[1][1].getText().equals(currentPlayerSymbol) &&
                board[2][0].getText().equals(currentPlayerSymbol)) return true;

        return false;
    }

    private boolean isBoardFull() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (board[row][col].getText().equals("")) {
                    return false;
                }
            }
        }
        return true;
    }
}
class UnoGame extends JFrame {
    private static final String[] COLORS = {"Red", "Blue", "Green", "Yellow"};
    private static final String[] VALUES = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "Skip", "Reverse", "+2", "Wild", "Wild+4"};

    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> player1Hand = new ArrayList<>();
    private ArrayList<Card> player2Hand = new ArrayList<>();
    private Card topCard;
    private boolean isPlayer1Turn = true;

    private JPanel player1Panel;
    private JPanel player2Panel;
    private JPanel centerPanel;
    private JButton drawButton;
    private JLabel topCardLabel;
    private JLabel turnLabel;

    public UnoGame() {
        setTitle("UNO Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1200, 800);
        setLayout(new BorderLayout());

        initializeGame();
        createGUI();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeGame() {
        
        for (String color : COLORS) {
            for (String value : VALUES) {
                if (value.equals("Wild") || value.equals("Wild+4")) {
                    deck.add(new Card("Black", value));
                } else {
                    deck.add(new Card(color, value));
                }
            }
        }

       
        Collections.shuffle(deck);

        for (int i = 0; i < 7; i++) {
            player1Hand.add(deck.remove(0));
            player2Hand.add(deck.remove(0));
        }

        do {
            topCard = deck.remove(0);
        } while (topCard.getColor().equals("Black"));
    }

    private void createGUI() {
        player1Panel = new JPanel(new FlowLayout());
        player2Panel = new JPanel(new FlowLayout());
        updatePlayerPanels();

        centerPanel = new JPanel(new FlowLayout());
        topCardLabel = new JLabel();
        updateTopCardLabel();

        turnLabel = new JLabel("Player 1's turn", SwingConstants.CENTER);
        turnLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Create draw button with UNO logo and set a larger size
        drawButton = new JButton();
        ImageIcon unoLogo = new ImageIcon("uno_logo.png"); // Ensure this path is correct
        drawButton.setIcon(unoLogo);
        drawButton.setPreferredSize(new Dimension(100, 150));
        drawButton.addActionListener(e -> drawCard());

        centerPanel.add(turnLabel);
        centerPanel.add(topCardLabel);
        centerPanel.add(drawButton);

        add(player1Panel, BorderLayout.SOUTH);
        add(player2Panel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void updatePlayerPanels() {
        player1Panel.removeAll();
        player2Panel.removeAll();

        // Update Player 1's panel
        for (Card card : player1Hand) {
            JButton cardButton = createCardButton(card, isPlayer1Turn); // Show cards only if it's Player 1's turn
            player1Panel.add(cardButton);
        }

        // Update Player 2's panel
        for (Card card : player2Hand) {
            JButton cardButton = createCardButton(card, !isPlayer1Turn); // Show cards only if it's Player 2's turn
            player2Panel.add(cardButton);
        }

        revalidate();
        repaint();
    }

    private JButton createCardButton(Card card, boolean isVisible) {
        JButton button = new JButton();
        if (isVisible) {
            button.setText(card.toString());
            button.setBackground(getColorFromString(card.getColor()));
            button.addActionListener(e -> playCard(card));
        } else {
            button.setText(""); // Hide text
            button.setBackground(Color.BLACK); // Black out the card
        }
        button.setPreferredSize(new Dimension(80, 120));
        button.setOpaque(true);
        button.setForeground(Color.WHITE); // Ensure text is readable if shown
        return button;
    }


    private void playCard(Card card) {
        ArrayList<Card> currentHand = isPlayer1Turn ? player1Hand : player2Hand;

        if (isValidPlay(card)) {
            currentHand.remove(card);
            topCard = card;

            // Handle special cards
            handleSpecialCard(card);

            // Check for win
            if (currentHand.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        (isPlayer1Turn ? "Player 1" : "Player 2") + " wins!");
                dispose();
                return;
            }

            isPlayer1Turn = !isPlayer1Turn;
            turnLabel.setText("Player " + (isPlayer1Turn ? "1" : "2") + "'s turn");
            updatePlayerPanels();
            updateTopCardLabel();
        }
    }

    private boolean isValidPlay(Card card) {
        return card.getColor().equals(topCard.getColor()) ||
                card.getValue().equals(topCard.getValue()) ||
                card.getColor().equals("Black");
    }

    private void handleSpecialCard(Card card) {
        // Handle Skip, Reverse, +2, Wild, and Wild+4
        switch (card.getValue()) {
            case "Skip":
                isPlayer1Turn = !isPlayer1Turn; // Skip the next player's turn
                break;
            case "+2":
                ArrayList<Card> opponentHand = isPlayer1Turn ? player2Hand : player1Hand;
                for (int i = 0; i < 2 && !deck.isEmpty(); i++) {
                    opponentHand.add(deck.remove(0));
                }
                break;
            case "Wild":
                topCard.setColor(chooseColor());
                break;
            case "Wild+4":
                topCard.setColor(chooseColor());
                ArrayList<Card> opponentHandWild = isPlayer1Turn ? player2Hand : player1Hand;
                for (int i = 0; i < 4 && !deck.isEmpty(); i++) {
                    opponentHandWild.add(deck.remove(0));
                }
                break;
        }
    }

    private String chooseColor() {
        String[] options = COLORS;
        return (String) JOptionPane.showInputDialog(
                this,
                "Choose a color:",
                "Color Selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }

    private void drawCard() {
        if (!deck.isEmpty()) {
            ArrayList<Card> currentHand = isPlayer1Turn ? player1Hand : player2Hand;
            currentHand.add(deck.remove(0));
            updatePlayerPanels();
            isPlayer1Turn = !isPlayer1Turn; // Switch turn after drawing
            turnLabel.setText("Player " + (isPlayer1Turn ? "1" : "2") + "'s turn");
        }
    }

    private void updateTopCardLabel() {
        topCardLabel.setText("Current Card: " + topCard.toString());

        // Set background color based on top card color
        switch (topCard.getColor()) {
            case "Red":
                topCardLabel.setBackground(Color.RED);
                break;
            case "Blue":
                topCardLabel.setBackground(Color.BLUE);
                break;
            case "Green":
                topCardLabel.setBackground(Color.GREEN);
                break;
            case "Yellow":
                topCardLabel.setBackground(new Color(255, 165, 0)); // Dark orange/gold for yellow
                break;
            default:
                topCardLabel.setBackground(Color.BLACK); // Black for wild cards
                break;
        }
        topCardLabel.setOpaque(true);
        topCardLabel.setFont(new Font("Arial", Font.BOLD, 16));
        topCardLabel.setForeground(Color.WHITE); // Text color on colored background
    }

    private void setPlayerHandsEnabled() {
        for (Component button : player1Panel.getComponents()) {
            button.setEnabled(isPlayer1Turn);
        }
        for (Component button : player2Panel.getComponents()) {
            button.setEnabled(!isPlayer1Turn);
        }
    }

    private Color getColorFromString(String colorStr) {
        switch (colorStr) {
            case "Red": return Color.RED;
            case "Blue": return Color.BLUE;
            case "Green": return Color.GREEN;
            case "Yellow": return Color.YELLOW;
            default: return Color.BLACK;
        }
    }
}

// Card class for UNO game
class Card {
    private String color;
    private final String value;

    public Card(String color, String value) {
        this.color = color;
        this.value = value;
    }

    public String getColor() { return color; }
    public String getValue() { return value; }
    public void setColor(String color) { this.color = color; }

    @Override
    public String toString() {
        return color + " " + value;
    }
}
class MemoryGame extends JFrame {
    private static final int SIZE = 6;
    private final JButton[][] buttons = new JButton[SIZE][SIZE];
    private final String[][] tileValues = new String[SIZE][SIZE];
    private boolean isPlayer1Turn = true;
    private int flippedCount = 0;
    private JButton firstButton = null;
    private JButton secondButton = null;

    public MemoryGame() {
        setTitle("Memory Game");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));
        setSize(600, 600);

        // Initialize tile values (pairs from "A" to "R")
        ArrayList<String> values = new ArrayList<>();
        for (char c = 'A'; c <= 'R'; c++) {
            values.add(String.valueOf(c));
            values.add(String.valueOf(c));
        }
        Collections.shuffle(values);

        // Set up buttons and assign values
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 24));
                button.setBackground(Color.LIGHT_GRAY);
                button.setFocusPainted(false);
                tileValues[i][j] = values.remove(0);
                button.addActionListener(new TileClickListener(i, j, button));
                buttons[i][j] = button;
                add(button);
            }
        }

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void flipTile(int row, int col, JButton button) {
        if (flippedCount == 0) {
            // First tile flipped
            firstButton = button;
            firstButton.setText(tileValues[row][col]);
            firstButton.setEnabled(false);
            flippedCount = 1;
        } else if (flippedCount == 1) {
            // Second tile flipped
            secondButton = button;
            secondButton.setText(tileValues[row][col]);
            secondButton.setEnabled(false);
            flippedCount = 2;

            // Check for match after a short delay
            Timer timer = new Timer(500, e -> checkMatch());
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void checkMatch() {
        if (firstButton.getText().equals(secondButton.getText())) {
            // Matching pair found, keep both tiles revealed
            firstButton.setBackground(isPlayer1Turn ? Color.RED : Color.BLUE);
            secondButton.setBackground(isPlayer1Turn ? Color.RED : Color.BLUE);
            firstButton = null;
            secondButton = null;
            flippedCount = 0;
        } else {
            // No match, flip both tiles back over
            firstButton.setText("");
            secondButton.setText("");
            firstButton.setEnabled(true);
            secondButton.setEnabled(true);
            firstButton = null;
            secondButton = null;
            flippedCount = 0;

            // Switch turn
            isPlayer1Turn = !isPlayer1Turn;
            JOptionPane.showMessageDialog(this,
                    (isPlayer1Turn ? "Player 1's" : "Player 2's") + " turn");
        }
    }

    private class TileClickListener implements ActionListener {
        private final int row;
        private final int col;
        private final JButton button;

        public TileClickListener(int row, int col, JButton button) {
            this.row = row;
            this.col = col;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (flippedCount < 2) {
                flipTile(row, col, button);
            }
        }
    }
}
