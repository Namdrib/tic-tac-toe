import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TestBoard
{
	private Board b;

	@Before
	public void setUp() throws Exception
	{
		b = new Board();
		b.init();
	}

	@Test
	public void testBoard()
	{
		assertNotNull(b);
		BoardToken[][] board = b.getBoard();
		assertEquals(3, board.length);
		assertEquals(3, board[0].length);
		assertEquals(3, b.getNumToWin());
	}

	@Test
	public void testInit()
	{
		b.init();
		BoardToken[][] board = b.getBoard();
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				assertEquals(BoardToken.EMPTY, board[i][j]);
			}
		}
	}

	@Test
	public void testSetPosition()
	{
		// Setup
		assertTrue(b.setPosition(new Move(0, 0, BoardToken.X)));
		assertTrue(b.setPosition(new Move(1, 0, BoardToken.O)));
		assertTrue(b.setPosition(new Move(2, 1, BoardToken.X)));
		//
		//  X |   | 
		// ---+---+---
		//  O |   |   
		// ---+---+---
		//    | X |
		//

		// Test positions
		assertFalse(b.setPosition(new Move(0, 0, BoardToken.X)));
		assertFalse(b.setPosition(new Move(0, 0, BoardToken.O)));
		assertFalse(b.setPosition(new Move(1, 2, null)));
		assertFalse(b.setPosition(new Move(5, 5, BoardToken.X)));
		assertFalse(b.setPosition(new Move(-1, -1, BoardToken.X)));

		assertTrue(b.setPosition(new Move(1, 1, BoardToken.X)));
	}

	@Test
	public void testGetBoard()
	{
		BoardToken[][] board = b.getBoard();
		assertNotNull(board);

		// Since getBoard returns a copy, changing the return value
		// should not change the original board
		board[0][0] = BoardToken.O;
		assertEquals(BoardToken.O, board[0][0]); // successfully changed the copy
		assertEquals(BoardToken.EMPTY, b.getBoard()[0][0]); // no change on original
	}

	@Test
	public void testIsFull()
	{
		BoardToken[][] board = b.getBoard();
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				assertTrue(b.setPosition(new Move(i, j, BoardToken.X)));
				if (!(i == board.length - 1 && j == board[i].length - 1))
				{
					assertFalse(b.isFull());
				}
			}
		}
		assertTrue(b.isFull());
	}

	@Test
	public void testHasWin()
	{
		// Empty board -> No win
		assertEquals(BoardToken.EMPTY, b.hasWin());

		// Normal win by X
		b.init();

		// Normal win by O
		b.init();

		// Draw
		b.init();

		// Win by X on last move
		b.init();

		// Win by O on [second to] last move
		b.init();

		// Win on each position (edges, centre) <-- regardless of dimensions
		b.init();

		fail("Not yet implemented"); // TODO
	}

}
