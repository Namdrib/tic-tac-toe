import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class TicTacToeGui
{
	TicTacToeArguments	ttta;

	private Game		g;

	// UI elements
	int					width;
	int					height;
	JPanel				gui;
	JPanel				boardPanel;
	JButton[][]			positions	= new JButton[3][3];
	JToolBar			toolbar;
	JMenuBar			menuBar;
	JMenu				menu;
	JMenuItem			menuItem;

	TicTacToeGui()
	{
		init();
	}

	TicTacToeGui(Game g)
	{
		this.g = g;
	}

	public void init()
	{
		gui = new JPanel(new BorderLayout(3, 3));
		setupToolbar();
		gui.add(toolbar, BorderLayout.NORTH);

		boardPanel = new JPanel(new GridLayout(0, 3));
		boardPanel.setSize(new Dimension(500, 500));

		for (int i = 0; i < positions.length; i++)
		{
			for (int j = 0; j < positions[i].length; j++)
			{
				JButton button = new JButton(" ");
				button.setName(i + " " + j);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						System.out.println("Button at " + button.getName());
						if (button.getText() != " ")
						{
							System.out.println("Already done a thing");
							return;
						}
						String name = button.getName();
						String[] parts = name.split(" ");
						int n = Integer.parseInt(parts[0]);
						int m = Integer.parseInt(parts[1]);
						button.setText("Minh");
						TicTacToeGui.this.g.getBoard();
						Move move = new Move(n, m, TicTacToeGui.this.g
								.getCurrentPlayer().getBoardToken());
						System.out.println(move);
					}
				});
				positions[i][j] = button;
				boardPanel.add(positions[i][j]);
			}
		}

		gui.add(boardPanel, BorderLayout.CENTER);

		newGame();
	}

	private void setupToolbar()
	{
		toolbar = new JToolBar();
		toolbar.setFloatable(false);

		toolbar.add(new AbstractAction("New Game") {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newGame();
			}
		});

	}

	private void handleException(Exception e)
	{
		JOptionPane.showMessageDialog(null,
				"Exception found: " + e.getMessage() + "\n",
				"Exception dialogue", JOptionPane.WARNING_MESSAGE);
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

	private void newGame()
	{
		this.g = new Game();
		this.boardPanel = new JPanel();

		JTextField p1Field = new JTextField("Human");
		JTextField p2Field = new JTextField("MinimaxComputer");
		JTextField firstField = new JTextField("1");
		Object[] prompts = {
			"Player 1 type:", p1Field,
			"Player 2 type:", p2Field,
			"First player:", firstField,
		};
		
		int result = JOptionPane.showConfirmDialog(null, prompts, "New game options", JOptionPane.OK_CANCEL_OPTION);

		String p1String, p2String;
		Player p1, p2;
		int first = 1;

		try
		{
			p1String = p1Field.getText();
			p2String = p2Field.getText();
			first = Integer.parseInt(firstField.getText());
			if (first < 1 || first > 2)
			{
				throw new Exception("First must be 1 or 2");
			}

			// Dynamically load the specified player types
			// (from -p1 and -p2 arguments)
			p1 = (Player) getConstructorsFor(p1String)[0].newInstance();
			p2 = (Player) getConstructorsFor(p2String)[0].newInstance();
			p1.setBoardToken(BoardToken.X);
			p2.setBoardToken(BoardToken.O);
		}
		catch (NumberFormatException ex)
		{
			handleException(ex);
			return;
		}
		catch (Exception ex)
		{
			handleException(ex);
			return;
		}

		Board b = new Board();

		this.g.connect(b, p1, p2);
		this.g.init();
	}

	public JPanel getGui()
	{
		return gui;
	}

	public static void main(String[] args)
	{
		Runnable r = new Runnable() {
			@Override
			public void run()
			{
				TicTacToeGui gui = new TicTacToeGui();
				JFrame frame = new JFrame("TicTacToe Gui");
				frame.add(gui.getGui());
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setLocationRelativeTo(null);
				frame.pack();
				frame.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}
}
