package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.Pieces.Pawn;
import com.zberman2.Pieces.Piece;
import com.zberman2.Pieces.Queen;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Piece class
 */
public class PieceTest {
    Piece piece;
    ArrayList<Piece> pieces;
    Board chessboard;

    /**
     * Create a main queen piece, and surrounding pawns which will
     * test if the Queen can move and capture properly
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        piece = new Queen(WHITE, 'd', 4);
        pieces = new ArrayList<Piece>();
        pieces.add(piece);

        // add pieces that will be in the way of the queen
        pieces.add(new Pawn(BLACK, 'd', 6));
        pieces.add(new Pawn(BLACK, 'f', 4));
        pieces.add(new Pawn(BLACK, 'f', 6));

        chessboard = new Board(pieces);
    }

    /**
     * Test that the isDiagonalMotion function only returns true
     * if a destination is located diagonally away from the Queen
     * @throws Exception
     */
    @Test
    public void testIsDiagonalMotion() throws Exception {
        assertEquals(true, piece.isDiagonalMotion('e', 5));
        assertEquals(true, piece.isDiagonalMotion('e', 3));
        assertEquals(true, piece.isDiagonalMotion('c', 3));
        assertEquals(false, piece.isDiagonalMotion('d', 5));
        assertEquals(false, piece.isDiagonalMotion('e', 4));
    }

    /**
     * Test that the isVerticalMotion function only returns true
     * if a destination is located vertically away from the Queen
     * @throws Exception
     */
    @Test
    public void testIsVerticalMotion() throws Exception {
        assertEquals(true, piece.isVerticalMotion('d', 5));
        assertEquals(true, piece.isVerticalMotion('d', 3));
        assertEquals(true, piece.isVerticalMotion('d', 1));
        assertEquals(false, piece.isVerticalMotion('c', 5));
        assertEquals(false, piece.isVerticalMotion('e', 4));
    }

    /**
     * Test that the isHorizontalMotion function only returns true
     * if a destination is located horizontally away from the Queen
     * @throws Exception
     */
    @Test
    public void testIsHorizontalMotion() throws Exception {
        assertEquals(true, piece.isHorizontalMotion('a', 4));
        assertEquals(true, piece.isHorizontalMotion('f', 4));
        assertEquals(true, piece.isHorizontalMotion('c', 4));
        assertEquals(false, piece.isHorizontalMotion('d', 5));
        assertEquals(false, piece.isHorizontalMotion('a', 2));
    }

    /**
     * Given diagonal paths, determine if the Queen can either capture or
     * move to a space if there are no pieces in between the starting and
     * ending points
     * @throws Exception
     */
    @Test
    public void testIsOpenDiagonalPath() throws Exception {
        assertEquals(true, piece.isOpenDiagonalPath('f', 6, chessboard));
        assertEquals(true, piece.isOpenDiagonalPath('c', 3, chessboard));
        assertEquals(false, piece.isOpenDiagonalPath('g', 7, chessboard));
    }

    /**
     * Given vertical paths, determine if the Queen can either capture or
     * move to a space if there are no pieces in between the starting and
     * ending points
     * @throws Exception
     */
    @Test
    public void testIsOpenVerticalPath() throws Exception {
        assertEquals(true, piece.isOpenVerticalPath('d', 6, chessboard));
        assertEquals(true, piece.isOpenVerticalPath('d', 2, chessboard));
        assertEquals(false, piece.isOpenVerticalPath('d', 7, chessboard));
    }

    /**
     * Given horizontal paths, determine if the Queen can either capture or
     * move to a space if there are no pieces in between the starting and
     * ending points
     * @throws Exception
     */
    @Test
    public void testIsOpenHorizontalPath() throws Exception {
        assertEquals(true, piece.isOpenHorizontalPath('f', 4, chessboard));
        assertEquals(true, piece.isOpenHorizontalPath('c', 4, chessboard));
        assertEquals(false, piece.isOpenHorizontalPath('g', 4, chessboard));
    }

    /**
     * Test that isAt will only return true if a piece is both located at an
     * (x, y) coordinate, and is not yet captured
     * @throws Exception
     */
    @Test
    public void testIsAt() throws Exception {
        assertEquals(false, piece.isAt('a', 1));
        assertEquals(true, piece.isAt(piece.getX(), piece.getY()));
        piece.setIsCapturedTrue();

        // now false because the queen has been captured
        assertEquals(false, piece.isAt(piece.getX(), piece.getY()));
    }
}