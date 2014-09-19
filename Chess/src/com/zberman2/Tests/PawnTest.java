package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
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
        pieces.add(new Pawn(BLACK, 'd', 5));
        chessboard = new StandardBoard(pieces);
    }

    /**
     * Test that the pawn can only move forward, that it can
     * only capture 1 space diagonally, and that it can only move
     * 2 spaces at once on its first move
     * @throws Exception
     */
    @Test
    public void testValidMotion() throws Exception {
        char file = whitePawn.getFile();
        int rank = whitePawn.getRank();

        assertEquals(true,
                whitePawn.validMotion((char)(file + 1), rank + 1, chessboard));
        assertEquals(false,
                whitePawn.validMotion(file, rank + 2, chessboard));
    }

    /**
     * Essentially the same as the test above, only now with the
     * canMove function (which determines valid and legal movement)
     * @throws Exception
     */
    @Test
    public void testCanMove() throws Exception {
        char file = whitePawn.getFile();
        int rank = whitePawn.getRank();

        assertEquals(true,
                whitePawn.canMove((char)(file + 1), rank + 1, chessboard));
        assertEquals(false,
                whitePawn.canMove(file, rank + 2, chessboard));

        // can't capture moving 1 space vertically
        assertEquals(false,
                whitePawn.canMove(file, rank + 1, chessboard));
    }
}