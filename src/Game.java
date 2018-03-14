import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Game
{
	Board		board;
	Player		p1;
	Player		p2;
	Player		currentPlayer;	// Points to either p1 or p2
	BoardToken	winner;

	public Game()
	{
		;
	}

	// Connect the game with the components,
	// connect the players to the board
	// The players should already have tokens assigned
	public void connect(Board board, Player p1, Player p2)
	{
		this.board = board;
		this.p1 = p1;
		this.p2 = p2;

		p1.setBoardReference(this.board);
		p2.setBoardReference(this.board);
	}

	// Choose random player (1 or 2) to go first
	public void init()
	{
		this.init(ThreadLocalRandom.current().nextInt(1, 3));
	}

	public void init(int startingPlayer)
	{
		board.init();

		// Set currentPlayer to either p1 or p2
		currentPlayer = (startingPlayer <= 1) ? p1 : p2;
		winner = BoardToken.EMPTY;
	}

	public void play(Scanner scan)
	{
		// Keep playing until winner or board full
		winner = BoardToken.EMPTY;
		while (winner == BoardToken.EMPTY && !board.isFull())
		{
			// Keep trying to get move until provided valid move
			Move currentMove;
			do
			{
				currentPlayer.generateMove(scan);
				currentMove = currentPlayer.getMove();
			} while (!board.setPosition(currentMove));
			winner = board.hasWin();

			currentPlayer = (currentPlayer == p1) ? p2 : p1; // switch players

			board.displayBoard();
		}
	}
	
	public Board getBoard()
	{
		return board;
//		return new Board(board);
	}
	
	public Player getCurrentPlayer()
	{
		return currentPlayer;
	}

	public BoardToken getWinner()
	{
		return winner;
	}
	
	public void announceWinner()
	{;
		String message;
		switch (winner)
		{
		case X:
			message = "Player 1 wins!";
			break;
		case O:
			message = "Player 2 wins!";
			break;
		default:
			message = "Draw!";
			break;
		}
		System.out.println(message);
	}
}
