package com.zberman2.Tests;

import static com.zberman2.DataManager.Constants.*;
import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import com.zberman2.Pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Class which tests the functionality of the Board class
 */
public class BoardTest {
    Board chessboard;
    Piece queen;

    /**
     * Creates a board with a White Queen, and a few black pawns
     * that will either block the Queen's path, or be taken by the Queen
     *
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        chessboard = new StandardBoard();
        ArrayList<Piece> pieces = new ArrayList<Piece>();
        queen = new Queen(WHITE, 'd', 4);
        pieces.add(queen);
        pieces.add(new Pawn(BLACK, 'b', 4));
        pieces.add(new Pawn(BLACK, 'd', 2));
        pieces.add(new Pawn(BLACK, 'b', 6));
        chessboard.setPieces(pieces);
    }

    /**
     * Tests that the board responds with the correct piece
     * at a given rank and file, or null if nothing is there.
     * @throws Exception
     */
    @Test
    public void testAt() throws Exception {
        assertEquals(queen, chessboard.at('d', 4));
        assertEquals(null, chessboard.at('a', 1));
    }

    /**
     * Tests whether or not the Queen has an open path to a new
     * rank and file
     * @throws Exception
     */
    @Test
    public void testIsOpenDiagonalPath() throws Exception {
        assertEquals(true,
                chessboard.isOpenDiagonalPath('f', 6, queen));
        // can't jump pieces
        assertEquals(false,
                chessboard.isOpenDiagonalPath('a', 7, queen));
        // can capture black pawn
        assertEquals(true,
                chessboard.isOpenDiagonalPath('b', 6, queen));
    }

    /**
     * Tests whether or not the Queen has an open path to a new
     * rank and file
     * @throws Exception
     */
    @Test
    public void testIsOpenVerticalPath() throws Exception {
        assertEquals(true,
                chessboard.isOpenVerticalPath('d', 7, queen));
        // can't jump pieces
        assertEquals(false,
                chessboard.isOpenVerticalPath('d', 1, queen));
        // can capture black pawn
        assertEquals(true,
                chessboard.isOpenVerticalPath('d', 2, queen));
    }

    /**
     * Tests whether or not the Queen has an open path to a new
     * rank and file
     * @throws Exception
     */
    @Test
    public void testIsOpenHorizontalPath() throws Exception {
        assertEquals(true,
                chessboard.isOpenHorizontalPath('f', 4, queen));
        // can't jump pieces
        assertEquals(false,
                chessboard.isOpenHorizontalPath('a', 4, queen));
        // can capture black pawn
        assertEquals(true,
                chessboard.isOpenHorizontalPath('b', 4, queen));
    }
}