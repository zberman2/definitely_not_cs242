package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.KNIGHT;

/**
 * Class describing the Knight
 * Created by Zack Berman on 9/10/2014.
 */
public class Knight extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param x x coordinate
     * @param y y coordinate
     */
    public Knight(int color, char x, int y) {
        super(color, x, y);
    }

    /**
     * Asserts that the new space (a,b) is either 2 up/down and 1 left/right
     * or 1 up/down and 2 left/right away from the Knight's current position
     * @param a x coordinate of new position
     * @param b y coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Knights's behavior
     */
    @Override
    public boolean validMotion(char a, int b, Board board) {
        int xDifference = xDifference(a);
        int yDifference = yDifference(b);

        return ((xDifference == 1 && yDifference == 2) ||
                (xDifference == 2 && yDifference == 1));
    }

    /**
     * Method used for printing the character representing the Knight
     * @return the character for a Knight: 'N'
     */
    public char pieceNotation() { return KNIGHT; }
}
