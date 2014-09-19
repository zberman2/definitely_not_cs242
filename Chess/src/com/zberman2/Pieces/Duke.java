package com.zberman2.Pieces;

import static com.zberman2.DataManager.Constants.DUKE;
import com.zberman2.DataManager.Board;

/**
 * Created by Zack Berman on 9/13/2014.
 */
public class Duke extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Duke(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * The Duke can jump to any spot as long as it is 2 spaces away in either
     * the vertical or horizontal direction
     * @param newRank rank coordinate of new space
     * @param newFile file coordinate of new space
     * @param board current instance of the chess board
     * @return true if the move is valid
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        int xDifference = fileDifference(newFile);
        int yDifference = rankDifference(newRank);

        return (xDifference % 2 == 0 && yDifference % 2 == 0 &&
                xDifference     <= 3 && yDifference     <= 3);
    }

    /**
     * Method used for printing the character representing the King
     * @return the character for a Duke: 'D'
     */
    @Override
    public char pieceNotation() { return DUKE; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return -1 since we don't have an image of this piece
     */
    public int imageIndex() { return -1; }
}
