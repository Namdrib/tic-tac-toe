import com.beust.jcommander.Parameter;

public class TicTacToeArguments
{
	@Parameter(names = { "-h", "--help" }, help = true)
	public boolean	help;

	@Parameter(names = { "-f",
			"--first" }, description = "Which player goes first (1 or 2)")
	public int		first	= 1;

	@Parameter(names = { "-p1",
			"--player1" }, description = "player1 type (class name)")
	public String	player1	= "Human";

	@Parameter(names = { "-p2",
			"--player2" }, description = "player2 type (class name)")
	public String	player2	= "MinimaxComputer";
}
