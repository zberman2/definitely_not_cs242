package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Piece;
import com.zberman2.Pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Queen class
 */
public class QueenTest {
    Queen queen;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single queen and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        queen = new Queen(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(queen);
        chessboard = new Board(pieces);
    }

    /**
     * Test that it can move diagonally, horizontally, or vertically
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char x = queen.getX();
        int y = queen.getY();

        assertEquals(true, queen.validMotion((char) (x + 1), y + 1, chessboard));
        assertEquals(true, queen.validMotion((char) (x + 3), y, chessboard));
        assertEquals(false, queen.validMotion((char)(x + 1), y + 2, chessboard));
    }

    /**
     * Test that it can only move diagonally, horizontally or vertically
     * to open spaces on the board with no pieces in between the
     * starting and ending points
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char x = queen.getX();
        int y = queen.getY();

        assertEquals(true, queen.canMove((char) (x + 1), y + 1, chessboard));
        assertEquals(true, queen.canMove((char) (x + 3), y, chessboard));
        assertEquals(false, queen.canMove((char) (x + 1), y + 2, chessboard));
    }
}