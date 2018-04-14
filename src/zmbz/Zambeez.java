//TODO: take a look and clean up everything

package zmbz;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Zambeez extends JPanel implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, ActionListener, NetworkMessage
{
	private static final long serialVersionUID = 1L;
	private static int maxXCord = 1200;
	private static int maxYCord = 700;
	private static BufferedImage myImage;
	private static Graphics myBuffer;
	private static Player player;
	private static Player player2;
	private static Timer t;
	private static int totalZombies = 100;
	private static int activeZombies = 20;
	private static ArrayList<Zombie> zombiesN = new ArrayList<Zombie>(totalZombies / 4);
	private static ArrayList<Zombie> zombiesS = new ArrayList<Zombie>(totalZombies / 4);
	private static ArrayList<Zombie> zombiesE = new ArrayList<Zombie>(totalZombies / 4);
	private static ArrayList<Zombie> zombiesW = new ArrayList<Zombie>(totalZombies / 4);
	private static ArrayList<Player> players = new ArrayList<Player>();
	private static int gameLevel = 1;
	private static int mouseX;
	private static int mouseY;
	private static boolean paused = true;
	private static boolean options = false;
	private static boolean cprofile = false;
	private static boolean help = false;
	private static boolean multi = false;
	private static boolean mp = false;
	private static String playername = "AAA";
	private static boolean rise = false;
	private static double dnVal = 190 * 20;
	private static double dnDisp = 190;
	private static boolean win = false;
	private static int eggcount = 0;
	private static int savedTime;
	private static long startTime;
	private static long currentTime;
	private static Color playercol;
	private ImageIcon i;
	private ImageIcon background;
	private ImageIcon i2;
	public static Image gun;
	byte[] imageByte = new byte[0];
	private Cursor myCursor;
	private Point myPoint = new Point(0, 0);
	private Image cursorImage = Toolkit.getDefaultToolkit().createImage(imageByte);
	private int mod = 0;
	private boolean shooting;
	private static NetworkServerThread myServer;
	private int[] flashX = new int[3];
	private int[] flashY = new int[3];
	private int[] flashX2 = new int[3];
	private int[] flashY2 = new int[3];
	private int scroll;
	private int mx;
	private int my;
	private static long tempTime;
	private static boolean isHost = true;
	private static int remZombies;
	private String p2pu = "";
	private String p2we = "";
	private int p2ammo = 0;
	private int packCnt = 0;
	private static int xc;
	private static int yc;
	private static Player me;
	private static Player them;

	public static int getEC()
	{
		return eggcount;
	}

	public static boolean isHost()
	{
		return isHost;
	}

	public static int getXc()
	{
		return xc;
	}

	public void setXc(int xc)
	{
		this.xc = xc;
	}

	public static int getYc()
	{
		return yc;
	}

	public void setYc(int yc)
	{
		this.yc = yc;
	}

	public static void setHost(boolean isHost)
	{
		Zambeez.isHost = isHost;
	}

	public static void makePU(int x, int y)
	{
		PowerUp pU = new PowerUp(x, y);
	}

	class Circle
	{
		int xCord;
		int yCord;
		int radius;

		public Circle(int x, int y, int r)
		{
			xCord = x;
			yCord = y;
			radius = r;
		}

		public void draw(Graphics g, int r, int gr, int b)
		{
			Color cc = new Color(r, gr, b);
			int ulx = xCord - radius;
			int uly = yCord - radius;
			// g.setColor(cc);
			g.setColor(cc);
			g.fillOval(ulx, uly, radius * 2, radius * 2);
		}
	}

	public static ArrayList<Player> getPlayers()
	{
		return players;
	}

	public static void setPlayers(ArrayList<Player> players)
	{
		Zambeez.players = players;
	}

	public static double retX()
	{
		return (double) mouseX;
	}

	public static double retY()
	{
		return (double) mouseY;
	}

	public static int retMaxX()
	{
		return maxXCord;
	}

	public static int retMaxY()
	{
		return maxYCord;
	}

	public static void reset()
	{
		if (isHost)
		{
			win = false;
			gameLevel = 0;
			startTime = System.currentTimeMillis();
			activeZombies = 20;
			me.reset();
			them.reset();
			them.setCol(Color.cyan);
			for (int i = 0; i < activeZombies / 4; i++)
			{
				zombiesN.get(i).reset((int) (Math.random() * maxXCord), -40);
				zombiesS.get(i).reset((int) (Math.random() * maxXCord), maxYCord + 40);
				zombiesE.get(i).reset((maxXCord + 40), (int) (Math.random() * maxYCord));
				zombiesW.get(i).reset(0 - 40, (int) (Math.random() * maxYCord));
			}
			t.stop();
			t.start();
			Zombie.resetDZ();
			Zombie.resetDZT();
			dnVal = 190 * 20;
			dnDisp = 190;
			currentTime = System.currentTimeMillis() - startTime;
			tempTime = currentTime;
			newWave();
		} else
		{
			win = false;
			gameLevel = 0;
			startTime = System.currentTimeMillis();
			activeZombies = 20;
			me.reset();
			them.reset();
			them.setCol(Color.cyan);
			for (int i = 0; i < activeZombies / 4; i++)
			{
				zombiesN.get(i).reset((int) (Math.random() * maxXCord), -40);
				zombiesS.get(i).reset((int) (Math.random() * maxXCord), maxYCord + 40);
				zombiesE.get(i).reset((maxXCord + 40), (int) (Math.random() * maxYCord));
				zombiesW.get(i).reset(0 - 40, (int) (Math.random() * maxYCord));
			}
			t.stop();
			t.start();
			Zombie.resetDZ();
			Zombie.resetDZT();
			dnVal = 190 * 20;
			dnDisp = 190;
			currentTime = System.currentTimeMillis() - startTime;
			tempTime = currentTime;
		}
	}

	public Zambeez(JFrame frame)
	{
		setSize(maxXCord, maxYCord);
		myImage = new BufferedImage(maxXCord, maxYCord, BufferedImage.TYPE_INT_RGB);
		myBuffer = myImage.getGraphics();
		myBuffer.fillRect(0, 0, maxXCord, maxYCord);
		player = new Player(maxXCord / 2 - 50, maxYCord / 2);
		player2 = new Player(maxXCord / 2 + 50, maxYCord / 2);
		me = player;
		them = player2;
		players.add(player);
		players.add(player2);
		URL iURL = getClass().getClassLoader().getResource("resources/zombie screen shot.PNG");
		i = new ImageIcon(iURL);
		URL i2URL = getClass().getClassLoader().getResource("resources/zombie screen shot easter egg.PNG");
		i2 = new ImageIcon(i2URL);
		URL backgroundURL = getClass().getClassLoader().getResource("resources/Backround.PNG");
		background = new ImageIcon(backgroundURL);
		for (int i = 0; i < totalZombies / 4; i++)
		{
			try
			{
				zombiesN.add(new NormZombie((int) (Math.random() * maxXCord), -40, player));
				zombiesS.add(new NormZombie((int) (Math.random() * maxXCord), maxYCord + 40, player));
				zombiesE.add(new NormZombie((maxXCord + 40), (int) (Math.random() * maxYCord), player));
				zombiesW.add(new NormZombie((0 - 40), (int) (Math.random() * maxYCord), player));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		startTime = System.currentTimeMillis();
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addMouseMotionListener(this);
		frame.addMouseWheelListener(this);
		try
		{
			myServer = new NetworkServerThread(this);
			myServer.start();
		} catch (Exception e)
		{
			System.out.println("exception starting server");
		}
		t = new Timer(15, this);
		t.start();
	}

	public void actionPerformed(ActionEvent ev)
	{
		if (isHost)
		{
			remZombies = (activeZombies - Zombie.getDZ());
		}
		if (paused)
		{
			if (!help)
				paintBackGround(myBuffer);
			paintText(myBuffer);
		} else
		{
			Zombie.unpause(gameLevel);
			me.unpause();
			them.unpause();
			dayCycle(myBuffer);
			if (!mp)
				paintFlashlight(myBuffer, player, player2);
			for (int k = 0; k < activeZombies / 4; k++)
			{
				zombiesN.get(k).drawObject(myBuffer);
				zombiesS.get(k).drawObject(myBuffer);
				zombiesE.get(k).drawObject(myBuffer);
				zombiesW.get(k).drawObject(myBuffer);
			}
			me.drawObject(myBuffer, mouseX, mouseY, maxXCord, maxYCord);
			them.drawObject(myBuffer, mouseX, mouseY, maxXCord, maxYCord - 100);
			myBuffer.setColor(Color.white);
			if (me.amAlive() && them.amAlive())
			{
				myBuffer.setFont(new Font("Reprise Stamp", Font.BOLD, 13));
				if (dnDisp < 65)
				{
					myBuffer.drawString("Don't be afraid of the dark...", 50, 50);
				}

				myBuffer.drawString("Wave: " + gameLevel, maxXCord - 300, maxYCord - 210);
				myBuffer.drawString("Zambies in Wave remaining:" + remZombies, maxXCord - 300, maxYCord - 180);
				myBuffer.drawString("Time elapsed: " + currentTime, maxXCord - 300, maxYCord - 150);

				if (!isHost)
				{
					myBuffer.drawString("Active Powerup1: " + p2pu, maxXCord - 300, maxYCord - 120);
					myBuffer.drawString("Weapon Equipped1: " + p2we + " " + p2ammo, maxXCord - 300, maxYCord - 90);
					myBuffer.drawString("Active Powerup2: " + me.getPUString(), maxXCord - 300, maxYCord - 60);
					myBuffer.drawString("Weapon Equipped2: " + me.getWeapontitle() + " " + me.getAmmo(), maxXCord - 300, maxYCord - 30);
				} else
				{
					myBuffer.drawString("Active Powerup1: " + me.getPUString(), maxXCord - 300, maxYCord - 120);
					myBuffer.drawString("Weapon Equipped1: " + me.getWeapontitle() + " " + me.getAmmo(), maxXCord - 300, maxYCord - 90);
					if (mp)
					{
						myBuffer.drawString("Active Powerup2: " + them.getPUString(), maxXCord - 300, maxYCord - 60);
						myBuffer.drawString("Weapon Equipped2: " + them.getWeapontitle() + " " + them.getAmmo(), maxXCord - 300, maxYCord - 30);
					}
				}

				paintGameLevel();
			} else
			{
				if (savedTime == 0)
					savedTime = (int) currentTime;
				me.life();
				them.life();
				myBuffer.setColor(Color.red.darker());
				myBuffer.setFont(new Font("chiller", Font.BOLD, 350));
				if (currentTime - savedTime < 3)
					myBuffer.drawString("YOU DIE!", 15, 350);
				myBuffer.setFont(new Font("Reprise Stamp", Font.BOLD, 45));
				if (currentTime - savedTime >= 3)
				{
					myBuffer.setFont(new Font("Reprise Stamp", Font.BOLD, 45));
					myBuffer.setColor(Color.white);
					myBuffer.drawString(playername, Zambeez.retMaxX() / 2 - 135, 275);
					myBuffer.drawString("Zambies Killed: " + Zombie.getDZT(), Zambeez.retMaxX() / 2 - 135, 325);
					myBuffer.drawString("Time elapsed: " + savedTime, Zambeez.retMaxX() / 2 - 135, 375);
				}
			}
		}
		myBuffer.setColor(Color.BLACK);
		if (paused)
			myBuffer.setColor(Color.white);
		repaint();
		if (win)
		{
			myBuffer.setColor(Color.white);
			myBuffer.setFont(new Font("Reprise Stamp", Font.BOLD, 130));
			if (currentTime - tempTime < 250)
			{
				myBuffer.drawString("YOU WIN!!!", maxXCord / 2 - 500, 512);
			}
		}
		myBuffer.drawOval((int) (mouseX - me.getBoom() / 2), (int) (mouseY - me.getBoom() / 2), me.getBoom(), me.getBoom());
		if (checkTargeted(mouseX, mouseY))
			myBuffer.setColor(Color.red);
		else
			myBuffer.setColor(Color.black);
		myBuffer.drawLine(mouseX, mouseY - (me.getBoom() / 2 + 1), mouseX, mouseY + (me.getBoom() / 2 + 1));
		myBuffer.drawLine(mouseX - (me.getBoom() / 2 + 1), mouseY, mouseX + (me.getBoom() / 2 + 1), mouseY);
		myBuffer.setColor(Color.blue);
		if (player2.isActive())
		{
			myBuffer.drawOval((int) (mx - them.getBoom() / 2), (int) (my - them.getBoom() / 2), them.getBoom(), them.getBoom());
			myBuffer.drawLine(mx, my - (them.getBoom() / 2 + 1), mx, my + (them.getBoom() / 2 + 1));
			myBuffer.drawLine(mx - (them.getBoom() / 2 + 1), my, mx + (them.getBoom() / 2 + 1), my);
		}
		Toolkit.getDefaultToolkit().sync();
		currentTime = (System.currentTimeMillis() - startTime) / 1000;
		if (isHost)
		{
			if (!paused)
			{
				for (int k = 0; k < activeZombies / 4; k++)
				{
					if (isHost)
					{
						zombiesN.get(k).move();
						zombiesS.get(k).move();
						zombiesE.get(k).move();
						zombiesW.get(k).move();
					}
				}
			}
			if (remZombies == 0)
			{
				if (gameLevel != 1)
					activeZombies += 4;
				Zombie.resetDZ();
				newWave();
			}
		}
		myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, myPoint, "cursor");
		this.setCursor(myCursor);
		if (shooting)
		{
			if (mod % 3 == 0)
			{
				me.shoot(myBuffer, mouseX, mouseY);
			}
			mod++;
		}
		transmit("M ");
		myBuffer.setColor(Color.black);
		myBuffer.setFont(new Font("Reprise Stamp", Font.BOLD, 13));
		myBuffer.drawString("MP: " + mp, maxXCord - 100, 25);
		myBuffer.drawString("Host: " + isHost, maxXCord - 100, 50);
		repaint();
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}

	public void update(Graphics g)
	{
		paint(g);
	}

	public static void newWave()
	{
		if (isHost)
		{
			tempTime = currentTime;
			if (gameLevel == 20 || (Zombie.getDZ() == activeZombies))
			{
				Zombie.pause();
				myBuffer.setColor(Color.white);
				myBuffer.setFont(new Font("Reprise Stamp", Font.BOLD, 130));
				win = true;
			} else
			{

				if (gameLevel == 9)
				{
					for (int k = 0; k < activeZombies / 4; k++)
					{
						zombiesN.get(k).incHealth();
						zombiesS.get(k).incHealth();
						zombiesE.get(k).incHealth();
						zombiesW.get(k).incHealth();
					}
					Zombie.incSpeed();
				}
				if (gameLevel == 14)
					Zombie.incSpeed();
				for (int k = 0; k < activeZombies / 4; k++)
				{
					zombiesN.get(k).newWave((int) (Math.random() * maxXCord), -20);
					zombiesS.get(k).newWave((int) (Math.random() * maxXCord), maxYCord + 20);
					zombiesE.get(k).newWave((maxXCord + 20), (int) (Math.random() * maxYCord));
					zombiesW.get(k).newWave(0 - 20, (int) (Math.random() * maxYCord));
				}

				if (me.getPU() == 4 || me.getPU() == 5)
				{
					me.setPU(101);
				}
				if (them.getPU() == 4 || them.getPU() == 5)
				{
					them.setPU(101);
				}
				gameLevel++;
				transmit("W ");
			}
		} else
		{
			transmit("W ");
		}
	}

	public void keyTyped(KeyEvent e)
	{

	}

	public void keyPressed(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if (keyCode == 101 || keyCode == KeyEvent.VK_CONTROL)
		{
			me.usePU(myBuffer);
			if (!isHost)
				transmit("I ");
		}
		if (keyCode == 104 || keyCode == 87 || keyCode == KeyEvent.VK_UP)
			me.changeState(1);
		if (keyCode == 105)
			me.changeState(2);
		if (keyCode == 102 || keyCode == 68 || keyCode == KeyEvent.VK_RIGHT)
			me.changeState(3);
		if (keyCode == 99)
			me.changeState(4);
		if (keyCode == 98 || keyCode == 83 || keyCode == KeyEvent.VK_DOWN)
			me.changeState(5);
		if (keyCode == 97)
			me.changeState(6);
		if (keyCode == 100 || keyCode == 65 || keyCode == KeyEvent.VK_LEFT)
			me.changeState(7);
		if (keyCode == 103)
			me.changeState(8);
		if ((keyCode == KeyEvent.VK_ESCAPE))
		{
			if (!paused)
				paused = true;
			options = false;
			cprofile = false;
			help = false;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int keyCode = e.getKeyCode();
		if ((keyCode == 104 || keyCode == 87 || keyCode == KeyEvent.VK_UP) && me.getState() == 1)
			me.changeState(0);
		if ((keyCode == 105) && me.getState() == 2)
			me.changeState(0);
		if ((keyCode == 102 || keyCode == 68 || keyCode == KeyEvent.VK_RIGHT) && me.getState() == 3)
			me.changeState(0);
		if ((keyCode == 99) && me.getState() == 4)
			me.changeState(0);
		if ((keyCode == 98 || keyCode == 83 || keyCode == KeyEvent.VK_DOWN) && me.getState() == 5)
			me.changeState(0);
		if ((keyCode == 97) && me.getState() == 6)
			me.changeState(0);
		if ((keyCode == 100 || keyCode == 65 || keyCode == KeyEvent.VK_LEFT) && me.getState() == 7)
			me.changeState(0);
		if ((keyCode == 103) && me.getState() == 8)
			me.changeState(0);
	}

	public void mouseMoved(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		me.setMX((double) mouseX);
		me.setMY((double) mouseY);
		repaint();
	}

	public void mouseClicked(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		if (me.getWeaponequipped() == 2)
		{
			me.shoot(myBuffer, mouseX, mouseY);
		}
	}

	public void mousePressed(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		if (paused)
		{
			if (options)
			{
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					options = false;
				}
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					options = false;
					multi = true;
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					options = false;
					cprofile = true;
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					int quit = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?");
					if (quit == 0)
					{
						System.exit(0);
						// change player code goes here
					}
				}
			} else if (multi)
			{
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					multi = false;
					options = true;
				}
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					InetAddress addr;
					String str = "Error finding IP";
					try
					{
						addr = InetAddress.getLocalHost();
						str = addr.getHostAddress();
					} catch (UnknownHostException e1)
					{
					}
					String s = JOptionPane.showInputDialog("Set player2's IP: \nYour IP is: " + str);
					int saved = 0;
					byte[] meh = new byte[4];
					int inc = 0;
					for (int k = 0; k < s.length(); k++)
					{
						if (s.charAt(k) == '.')
						{
							String temp = s.substring(saved, k);
							meh[inc] = (byte) Integer.parseInt(temp);
							inc++;
							saved = k + 1;
						}
						if (inc == 3)
						{
							String temp = s.substring(saved, s.length());
							meh[inc] = (byte) Integer.parseInt(temp);
							break;
						}
					}
					myServer.setRemoteIP(meh[0], meh[1], meh[2], meh[3]);
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					isHost = !isHost;
					if (isHost)
					{
						me = player;
						them = player2;
					} else if (!isHost)
					{
						me = player2;
						them = player;
					}

				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					mp = !mp;
					if (!mp)
					{
						me = player;
						them = player2;
					}
				}

			} else if (cprofile)
			{
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					cprofile = false;
					options = true;
				}
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					String s = JOptionPane.showInputDialog("Enter your name!");
					playername = s;
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					int pr = Integer.parseInt(JOptionPane.showInputDialog("Enter a red value 0-255."));
					int pg = Integer.parseInt(JOptionPane.showInputDialog("Enter a green value 0-255."));
					int pb = Integer.parseInt(JOptionPane.showInputDialog("Enter a blue value 0-255."));
					if (pr < 0 || pr > 255)
						pr = 0;
					if (pg < 0 || pg > 255)
						pb = 0;
					if (pb < 0 || pb > 255)
						pb = 0;
					playercol = new Color(pr, pg, pb);
					me.setCol(playercol);
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					int quit = JOptionPane.showConfirmDialog(this, "Doing this will erase your current progress. Are you sure you want to do this?");
					if (quit == 0)
					{
						reset();
						// change player code goes here
					}
				}
			} else if (help)
			{
				if (mouseX > 227 && mouseX < 246 && mouseY > 350 && mouseY < 366)
				{
					eggcount++;
				}
			} else
			{
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					paused = false;
					me.changeActive(true);
				}
				if (mouseX > (maxXCord / 2) - 515 && mouseX < (maxXCord / 2) - 65 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					transmit("R ");
					if (isHost)
					{
						String s = JOptionPane.showInputDialog("Enter your name!");
						playername = s;
						reset();
					}
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 355 && mouseY < maxYCord - 255)
				{
					options = true;
				}
				if (mouseX > (maxXCord / 2) + 75 && mouseX < (maxXCord / 2) + 525 && mouseY > maxYCord - 155 && mouseY < maxYCord - 55)
				{
					help = true;
				}
			}
		} else
		{
			if (me.getWeaponequipped() == 3)
			{
				shooting = true;
			} else
			{
				me.shoot(myBuffer, mouseX, mouseY);
			}
		}
		repaint();
	}

	public void mouseReleased(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();
		if (me.getWeaponequipped() == 2)
		{
			me.shoot(myBuffer, mouseX, mouseY);
		}
		if (me.getWeaponequipped() == 3)
		{
			shooting = false;
		}
		repaint();
	}

	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX();
		mouseY = e.getY();

	}

	public void mouseWheelMoved(MouseWheelEvent e)
	{
		scroll = e.getWheelRotation();
		me.switchGun(scroll);
	}

	public void mouseEntered(MouseEvent e)
	{
	}

	public void mouseExited(MouseEvent e)
	{
	}

	public static void projhit(int x, int y)
	{
		for (int i = 0; i < activeZombies / 4; i++)
		{
			zombiesN.get(i).amHit(x, y);
			zombiesS.get(i).amHit(x, y);
			zombiesE.get(i).amHit(x, y);
			zombiesW.get(i).amHit(x, y);
		}
	}

	public static boolean checkTargeted(int x, int y)
	{
		for (int i = 0; i < activeZombies / 4; i++)
		{
			if (zombiesN.get(i).amTargeted(x, y) || zombiesS.get(i).amTargeted(x, y) || zombiesE.get(i).amTargeted(x, y) || zombiesW.get(i).amTargeted(x, y))
				return true;
		}
		return false;
	}

	public static void unpause()
	{
		paused = !paused;
	}

	public void paintBackGround(Graphics g)
	{
		myBuffer.drawImage(background.getImage(), 0, 0, maxXCord, maxYCord, this);
	}

	public void paintText(Graphics g)
	{
		myBuffer.setColor(Color.white);
		myBuffer.setFont(new Font("Gremlin", Font.BOLD, 50));
		if (options)
		{
			myBuffer.drawString("BACK", (maxXCord / 2) - 370, maxYCord - 285);
			myBuffer.drawString("MULTIPLAYER", (maxXCord / 2) - 475, maxYCord - 85);
			myBuffer.drawString("CHANGE PROFILE", (maxXCord / 2) + 80, maxYCord - 285);
			myBuffer.drawString("EXIT", (maxXCord / 2) + 235, maxYCord - 85);
		} else if (multi)
		{
			myBuffer.drawString("BACK", (maxXCord / 2) - 370, maxYCord - 285);
			myBuffer.drawString("SET IP", (maxXCord / 2) - 365, maxYCord - 85);
			myBuffer.drawString("HOST/JOIN", (maxXCord / 2) + 160, maxYCord - 285);
			myBuffer.drawString("TOGGLE MP", (maxXCord / 2) + 150, maxYCord - 85);
		} else if (cprofile)
		{
			myBuffer.drawString("BACK", (maxXCord / 2) - 370, maxYCord - 285);
			myBuffer.drawString("CHANGE NAME", (maxXCord / 2) - 475, maxYCord - 85);
			myBuffer.drawString("CHANGE COLOR", (maxXCord / 2) + 95, maxYCord - 285);
			myBuffer.drawString("CHANGE PLAYER", (maxXCord / 2) + 85, maxYCord - 85);
		} else if (help)
		{
			if (eggcount != 0)
				myBuffer.drawImage(i2.getImage(), 0, 0, maxXCord, maxYCord, this);
			else
				myBuffer.drawImage(i.getImage(), 0, 0, maxXCord, maxYCord, this);
		} else
		{
			myBuffer.drawString("RESUME", (maxXCord / 2) - 400, maxYCord - 285);
			myBuffer.drawString("NEW GAME", (maxXCord / 2) - 435, maxYCord - 85);
			myBuffer.drawString("OPTIONS", (maxXCord / 2) + 185, maxYCord - 285);
			myBuffer.drawString("HELP", (maxXCord / 2) + 220, maxYCord - 85);
		}
	}

	public void dayCycle(Graphics g)
	{
		// System.out.println(dnDisp + " " + dnVal);
		dnDisp = dnVal / 20;
		if (dnDisp < 0 || dnDisp > 210)
			rise = !rise;
		if (!mp)
		{
			if (rise)
				dnVal += .6;
			else
				dnVal -= .6;
		}
		if (dnDisp < 45)
			myBuffer.setColor(new Color(0, 45, 0));
		else if (dnDisp > 190)
			myBuffer.setColor(new Color(0, 190, 0));
		else
			myBuffer.setColor(new Color(0, (int) dnDisp, 0));
		myBuffer.fillRect(0, 0, maxXCord, maxYCord);
	}

	public void paintGameLevel()
	{
		if (currentTime < tempTime + 3.8)
		{
			myBuffer.setFont(new Font("chiller", Font.BOLD, 250));
			myBuffer.setColor(new Color(201, 0, 0));
			myBuffer.drawString("Level: " + gameLevel, 250, 312);
		}
	}

	public void paintFlashlight(Graphics g, Player p, Player p2)
	{
		myBuffer.setColor(new Color(0, 190, 0));
		int gr = 45;
		double re = 0;
		double bl = 0;
		int pD = 200;
		int pX = p.getXCord();
		int pY = p.getYCord();
		int p2X = p.getXCord();
		int p2Y = p.getYCord();
		int pR = 100;
		int rad = 100;
		flashX[0] = p.getXCord();
		flashY[0] = p.getYCord();
		flashX2[0] = p2.getXCord();
		flashY2[0] = p2.getYCord();
		double ang;
		if (p.getAngle() > 90)
			ang = 180 - p.getAngle();
		else if (p.getAngle() > 180)
			ang = 270 - p.getAngle();
		else if (p.getAngle() > 270)
			ang = 360 - p.getAngle();
		else
			ang = p.getAngle();
		double ang2;
		if (p2.getAngle() > 90)
			ang2 = 180 - p2.getAngle();
		else if (p2.getAngle() > 180)
			ang2 = 270 - p2.getAngle();
		else if (p2.getAngle() > 270)
			ang2 = 360 - p2.getAngle();
		else
			ang2 = p2.getAngle();

		if (dnDisp < 50)
		{
			for (int k = 0; k < 100; k++)
			{
				myBuffer.setColor(new Color((int) re, gr, (int) bl));
				if (pD > 0)
				{
					Circle pCircle = new Circle(pX, pY, pR);
					pCircle.draw(myBuffer, (int) re, gr, (int) bl);
					if (mp)
					{
						Circle p2Circle = new Circle(p2X, p2Y, pR);
						p2Circle.draw(myBuffer, (int) re, gr, (int) bl);
					}
				}
				Circle circle = new Circle((int) p.getMX(), (int) p.getMY(), rad);
				circle.draw(myBuffer, (int) re, gr, (int) bl);
				myBuffer.fillPolygon(flashX, flashY, 3);
				flashX[2] = (int) (p.getMX() - (int) (rad * Math.cos(Math.PI / 2 - ang)));
				flashY[2] = (int) (p.getMY() - (int) (rad * Math.sin(Math.PI / 2 - ang)));
				flashX[1] = (int) (p.getMX() + (int) (rad * Math.cos(Math.PI / 2 - ang)));
				flashY[1] = (int) (p.getMY() + (int) (rad * Math.sin(Math.PI / 2 - ang)));
				if (mp)
				{
					Circle circle2 = new Circle((int) p2.getMX(), (int) p2.getMY(), rad);
					circle2.draw(myBuffer, (int) re, gr, (int) bl);
					myBuffer.fillPolygon(flashX2, flashY2, 3);
					flashX2[2] = (int) (p2.getMX() - (int) (rad * Math.cos(Math.PI / 2 - ang2)));
					flashY2[2] = (int) (p2.getMY() - (int) (rad * Math.sin(Math.PI / 2 - ang2)));
					flashX2[1] = (int) (p2.getMX() + (int) (rad * Math.cos(Math.PI / 2 - ang2)));
					flashY2[1] = (int) (p2.getMY() + (int) (rad * Math.sin(Math.PI / 2 - ang2)));

				}
				pD -= 2;
				gr++;
				re += .5;
				bl += .3;
				rad--;
				pR--;
			}
		}
	}

	public static void transmit(String str)
	{
		if (mp)
		{
			if (str.equals("R ") || str.equals("I ") || str.equals("S "))
			{
				myServer.sendMessage(str);
			}
			String n = "";
			String s = "";
			String e = "";
			String w = "";
			String d = "";
			if (isHost)
			{
				d += (me.getXCord() + " " + me.getYCord() + " " + mouseX + " " + mouseY + " " + remZombies + " " + gameLevel + " " + activeZombies + " " + me.getHealth() + " " + them.getHealth() + " " + them.getActivePU() + " " + me.getPUString() + " " + me.getWeapontitle() + " " + me.getAmmo() + " " + them.getAmmo());
				for (int k = 0; k < activeZombies / 4; k++)
				{
					n += zombiesN.get(k).sendData();
					s += zombiesS.get(k).sendData();
					e += zombiesE.get(k).sendData();
					w += zombiesW.get(k).sendData();
				}
				myServer.sendMessage(str + "D " + d);
				myServer.sendMessage(str + "N " + n);
				myServer.sendMessage(str + "S " + s);
				myServer.sendMessage(str + "E " + e);
				myServer.sendMessage(str + "W " + w);
			} else
			{
				d += (me.getXCord() + " " + me.getYCord() + " " + mouseX + " " + mouseY + " " + them.getPUString() + " " + me.getWE() + " ");
				for (int k = 0; k < activeZombies / 4; k++)
				{
					n += zombiesN.get(k).getHealth() + " ";
					s += zombiesS.get(k).getHealth() + " ";
					e += zombiesE.get(k).getHealth() + " ";
					w += zombiesW.get(k).getHealth() + " ";
				}
				myServer.sendMessage(str + "D " + d);
				myServer.sendMessage(str + "N " + n);
				myServer.sendMessage(str + "S " + s);
				myServer.sendMessage(str + "E " + e);
				myServer.sendMessage(str + "W " + w);
			}
			// System.out.println(str + "D: " + d);
			// System.out.println(str + "N: " + n);
			// System.out.println(str + "S: " + s);
			// System.out.println(str + "E: " + e);
			// System.out.println(str + "W: " + w);
		}
	}

	public void networkMove(String msg)
	{
		if (mp)
		{
			try
			{
				Scanner scanner = new Scanner(msg);
				String temp2 = scanner.next();
				String temp = scanner.next();
				if (!isHost)
				{
					me = player2;
					them = player;
					if (temp2.equals("R"))
					{
						System.out.print("Test");
						reset();
					} else if (temp.equals("D"))
					{
						xc = scanner.nextInt();
						yc = scanner.nextInt();
						mx = scanner.nextInt();
						my = scanner.nextInt();
						remZombies = scanner.nextInt();
						gameLevel = scanner.nextInt();
						activeZombies = scanner.nextInt();
						me.setHealth(scanner.nextInt());
						them.setHealth(scanner.nextInt());
						me.setPU(scanner.nextInt());
						p2pu = scanner.next();
						p2we = scanner.next();
						p2ammo = scanner.nextInt();
						me.setAmmo(scanner.nextInt());
						them.setCoord(xc, yc);
						them.setMX(mx);
						them.setMY(my);
						them.changeActive(true);

						if (temp2.equals("W"))
						{
							tempTime = currentTime;
							paintGameLevel();
							if (gameLevel == 10)
							{
								for (int k = 0; k < activeZombies / 4; k++)
								{
									zombiesN.get(k).incHealth();
									zombiesS.get(k).incHealth();
									zombiesE.get(k).incHealth();
									zombiesW.get(k).incHealth();
								}
								Zombie.incSpeed();
							}
							if (gameLevel == 15)
								Zombie.incSpeed();

						}
					} else if (temp.equals("N"))
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int x = scanner.nextInt();
							int y = scanner.nextInt();
							int s = scanner.nextInt();
							int r = scanner.nextInt();
							if (temp2.equals("W"))
								zombiesN.get(k).override(x, y, s, r);
							else
								zombiesN.get(k).setData(x, y, s, r);
						}
					else if (temp.equals("S"))
					{
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int x = scanner.nextInt();
							int y = scanner.nextInt();
							int s = scanner.nextInt();
							int r = scanner.nextInt();
							if (temp2.equals("W"))
								zombiesS.get(k).override(x, y, s, r);
							else
								zombiesS.get(k).setData(x, y, s, r);
						}
					} else if (temp.equals("E"))
					{
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int x = scanner.nextInt();
							int y = scanner.nextInt();
							int s = scanner.nextInt();
							int r = scanner.nextInt();
							if (temp2.equals("W"))
								zombiesE.get(k).override(x, y, s, r);
							else
								zombiesE.get(k).setData(x, y, s, r);
						}
					} else if (temp.equals("W"))
					{
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int x = scanner.nextInt();
							int y = scanner.nextInt();
							int s = scanner.nextInt();
							int r = scanner.nextInt();
							if (temp2.equals("W"))
								zombiesW.get(k).override(x, y, s, r);
							else
								zombiesW.get(k).setData(x, y, s, r);
						}
					}
				} else
				{
					me = player;
					them = player2;
					if (temp2.equals("I"))
						them.setPU(101);
					if (temp2.equals("S"))
					{
						if (packCnt % 5 == 0)
							them.setAmmo(them.getAmmo() - 1);
						packCnt++;
					}
					if (temp.equals("D"))
					{

						xc = scanner.nextInt();
						yc = scanner.nextInt();
						mx = scanner.nextInt();
						my = scanner.nextInt();
						p2pu = scanner.next();
						them.setWE(scanner.nextInt());
						them.setCoord(xc, yc);
						them.setMX(mx);
						them.setMY(my);
						them.changeActive(true);
					} else if (temp.equals("N"))
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int h = scanner.nextInt();
							if (temp2.equals("M"))
								zombiesN.get(k).setHealth(h);
							else
								zombiesN.get(k).override(h);
						}
					else if (temp.equals("S"))
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int h = scanner.nextInt();
							if (temp2.equals("M"))
								zombiesS.get(k).setHealth(h);
							else
								zombiesS.get(k).override(h);
						}
					else if (temp.equals("E"))
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int h = scanner.nextInt();
							if (temp2.equals("M"))
								zombiesE.get(k).setHealth(h);
							else
								zombiesE.get(k).override(h);
						}
					else if (temp.equals("W"))
						for (int k = 0; k < activeZombies / 4; k++)
						{
							int h = scanner.nextInt();
							if (temp2.equals("M"))
								zombiesW.get(k).setHealth(h);
							else
								zombiesW.get(k).override(h);
						}
				}
			} catch (NoSuchElementException ev)
			{

			}
		}
	}
}
