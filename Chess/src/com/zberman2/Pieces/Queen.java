package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.zberman2.DataManager.Constants.QUEEN;
import static com.zberman2.DataManager.Constants.QUEEN_IMAGE_INDEX;
import static com.zberman2.DataManager.Constants.chessPieces;

/**
 * Class describing the Queen
 * Created by Zack Berman on 9/10/2014.
 */
public class Queen extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Queen(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * Asserts that the new space (newFile, newRank) is either diagonally, horizontally,
     * or vertically away from the Queen's current position. If it is, this
     * method checks to make sure no pieces are in the Queen's way in that
     * direction.
     * @param newFile file coordinate of new position
     * @param newRank rank coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Queen's behavior
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        if (!isDiagonalMotion(newFile, newRank)) {
            if (!isVerticalMotion(newFile, newRank)) {
                return isHorizontalMotion(newFile, newRank) &&
                        board.isOpenHorizontalPath(newFile, newRank, this);
            } else { // vertical motion
                return board.isOpenVerticalPath(newFile, newRank, this);
            }
        } else { // diagonal motion
            return board.isOpenDiagonalPath(newFile, newRank, this);
        }
    }

    /**
     * Method used for printing the character representing the Queen
     * @return the character for a Queen: 'Q'
     */
    public char pieceNotation() { return QUEEN; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return queen image index
     */
    public int imageIndex() { return QUEEN_IMAGE_INDEX; }
}
