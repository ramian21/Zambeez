package zmbz;

import java.awt.*;
import java.util.ArrayList;

public class Player
{
	private int myXCord;
	private int myYCord;
	private int mySpeed = 3;
	private boolean alive = true;
	private static boolean paused = false;
	private int boom = 10;
	private int myRadius = 10;
	private int healthCnt = 100;
	private int activePU = 101;
	private int activeGunPU = 101;
	private int activeHealthPU = 101;
	private int savedXCord;
	private int savedYCord;
	private int tpVar = 1;
	private Color playercolor = Color.white;
	public ArrayList<String> puSArray = new ArrayList<String>();
	public ArrayList<String> gunNames = new ArrayList<String>();
	public ArrayList<Integer> guns = new ArrayList<Integer>();
	private int weaponequipped;
	private String weapontitle;
	private double mX;
	private double mY;
	private boolean active = false;
	private static int playerInc = 1;
	private int playerID;
	private int state = 0;

	public void changeState(int fart)
	{
		state = fart;
	}

	public int getState()
	{
		return state;
	}

	public boolean isActive()
	{
		return active;
	}

	public void changeActive(boolean bah)
	{
		active = bah;
	}

	public int getAmmo()
	{
		return guns.get(weaponequipped);
	}

	public void setAmmo(int x)
	{
		if (x - getAmmo() > 10)
			guns.set(weaponequipped, x);
		else
			guns.set(weaponequipped, Math.min(x, getAmmo()));
	}

	public int getWE()
	{
		return weaponequipped;
	}

	public void setWE(int x)
	{
		weaponequipped = x;
	}

	public int getHealth()
	{
		return healthCnt;
	}

	public void setHealth(int x)
	{
		healthCnt = x;
	}

	public boolean amAlive()
	{
		return alive;
	}

	public void setMX(double mouseX)
	{
		mX = mouseX;
	}

	public void setMY(double y)
	{
		mY = y;
	}

	public double getMX()
	{
		return mX;
	}

	public double getMY()
	{
		return mY;
	}

	public int getXCord()
	{
		return myXCord;
	}

	public int getYCord()
	{
		return myYCord;
	}

	public int getBoom()
	{
		return boom;
	}

	public void setPU(int x)
	{
		activePU = x;
	}

	public int getPU()
	{
		if (activePU < 31)
			return 1;
		else if (activePU < 56)
			return 2;
		else if (activePU < 71)
			return 3;
		else if (activePU < 86)
			return 4;
		else if (activePU < 96)
			return 5;
		else if (activePU < 101)
			return 6;
		else
			return 0;
	}

	public void usePU(Graphics g)
	{
		if (getPU() == 2)
			teleport(g);
		if (getPU() == 3)
			for (int k = 0; k < 15; k++)
				areaBomb(g);
		if (getPU() == 6)
			for (int k = 0; k < 100; k++)
				nuke(g);
	}

	public int getActivePU()
	{
		return activePU;
	}

	public String getPUString()
	{
		return puSArray.get(getPU());
	}

	public String getWeapontitle()
	{
		return weapontitle;
	}

	public void life()
	{
		alive = false;
	}

	public void setCol(Color col)
	{
		playercolor = col;
	}

	public void reset()
	{
		myXCord = Zambeez.retMaxX() / 2;
		myYCord = Zambeez.retMaxY() / 2;
		alive = true;
		boom = 10;
		healthCnt = 100;
		activePU = 101;
		tpVar = 1;
		for (int k = 0; k < 5; k++)
			guns.set(k, 0);
	}

	/*
	 * PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP
	 * 1-6----(1)---Speed boost--------------------------DONE!
	 * 31-55---(2)---Teleport-----------------------------DONE!
	 * 56-70---(3)---Area bomb----------------------------DONE!
	 * 71-85---(4)---Freeze zombies-----------------------DONE!
	 * 86-95---(5)---Inverted Controls (bad thing)--------DONE!
	 * 96-100--(6)---Full Screen Area Bomb----------------DONE!
	 * PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP
	 */
	/*
	 * HPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHP
	 * 1-50----(1)---25HP---------------------------------DONE!
	 * 51-60---(2)---FULLHP-------------------------------DONE!
	 * 61-80---(3)---50HP---------------------------------DONE!
	 * 81-100--(4)---10HP---------------------------------DONE!
	 * HPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHP
	 */
	/*
	 * GPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGP
	 * 101----(101)--Desert Eagle-------------------------DONE!
	 * 1-6----(1)---Shot Gun-----------------------------DONE!
	 * 31-60---(2)---AN-94--------------------------------DONE!
	 * 61-90---(3)---MP7----------------------------------DONE!
	 * 91-100--(4)---Grenade Launcher---------------------DONE!
	 * GPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGP
	 */

