import java.util.Scanner;

public class MinimaxComputer extends Computer
{
	// Return {bestValue, bestN, bestM}
	// ^ Use the bestValue to determine further steps of minimax
	// ^ Use the bestN and bestM to target a position
	// Depth should be given as a positive number representing how
	// deep to search (reaching zero means "lowest" level to search)
	private int[] minimax(int depth, BoardToken queryToken)
	{
		int bestValue = (token == queryToken) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		int currentValue = 0;
		int bestN = -1;
		int bestM = -1;
		// "End" state (someone's won or reached depth limit)
		if (depth == 0 || isAtLeafNode())
		{
			// bestValue = boardUtility();
			bestValue = evaluate();
			return new int[] { bestValue, bestN, bestM };
		}

		// Go down every possible move
		for (Move move : getPossibleMoves())
		{
			// Try
			move.setToken(queryToken);
			boardRef.setPosition(move);

			// Check how the value has changed
			if (token == queryToken) // maximising
			{
				currentValue = minimax(depth - 1, theirToken)[0];
				if (currentValue > bestValue)
				{
					bestValue = currentValue;
					bestN = move.getN();
					bestM = move.getM();
				}
			}
			else // minimising
			{
				currentValue = minimax(depth - 1, token)[0];
				if (currentValue < bestValue)
				{
					bestValue = currentValue;
					bestN = move.getN();
					bestM = move.getM();
				}
			}

			// Reset
			move.setToken(BoardToken.EMPTY);
			boardRef.setPosition(move);
		}
		return new int[] { bestValue, bestN, bestM };
	}

	// Use minimax to decide on best outcome
	@Override
	public void generateMove(Scanner scan)
	{
		int[] result = minimax(9, token);
		n = result[1];
		m = result[2];
	}
}
