import java.util.Scanner;

public class Human implements Player
{
	protected BoardToken	token		= null;
	protected int			n, m;
	protected Board			boardRef	= null;

	public Human()
	{
		;
	}

	public Human(BoardToken token)
	{
		setBoardToken(token);
	}

	@Override
	public void setBoardReference(Board b)
	{
		boardRef = b;
	}

	// Ask the player for which row and column to use
	@Override
	public void generateMove(Scanner scan)
	{
		// TODO : Exception checking and/or prompting
		n = scan.nextInt();
		m = scan.nextInt();
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
		}
	}

	@Override
	public BoardToken getBoardToken()
	{
		return token;
	}
}