	public Player(int x, int y)
	{
		myXCord = x;
		myYCord = y;
		puSArray.add(0, "NONE");
		puSArray.add(1, "SpeedBoost");
		puSArray.add(2, "Teleport");
		puSArray.add(3, "AreaBomb");
		puSArray.add(4, "Freeze");
		puSArray.add(5, "InvertedControls");
		puSArray.add(6, "FSAB");
		weaponequipped = 0;
		weapontitle = "DesertEagle";
		for (int k = 0; k < 5; k++)
			guns.add(k, 0);
		gunNames.add(0, "DesertEagle");
		gunNames.add(1, "Striker");
		gunNames.add(2, "AN-94");
		gunNames.add(3, "MP7");
		gunNames.add(4, "XM25");
		playerID = playerInc;
		playerInc++;
	}

	public void shoot(Graphics g, int x, int y)
	{
		if (alive)
		{
			if (!paused && guns.get(weaponequipped) != 0)
			{
				g.setColor(Color.RED);
				g.drawOval((int) (x - getBoom() / 2), (int) (y - getBoom() / 2), getBoom(), getBoom());
				g.drawLine(myXCord, myYCord, x, y);
				Zambeez.projhit(x, y);
				Zambeez.transmit("S ");
				guns.set(weaponequipped, guns.get(weaponequipped) - 1);
			} else if (!paused && (weaponequipped == 0))
			{
				g.setColor(Color.RED);
				g.drawOval((int) (x - getBoom() / 2), (int) (y - getBoom() / 2), getBoom(), getBoom());
				g.drawLine(myXCord, myYCord, x, y);
				Zambeez.projhit(x, y);
			}
		}
	}

	public int getWeaponequipped()
	{
		return weaponequipped;
	}

	public void switchGun(int x)
	{
		if (x < 0 && weaponequipped != 0)
			weaponequipped = weaponequipped - 1;
		if (x > 0 && weaponequipped != guns.size() - 1)
			weaponequipped = weaponequipped + 1;
		if (weaponequipped < 0)
			weaponequipped = 0;
		if (weaponequipped > 4)
			weaponequipped = 4;
	}

	public boolean areYouHit(int a, int b, int rad, boolean state, boolean gunState, boolean healthState, boolean living, int random)
	{
		double d = Math.sqrt(((myXCord - a) * (myXCord - a) + (myYCord - b) * (myYCord - b)));
		int r = rad + myRadius;
		if (living)
		{
			if (d < r)
			{
				healthCnt--;
				if (healthCnt <= 0)
				{
					alive = false;
				}
				return true;
			}
		} else if (d < r && state)
		{
			activePU = random - 900;
			return true;
		} else if (d < r && gunState)
		{
			activeGunPU = random - 700;
			addAmmo(activeGunPU);
			return true;
		} else if (d < r && healthState)
		{
			activeHealthPU = random - 800;

			if (activeHealthPU > 80)
			{
				healthCnt = 200;

			} else if (activeHealthPU > 60)
			{
				healthCnt += 50;

			} else
			{
				healthCnt += 10;

			}
			return true;
		}
		return false;
	}

	private void addAmmo(int x)
	{
		if (x < 31)
			guns.set(1, 6 + guns.get(1));
		else if (x < 61)
			guns.set(2, 120 + guns.get(2));
		else if (x < 91)
			guns.set(3, 200 + guns.get(3));
		else if (x < 100)
			guns.set(4, 24 + guns.get(4));
	}

	public void drawObject(Graphics g, int x, int y, int mx, int my)
	{
		weapontitle = gunNames.get(weaponequipped);
		if (healthCnt <= 0)
			alive = false;
		if (active)
		{
			if (alive)
			{
				drawPlayer(g, playercolor);
				if (healthCnt > 200)
					healthCnt = 200;
				drawHealth(g, mx, my);
			} else
			{
				drawPlayer(g, Color.red.darker());
				mySpeed = 0;
			}
		}
	}

	public void teleport(Graphics g)
	{
		if (tpVar % 2 == 1)
		{
			savedXCord = myXCord;
			savedYCord = myYCord;
		} else if (tpVar % 2 == 0)
		{
			myXCord = savedXCord;
			myYCord = savedYCord;
			drawPlayer(g, Color.white);
			activePU = 101;
		}
		tpVar++;
	}

