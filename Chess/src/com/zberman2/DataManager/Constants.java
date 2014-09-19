package com.zberman2.DataManager;

import java.awt.*;

/**
 * Class containing constants used across the Chess project
 * Created by Zack Berman on 9/11/2014.
 */
public final class Constants {
    // chars for representing pieces in the terminal window
    public static final char PAWN = 'P', ROOK = 'R', KNIGHT = 'N', // K is taken by King
            BISHOP = 'B', KING = 'K', QUEEN = 'Q', DUKE = 'D', ZACK = 'Z';

    // indexes of the piece images in the URL below
    public static final int KING_IMAGE_INDEX = 0, QUEEN_IMAGE_INDEX = 1,
            ROOK_IMAGE_INDEX = 2, KNIGHT_IMAGE_INDEX = 3,
            BISHOP_IMAGE_INDEX = 4, PAWN_IMAGE_INDEX = 5;

    public static final int BLACK = 0, WHITE = 1;

    public static final Color illiniOrange = new Color(244, 127, 36);
    public static final Color illiniBlue = new Color(0, 60, 125);

    // the following Constants are utilized by the GUI class
    public static final int FONT_SIZE = 37;
    public static final String FONT = "MONOSPACED";
    public static final int EMPTY_BORDER_THICKNESS = 15;
    public static final int LINE_BORDER_THICKNESS = 10;
    public static final int IMAGE_DIMENSION = 64;

    // location of the chess piece images for the GUI
    public static final String CHESS_PIECES_URL = "http://i.stack.imgur.com/memI0.png";

    private Constants() {
        // this prevents even the native class from calling
        // this constructor
        throw new AssertionError();
    }
}
