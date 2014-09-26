package com.zberman2.DataManager;

import com.zberman2.Game.GUI;
import com.zberman2.Pieces.Piece;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import static com.zberman2.DataManager.Constants.*;

/**
 * Class which controls the GUI
 * Implements the Control part of the
 * Model-View-Control Architecture (in which the
 * model is the master/board, and the view is the GUI itself)
 * Created by Zack Berman on 9/25/2014.
 */
public class GUIControl {
    private GUI gui;
    // reference to the current state of the board
    private Board board;
    // reference to the master controlling the pieces
    private Master master;
    // Piece variable for last piece clicked on
    private Piece currentPiece = null;
    // the side with the current turn
    int currentColor = WHITE;
    // stack of Moves for undo
    Stack<Move> moveStack;

    // ActionListeners for all the buttons in the GUI
    ActionListener spaceListener;
    ActionListener standardGameListener;
    ActionListener alternateGameListener;
    ActionListener undoListener;
    ActionListener forfeitListener;
    ActionListener restartListener;

    /**
     * Constructor of a GUI controller
     * Simply creates an empty Move stack, and assigns
     * the board and the master
     * @param board Board used in the game
     * @param master Master which controls the game
     */
    public GUIControl(Board board, Master master) {
        this.board = board;
        this.master = master;
        this.moveStack = new Stack<Move>();
    }

    /**
     * Method which creates a new GUI, and calls helper methods
     * to set up the listeners for all the buttons on the board.
     * @throws IOException
     */
    public void init() throws IOException {
        gui = new GUI(board);
        setCurrentSide(WHITE);
        JFrame f = new JFrame("Fighting Illini Chess");
        f.add(gui.getGUI());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);

