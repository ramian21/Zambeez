package zmbz;
public class Score implements Comparable<Object>
{
	private String name;
	private int zKilled;
	private int tElapsed;

	public Score(int dz, int tE, String pN)
	{
		zKilled = dz;
		tElapsed = tE;
		name = pN;
	}
	public String getName()
	{
		return name;
	}

	public int getZK()
	{
		return zKilled;
	}

	public int getTE()
	{
		return tElapsed;
	}

	public String toString()
	{
		return (name + " " + zKilled + " " + tElapsed);
	}

	public int compareTo(Object s)
	{
		if (zKilled <((Score) s).getZK())
			return -1;
		else if (zKilled > ((Score) s).getZK())
			return 1;
		else
		{
			if (tElapsed > ((Score) s).getTE())
				return -1;
			else if (tElapsed < ((Score) s).getTE())
				return 1;
			else
			{
				if (name.compareTo(((Score) s).getName()) == -1)
					return 1;
				else if (name.compareTo(((Score) s).getName()) == 1)
					return -1;
				else
					return 0;
			}
		}
	}
}
