import java.util.Scanner;

public class AlphaBetaPruningComputer extends Computer
{	// Return {bestValue, bestN, bestM}
	// ^ Use the bestValue to determine further steps of minimax
	// ^ Use the bestN and bestM to target a position
	// Depth should be given as a positive number representing how
	// deep to search (reaching zero means "lowest" level to search)
	private int[] alphaBeta(int depth, BoardToken queryToken, int alpha, int beta)
	{
		boolean maximising = (token == queryToken);
		int currentValue = (maximising) ? alpha : beta;
		int bestN = -1;
		int bestM = -1;
		// "End" state (someone's won or reached depth limit)
		if (depth == 0 || isAtLeafNode())
		{
			// bestValue = boardUtility();
			currentValue = evaluate();
			return new int[] { currentValue, bestN, bestM };
		}

		// Go down every possible move
		for (Move move : getPossibleMoves())
		{
			// Try
			move.setToken(queryToken);
			boardRef.setPosition(move);

			// Check how the value has changed
			if (maximising)
			{
				currentValue = alphaBeta(depth - 1, theirToken, alpha, beta)[0];
				if (currentValue > alpha)
				{
					alpha = currentValue;
					bestN = move.getN();
					bestM = move.getM();
				}
			}
			else // minimising
			{
				currentValue = alphaBeta(depth - 1, token, alpha, beta)[0];
				if (currentValue < beta)
				{
					beta = currentValue;
					bestN = move.getN();
					bestM = move.getM();
				}
			}

			// Reset
			move.setToken(BoardToken.EMPTY);
			boardRef.setPosition(move);
			if (alpha >= beta)
			{
				break;
			}
		}
		int outValue = (maximising) ? alpha : beta;
		return new int[] { outValue, bestN, bestM };
	}

	// Use minimax to decide on best outcome
	@Override
	public void generateMove(Scanner scan)
	{
		int[] result = alphaBeta(9, token, Integer.MIN_VALUE, Integer.MAX_VALUE);
		n = result[1];
		m = result[2];
	}
}
