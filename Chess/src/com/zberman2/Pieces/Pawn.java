package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.PAWN;
import static com.zberman2.DataManager.Constants.PAWN_IMAGE_INDEX;
import static com.zberman2.DataManager.Constants.WHITE;

/**
 * Created by Zack Berman on 9/10/2014.
 */
public class Pawn extends Piece {
    private boolean isFirstMove; // flag which, if on, allows the pawn to move
                                 // 2 spaces on its first turn

    /**
     * Call the superclass constructor, and then initializes the
     * isFirstMove variable
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Pawn(int color, char file, int rank) {
        super(color, file, rank);
        isFirstMove = true;
    }

    /**
     * Asserts that the new space is 2 spaces up/down (if it is the Pawn's
     * first move) or only 1 space up/down.  In the case that the Pawn is
     * capturing another piece, this function allows for moving
     * diagonally 1 space.
     * @param newFile file coordinate of new position
     * @param newRank rank coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Pawns's behavior
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        // make sure the pawn is moving forward, depending on its color
        if (getColor() == WHITE) {
            if (newRank < getRank()) return false;
        } else {
            if (newRank > getRank()) return false;
        }

        int yDifference = rankDifference(newRank);
        int xDifference = fileDifference(newFile);
        Piece piece = board.at(newFile, newRank);

        // moving to an empty space
        if (piece == null) {
            // can only move 2 spaces forward on first move
            if (yDifference == 2) {
                return isFirstMove && xDifference == 0 &&
                        board.isOpenVerticalPath(newFile, newRank, this);
            } else {
                return xDifference == 0 && yDifference == 1;
            }
        } else {
            // capture by moving 1 space diagonally
            return (xDifference == 1 && yDifference == 1);
        }
    }

    /**
     * Calls the superclass' move method, but also set's the isFirstMove
     * flag to false.
     * @param newFile x coordinate of new position
     * @param newRank y coordinate of new position
     */
    @Override
    public void move(char newFile, int newRank) {
        super.move(newFile, newRank);
        isFirstMove = false;
    }

    /**
     * Method used for printing the character representing the Pawn
     * @return the character for a Pawn: 'P'
     */
    public char pieceNotation() { return PAWN; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return pawn image index
     */
    public int imageIndex() { return PAWN_IMAGE_INDEX; }
}
