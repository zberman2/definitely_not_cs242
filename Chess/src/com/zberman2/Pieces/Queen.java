package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import static com.zberman2.DataManager.Constants.QUEEN;

/**
 * Created by Zack Berman on 9/10/2014.
 */
public class Queen extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param x x coordinate
     * @param y y coordinate
     */
    public Queen(int color, char x, int y) {
        super(color, x, y);
    }

    /**
     * Asserts that the new space (a,b) is either diagonally, horizontally,
     * or vertically away from the Queen's current position. If it is, this
     * method checks to make sure no pieces are in the Queen's way in that
     * direction.
     * @param a x coordinate of new position
     * @param b y coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Queen's behavior
     */
    @Override
    public boolean validMotion(char a, int b, Board board) {
        if (!isDiagonalMotion(a,b)) {
            if (!isVerticalMotion(a,b)) {
                if (!isHorizontalMotion(a, b)) { // illegal motion
                    return false;
                } else { // horizontal motion
                    return isOpenHorizontalPath(a, b, board);
                }
            } else { // vertical motion
                return isOpenVerticalPath(a, b, board);
            }
        } else { // diagonal motion
            return isOpenDiagonalPath(a, b, board);
        }
    }

    /**
     * Method used for printing the character representing the Queen
     * @return the character for a Queen: 'Q'
     */
    public char pieceNotation() { return QUEEN; }
}
