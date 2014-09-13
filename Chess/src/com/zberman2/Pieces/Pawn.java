package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.PAWN;
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
     * @param x x coordinate
     * @param y y coordinate
     */
    public Pawn(int color, char x, int y) {
        super(color, x, y);
        isFirstMove = true;
    }

    /**
     * Asserts that the new space is 2 spaces up/down (if it is the Pawn's
     * first move) or only 1 space up/down.  In the case that the Pawn is
     * capturing another piece, this function allows for moving
     * diagonally 1 space.
     * @param a x coordinate of new position
     * @param b y coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Pawns's behavior
     */
    @Override
    public boolean validMotion(char a, int b, Board board) {
        // make sure the pawn is moving forward, depending on its color
        if (getColor() == WHITE) {
            if (b < getY()) return false;
        } else {
            if (b > getY()) return false;
        }

        int yDifference = yDifference(b);
        int xDifference = xDifference(a);
        Piece piece = board.at(a,b);

        // moving to an empty space
        if (piece == null) {
            // can only move 2 spaces forward on first move
            if (yDifference == 2) {
                return isFirstMove && xDifference == 0 &&
                        isOpenVerticalPath(a, b, board);
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
     * @param a x coordinate of new position
     * @param b y coordinate of new position
     */
    @Override
    public void move(char a, int b) {
        super.move(a, b);
        isFirstMove = false;
    }

    /**
     * Method used for printing the character representing the Pawn
     * @return the character for a Pawn: 'P'
     */
    public char pieceNotation() { return PAWN; }
}
