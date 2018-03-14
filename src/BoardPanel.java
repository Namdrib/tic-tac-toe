import java.awt.Graphics;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class BoardPanel extends JPanel
{
	private Board b;
	
	public BoardPanel()
	{
		;
	}
	
	void connectBoard(Board b)
	{
		this.b = b;
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		;
	}
}
