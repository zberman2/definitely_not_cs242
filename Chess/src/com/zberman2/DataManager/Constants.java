package com.zberman2.DataManager;

/**
 * Class containing constants for the chars representing pieces
 * and the integers representing colors
 * Created by Zack Berman on 9/11/2014.
 */
public final class Constants {
    public static final char PAWN = 'P';
    public static final char ROOK = 'R';
    public static final char KNIGHT = 'N'; // K is taken by King
    public static final char BISHOP = 'B';
    public static final char KING = 'K';
    public static final char QUEEN = 'Q';

    public static final int WHITE = 0;
    public static final int BLACK = 1;

    private Constants() {
        // this prevents even the native class from calling
        // this constructor
        throw new AssertionError();
    }
}
