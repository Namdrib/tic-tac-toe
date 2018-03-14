import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Computer implements Player
{
	protected BoardToken	token;
	protected BoardToken    theirToken;
	protected int			n, m;
	protected Board			boardRef;

	public Computer()
	{
		;
	}

	public Computer(BoardToken token)
	{
		setBoardToken(token);
	}

	@Override
	public void setBoardReference(Board b)
	{
		boardRef = b;
	}

	// Curate a list of possible moves (where the board is empty)
	protected List<Move> getPossibleMoves()
	{
		List<Move> possibleMoves = new ArrayList<Move>();

		BoardToken[][] board = boardRef.getBoard();
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if (board[i][j] == BoardToken.EMPTY)
				{
					possibleMoves.add(new Move(i, j, token));
				}
			}
		}

		return possibleMoves;
	}
	
	protected int boardUtility()
	{
		// TODO : {single,double}\ {wins,losses}
		return 0;
	}

	// -----------------------------------------------------------------------
	/**
	 * The heuristic evaluation function for the current board
	 * 
	 * @Return +100, +10, +1 for EACH 3-, 2-, 1-in-a-line for computer. -100,
	 *         -10, -1 for EACH 3-, 2-, 1-in-a-line for opponent. 0 otherwise
	 */
	protected int evaluate()
	{
		int score = 0;
		// Evaluate score for each of the 8 lines (3 rows, 3 columns, 2 diagonals)
		score += evaluateLine(0, 0, 0, 1, 0, 2); // row 0
		score += evaluateLine(1, 0, 1, 1, 1, 2); // row 1
		score += evaluateLine(2, 0, 2, 1, 2, 2); // row 2
		score += evaluateLine(0, 0, 1, 0, 2, 0); // col 0
		score += evaluateLine(0, 1, 1, 1, 2, 1); // col 1
		score += evaluateLine(0, 2, 1, 2, 2, 2); // col 2
		score += evaluateLine(0, 0, 1, 1, 2, 2); // diagonal
		score += evaluateLine(0, 2, 1, 1, 2, 0); // alternate diagonal
		return score;
	}

	/**
	 * The heuristic evaluation function for the given line of 3 cells
	 * 
	 * @Return +100, +10, +1 for 3-, 2-, 1-in-a-line for computer. -100, -10, -1
	 *         for 3-, 2-, 1-in-a-line for opponent. 0 otherwise
	 */
	protected int evaluateLine(int row1, int col1, int row2, int col2, int row3,
			int col3)
	{
		int score = 0;
		BoardToken board[][] = boardRef.getBoard();
		// First cell
		if (board[row1][col1] == token)
		{
			score = 1;
		}
		else if (board[row1][col1] != token)
		{
			score = -1;
		}

		// Second cell
		if (board[row2][col2] == token)
		{
			if (score == 1)
			{ // cell1 is token
				score = 10;
			}
			else if (score == -1)
			{ // cell1 is oppSeed
				return 0;
			}
			else
			{ // cell1 is empty
				score = 1;
			}
		}
		else if (board[row2][col2] != token)
		{
			if (score == -1)
			{ // cell1 is oppSeed
				score = -10;
			}
			else if (score == 1)
			{ // cell1 is token
				return 0;
			}
			else
			{ // cell1 is empty
				score = -1;
			}
		}

		// Third cell
		if (board[row3][col3] == token)
		{
			if (score > 0)
			{ // cell1 and/or cell2 is token
				score *= 10;
			}
			else if (score < 0)
			{ // cell1 and/or cell2 is oppSeed
				return 0;
			}
			else
			{ // cell1 and cell2 are empty
				score = 1;
			}
		}
		else if (board[row3][col3] != token)
		{
			if (score < 0)
			{ // cell1 and/or cell2 is oppSeed
				score *= 10;
			}
			else if (score > 1)
			{ // cell1 and/or cell2 is token
				return 0;
			}
			else
			{ // cell1 and cell2 are empty
				score = -1;
			}
		}
		return score;
	}
	// -----------------------------------------------------------------------

	protected boolean isAtLeafNode()
	{
		return boardRef.isFull() || boardRef.hasWin() != BoardToken.EMPTY;
	}


	// Randomly pick a move
	@Override
	public void generateMove(Scanner scan)
	{
		;
	}

	@Override
	public Move getMove()
	{
		return new Move(n, m, token);
	}

	@Override
	public void setBoardToken(BoardToken token)
	{
		if (this.token == null)
		{
			this.token = token;
			theirToken = (token == BoardToken.X) ? BoardToken.O : BoardToken.X;
		}
	}

	@Override
	public BoardToken getBoardToken()
	{
		return token;
	}

}
