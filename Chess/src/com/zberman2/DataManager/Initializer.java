package com.zberman2.DataManager;

import com.zberman2.Pieces.*;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;

/**
 * Created by Zack Berman on 9/12/2014.
 */
public class Initializer {
    Board chessboard; // reference to the Master's chessboard

    /**
     * Constructor for the initializer, which is solely responsible
     * for initializing the chess pieces for the Master class
     * @param chessboard Reference to the initially empty board
     */
    public Initializer(Board chessboard) { this.chessboard = chessboard; }

    /**
     * Populates an ArrayList of pieces, and returns it to the caller
     * @return initial ArrayList of pieces
     */
    public ArrayList<Piece> initializePieces() {
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        initializeWhitePieces(pieces);
        initializeBlackPieces(pieces);
        return pieces;
    }

    /**
     * Places the initial set of white pieces in the ArrayList
     * @param pieces initial ArrayList of pieces
     */
    private void initializeWhitePieces(ArrayList<Piece> pieces) {
        int y = pawnRow(WHITE);
        initializePawns(WHITE, y, pieces);
        y = backRow(WHITE);
        initializeRooks(WHITE, y, pieces);
        initializeKnights(WHITE, y, pieces);
        initializeBishops(WHITE, y, pieces);
        initializeQueen(WHITE, y, pieces);
        initializeKing(WHITE, y, pieces);
    }

    /**
     * Places the initial set of black pieces in the ArrayList
     * @param pieces initial ArrayList of pieces
     */
    private void initializeBlackPieces(ArrayList<Piece> pieces) {
        int y = pawnRow(BLACK);
        initializePawns(BLACK, y, pieces);
        y = backRow(BLACK);
        initializeRooks(BLACK, y, pieces);
        initializeKnights(BLACK, y, pieces);
        initializeBishops(BLACK, y, pieces);
        initializeQueen(BLACK, y, pieces);
        initializeKing(BLACK, y, pieces);
    }

    /**
     * Creates the Rooks and places them in the ArrayList of pieces
     * @param color black or white
     * @param y y coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeRooks(int color, int y,
                                 ArrayList<Piece> pieces) {
        pieces.add(new Rook(color, 'a', y));
        pieces.add(new Rook(color, 'h', y));
    }

    /**
     * Creates the Bishops and places them in the ArrayList of pieces
     * @param color black or white
     * @param y y coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeBishops(int color, int y,
                                   ArrayList<Piece> pieces) {
        pieces.add(new Bishop(color, 'c', y));
        pieces.add(new Bishop(color, 'f', y));
    }

    /**
     * Creates the Knights and places them in the ArrayList of pieces
     * @param color black or white
     * @param y y coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeKnights(int color, int y,
                                   ArrayList<Piece> pieces) {
        pieces.add(new Knight(color, 'b', y));
        pieces.add(new Knight(color, 'g', y));
    }

    /**
     * Creates the Queen and places her in the ArrayList of pieces
     * @param color black or white
     * @param y y coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeQueen(int color, int y,
                                 ArrayList<Piece> pieces) {
        pieces.add(new Queen(color, 'd', y));
    }

    /**
     * Creates the King and places him in the ArrayList of pieces
     * @param color black or white
     * @param y y coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeKing(int color, int y,
                                ArrayList<Piece> pieces) {
        pieces.add(new King(color, 'e', y));
    }

    /**
     * Creates the Pawns and places them in the ArrayList of pieces
     * @param color black or white
     * @param y y coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializePawns(int color, int y,
                                 ArrayList<Piece> pieces) {
        for (char x = 'a'; x < ('a' + chessboard.getxDimension()); x++) {
            pieces.add(new Pawn(color, x, y));
        }
    }

    /**
     * Returns the corresponding row number for the Pawns, depending
     * on their color
     * @param color color of the Pawns
     * @return initial row for the Pawns
     */
    private int pawnRow(int color) {
        if (color == WHITE) return 2;
        else return 7;
    }

    /**
     * Returns the corresponding row number for the pieces in the back row,
     * depending on their color
     * @param color color of the non-Pawn pieces
     * @return initial row of back row pieces
     */
    private int backRow(int color) {
        if (color == WHITE) return 1;
        else return 8;
    }
}
