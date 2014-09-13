package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.KING;

/**
 * Class describing the King
 * Created by Zack Berman on 9/10/2014.
 */
public class King extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param x x coordinate
     * @param y y coordinate
     */
    public King(int color, char x, int y) {
        super(color, x, y);
    }

    /**
     * Asserts that the new space (a,b) is only 1 space left, right,
     * up, down or diagonally from the kings position
     * @param a x coordinate of new position
     * @param b y coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the King's behavior
     */
    @Override
    public boolean validMotion(char a, int b, Board board) {
        int xDifference = xDifference(a);
        int yDifference = yDifference(b);

        return xDifference <= 1 && yDifference <= 1;
    }

    /**
     * Method used for printing the character representing the King
     * @return the character for a King: 'K'
     */
    public char pieceNotation() { return KING; }
}
