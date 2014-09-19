package com.zberman2.DataManager;

import com.zberman2.Pieces.Piece;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import static com.zberman2.DataManager.Constants.*;

/**
 * Class which describes the functionality of the Graphical User Interface
 * (for now, the GUI is static)
 *
 * Creates an 8x8 chessboard, outlined with rank and file labels
 *
 * Referenced
 * http://stackoverflow.com/questions/21077322/create-a-chess-board-with-jpanel
 * to get started on this GUI design. The only function below actually taken from
 * stackoverflow.com is createImages(), everything else is my code, and my
 * refactoring done to make everything easy to read
 */
public class GUI {
    // JPanel to where all components of the board with reside
    private final JPanel gui = new JPanel(new BorderLayout(5, 5));

    // 2D array of JButtons which will represent the individual spaces
    // on the chess board
    private JButton[][] chessboardSpaces;

    // contains the images of pieces for the game
    private Image[][] chessPieceImages = new Image[2][6];

    // JPanel which will house chessboardSpaces
    private JPanel chessboard;

    // reference to the current state of the board
    private Board board;

    /**
     * Constructs the gui by setting the board object, and calling
     * the drawBoard entry method
     * @param board Reference to the state of the chess board
     */
    public GUI(Board board) {
        this.board = board;
        drawBoard();
    }

    /**
     * Entry method which builds the board and labels step by step
     */
    private void drawBoard() {
        // set up components of the Board
        initializeBoard();

        // set up the 2D array of JButtons
        initializeChessboardSpaces();

        // draw file labels above the board
        drawFileLabels();

        // draw the spaces and the rank labels
        drawChessboardSpaces();

        // draw file labels below the board as well
        drawFileLabels();
    }

    /**
     * Method responsible for setting up the chess piece images,
     * and initializing the gui, chessboardSpaces and chessboard objects
     */
    private void initializeBoard() {
        createImages();
        chessboardSpaces = new JButton[board.getxDimension()][board.getxDimension()];

        // Empty border will give the gui a small border between itself and
        // the window presenting the gui JPanel
        gui.setBorder(new EmptyBorder(EMPTY_BORDER_THICKNESS,
                EMPTY_BORDER_THICKNESS,
                EMPTY_BORDER_THICKNESS,
                EMPTY_BORDER_THICKNESS));

        gui.setBackground(illiniBlue);

        // chessboard has a GridLayout so it can neatly display the
        // spaces in a 2D array. It has 10 columns so it can have rank labels
        // on either side of the 8x8 board
        chessboard = new JPanel(new GridLayout(0, board.getxDimension()+2));

        chessboard.setBackground(Color.WHITE);

        chessboard.setBorder(new LineBorder(illiniOrange,
                LINE_BORDER_THICKNESS));

        // place the chessboard within the gui JPanel
        gui.add(chessboard);
    }

    /**
     * Helper method for the initializeChessboardSpaces method
     * This method is responsible for creating a returning a new
     * JButton which will soon become a new chessboard space
     * @param margin 0 so that there is no margin between spaces
     * @return new chessboard space JButton
     */
    private JButton setUpButton(Insets margin) {
        JButton space = new JButton();
        space.setMargin(margin);

        // black image which can either be a blank black or white
        // square, or a black or white square with a piece on top
        ImageIcon icon = new ImageIcon(
                new BufferedImage(IMAGE_DIMENSION, IMAGE_DIMENSION,
                        BufferedImage.TYPE_INT_ARGB));
        space.setIcon(icon);

        return space;
    }

