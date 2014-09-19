package com.zberman2.DataManager;

import com.zberman2.Pieces.Piece;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Class describing the functionality of a chess board
 * Created by Zack Berman on 9/10/2014.
 */
public class Board {
    private int xDimension; // spaces in the x direction
    private int yDimension; // spaces in the y dimension
    private int numSides;   // defines the shape of the board
    private ArrayList<Piece> pieces; // list of chess pieces on the board

    /**
     * Blank constructor
     */
    public Board(int xDimension, int yDimension, int numSides) {
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.numSides = numSides;
    }

    /**
     * Constructor which accepts an ArrayList of pieces
     *
     * @param pieces ArrayList of initial chess pieces to be
     *               placed on the board
     */
    public Board(ArrayList<Piece> pieces,
                 int xDimension, int yDimension, int numSides) {
        this.pieces = pieces;
        this.xDimension = xDimension;
        this.yDimension = yDimension;
        this.numSides = numSides;
    }

    /**
     * Setter for the ArrayList of pieces.
     * Used with the blank constructor to construct a blank board, and
     * later add the pieces.
     *
     * @param pieces ArrayList of initial chess pieces to be
     *               placed on the board
     */
    public void setPieces(ArrayList<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * Getter for pieces
     *
     * @return ArrayList of pieces on the board
     */
    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    /**
     * Getter for y dimension of the board
     *
     * @return yDimension
     */
    public int getyDimension() {
        return yDimension;
    }

    /**
     * Getter for x dimension of the board
     *
     * @return xDimension
     */
    public int getxDimension() {
        return xDimension;
    }

    /**
     * Getter for number of sides of the board
     * (not used, but may be useful if game is changed from a standard
     * square board to something else)
     *
     * @return number of sides on the chess board
     */
    public int getNumSides() {
        return numSides;
    }

    /**
     * Method which iterates through the list of pieces and
     * determines if there is a piece at (x, y) on the board.
     * If there is, it returns that piece, null otherwise.
     *
     * @param file file coordinate
     * @param rank rank coordinate
     * @return The piece at (file, rank), null if one doesn't exist
     */
    public Piece at(char file, int rank) {
        for (Piece piece : pieces) {
            if (piece.isAt(file, rank)) return piece;
        }
        return null;
    }

    /**
     * Given a diagonal path, this method determines if there are any pieces
     * located between the Piece's current position and its destination,
     * (newFile, newRank)
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @param piece reference to the piece trying to move
     * @return true if there are no Pieces in the way
     */
    public boolean isOpenDiagonalPath(char newFile, int newRank, Piece piece) {
        if (!piece.isDiagonalMotion(newFile, newRank)) return false;
        int difference = piece.fileDifference(newFile);
        // fileDifference and rankDifference will return the same value for
        // diagonal paths

        // incrementX will be 1 if newFile > current file coordinate, and -1 if not
        int incrementFile = (newFile - piece.getFile()) / difference;
        // incrementY will be 1 if newRank > current rank coordinate, and -1 if not
        int incrementRank = (newRank - piece.getRank()) / difference;

        // check all spaces in a diagonal path between (file, rank)
        // and (newFile, newRank)
        for (int i = 2; i <= difference; i++) {
            int currentFile = piece.getFile() + (incrementFile*i) - incrementFile;
            int currentRank = piece.getRank() + (incrementRank*i) - incrementRank;

            // return false if we find a piece at (currentFile, currentRank)
            if (at((char) currentFile, currentRank) != null) return false;
        }
        // no pieces found
        return true;
    }

    /**
     * Given a vertical path, this method determines if there are any pieces
     * located between the Piece's current position and its destination, (a, b)
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @param piece reference to the piece trying to move
     * @return true if there are no Pieces in the way
     */
    public boolean isOpenVerticalPath(char newFile, int newRank, Piece piece) {
        if (!piece.isVerticalMotion(newFile, newRank)) return false;
        // fileDifference is 0 for vertical paths
        int difference = piece.rankDifference(newRank);

        // incrementY will be 1 if newRank > rank, -1 if not
        int incrementRank = (newRank - piece.getRank()) / difference;

        // check all spaces in a vertical path between (file, rank)
        // and (newFile, newRank)
        for (int i = 2; i <= difference; i++) {
            int currentRank = piece.getRank() + (incrementRank*i) - incrementRank;

            // return false if we find a piece at (currentFile, currentRank)
            if (at(newFile, currentRank) != null) return false;
        }
        // no pieces found
        return true;
    }

    /**
     * Given a horizontal path, this method determines if there are any pieces
     * located between the Piece's current position and its destination, (a, b)
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @param piece reference to the piece trying to move
     * @return true if there are no Pieces in the way
     */
    public boolean isOpenHorizontalPath(char newFile, int newRank, Piece piece) {
        if (!piece.isHorizontalMotion(newFile, newRank)) return false;
        // rankDifference is 0 for horizontal paths
        int difference = piece.fileDifference(newFile);

        // incrementX will be 1 if newFile > file, -1 if not
        int incrementFile = (newFile - piece.getFile()) / difference;

        // check all spaces in a horizontal path between (file, rank)
        // and (newFile, newRank)
        for (int i = 2; i <= difference; i++) {
            int currentFile = piece.getFile() + (incrementFile*i) - incrementFile;

            // return false if we find a piece at (currentFile, currentRank)
            if (at((char) currentFile, newRank) != null) return false;
        }
        // no pieces found
        return true;
    }

    /**
     * Prints terminal window chess board
     */
    public void printBoard() {
        TerminalWindowGUI gui = new TerminalWindowGUI(this);
        gui.printBoard();
    }

    /**
     * Creates a new GUI object and displays a chessboard GUI
     */
    public void drawBoard() {
        GUI gui = new GUI(this);
        JFrame f = new JFrame("Chess Board");
        f.add(gui.getGUI());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ensures the frame is the minimum size it needs to be
        // in order display the components within it
        f.pack();
        // ensures the minimum size is enforced.
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
    }
}