package zmbz;
import java.io.IOException;


public class NormZombie extends Zombie
{

	public NormZombie(int x, int y, Player player) throws IOException
	{
		super(x, y, player);
		myRadius = 10;
		health = 3;
	}
}
