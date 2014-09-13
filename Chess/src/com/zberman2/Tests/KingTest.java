package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.King;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the King class
 */
public class KingTest {
    King king;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Bishop and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        king = new King(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(king);
        chessboard = new Board(pieces);
    }

    /**
     * Test that it can only move 1 space horizontally, vertically
     * or diagonally
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char x = king.getX();
        int y = king.getY();

        assertEquals(true, king.validMotion((char) (x + 1), y + 1, chessboard));
        assertEquals(true, king.validMotion((char)(x + 1), y, chessboard));
        assertEquals(false, king.validMotion(x, y + 2, chessboard));
    }

    /**
     * Test that it can only move 1 space horizontally, vertically
     * or diagonally to open spaces on the board
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char x = king.getX();
        int y = king.getY();

        assertEquals(true, king.canMove((char)(x + 1), y + 1, chessboard));
        assertEquals(true, king.canMove((char)(x + 1), y, chessboard));
        assertEquals(false, king.canMove(x, y + 2, chessboard));
    }
}