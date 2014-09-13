package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.BISHOP;

/**
 * Class describing the Bishop
 * Created by Zack Berman on 9/10/2014.
 */
public class Bishop extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param x x coordinate
     * @param y y coordinate
     */
    public Bishop(int color, char x, int y) {
        super(color, x, y);
    }

    /**
     * Asserts that the new space (a,b) is diagonally away from the
     * Bishop's current position. If it is, this method checks to
     * make sure that there are no pieces in the bishop's way in that
     * direction.
     * @param a x coordinate of new position
     * @param b y coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Bishop's behavior
     */
    @Override
    public boolean validMotion(char a, int b, Board board) {
        if (!isDiagonalMotion(a,b)) return false;
        return isOpenDiagonalPath(a, b, board);
    }

    /**
     * Method used for printing the character representing the Bishop
     * @return the character for a Bishop: 'B'
     */
    public char pieceNotation() { return BISHOP; }
}
