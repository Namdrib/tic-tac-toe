import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class RandomComputer extends Computer
{
	// Picks a random unoccupied location
	@Override
	public void generateMove(Scanner scan)
	{
		// Store all the unused points
		List<Move> possibleMoves = getPossibleMoves();

		// Pick a location at random and store its position
		int index = ThreadLocalRandom.current().nextInt(possibleMoves.size());
		n = (int) possibleMoves.get(index).getN();
		m = (int) possibleMoves.get(index).getM();
	}

}
