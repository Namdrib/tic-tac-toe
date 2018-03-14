import javax.swing.JPanel;

public class Board extends JPanel
{
	// Restrictions:
	// Board must be at least 2*2
	// requiredToWin must be: 1 < requiredToWin <= min(n, m)
	// where n = num columns, m = num rows
	// e.g. in a 3*5 board, requiredToWin must be 2 or 3
	private final int		n			= 3;
	private final int		m			= 3;
	private BoardToken[][]	board;
	private final int		numToWin	= 3;

	// Constructor (defaults to a 3*3 board with 3 to win)
	public Board()
	{
		board = new BoardToken[3][3];
	}
	
	public Board(BoardToken[][] board)
	{
		this.board = new BoardToken[n][m];
		for (int i = 0; i < n; i++)
		{
			this.board[i] = board[i].clone();
		}
	}

	// Copy constructor
	public Board(Board b)
	{
		this(b.getBoard());
	}

	// Initialise the board by setting all spaces to EMPTY
	public void init()
	{
		for (int i = 0; i < this.n; i++)
		{
			for (int j = 0; j < this.m; j++)
			{
				board[i][j] = BoardToken.EMPTY;
			}
		}
	}

	// The outside world's interaction with the board
	public boolean setPosition(Move move)
	{
		int moveN = move.getN();
		int moveM = move.getM();
		BoardToken moveToken = move.getToken();

		// Null token
		if (moveToken == null)
		{
			System.err.println("Null token");
			return false;
		}
		// Out of bounds
		else if (moveN < 0 || moveN >= this.n || moveM < 0 || moveM >= this.m)
		{
			System.err.println(moveN + " " + moveM + " out of bounds");
			System.exit(1);
			return false;
		}
		// Piece already occupying this space
		else if (board[moveN][moveM] != BoardToken.EMPTY
				&& moveToken != BoardToken.EMPTY)
		{
			System.err.println(moveN + " " + moveM + " already occupied");
			return false;
		}
		else
		{
			// Valid: set and return true
			board[moveN][moveM] = moveToken;
			return true;
		}
	}

	// Return a copy of the board
	public BoardToken[][] getBoard()
	{
		BoardToken[][] out = new BoardToken[n][m];
		for (int i = 0; i < n; i++)
		{
			out[i] = board[i].clone();
		}

		return out;
	}

	public int getNumToWin()
	{
		return numToWin;
	}

	// Returns true iff board contains no BoardToken.EMPTY
	public boolean isFull()
	{
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
			{
				if (board[i][j] == BoardToken.EMPTY)
				{
					return false;
				}
			}
		}
		return true;
	}

	// Returns BoardToken.EMPTY if nobody has any winning moves
	// Otherwise returns the BoardToken with winning placement
	public BoardToken hasWin()
	{
		// TODO : General for any n, m, p

		// This is only for 3, 3, 3
		// Check rows
		boolean flag;
		for (int i = 0; i < n; i++)
		{
			flag = true;
			for (int j = 1; j < m; j++)
			{
				BoardToken current = board[i][j];
				if (current != board[i][0] || current == BoardToken.EMPTY)
				{
					flag = false;
					break;
				}
			}
			if (flag == true)
			{
				// System.out.println("Win by row " + i);
				return board[i][0];
			}
		}

		// Check columns
		for (int i = 0; i < m; i++)
		{
			flag = true;
			for (int j = 0; j < n; j++)
			{
				BoardToken current = board[j][i];
				if (board[j][i] != board[0][i] || current == BoardToken.EMPTY)
				{
					flag = false;
					break;
				}
			}
			if (flag == true)
			{
				// System.out.println("Win by column " + i);
				return board[0][i];
			}
		}

		// Check diagonals
		// Top-left to bottom-right
		flag = true;
		for (int i = 0; i < n; i++)
		{
			BoardToken current = board[i][i];
			if (board[i][i] != board[0][0] || current == BoardToken.EMPTY)
			{
				flag = false;
				break;
			}
		}

		if (flag == true)
		{
			// System.out.println("Win by top-left to bottom-right");
			return board[0][0];
		}

		// Bottom-left to top-right
		flag = true;
		for (int i = 0; i < n; i++)
		{
			BoardToken current = board[n - i - 1][i];
			if (current != board[0][n - 1] || current == BoardToken.EMPTY)
			{
				flag = false;
				break;
			}
		}
		if (flag == true)
		{
			// System.out.println("Win by bottom-left to top-right");
			return board[0][n - 1];
		}

		return BoardToken.EMPTY;
	}

	// Display the board in a fashion like this:
	//  X | X | X 
	// ---+---+---
	//  X | X | X 
	// ---+---+---
	//  X | X | X 
	// where X is replaced with the token. If the token is empty, leave a space
	public void displayBoard()
	{
		for (int i = 0; i < n; i++)
		{
			// Build current row's output and separator
			String outString = new String();
			String separator = new String();
			for (int j = 0; j < m; j++)
			{
				outString += " " + board[i][j].toString();
				separator += "---";
				if (j < m - 1)
				{
					outString += " |";
					separator += "+";
				}
			}

			System.out.println(outString);

			// Print separator
			if (i < n - 1)
			{
				System.out.println(separator);
			}
		}
		System.out.println();
	}

	public static void main(String[] args)
	{
		Board board = new Board();
		BoardToken x = BoardToken.X;
		BoardToken o = BoardToken.O;

		for (int i = 0; i < 3; i++)
		{
			if (i % 2 == 0) for (int j = 0; j < 3; j++)
			{
				Move m = new Move(i, j, x);
				if (!board.setPosition(new Move(i, j, x)))
				{
					System.out.println("Failed to set " + m);
				}
			}
		}
		board.displayBoard();
	}
}
