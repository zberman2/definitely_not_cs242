package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Knight;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Knight class
 */
public class KnightTest {
    Knight knight;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a single Knight and add it to a board so we can
     * see how it behaves
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        knight = new Knight(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(knight);
        chessboard = new Board(pieces);
    }

    /**
     * Test that it can only move in L shapes where one leg is
     * 2 sides long and the other is 1 side long
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char x = knight.getX();
        int y = knight.getY();

        assertEquals(true, knight.validMotion((char) (x + 2), y + 1, chessboard));
        assertEquals(true, knight.validMotion((char)(x + 1), y + 2, chessboard));
        assertEquals(false, knight.validMotion(x, y + 2, chessboard));
    }

    /**
     * Test that it can only move in L shapes to open spaces
     * on the board
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char x = knight.getX();
        int y = knight.getY();

        assertEquals(true, knight.canMove((char) (x + 2), y + 1, chessboard));
        assertEquals(true, knight.canMove((char)(x + 1), y + 2, chessboard));
        assertEquals(false, knight.canMove(x, y + 2, chessboard));
    }
}