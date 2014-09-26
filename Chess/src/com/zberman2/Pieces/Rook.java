package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.zberman2.DataManager.Constants.ROOK;
import static com.zberman2.DataManager.Constants.ROOK_IMAGE_INDEX;
import static com.zberman2.DataManager.Constants.chessPieces;

/**
 * Created by Zack Berman on 9/10/2014.
 * Class to define the Rook
 */
public class Rook extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Rook(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * Asserts that the new space (newFile, newRank) is horizontally or vertically
     * away from the Rook's current position. If it is, this method
     * checks to make sure no pieces are in the Rook's way in that direction.
     * @param newFile file coordinate of new position
     * @param newRank rank coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Rook's behavior
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        if (!isVerticalMotion(newFile, newRank)) {
            return isHorizontalMotion(newFile, newRank) &&
                    board.isOpenHorizontalPath(newFile, newRank, this);
        } else { // vertical motion
            return board.isOpenVerticalPath(newFile, newRank, this);
        }
    }

    /**
     * Method used for printing the character representing the Rook
     * @return the character for a Rook: 'R'
     */
    public char pieceNotation() { return ROOK; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return piece image index
     */
    public int imageIndex() { return ROOK_IMAGE_INDEX; }
}
