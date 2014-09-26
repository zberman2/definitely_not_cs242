package com.zberman2.Pieces;

import com.zberman2.DataManager.Board;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.zberman2.DataManager.Constants.KNIGHT;
import static com.zberman2.DataManager.Constants.KNIGHT_IMAGE_INDEX;
import static com.zberman2.DataManager.Constants.chessPieces;

/**
 * Class describing the Knight
 * Created by Zack Berman on 9/10/2014.
 */
public class Knight extends Piece {
    /**
     * Call the superclass constructor
     * @param color white or black
     * @param file x coordinate
     * @param rank y coordinate
     */
    public Knight(int color, char file, int rank) {
        super(color, file, rank);
    }

    /**
     * Asserts that the new space (newFile, newRank) is either 2 up/down and 1 left/right
     * or 1 up/down and 2 left/right away from the Knight's current position
     * @param newFile file coordinate of new position
     * @param newRank rank coordinate of new position
     * @param board Current state of the chess board
     * @return true if the motion fits the Knights' behavior
     */
    @Override
    public boolean validMotion(char newFile, int newRank, Board board) {
        int xDifference = fileDifference(newFile);
        int yDifference = rankDifference(newRank);

        return ((xDifference == 1 && yDifference == 2) ||
                (xDifference == 2 && yDifference == 1));
    }

    /**
     * Method used for printing the character representing the Knight
     * @return the character for a Knight: 'N'
     */
    public char pieceNotation() { return KNIGHT; }

    /**
     * Returns the index of the image array in the GUI class where this
     * particular type of piece is located
     * @return knight image index
     */
    public int imageIndex() { return KNIGHT_IMAGE_INDEX; }
}
