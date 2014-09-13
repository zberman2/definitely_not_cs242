package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Bishop;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

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
        chessboard = new Board(pieces);
    }

    /**
     * Test that it can only move diagonally
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char x = bishop.getX();
        int y = bishop.getY();

        assertEquals(true, bishop.validMotion((char)(x+3), y-3, chessboard));
        assertEquals(true, bishop.validMotion((char)(x-2), y+2, chessboard));
        assertEquals(false, bishop.validMotion((char)(x+1), y-3, chessboard));
    }

    /**
     * Test that it can only move diagonally to open spaces, with
     * no spaces in between the starting and ending points
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char x = bishop.getX();
        int y = bishop.getY();

        assertEquals(true, bishop.canMove((char)(x+3), y-3, chessboard));
        assertEquals(true, bishop.canMove((char)(x-2), y+2, chessboard));
        assertEquals(false, bishop.canMove((char)(x+1), y-3, chessboard));
    }
}