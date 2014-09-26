package com.zberman2.DataManager;

import com.zberman2.Pieces.*;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;

/**
 * Class which describes the behavior of an Alternate Initializer,
 * which is in charge of setting up a list of pieces with
 * alternate chess pieces.
 * Created by Zack Berman on 9/24/2014.
 */
public class AlternateInitializer extends Initializer{

    /**
     * Constructor for the initializer, which is solely responsible
     * for initializing the chess pieces for the Master class
     *
     * @param chessboard Reference to the initially empty board
     */
    public AlternateInitializer(Board chessboard) {
        super(chessboard);
    }

    /**
     * Places the initial set of white pieces in the ArrayList
     * @param pieces initial ArrayList of pieces
     */
    public void initializeWhitePieces(ArrayList<Piece> pieces) {
        int y = pawnRank(WHITE);
        initializePawns(WHITE, y, pieces);
        y = backRank(WHITE);
        initializeRooks(WHITE, y, pieces);
        initializeDukes(WHITE, y, pieces);
        initializeBishops(WHITE, y, pieces);
        initializeZack(WHITE, y, pieces);
        initializeKing(WHITE, y, pieces);
        initializeQueen(WHITE, y, pieces);
    }

    /**
     * Places the initial set of black pieces in the ArrayList
     * @param pieces initial ArrayList of pieces
     */
    public void initializeBlackPieces(ArrayList<Piece> pieces) {
        int y = pawnRank(BLACK);
        initializePawns(BLACK, y, pieces);
        y = backRank(BLACK);
        initializeRooks(BLACK, y, pieces);
        initializeDukes(BLACK, y, pieces);
        initializeBishops(BLACK, y, pieces);
        initializeZack(BLACK, y, pieces);
        initializeKing(BLACK, y, pieces);
        initializeQueen(BLACK, y, pieces);
    }

    /**
     * Creates the Rooks and places them in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeRooks(int color, int rank,
                                 ArrayList<Piece> pieces) {
        pieces.add(new Rook(color, 'a', rank));
        pieces.add(new Rook(color, 'h', rank));
    }

    /**
     * Creates the Bishops and places them in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeBishops(int color, int rank,
                                   ArrayList<Piece> pieces) {
        pieces.add(new Bishop(color, 'c', rank));
        pieces.add(new Bishop(color, 'f', rank));
    }

    /**
     * Creates the Knights and places them in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeDukes(int color, int rank,
                                 ArrayList<Piece> pieces) {
        if (color == WHITE) {
            pieces.add(new Duke(color, 'b', rank));
        } else {
            pieces.add(new Duke(color, 'g', rank));
        }
    }

    /**
     * Creates the Queen and places her in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeQueen(int color, int rank,
                                 ArrayList<Piece> pieces) {
        pieces.add(new Queen(color, 'd', rank));
    }

    /**
     * Creates the Queen and places her in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeZack(int color, int rank,
                                ArrayList<Piece> pieces) {
        if (color == WHITE) {
            pieces.add(new Zack(color, 'g', rank));
        } else {
            pieces.add(new Zack(color, 'b', rank));
        }
    }

    /**
     * Creates the King and places him in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializeKing(int color, int rank,
                                ArrayList<Piece> pieces) {
        pieces.add(new King(color, 'e', rank));
    }

    /**
     * Creates the Pawns and places them in the ArrayList of pieces
     * @param color black or white
     * @param rank rank coordinate
     * @param pieces initial ArrayList of pieces
     */
    private void initializePawns(int color, int rank,
                                 ArrayList<Piece> pieces) {
        for (char x = 'a'; x < ('a' + chessboard.getXDimension()); x++) {
            pieces.add(new Pawn(color, x, rank));
        }
    }

    /**
     * Returns the corresponding row number for the Pawns, depending
     * on their color
     * @param color color of the Pawns
     * @return initial rank for the Pawns
     */
    private int pawnRank(int color) {
        if (color == WHITE) return 2;
        else return 7;
    }

    /**
     * Returns the corresponding row number for the pieces in the back row,
     * depending on their color
     * @param color color of the non-Pawn pieces
     * @return initial rank of back row pieces
     */
    private int backRank(int color) {
        if (color == WHITE) return 1;
        else return 8;
    }
}
