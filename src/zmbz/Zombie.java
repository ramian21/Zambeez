package zmbz;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Zombie
{
	protected int myXCord;
	protected int myYCord;
	protected Player myPlayer;
	protected boolean alive;
	protected boolean drop;
	protected boolean dropHealth;
	protected boolean dropGun;
	protected int myRadius = 10;
	protected int hits = 0;
	protected int delayCount = 0;
	protected static int delay = 3;
	protected static int dz = 0;
	protected static int dzt = 0;
	protected int random = 0;
	protected int powerUp = 0;
	protected int health;
	protected int count = 0;
	protected int myDelay = (int) (Math.random() * 120 + 40);
	protected static boolean frozen;
	protected static double mySpeed = 1;
	protected static Image gun;
	protected String imageFilename;
	protected static final String imageExtension = ".png";

	public int getHealth()
	{
		return hits;
	}

	public void setHealth(int x)
	{
		hits = Math.max(x, hits);
	}

	public int getMyXCord()
	{
		return myXCord;
	}

	public void setMyXCord(int myXCord)
	{
		this.myXCord = myXCord;
	}

	public int getMyYCord()
	{
		return myYCord;
	}

	public void setMyYCord(int myYCord)
	{
		this.myYCord = myYCord;
	}

	public int getMyDelay()
	{
		return myDelay;
	}

	public void setMyDelay(int myDelay)
	{
		this.myDelay = myDelay;
	}

	public int getMyStatus()
	{
		if (alive)
			return 1;
		return 0;
	}

	public void setMyStatus(int x)
	{
		if (x == 1)
			alive = true;
		else
			alive = false;
	}

	public static int getDZT()
	{
		return dzt;
	}

	public static int getDZ()
	{
		return dz;
	}

	public static void resetDZ()
	{
		dz = 0;
	}

	public static void resetDZT()
	{
		dzt = 0;
	}

	public static void pause()
	{
		frozen = true;
	}

	public static void unpause(int a)
	{
		frozen = false;
	}

	public static void incSpeed()
	{
		mySpeed += .5;
	}

	public void incHealth()
	{
		health++;
	}

	public String sendData()
	{
		return myXCord + " " + myYCord + " " + getHealth() + " " + random + " ";
	}

	public void setData(int x, int y, int s, int r)
	{
		setMyXCord(x);
		setMyYCord(y);
		setHealth(s);
		setRandom(r);
	}

	public void override(int s)
	{
		hits = s;
	}

	public void override(int x, int y, int s, int r)
	{
		setMyXCord(x);
		setMyYCord(y);
		hits = s;
		setRandom(r);
	}

	private void setRandom(int r)
	{
		random = r;
	}

	public void getClosest(ArrayList<Player> players)
	{
		double[] distances = new double[players.size()];
		int k = 0;
		for (Player p : players)
		{
			if (p.isActive())
			{
				if (p.amAlive())
					distances[k] = Math.sqrt(((myXCord - p.getXCord()) * (myXCord - p.getXCord()) + (myYCord - p.getYCord())
							* (myYCord - p.getYCord())));
				else
					distances[k] = 10000000;
			} else
				distances[k] = 10000000;
			k++;
		}
		double min = distances[0];
		int ind = 0;
		for (int n = 0; n < distances.length; n++)
			if (min > distances[n])
			{
				min = distances[n];
				ind = n;
			}
		myPlayer = players.get(ind);
	}

	public void reset(int x, int y)
	{
		myXCord = x;
		myYCord = y;
		alive = true;
		hits = 0;
		delayCount = 0;
		random = 0;
		count = 0;
		mySpeed = 1;
		health = 3;
		random = (int) (Math.random() * 1000 + 1);
		if (random > 900)
		{
			drop = true;
		} else if (random > 800)
		{
			dropHealth = true;
		}
		myDelay = (int) (Math.random() * 120 + 40);
	}

	public void newWave(int x, int y)
	{
		myXCord = x;
		myYCord = y;
		alive = true;
		hits = 0;
		delayCount = 0;
		powerUp = 0;
		count = 0;
		random = (int) (Math.random() * 1000 + 1);
		if (random > 900)
		{
			drop = true;
		} else if (random > 800)
		{
			dropHealth = true;
		} else if (random > 700)
		{
			dropGun = true;
		} else
		{
			drop = false;
			dropHealth = false;
			dropGun = false;
		}
		myDelay = (int) (Math.random() * 120 + 40);
	}

	public Zombie(int x, int y, Player player) throws IOException
	{
		myXCord = x;
		myYCord = y;
		myPlayer = player;
		alive = true;
		drop = false;
		random = (int) (Math.random() * 1000 + 1);
		if (random > 900)
		{
			drop = true;
		} else if (random > 800)
		{
			dropHealth = true;
		} else if (random > 700)
		{
			dropGun = true;
		}
		// Class<Zombie> cl = Zombie.class;
		// imageFilename = "gun";// cl.getName().replace('.', '/');
		// URL url = cl.getClassLoader().getResource("gun.png");
		// System.out.println(url);
		// if (url == null)
		// throw new FileNotFoundException(imageFilename + imageExtension +
		// " not found.");
		// gun = ImageIO.read(url);
	}

	public boolean amHit(int a, int b)
	{
		if (amTargeted(a, b))
		{
			hits++;
			if (hits >= health)
				return true;
		}
		return false;
	}

	public boolean amTargeted(int a, int b)
	{
		double d = Math.sqrt(((myXCord - a) * (myXCord - a) + (myYCord - b) * (myYCord - b)));
		int max = 0;
		for (Player p : Zambeez.getPlayers())
			if (p.getBoom() > max)
				max = p.getBoom();
		int boom = max;
		int r = boom + myRadius;
		if (alive)
		{
			if (d < r - (boom / 2))
			{
				return true;
			}
		}
		return false;
	}

	public String direction(int xc, int yc)
	{
		getClosest(Zambeez.getPlayers());
		double diffX = xc - myXCord;
		double diffY = -(yc - myYCord);
		double slope = diffY / diffX;
		if (myYCord < yc && myXCord <= xc)
		{
			if (slope <= -3.5)
				return ("S");

			if (slope <= -2)
				return ("SSE");

			if (slope <= -(2.0 / 3.0))
				return ("SE");

			if (slope <= -(1.0 / 6.0))
				return ("SEE");
		}
		if (myYCord >= yc && myXCord < xc)
		{
			if (slope >= 3.5)
				return ("N");

			if (slope >= 2)
				return ("NNE");

			if (slope >= (2.0 / 3.0))
				return ("NE");

			if (slope >= (1.0 / 6.0))
				return ("NEE");
		}
		if (myYCord > yc && myXCord >= xc)
		{
			if (slope <= -3.5)
				return ("N");

			if (slope <= -2)
				return ("NNW");

			if (slope <= -(2.0 / 3.0))
				return ("NW");

			if (slope <= -(1.0 / 6.0))
				return ("NWW");
		}
		if (myYCord <= yc && myXCord > xc)
		{
			if (slope >= 3.5)
				return ("S");

			if (slope >= 2)
				return ("SSW");

			if (slope >= (2.0 / 3.0))
				return ("SW");

			if (slope >= (1.0 / 6.0))
				return ("SWW");
		}
		if (myXCord <= xc)
		{
			if (slope > -(1.0 / 6.0) || slope < (1.0 / 6.0))
				return ("E");
		}
		if (myXCord >= xc)
		{
			if (slope > -(1.0 / 6.0) || slope < (1.0 / 6.0))
				return ("W");
		}
		return "SUCKS";
	}

	public void setCoord()
	{
		if (count > myDelay)
		{
			if (alive && !frozen)
			{
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "N")
				{
					myYCord -= (int) (mySpeed * 4);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "NNE")
				{
					myXCord += (int) (mySpeed * 1);
					myYCord -= (int) (mySpeed * 3);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "NE")
				{
					myXCord += (int) (mySpeed * 2);
					myYCord -= (int) (mySpeed * 2);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "NEE")
				{
					myXCord += (int) (mySpeed * 3);
					myYCord -= (int) (mySpeed * 1);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "S")
				{
					myYCord += (int) (mySpeed * 4);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "SSE")
				{
					myXCord += (int) (mySpeed * 1);
					myYCord += (int) (mySpeed * 3);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "SE")
				{
					myXCord += (int) (mySpeed * 2);
					myYCord += (int) (mySpeed * 2);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "SEE")
				{
					myXCord += (int) (mySpeed * 3);
					myYCord += (int) (mySpeed * 1);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "E")
				{
					myXCord += (int) (mySpeed * 4);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "W")
				{
					myXCord -= (int) (mySpeed * 4);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "NWW")
				{
					myXCord -= (int) (mySpeed * 3);
					myYCord -= (int) (mySpeed * 1);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "NW")
				{
					myXCord -= (int) (mySpeed * 2);
					myYCord -= (int) (mySpeed * 2);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "NNW")
				{
					myXCord -= (int) (mySpeed * 1);
					myYCord -= (int) (mySpeed * 3);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "SSW")
				{
					myXCord -= (int) (mySpeed * 1);
					myYCord += (int) (mySpeed * 3);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "SW")
				{
					myXCord -= (int) (mySpeed * 2);
					myYCord += (int) (mySpeed * 2);
				}
				if (direction(myPlayer.getXCord(), myPlayer.getYCord()) == "SWW")
				{
					myXCord -= (int) (mySpeed * 3);
					myYCord += (int) (mySpeed * 1);
				}
			}
		} else
			count++;
	}

	public void move()
	{
		ArrayList<Player> temp = Zambeez.getPlayers();
		getClosest(temp);
		for (Player p : temp)
		{
			if (p.getPU() == 4)
			{
				frozen = true;
			}
		}
		if (alive)
		{
			if (delayCount % delay == 0)
			{
				setCoord();
				delayCount++;
			} else
				delayCount++;
			myPlayer.areYouHit(myXCord, myYCord, myRadius, false, false, false, true, random);
		} else
		{
			if (myPlayer.areYouHit(myXCord, myYCord, myRadius, drop, dropGun, dropHealth, false, random))
			{
				drop = false;
				dropGun = false;
				dropHealth = false;
				random = 0;
			}
		}
	}

	public void drawObject(Graphics g)
	{
		if (hits < health)
		{
			alive = true;
		}
		if (alive)
		{
			if (hits >= health)
			{
				alive = false;
				dz++;
				dzt++;
			}
			g.setColor(new Color(0, 45, 0));
			g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
		} else
		{
			if (random > 900)
			{
				drop = true;
				dropHealth = false;
				dropGun = false;
			} else if (random > 800)
			{
				drop = false;
				dropHealth = true;
				dropGun = false;
			} else if (random > 700)
			{
				drop = false;
				dropHealth = false;
				dropGun = true;
			} else
			{
				drop = false;
				dropHealth = false;
				dropGun = false;
			}
			if (drop)
			{
				g.setColor(Color.blue);
				g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
			} else if (dropGun)
			{
				g.setColor(new Color(50, 50, 50));
				g.fillRect(myXCord - (2 * myRadius / 3), myYCord - 2, (4 * myRadius / 3), 4);
				g.fillRect(myXCord - (2 * myRadius / 3), myYCord - 2, 4, 8);
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
		}
		g.fillRect(myXCord - myRadius, myYCord - myRadius - (int) (myRadius * 0.25), (health - hits) * (myRadius * 2) / health, 2);
	}

}
