
public enum BoardToken
{
	X,
	O,
	EMPTY {
		public String toString() {
			return " ";
		}
	}
}
