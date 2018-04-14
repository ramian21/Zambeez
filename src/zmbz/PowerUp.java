package zmbz;

import java.awt.Graphics;

public class PowerUp {/*
	 * PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP
	 * 1-30----(1)---Speed boost--------------------------DONE!
	 * 31-55---(2)---Teleport-----------------------------DONE!
	 * 56-70---(3)---Area bomb----------------------------DONE!
	 * 71-85---(4)---Freeze zombies-----------------------DONE!
	 * 86-95---(5)---Inverted Controls (bad thing)--------DONE!
	 * 96-100--(6)---Full Screen Area Bomb----------------DONE!
	 * PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP
	 */
	/*
	 * HPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHP
	 * 1-50----(7)---25HP---------------------------------DONE!
	 * 51-60---(8)---FULLHP-------------------------------DONE!
	 * 61-80---(9)---50HP---------------------------------DONE!
	 * 81-100--(10)--10HP---------------------------------DONE!
	 * HPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHPHP
	 */
	/*
	 * GPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGP
	 * 1-30----(11)--Shot Gun-----------------------------DONE!
	 * 31-60---(12)--AN-94--------------------------------DONE!
	 * 61-90---(13)--MP7----------------------------------DONE!
	 * 91-100--(14)--Grenade Launcher---------------------DONE!
	 * GPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGPGP
	 */
	private int myX;
	private int myY;
	private int myVal;
	private int myType;
	private int real;
	public PowerUp(int x, int y)
	{
		myX=x;
		myY=y;
		myType=(int)(Math.random()*3+1);
		myVal=(int)(Math.random()*100);
	}
	public int getX()
	{
		return myX;
	}
	public int getY()
	{
		return myY;
	}
	public int getReal()
	{
		if (myType == 1) 
		{
			if (myVal < 30)
				real = 1;
			if (myVal < 55)
				real = 2;
			if (myVal < 70)
				real = 3;
			if (myVal < 85)
				real = 4;
			if (myVal < 95)
				real = 5;
			if (myVal < 100)
				real = 6;
		}
		if (myType == 2) 
		{
			if (myVal < 50)
				real = 7;
			if (myVal < 60)
				real = 8;
			if (myVal < 80)
				real = 9;
			if (myVal < 100)
				real = 10;

		}
		else
		{
			if (myVal < 30)
				real = 11;
			if (myVal < 60)
				real = 12;
			if (myVal < 90)
				real = 13;
			if (myVal < 100)
				real = 14;
		}
		return real;
	}
	public void draw(Graphics g)
	{
		
	}
	


}