    /**
     * Method for setting up the 2D array of chessboard spaces
     * This method creates the buttons, sets them to the proper color,
     * and populates them with a piece image if the board has a piece
     * at a given rank and file
     */
    private void initializeChessboardSpaces() {
        Insets margin = new Insets(0, 0, 0, 0);
        for (char file = 'a'; file < ('a' + 8); file++) {
            for (int rank = 1; rank <= 8; rank++) {
                JButton space = setUpButton(margin);
                int fileInt = file - 'a' + 1;

                space.setBackground(spaceColor(fileInt, rank));
                setBorder(fileInt, rank, space);
                chessboardSpaces[rank-1][fileInt-1] = space;

                // if there is a piece at (file, rank), place the image
                // in the current instance of space
                Piece piece = board.at(file, rank);
                // if the image index is equal to -1, we don't have it's image
                if (piece != null && piece.imageIndex() != -1) {
                    int color = piece.getColor();
                    int index = piece.imageIndex();
                    space.setIcon(new ImageIcon(
                            chessPieceImages[color][index]));
                }
            }
        }
    }

    /**
     * Helper method for initializeChessboardSpaces
     * Determines whether a space at a given rank and file should be
     * black or white
     * @param fileInt integer representation of the file
     * @param rank the rank of the space
     * @return black or white
     */
    private Color spaceColor(int fileInt, int rank) {
        if (rank % 2 == fileInt % 2) return illiniBlue;
        else return illiniOrange;
    }

    /**
     * Method which sets the border of the space passed in as a parameter
     * @param fileInt file of the space
     * @param rank the rank of the space
     * @param space Reference to the space which needs a border
     */
    private void setBorder(int fileInt, int rank, JButton space) {
        if (rank % 2 == fileInt % 2) {
            space.setBorder(new EtchedBorder(
                    EtchedBorder.LOWERED, illiniOrange, illiniBlue));
        } else {
            space.setBorder(new EtchedBorder(
                    EtchedBorder.LOWERED, illiniBlue, illiniOrange));
        }
    }

    /**
     * Prints out file labels above and below the board
     * (i.e.) A, B, C, etc.
     */
    private void drawFileLabels() {
        // empty label before labels begin
        chessboard.add(new JLabel(""));
        for (char x = 'a'; x < ('a' + 8); x++) {
            JLabel fileLabel = new JLabel("" + Character.toUpperCase(x),
                    SwingConstants.CENTER);
            fileLabel.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
            fileLabel.setForeground(illiniBlue);
            chessboard.add(fileLabel);
        }
        // empty label after labels end
        chessboard.add(new JLabel(""));
    }

    /**
     * Method used for printing the rank beside the board
     * @param rank The number corresponding the rank on the board
     */
    private void drawRankLabel(int rank) {
        JLabel rankLabel = new JLabel("" + rank,
                SwingConstants.CENTER);
        rankLabel.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
        rankLabel.setForeground(illiniOrange);
        chessboard.add(rankLabel);
    }

    /**
     * Draws the board itself by printing rank labels before and
     * after the board, and printing the chessboard spaces in between
     */
    private void drawChessboardSpaces() {
        for (int y = 8; y >= 1; y--) {
            int row = y;

            // rank label to the left of the board
            drawRankLabel(row);
            for (char x = 'a'; x < ('a' + 8); x++) {
                int column = (x - 'a') + 1;
                chessboard.add(chessboardSpaces[row - 1][column - 1]);
            }
            // rank label to the right of the board
            drawRankLabel(row);
        }
    }

    /**
     * Getter for the gui JPanel
     * @return gui
     */
    public JComponent getGUI() {
        return gui;
    }

    /**
     * Method used for dividing up the image of all 12 chess pieces into
     * individual 64 pixel x 64 pixel images of each piece
     * Function credit:
     * http://stackoverflow.com/questions/21142686/making-a-robust-resizable-chess-gui
     */
    private final void createImages() {
        try {
            URL url = new URL(CHESS_PIECES_URL);
            BufferedImage bi = ImageIO.read(url);
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 6; j++) {
                    chessPieceImages[i][j] = bi.getSubimage(
                            j * 64, i * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    /**
     * Tester for the GUI class
     * Simply displays the gui and leaves it there until it is closed
     * @param args empty String array
     */
    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                Master chessmaster = new Master();
                chessmaster.drawBoard();
                chessmaster.printBoard();
            }
        };
        SwingUtilities.invokeLater(r);
    }
}