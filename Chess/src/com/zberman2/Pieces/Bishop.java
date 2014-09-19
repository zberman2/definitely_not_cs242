package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.BISHOP;
import static com.zberman2.DataManager.Constants.BISHOP_IMAGE_INDEX;

/**
 * Class describing the Bishop
 * Created by Zack Berman on 9/10/2014.
 */
public class Bishop extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Bishop(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * Asserts that the new space (newFile, newRank) is diagonally away from the
     * Bishop's current position. If it is, this method checks to
     * make sure that there are no pieces in the bishop's way in that
     * direction.
     * @param newRank rank coordinate of new position
     * @param newFile file coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Bishop's behavior
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        if (!isDiagonalMotion(newFile, newRank)) return false;
        return board.isOpenDiagonalPath(newFile, newRank, this);
    }

    /**
     * Method used for printing the character representing the Bishop
     * @return the character for a Bishop: 'B'
     */
    public char pieceNotation() { return BISHOP; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return bishop image index
     */
    public int imageIndex() { return BISHOP_IMAGE_INDEX; }
}