	public void areaBomb(Graphics g)
	{
		if (alive)
		{
			if (!paused)
			{
				g.setColor(Color.RED);
				g.fillOval(myXCord - boom, myYCord - boom, boom * 2, boom * 2);
				boom = 200;
				Zambeez.projhit(myXCord, myYCord);
				activePU = 101;
				boom = 10;
			}
		}
	}

	public void setCoord(int x, int y)
	{
		myXCord = x;
		myYCord = y;
	}

	public void nuke(Graphics g)
	{
		if (alive)
		{
			if (!paused)
			{
				g.setColor(Color.RED);
				g.fillOval(myXCord - boom, myYCord - boom, boom * 2, boom * 2);
				boom = 2000;
				Zambeez.projhit(myXCord, myYCord);
				activePU = 101;
				boom = 10;
			}
		}
	}

	public void drawPlayer(Graphics g, Color cc)
	{
		if (Zambeez.getEC() != 0)
			guns.set(3, 999);
		move();
		if (weaponequipped == 1)
		{
			boom = 50;
		} else if (getPU() != 4 && getPU() != 8)
			boom = 10;
		g.setColor(cc);
		if (myXCord < 0)
			myXCord = 0;

		if (myXCord > Zambeez.retMaxX())
			myXCord = Zambeez.retMaxX();

		if (myYCord < 0)
			myYCord = 0;

		if (myYCord > Zambeez.retMaxY())
			myYCord = Zambeez.retMaxY();
		g.fillOval(myXCord - myRadius, myYCord - myRadius, 2 * myRadius, 2 * myRadius);
		g.drawLine(myXCord, myYCord, (int) (myXCord + 20 * (Math.cos(getAngle()))), (int) (myYCord - 20 * (Math.sin(getAngle()))));
	}

	public void drawHealth(Graphics g, int x, int y)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("Reprise Stamp", Font.BOLD, 20));
		g.drawString("Player Health", x / 25, y - (y / 20));
		if (healthCnt <= 100)
			g.fillRect(x / 25, y - (y / 25), healthCnt * 5, 15);
		else
		{
			g.setColor(Color.CYAN);
			g.fillRect((x / 25), (y - (y / 25)) - 5, (healthCnt - 100) * 5, 25);
			g.setColor(Color.red);
			g.fillRect(x / 25, y - (y / 25), (100) * 5, 15);
		}
	}

	public double getAngle()
	{
		if (myXCord < mX)
			return ((-1) * Math.atan((myYCord - mY) / (myXCord - mX)));
		return ((-1) * Math.atan((myYCord - mY) / (myXCord - mX)) + 3.1415926535897932384626433);
	}

	public void pause()
	{
		mySpeed = 0;
		paused = true;
	}

	public void unpause()
	{
		mySpeed = 3;
		paused = false;
	}

	public void move()
	{
		if (state == 0)
		{
			return;
		}
		if (state == 1)
			moveUp();
		if (state == 2)
			moveUR();
		if (state == 3)
			moveRight();
		if (state == 4)
			moveDR();
		if (state == 5)
			moveDown();
		if (state == 6)
			moveDL();
		if (state == 7)
			moveLeft();
		if (state == 8)
			moveUL();
	}

	public void moveUp()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
				myYCord += mySpeed;
			else
				myYCord -= mySpeed;
		}
	}

	public void moveDown()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
				myYCord -= mySpeed;
			else
				myYCord += mySpeed;
		}
	}

	public void moveLeft()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
				myXCord += mySpeed;
			else
				myXCord -= mySpeed;
		}
	}

	public void moveRight()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
				myXCord -= mySpeed;
			else
				myXCord += mySpeed;
		}
	}

	public void moveUR()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
			{
				myXCord -= mySpeed;
				myYCord += mySpeed;
			} else
			{
				myXCord += mySpeed;
				myYCord -= mySpeed;
			}
		}
	}

	public void moveDR()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
			{
				myXCord -= mySpeed;
				myYCord -= mySpeed;
			} else
			{
				myXCord += mySpeed;
				myYCord += mySpeed;
			}
		}
	}

	public void moveUL()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;

			if (getPU() == 5)
			{
				myXCord += mySpeed;
				myYCord += mySpeed;
			} else
			{
				myXCord -= mySpeed;
				myYCord -= mySpeed;
			}
		}
	}

	public void moveDL()
	{
		if (!paused && alive)
		{
			if (getPU() == 1)
				mySpeed = 6;
			else
				mySpeed = 3;
			if (getPU() == 5)
			{
				myXCord += mySpeed;
				myYCord -= mySpeed;
			} else
			{
				myXCord -= mySpeed;
				myYCord += mySpeed;
			}
		}
	}

}
