package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.KING;
import static com.zberman2.DataManager.Constants.KING_IMAGE_INDEX;

/**
 * Class describing the King
 * Created by Zack Berman on 9/10/2014.
 */
public class King extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public King(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * Asserts that the new space (newFile, newRank) is only 1 space left, right,
     * up, down or diagonally from the kings position
     * @param newFile file coordinate of new position
     * @param newRank rank coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the King's behavior
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        int xDifference = fileDifference(newFile);
        int yDifference = rankDifference(newRank);

        return xDifference <= 1 && yDifference <= 1;
    }

    /**
     * Method used for printing the character representing the King
     * @return the character for a King: 'K'
     */
    public char pieceNotation() { return KING; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return king image index
     */
    public int imageIndex() { return KING_IMAGE_INDEX; }
}
