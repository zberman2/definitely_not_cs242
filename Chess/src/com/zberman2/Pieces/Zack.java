package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;
import static com.zberman2.DataManager.Constants.*;

/**
 * Created by Zack Berman on 9/18/2014.
 */
public class Zack extends Piece{
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Zack(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * A Zack piece is the best kind of piece. Like a queen, a Zack can move
     * vertically, horizontally, or diagonally. But unlike a queen, a Zack can
     * jump over any pieces in its way.
     *
     * @param newFile     file coordinate of new space
     * @param newRank     rank coordinate of new space
     * @param board current instance of the chess board
     * @return true if moving to (newFile, newRank) is valid
     */
    public boolean validMotion(char newFile, int newRank, Board board) {
        return isHorizontalMotion(newFile, newRank) ||
                isVerticalMotion(newFile, newRank) ||
                isDiagonalMotion(newFile, newRank);
    }

    /**
     * Returns the character representing the type of Piece
     *
     * @return Piece character (see Constants for possible values)
     */
    public char pieceNotation() { return ZACK; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return -1 since we don't have an image for this piece
     */
    public int imageIndex() { return -1; }
}
