package com.zberman2.Tests;

import com.zberman2.DataManager.Board;
import com.zberman2.DataManager.Master;
import com.zberman2.DataManager.StandardBoard;
import com.zberman2.Pieces.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.zberman2.DataManager.Constants.BLACK;
import static com.zberman2.DataManager.Constants.WHITE;
import static org.junit.Assert.assertEquals;

/**
 * Class which tests the functionality of the Master class
 */
public class MasterTest {
    Board stalemateBoard;   // contains a stalemate configuration
    Board checkmateBoard;   // contains a checkmate configuration
    Board chessboard;       // contains neither (just one piece)
    Master stalemateMaster; // controls the stalemateBoard
    Master checkmateMaster; // controls the checkmateBoard
    Master master;          // controls the chessboard
    Queen queen;

    Board leaveKingInCheckBoard; // bishop blocking a king from a rook
    Master leaveKingInCheckMaster; // controls leaveKingInCheckBoard
    Bishop bishop;

    /**
     * Set up stalemateBoard with a stalemate configuration,
     * checkmateBoard with a checkmate configuration, and chessboard
     * with the queen above
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        ArrayList<Piece> stalematePieces;
        stalematePieces = new ArrayList<Piece>();
        stalematePieces.add(new Queen(WHITE, 'd', 4));
        stalematePieces.add(new King(WHITE, 'g', 4));
        stalematePieces.add(new Bishop(WHITE, 'h', 5));
        stalematePieces.add(new King(BLACK, 'h', 6));
        stalematePieces.add(new Pawn(BLACK, 'h', 7));
        stalemateBoard = new StandardBoard(stalematePieces);
        stalemateMaster = new Master(stalemateBoard);

        ArrayList<Piece> checkmatePieces;
        checkmatePieces = new ArrayList<Piece>();
        checkmatePieces.add(new King(BLACK, 'g', 1));
        checkmatePieces.add(new King(WHITE, 'g', 3));
        checkmatePieces.add(new Queen(WHITE, 'e', 1));
        checkmateBoard = new StandardBoard(checkmatePieces);
        checkmateMaster = new Master(checkmateBoard);

        ArrayList<Piece> pieces;
        pieces = new ArrayList<Piece>();
        queen = new Queen(WHITE, 'd', 4);
        pieces.add(queen);
        pieces.add(new King(WHITE, 'a', 1));
        chessboard = new StandardBoard(pieces);
        master = new Master(chessboard);

        ArrayList<Piece> leaveKingInCheckPieces;
        leaveKingInCheckPieces = new ArrayList<Piece>();
        bishop = new Bishop(WHITE, 'd', 4);
        leaveKingInCheckPieces.add(new King(WHITE, 'c', 4));
        leaveKingInCheckPieces.add(new Rook(BLACK, 'g', 4));
        leaveKingInCheckBoard = new StandardBoard(leaveKingInCheckPieces);
        leaveKingInCheckMaster = new Master(leaveKingInCheckBoard);
    }

    /**
     * Test that findPiece returns null for empty spaces,
     * and the correct piece for occupied spaces
     * @throws Exception
     */
    @Test
    public void testFindPiece() throws Exception {
        assertEquals(null, master.findPiece("a2"));
        assertEquals(queen, master.findPiece("d4"));
    }

    /**
     * Test that the queen can only move to valid spaces on the board
     * @throws Exception
     */
    @Test
    public void testMove() throws Exception {
        // can't move to the space it already occupies
        assertEquals(-1, master.move(queen, 'd', 4));

        // can't move off the board in the y direction
        assertEquals(-1, master.move(queen, 'd', 9));

        // can't move off the board in the x direction
        assertEquals(-1, master.move(queen, 'i', 4));

        // can't make an illegal move
        assertEquals(-1, master.move(queen, 'e', 7));

        // the following moves are legal
        assertEquals(0, master.move(queen, 'd', 6));
        assertEquals(0, master.move(queen, 'a', 6));
        assertEquals(0, master.move(queen, 'c', 4));

        // can't leave King in check
        assertEquals(-1, leaveKingInCheckMaster.move(bishop, 'e', 3));
    }

    /**
     * Test that the Black king is in check on the checkmateBoard, but
     * the White king is not. Also check that the black king is not in
     * check on the stalemate board
     * @throws Exception
     */
    @Test
    public void testIsCheck() throws Exception {
        assertEquals(1, checkmateMaster.isCheck(BLACK));
        assertEquals(0, checkmateMaster.isCheck(WHITE));
        assertEquals(0, stalemateMaster.isCheck(BLACK));
    }

    /**
     * Test that the black king is mated on the checkmate board and
     * that he is not on the stalemate board
     * @throws Exception
     */
    @Test
    public void testIsCheckmate() throws Exception {
        checkmateMaster.printBoard();
        assertEquals(true, checkmateMaster.isCheckmate(BLACK));
        assertEquals(false, stalemateMaster.isCheckmate(BLACK));
    }

    /**
     * Test that there is a stalemate if it is the Black side's turn
     * on the stalemateBoard, but not if the White side is up.
     * Also check that there are no legal moves for the Black side in
     * the checkmate board, but because the King is in check, it is not
     * a stalemate
     * @throws Exception
     */
    @Test
    public void testIsStalemate() throws Exception {
        stalemateMaster.printBoard();
        assertEquals(false, checkmateMaster.isStalemate(BLACK));
        assertEquals(true, stalemateMaster.isStalemate(BLACK));
        assertEquals(false, stalemateMaster.isStalemate(WHITE));
    }
}