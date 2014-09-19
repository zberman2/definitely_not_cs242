package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;
import javafx.util.Pair;

import static com.zberman2.DataManager.Constants.WHITE;

/**
 * Abstract class describing a generic chess piece
 * Created by Zack Berman on 9/10/2014.
 */
public abstract class Piece {
    private int color; // 0 for white, 1 for black
    private Pair<Character, Integer> position; // (rank, file) coordinate
    private boolean isCaptured = false;

    /**
     * Constructor for a Piece object. Sets the color and initial position
     * @param color 0 or 1 (white or black)
     * @param file initial file coordinate
     * @param rank initial rank coordinate
     */
    public Piece(int color, char file, int rank) {
        this.color = color;
        this.position = new Pair(file, rank);
    }

    /**
     * Called when a piece is captured in the game
     */
    public void setIsCapturedTrue() { isCaptured = true; }

    /**
     * Called to revert the capturing of a piece
     */
    public void setIsCapturedFalse() { isCaptured = false; }

    /**
     * Method that determines if the piece can make a valid move to
     * location (a, b) on the board
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @param board current instance of the chess board
     * @return true if moving to (newFile, newRank) is valid
     */
    public boolean canMove(char newFile, int newRank, Board board) {
        int xMax = board.getxDimension();
        int yMax = board.getyDimension();
        // make sure (a, b) is on the board
        if (newFile < 'a' || newFile >= ('a' + xMax) || newRank < 1 || newRank > yMax) {
            return false;
        }

        if (newFile == getFile() && newRank == getRank()) return false; // current location

        // call the pieces specific validMotion method
        if (!validMotion(newFile, newRank, board)) { return false; }

        // check that (a, b) is either unoccupied, or occupied
        // by one of the opponent's pieces
        Piece piece = board.at(newFile,newRank);
        return piece == null || piece.getColor() != this.getColor();
    }

    /**
     * Boolean method used by all subclasses of Piece
     * @param newRank file coordinate of new space
     * @param newRank rank coordinate of new space
     * @param board current instance of the chess board
     * @return true if moving to (newFile, newRank) is valid
     */
    abstract boolean validMotion(char newFile, int newRank, Board board);

    /**
     * Changes the position of the Piece to a new space
     * @param newFile file coordinate of new space
     * @param newRank file coordinate of new space
     */
    public void move(char newFile, int newRank) {
        position = new Pair(newFile, newRank);
    }

    /**
     * Determines if the Piece is currently located at (file, rank) and
     * not yet captured by the opponent
     * @param file file coordinate
     * @param rank rank coordinate
     * @return true if the piece is not captured and located at (file, rank)
     */
    public boolean isAt(char file, int rank) {
        return (!isCaptured) && ((getFile() == file) && (getRank() == rank));
    }

    /**
     * Getter for the Piece's color
     * @return color
     */
    public int getColor() { return color; }

    /**
     * Getter for file coordinate
     * @return file
     */
    public char getFile() { return position.getKey(); }

    /**
     * Getter for rank coordinate
     * @return rank
     */
    public int getRank() { return position.getValue(); }

    /**
     * Getter for isCaptured boolean variable
     * @return isCaptured
     */
    public boolean isCaptured() {
        return isCaptured;
    }

    /**
     * Returns the character representing the color of the Piece
     * @return 'w' for white pieces, 'b' for black pieces
     */
    public char colorNotation() {
        if (color == WHITE) return 'w';
        else return 'b';
    }

    /**
     * Returns the character representing the type of Piece
     * @return Piece character (see Constants for possible values)
     */
    public abstract char pieceNotation();

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return piece image index
     */
    public abstract int imageIndex();

    /**
     * Determines if (newFile, newRank) is located diagonally away from the Piece's
     * current location
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @return true if (newFile, newRank) is diagonally away from (file, rank)
     */
    public boolean isDiagonalMotion(char newFile, int newRank) {
        int fileDifference = fileDifference(newFile);
        int rankDifference = rankDifference(newRank);

        return fileDifference == rankDifference;
    }

    /**
     * Determines if (newFile, newRank) is located vertically away from the Piece's
     * current location
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @return true if (newFile, newRank) is vertically away from (file, rank)
     */
    public boolean isVerticalMotion(char newFile, int newRank) {
        return fileDifference(newFile) == 0;
    }

    /**
     * Determines if (newFile, newRank) is located horizontally away from the Piece's
     * current location
     * @param newFile file coordinate of new space
     * @param newRank rank coordinate of new space
     * @return true if (newFile, newRank) is horizontally away from (rank, file)
     */
    public boolean isHorizontalMotion(char newFile, int newRank) {
        return rankDifference(newRank) == 0;
    }

    /**
     * Method that determines the absolute value of the difference between
     * input newFile and the current file coordinate
     * @param newFile file coordinate of new space
     * @return difference between file and newFile
     */
    public int fileDifference(char newFile) {
        int file = getFile();
        return Math.abs(file-newFile);
    }

    /**
     * Method that determines the absolute value of the difference between
     * input newRank and the current rank coordinate
     * @param newRank rank coordinate of new space
     * @return difference between rank and newRank
     */
    public int rankDifference(int newRank) {
        int rank = getRank();
        return Math.abs(rank - newRank);
    }

    /**
     * toString Method for a Piece
     * Prints the color and (file, rank) position
     * @return A string containing the color and position of the Piece
     */
    @Override
    public String toString() {
        return "Piece{" +
                "color=" + getColor() +
                ", x=" + getFile() +
                ", y=" + getRank() +
                '}';
    }
}
