package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Piece;
import com.zberman2.Pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Rook class
 */
public class RookTest {
    Rook rook;
    Board chessboard;
    ArrayList<Piece> pieces;

    /**
     * Create a single Rook and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        rook = new Rook(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(rook);
        chessboard = new Board(pieces);
    }

    /**
     * Test that it can only move horizontally or vertically
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char x = rook.getX();
        int y = rook.getY();

        assertEquals(true, rook.validMotion((char)(x+2), y, chessboard));
        assertEquals(true, rook.validMotion((char)(x), y+3, chessboard));
        assertEquals(false, rook.validMotion((char)(x+2), y+1, chessboard));
    }

    /**
     * Test that it can only move horizontally or vertically to open
     * spaces on the board with no pieces in between the starting
     * and ending points
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char x = rook.getX();
        int y = rook.getY();

        assertEquals(true, rook.canMove((char) (x + 2), y, chessboard));
        assertEquals(true, rook.canMove((char) (x), y + 3, chessboard));
        assertEquals(false, rook.canMove((char) (x + 2), y + 1, chessboard));
    }
}