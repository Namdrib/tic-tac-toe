import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.util.Scanner;

public class Main
{

	public static void main(String[] args)
	{
		// Take command line options
		TicTacToeArguments ttta = new TicTacToeArguments();
		JCommander jcommander = JCommander.newBuilder().addObject(ttta).build();
		jcommander.parse(args);
		if (ttta.help)
		{
			jcommander.usage();
		}

		Main main = new Main();
		try
		{
			main.run(ttta);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	private Constructor[] getConstructorsFor(String className)
			throws ClassNotFoundException
	{
		Constructor ctors[] = Class.forName(className).getConstructors();

		if (ctors.length == 0)
		{
			throw new ClassNotFoundException("No constructors found");
		}

		return ctors;
	}

	public void run(TicTacToeArguments args) throws Exception
	{
		// Dynamically load the specified player types
		// (from -p1 and -p2 arguments)
		Player p1 = (Player) getConstructorsFor(args.player1)[0].newInstance();
		Player p2 = (Player) getConstructorsFor(args.player2)[0].newInstance();
		p1.setBoardToken(BoardToken.X);
		p2.setBoardToken(BoardToken.O);

		Board b = new Board();
		Game g = new Game();
		g.connect(b, p1, p2);
		g.init();

		Scanner scan = new Scanner(System.in);
		g.play(scan);
		g.announceWinner();

		scan.close();
	}

}