        // set up and add button action listeners
        initializeListeners();
        addListeners();
    }

    /**
     * Helper method which calls on other helper methods to
     * initialize the action listeners
     */
    private void initializeListeners() {
        initializeSpaceListener();
        initializeStandardListener();
        initializeAlternateListener();
        initializeUndoListener();
        initializeForfeitListener();
        initializeRestartListener();
    }

    /**
     * Takes the action listeners that were created in initializeListeners
     * and adds them to the board using the GUI's add methods
     */
    private void addListeners() {
        gui.addSpaceListeners(spaceListener);
        gui.addStandardGameListener(standardGameListener);
        gui.addaAlternateGameListener(alternateGameListener);
        gui.addUndoListener(undoListener);
        gui.addForfeitListener(forfeitListener);
        gui.addRestartListener(restartListener);
    }

    /**
     * Method called to handle the situation where a user selects one of
     * his/her pieces to move.
     * @param piece Piece selected on the board
     */
    public void handlePickPiece(Piece piece) {
        // set currentPiece and locate its position
        currentPiece = piece;
        char file = piece.getFile();
        int rank = piece.getRank();

        // low-light its space
        light(gui.getSpaceAt(file, rank), false);
        ArrayList<Pair<Character, Integer>> moveList =
                master.moveList(piece);

        // highlight and enable legal moves
        for (javafx.util.Pair<Character, Integer> move : moveList) {
            int currRank = move.getValue();
            char currFile = move.getKey();
            light(gui.getSpaceAt(currFile, currRank), true);
        }
    }

    /**
     * Method called to handle the situation where a user is moving
     * the currentPiece to a new location
     * @param position New space on the board for a piece to move
     */
    public void handleMove(Pair<Character, Integer> position) {
        // place the current move onto the stack
        Piece captured = board.at(position);
        Pair<Character, Integer> oldPosition = currentPiece.getPosition();
        moveStack.push(new Move(currentPiece, captured, oldPosition, position));

        // make the move
        master.move(currentPiece, position);

        // refresh board after piece is moved
        try {
            gui.refreshBoard();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        if (!moveStack.empty()) {
            // can undo now that there are moves in the stack
            gui.setUndoEnabled(true);
        }
        // reset backgrounds and change sides
        switchSides();
    }

    /**
     * Helper method for actionPerformed. Iterates through the spaces
     * and determines which space was clicked on.
     * @param event the click of a space
     * @return a (file, rank) pair corresponding to the space
     */
    public Pair<Character, Integer> findSource(ActionEvent event) {
        for (int y = board.getYDimension(); y >= 1; y--) {
            for (char x = 'a'; x < ('a' + board.getXDimension()); x++) {
                if (event.getSource() == gui.getSpaceAt(x, y)) {
                    return new Pair<Character, Integer>(x, y);
                }
            }
        }
        return null;
    }

    /**
     * Method used to highlight or low-light a space on the board.
     * This is used to indicate which space was clicked on, or which
     * spaces a piece can move to
     * @param space Space on the board to be modified
     * @param highlight If true, brighten space, else, darken space
     */
    public void light(JButton space, boolean highlight) {
        // set the space to be clickable
        space.setEnabled(true);
        Color color = space.getBackground();
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int[] components = {red, green, blue};
        for (int i = 0; i < 3; i++) {
            // modify the rgb values
            if (highlight) {
                components[i] += HIGHLIGHT_FACTOR;
            } else {
                components[i] -= LOWLIGHT_FACTOR;
            }
            if (components[i] > 255) {
                components[i] = 255;
            } else if (components[i] < 0) {
                components[i] = 0;
            }
        }
        space.setBackground(new Color(components[0], components[1],
                components[2]));
    }

    /**
     * Toggles the current color, and sets the side to that color
     */
    public void switchSides() {
        // alternate color
        currentColor = 1 - currentColor;
        gui.resetBackgrounds();
        setCurrentSide(currentColor);
        evaluateBoard();
    }

    /**
     * Checks the board to see if it is in a check, checkmate, or stalemate
     * configuration. If it is, it handles the situation properly.
     */
    private void evaluateBoard() {
        if (master.isCheckmate(currentColor)) {
            gui.checkmateMessage(currentColor);
            gameOver();
        } else if (master.isStalemate(currentColor)) {
            gui.stalemateMessage();
            gameOver();
        } else if (master.isCheck(currentColor) > 0) {
            // only a check, game not over
            gui.checkMessage(currentColor);
        } else {
            // display new turn
            gui.updateMessage(currentColor);
        }
    }

    /**
     * End game method. Sets all spaces to not be clickable, resets
     * the toolbar at the top of the window, clears the stack, and
     * resets the controller so its ready for a new game.
     */
    private void gameOver() {
        gui.resetBackgrounds();
        gui.resetToolbar();
        moveStack = new Stack<Move>();
        currentColor = WHITE;
        currentPiece = null;
    }

    /**
     * Makes it so all buttons are not clickable except for the
     * spaces with pieces of a particular color
     * @param color Pieces of this color can be clicked on
     */
    public void setCurrentSide(int color) {
        for (char file = 'a'; file < ('a' + board.getXDimension()); file++) {
            for (int rank = 1; rank <= board.getYDimension(); rank++) {
                Piece piece = board.at(file, rank);
                JButton space = gui.getSpaceAt(file, rank);
                // check if the space is empty, or if it matches the color parameter
                if (piece != null && piece.getColor() == color) {
                    space.setEnabled(true);
                } else {
                    space.setEnabled(false);
                }
            }
        }
    }

    /**
     * Entry method for resetting the backgrounds and
     * setting the current side
     */
    public void setBoard() {
        gui.resetBackgrounds();
        setCurrentSide(currentColor);
    }

    /**
     * Method invoked at the beginning of the first game.
     * Allows for players to select their names
     */
    private void promptNames() {
        if (gui.getTeamName(WHITE).equals("") ||
                gui.getTeamName(BLACK).equals("")) {
            String whiteName = "";
            // ensure the name is not blank
            while (whiteName.equals("")) {
                whiteName = JOptionPane.showInputDialog(
                        "White team name:");
            }
            String blackName = "";
            while (blackName.equals("")) {
                blackName = JOptionPane.showInputDialog(
                        "Black team name:");
            }
            gui.setTeamNames(whiteName, blackName);
        }
        gui.updateMessage(currentColor);
    }

    /**
     * ActionListener for button used to create an Alternate game of chess
     * with replacement pieces.
     */
    private void initializeAlternateListener() {
        alternateGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JButton button = (JButton)event.getSource();

                promptNames();
                // set not clickable after it is used
                button.setEnabled(false);

                // initialize alternate master, and set up the board
                master.alternateMaster();
                board = master.getBoard();
                gui.setBoard(board);
                try {
                    gui.refreshBoard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setBoard();
            }
        };
    }

    /**
     * ActionListener for button used to create a Standard game of chess
     * with regular pieces.
     */
    private void initializeStandardListener() {
        standardGameListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                JButton button = (JButton)event.getSource();

                promptNames();
                // set not clickable after it is used
                button.setEnabled(false);

                // initialize standard master, and set up the board
                master.standardMaster();
                board = master.getBoard();
                gui.setBoard(board);
                try {
                    gui.refreshBoard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                setBoard();
            }
        };
    }

    /**
     * ActionListener used by all spaces on the board
     */
    private void initializeSpaceListener() {
        spaceListener = new ActionListener() {
            /**
             * Method invoked upon clicking on a space.
             * Sets up the board, finds the space that was clicked on
             * and decides if the user was trying to select a piece to move,
             * or move a particular piece
             * @param event the click of a space
             */
            @Override
            public void actionPerformed(ActionEvent event) {
                setBoard();

                // determine which space was clicked on
                Pair<Character, Integer> position = findSource(event);
                Piece piece = board.at(position);

                // user is selecting on of his/her pieces
                if (piece != null && piece.getColor() == currentColor) {
                    handlePickPiece(piece);
                }
                // user is moving the current piece
                else {
                    handleMove(position);
                }
            }
        };
    }

    /**
     * ActionListener used by the undo button
     */
    private void initializeUndoListener() {
        undoListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // find the last move and revert it
                Move lastMove = moveStack.pop();
                lastMove.undo();
                switchSides();
                try {
                    gui.refreshBoard();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (moveStack.empty()) {
                    // set the button not clickable if no moves are in the stack
                    gui.setUndoEnabled(false);
                }
            }
        };
    }

    /**
     * ActionListener used by the forfeit button
     */
    private void initializeForfeitListener() {
        forfeitListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                // add one to the other team's score
                gui.scoreMessage(1 - currentColor);
                gameOver();
            }
        };
    }

    /**
     * ActionListener used by the restart button
     */
    private void initializeRestartListener() {
        restartListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String opponent = gui.getTeamName(1 - currentColor);

                // prompt the opponent to see if he or she agrees
                // to restarting the game
                int agree = JOptionPane.showConfirmDialog(null,
                        opponent + ", do you agree to a restart?",
                        "Restart Request",JOptionPane.YES_NO_OPTION);
                if (agree == JOptionPane.YES_OPTION) {
                    // end game, don't update scores on a restart
                    gameOver();
                    gui.scoreMessage();
                }
                else {
                    // continue game after message
                    String teamName = gui.getTeamName(currentColor);
                    JOptionPane.showMessageDialog(null, "I'm sorry " +
                            teamName + ". " + opponent + " did not agree to a restart");
                }
            }
        };
    }
}
