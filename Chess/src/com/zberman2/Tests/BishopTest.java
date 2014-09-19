package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.Bishop;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Bishop class
 */
public class BishopTest {
    Bishop bishop;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Bishop and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        bishop = new Bishop(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(bishop);
        pieces.add(new Pawn(BLACK, 'b', 2));
        pieces.add(new Pawn(WHITE, 'f', 6));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that it can only move diagonally
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = bishop.getFile();
        int rank = bishop.getRank();

        // show diagonal movement is valid, and other movement is not
        assertEquals(true,
                bishop.validMotion((char)(file+3), rank-3, chessboard));
        assertEquals(true,
                bishop.validMotion((char)(file-2), rank+2, chessboard));
        assertEquals(false,
                bishop.validMotion((char)(file+1), rank-3, chessboard));
    }

    /**
     * Test that it can only move diagonally to open spaces, with
     * no spaces in between the starting and ending points
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = bishop.getFile();
        int rank = bishop.getRank();

        // same spots as valid motion test
        assertEquals(true,
                bishop.canMove((char)(file+3), rank-3, chessboard));
        assertEquals(true,
                bishop.canMove((char)(file-2), rank+2, chessboard));
        assertEquals(false,
                bishop.canMove((char)(file+1), rank-3, chessboard));

        // can't capture own piece
        assertEquals(false,
                bishop.canMove('f', 6, chessboard));

        // can capture opponent's piece
        assertEquals(true,
                bishop.canMove('b', 2, chessboard));

        // piece is in the way
        assertEquals(false,
                bishop.canMove('a', 1, chessboard));
    }
}