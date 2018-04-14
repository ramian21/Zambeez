package zmbz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class BigZombie extends Zombie
{
	public void reset(int x, int y)
	{
		myXCord = x;
		myYCord = y;
		alive = true;
		hits = 0;
		delayCount = 0;
		powerUp = 0;
		count = 0;
		mySpeed = 1;
		health = 15;
	}

	public BigZombie(int x, int y, Player player) throws IOException
	{
		super(x, y, player);
		health = 15;
		myRadius = 20;
	}

	public void drawObject(Graphics g)
	{
		if (!alive)
		{
			if (drop)
			{
				g.setColor(Color.blue);
				g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
			} else if (dropGun)
			{
				g.setColor(Color.red);
				g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
				g.drawImage(gun, myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius, (ImageObserver) null);
			} else if (dropHealth)
			{
				g.setColor(Color.white);
				g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
				g.setColor(Color.red);
				g.fillRect(myXCord - (2 * myRadius / 3), myYCord - 2, (4 * myRadius / 3), 4);
				g.fillRect(myXCord - 2, myYCord - (2 * myRadius / 3), 4, (4 * myRadius / 3));
			} else
			{
				g.setColor(Color.red);
				g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
			}
		} else
		{
			g.setColor(new Color(0, 45, 0));
			g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
		}
		g.fillRect(myXCord - myRadius, myYCord - myRadius - (int) (myRadius * 0.25), (health - hits) * (myRadius * 2) / health, 2);
	}

	public boolean amHit(int a, int b)
	{
		double d = Math.sqrt(((myXCord - a) * (myXCord - a) + (myYCord - b) * (myYCord - b)));
		int r = myPlayer.getBoom() + myRadius;
		if (alive)
		{
			if (d < r - (myPlayer.getBoom() / 2))
			{
				hits++;
				if (hits >= health)
				{
					alive = false;
					dz++;
					dzt++;
					random = (int) (Math.random() * 1000 + 1);
					if (random > 900)
					{
						drop = true;
					} else if (random > 700)
					{
						dropGun = true;
					} else if (random > 600)
					{
						dropHealth = true;
					}
					return true;
				}
			}
		}
		return false;
	}
}
