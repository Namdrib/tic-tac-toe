
public class Move
{
	int n, m;
	BoardToken token;
	
	Move(int n, int m, BoardToken token)
	{
		this.n= n;
		this.m = m;
		this.token = token;
	}
	
	int getN()
	{
		return n;
	}
	
	int getM()
	{
		return m;
	}
	
	void setToken(BoardToken token)
	{
		this.token = token;
	}
	
	BoardToken getToken()
	{
		return token;
	}
	
	@Override
	public String toString()
	{
		return "(" + n + ", " + m + ") : " + token;
	}
}
