package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Pawn class
 */
public class PawnTest {
    Pawn whitePawn;
    Pawn blackPawn;
    Board chessboard;
    ArrayList<Piece> pieces;

    /**
     * Create two pawns and add them to the board so we can
     * see how they move and especially how they capture
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        whitePawn = new Pawn(WHITE, 'd', 3);
        whitePawn.move('d', 4);
        blackPawn = new Pawn(BLACK, 'e', 5);
        pieces = new ArrayList<Piece>();
        pieces.add(whitePawn);
        pieces.add(blackPawn);
        chessboard = new Board(pieces);
    }

    /**
     * Test that the pawn can only move forward, that it can
     * only capture 1 space diagonally, and that it can only move
     * 2 spaces at once on its first move
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char x = whitePawn.getX();
        int y = whitePawn.getY();

        assertEquals(true, whitePawn.validMotion((char) (x), y + 1, chessboard));
        assertEquals(true, whitePawn.validMotion((char)(x + 1), y + 1, chessboard));
        assertEquals(false, whitePawn.validMotion(x, y + 2, chessboard));
    }

    /**
     * Essentially the same as the test above, only now with the
     * canMove function (which determines valid and legal movement)
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char x = whitePawn.getX();
        int y = whitePawn.getY();

        assertEquals(true, whitePawn.canMove((char) (x), y + 1, chessboard));
        assertEquals(true, whitePawn.canMove((char)(x + 1), y + 1, chessboard));
        assertEquals(false, whitePawn.canMove(x, y + 2, chessboard));
    }
}