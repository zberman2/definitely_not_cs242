package com.zberman2.Game;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Piece;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

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
    private JButton[] toolBarButtons;
    private JLabel message;

    // team names for each side
    private String whiteTeamName = "";
    private String blackTeamName = "";

    // scores, initialized to 0
    private int whiteTeamScore = 0;
    private int blackTeamScore = 0;

    // JPanel which will house chessboardSpaces
    private JPanel chessboard;

    // reference to the current state of the board
    private Board board;

    /**
     * Constructs the gui by setting the board object, and calling
     * the drawBoard entry method
     *
     */
    public GUI(Board board) throws IOException {
        this.board = board;
        drawBoard();
    }

    /**
     * Reset the board instance variable.
     * @param board new Board object
     */
    public void setBoard(Board board) {
        this.board = board;
        // disable standard game button and
        // alternate game button
        toolBarButtons[0].setEnabled(false);
        toolBarButtons[1].setEnabled(false);

        // enable forfeit and restart buttons
        toolBarButtons[3].setEnabled(true);
        toolBarButtons[4].setEnabled(true);
    }

    /**
     * Reset the team names to the parameters passed in
     * @param white White team name
     * @param black Black team name
     */
    public void setTeamNames(String white, String black) {
        whiteTeamName = white;
        blackTeamName = black;
    }

    /**
     * Set the message in the toolbar to display
     * who's turn it is.
     * @param color Team who is currently up
     */
    public void updateMessage(int color) {
        String teamName = getTeamName(color);
        message.setText(" " + teamName + "'s turn");
    }

    /**
     * Set the message in the toolbar to say that the game is
     * over due to a checkmate
     * @param color Color who lost
     */
    public void checkmateMessage(int color) {
        String teamName = getTeamName(color);
        message.setText(" Checkmate. " + teamName + " loses.");
    }

    /**
     * Set the message in the toolbar to say the game is over
     * due to a stalemate
     */
    public void stalemateMessage() {
        message.setText("Stalemate. Game over.");
    }

    /**
     * Let the side who is in check know that their king is
     * under attack
     * @param color King who is in check
     */
    public void checkMessage(int color) {
        String teamName = getTeamName(color);
        message.setText(" " + teamName + "'s King is in Check");
    }

    /**
     * Increment a team score and display the socre
     * @param color Color that just won
     */
    public void scoreMessage(int color) {
        if (color == WHITE) whiteTeamScore ++;
        else blackTeamScore++;
        scoreMessage();
    }

    /**
     * Display the current score without updating anything
     */
    public void scoreMessage() {
        String white = getTeamName(WHITE);
        String black = getTeamName(BLACK);
        message.setText(" " + white + ": " + whiteTeamScore + ", " +
                black + ": " + blackTeamScore);
    }

    /**
     * Getter for the team name
     * @param color Team
     * @return Team name for that color
     */
    public String getTeamName(int color) {
        if (color == WHITE) return whiteTeamName;
        else return blackTeamName;
    }

    /**
     * Entry method which builds the board and labels step by step
     */
    private void drawBoard() throws IOException {
        // set up components of the Board
        initializeBoard();
        // set up the 2D array of JButtons
        initializeChessboardSpaces();
        // draw file labels above the board
        drawFileLabels(true);
        // draw the spaces and the rank labels
        drawChessboardSpaces();
        // draw file labels below the board as well
        drawFileLabels(false);
    }

    /**
     * Method responsible for setting up the chess piece images,
     * and initializing the gui, chessboardSpaces and chessboard objects
     */
    private void initializeBoard() {
        createImages();
        chessboardSpaces = new JButton[board.getXDimension()][board.getXDimension()];
        setUpToolbarElements();

        setUpToolbar();

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
        chessboard = new JPanel(new GridLayout(0, board.getXDimension()+2));

        chessboard.setBackground(Color.WHITE);

        chessboard.setBorder(new LineBorder(illiniOrange,
                LINE_BORDER_THICKNESS));

        // place the chessboard within the gui JPanel
        gui.add(chessboard);
    }

    /**
     * Helper method for building the toolbar
     * Initializes each component
     */
    private void setUpToolbarElements() {
        // set up toolbar buttons
        toolBarButtons = new JButton[5];
        toolBarButtons[0] = new JButton("Standard Game");
        toolBarButtons[1] = new JButton("Alternate Game");
        toolBarButtons[2] = new JButton("Undo");
        toolBarButtons[2].setEnabled(false);
        toolBarButtons[3] = new JButton("Forfeit");
        toolBarButtons[3].setEnabled(false);
        toolBarButtons[4] = new JButton("Restart");
        toolBarButtons[4].setEnabled(false);

        // set the colors and fonts of the toolbar buttons
        for (JButton toolBarButton : toolBarButtons) {
            toolBarButton.setForeground(Color.WHITE);
            toolBarButton.setBackground(illiniOrange);
            toolBarButton.setFont(
                    new Font(FONT, Font.BOLD, TOOLBAR_FONT_SIZE));
        }

        // initialize the toolbar message
        message = new JLabel(" Welcome to Illini Chess!");
        // message font is 2 more than the toolbar buttons' font
        message.setFont(new Font(FONT, Font.BOLD, TOOLBAR_FONT_SIZE+2));
        message.setForeground(illiniOrange);
    }

    /**
     * Enable/disable the undo button
     * @param enabled if true, enable, else, disable.
     */
    public void setUndoEnabled(boolean enabled) {
        toolBarButtons[2].setEnabled(enabled);
    }

    /**
     * Build the toolbar at the top of the GUI
     */
    private void setUpToolbar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setBackground(illiniBlue);
        toolBar.setForeground(illiniOrange);
        toolBar.setFloatable(false);
        toolBar.setMargin(new Insets(0, 0, 0, 0));
        gui.add(toolBar, BorderLayout.PAGE_START);

        // add buttons one by one
        for (JButton toolBarButton : toolBarButtons) {
            toolBar.add(toolBarButton);
        }
        toolBar.add(message);
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
    private void initializeChessboardSpaces() throws IOException {
        Insets margin = new Insets(0, 0, 0, 0);
        for (char file = 'a'; file < ('a' + board.getXDimension()); file++) {
            for (int rank = 1; rank <= board.getYDimension(); rank++) {
                JButton space = setUpButton(margin);
                setBorder(fileIndex(file), rank, space);
                chessboardSpaces[rankIndex(rank)][fileIndex(file)] = space;
            }
        }

        // set the background colors and places pieces at
        // their initial positions
        resetBackgrounds();
        refreshBoard();
    }

    /**
     * Initially called to the set the background colors on the board
     * Subsequent calls will be used to reset the colors after certain spaces
     * are highlighted (indicating legal moves)
     */
    public void resetBackgrounds() {
        for (char file = 'a'; file < ('a' + board.getXDimension()); file++) {
            for (int rank = 1; rank <= board.getYDimension(); rank++) {
                // set space colors and disable buttons
                chessboardSpaces[rankIndex(rank)][fileIndex(file)].setBackground(
                        spaceColor(fileIndex(file), rank));
                chessboardSpaces[rankIndex(rank)][fileIndex(file)].
                        setEnabled(false);
            }
        }
    }

    /**
     * Method used to reset the toolbar after the game by
     * enable the standard and alternate game buttons, and disabling
     * everything else
     */
    public void resetToolbar() {
        toolBarButtons[0].setEnabled(true);
        toolBarButtons[1].setEnabled(true);
        toolBarButtons[2].setEnabled(false);
        toolBarButtons[3].setEnabled(false);
        toolBarButtons[4].setEnabled(false);
    }

    /**
     * After a move takes place, this method is called to update the board.
     * It sets the icons of spaces with pieces with their image, and sets the
     * icons of spaces without pieces to null.
     * @throws IOException
     */
    public void refreshBoard() throws IOException {
        for (char file = 'a'; file < ('a' + board.getXDimension()); file++) {
            for (int rank = 1; rank <= board.getYDimension(); rank++) {
                JButton space =
                        chessboardSpaces[rankIndex(rank)][fileIndex(file)];

                // if there is a piece at (file, rank), place the image
                // in the current instance of space
                Piece piece = board.at(file, rank);
                // if the image index is equal to -1, we don't have it's image
                if (piece != null) {
                    ImageIcon pieceImage = new ImageIcon(piece.getImage());
                    space.setIcon(pieceImage);
                    space.setDisabledIcon(pieceImage);
                } else {
                    space.setIcon(null);
                    space.setDisabledIcon(null);
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
    private void drawFileLabels(boolean isTop) {
        // empty label before labels begin
        JLabel leftCorner = new JLabel("");
        if (isTop) leftCorner.setIcon(new ImageIcon(victoryBadge));
        else leftCorner.setIcon(new ImageIcon(blockI2));

        JLabel rightCorner = new JLabel("");
        if (isTop) rightCorner.setIcon(new ImageIcon(chief));
        else rightCorner.setIcon(new ImageIcon(blockI));

        if (isTop) chessboard.add(leftCorner);
        else chessboard.add(rightCorner);
        for (char x = 'a'; x < ('a' + board.getXDimension()); x++) {
            JLabel fileLabel = new JLabel("" + Character.toUpperCase(x),
                    SwingConstants.CENTER);
            fileLabel.setFont(new Font(FONT, Font.BOLD, FONT_SIZE));
            fileLabel.setForeground(illiniBlue);
            chessboard.add(fileLabel);
        }
        // empty label after labels end

        if (isTop) chessboard.add(rightCorner);
        else chessboard.add(leftCorner);
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
        for (int y = board.getYDimension(); y >= 1; y--) {

            // rank label to the left of the board
            drawRankLabel(y);
            for (char x = 'a'; x < ('a' + board.getXDimension()); x++) {
                chessboard.add(chessboardSpaces[rankIndex(y)][fileIndex(x)]);
            }
            // rank label to the right of the board
            drawRankLabel(y);
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
     * Getter for a particular space within the 2D array of
     * JButtons, chessboardSpaces
     * @param file the character corresponding to the file on the board
     * @param rank the integer corresponding to the rank on the board
     * @return the JButton at that (file, rank) coordinate
     */
    public JButton getSpaceAt(char file, int rank) {
        int fileIndex = fileIndex(file);
        int rankIndex = rankIndex(rank);
        return chessboardSpaces[rankIndex][fileIndex];
    }

    /**
     * Converts a rank on the board to its index within the
     * 2D array of JButtons defining the spaces
     * @param rank The number corresponding the rank on the board
     * @return the index of that rank in chessBoardSpaces
     */
    public int rankIndex(int rank) {
        return rank-1;
    }

    /**
     * Converts a file on the board to its index within the
     * 2D array of JButtons defining the spaces
     * @param file the character corresponding to the file on the board
     * @return the index of that file in chessBoardSpaces
     */
    public int fileIndex(char file) {
        return (file - 'a');
    }

    /**
     * Method called by the the GUIControl class to add
     * ActionListeners to all of the spaces on the board
     * @param listener ActionListener from the GUIControl class
     *                 that determines what happens when a space
     *                 is clicked on.
     */
    public void addSpaceListeners(ActionListener listener) {
        for (char file = 'a'; file < ('a' + board.getXDimension()); file++) {
            for (int rank = 1; rank <= board.getYDimension(); rank++) {
                JButton space =
                        chessboardSpaces[rankIndex(rank)][fileIndex(file)];
                space.addActionListener(listener);
            }
        }
    }

    /**
     * Method used to add an ActionListener to the standard game button
     * @param listener Standard Game ActionListener
     */
    public void addStandardGameListener(ActionListener listener) {
        toolBarButtons[0].addActionListener(listener);
    }

    /**
     * Method used to add an ActionListener to the alternate game button
     * @param listener Alternate Game ActionListener
     */
    public void addaAlternateGameListener(ActionListener listener) {
        toolBarButtons[1].addActionListener(listener);
    }

    /**
     * Method used to add an ActionListener to the undo button
     * @param listener Undo ActionListener
     */
    public void addUndoListener(ActionListener listener) {
        toolBarButtons[2].addActionListener(listener);
    }

    /**
     * Method used to add an ActionListener to the forfeit button
     * @param listener Forfeit ActionListener
     */
    public void addForfeitListener(ActionListener listener) {
        toolBarButtons[3].addActionListener(listener);
    }

    /**
     * Method used to add an ActionListener to the Restart button
     * @param listener Restart ActionListener
     */
    public void addRestartListener(ActionListener listener) {
        toolBarButtons[4].addActionListener(listener);
    }
}