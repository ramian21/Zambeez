package zmbz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class Hiscores
{
	private ArrayList<Score> hiscores = new ArrayList<Score>();
	public static ArrayList<String> sts = new ArrayList<String>();
	private Scanner s = new Scanner(new File("Scoreboard.txt"));

	public Hiscores(Score score) throws FileNotFoundException, Exception
	{
		int k = 0;
		int dz;
		int tE;
		String pN;
		while (s.hasNext())
		{
			pN = s.next();
			// System.out.println(pN+k);
			dz = s.nextInt();
			// System.out.println(dz+k);
			tE = s.nextInt();
			// System.out.println(tE+k);
			hiscores.add(new Score(dz, tE, pN));
			k++;
		}
		addScore(score);
		for (k = 0; k < hiscores.size(); k++)
		{
			sts.add(hiscores.get(k).toString());
		}
	//	writeArrayToFile(sts, "Scoreboard.txt");
	}

	public static void writeArrayToFile(ArrayList<String> array, String fileName) throws Exception
	{
		try
		{
			File f = new File(fileName);
			if (f.exists())
			{
				BufferedWriter out = new BufferedWriter(new FileWriter(f, true));

				if (f.canWrite())
				{
					for (int k = 0; k < array.size(); k++)
					{
						out.newLine();
						out.write(array.get(k));
						System.out.println(array.get(k));
					}
					// JOptionPane.showMessageDialog(null, "Text is written in "
					// + fileName);
				}

				out.close();

			}
			else
			{
				JOptionPane.showMessageDialog(null, "File not found!");
			}

		} catch (Exception x)
		{
			x.printStackTrace();
		}
	}

	public void addScore(Score sc)
	{
		hiscores.add(sc);
		for (int k = 0; k < hiscores.size(); k++)
		{
			Score min = hiscores.get(k);
			int ind = k;
			for (int j = k; j < hiscores.size(); j++)
			{
				if (hiscores.get(j).compareTo(min) == 1)
				{
					min = hiscores.get(j);
					ind = j;
				}
			}
			hiscores.add(k, min);
			hiscores.remove(ind + 1);
		}
	}

	public static void dispScores(Graphics g, int x, int y)
	{
		g.setColor(Color.white);
		g.setFont(new Font("Serif", Font.BOLD, 100));
		g.drawString("High Scores", Zambeez.retMaxX() / 2 - 200, 325);
		g.setFont(new Font("Serif", Font.BOLD, 45));
		for (int k = 425; k < 675; k += 50)
		{
			//System.out.println(sts.get(((k - 425) / 50)));
			g.drawString(sts.get(((k - 425) / 50)), Zambeez.retMaxX() / 2 - 75, k);
		}
	}
}
// public class Hiscores
// {
// private ArrayList<Score> hiscores = new ArrayList<Score>();
// private ArrayList<String> sts = new ArrayList<String>();
// private Scanner s = new Scanner(new File("Scoreboard.txt"));
//
// public Hiscores(Score score) throws FileNotFoundException, Exception
// {
// int k = 0;
// int dz;
// int tE;
// String pN;
// while (s.hasNext())
// {
// pN = s.next();
// dz = s.nextInt();
// tE = s.nextInt();
// hiscores.add(new Score(dz, tE, pN));
// k++;
// }
// addScore(score);
// for (k = 0; k < hiscores.size(); k++)
// {
// sts.add(hiscores.get(k).toString());
// }
// writeArrayToFile(sts, "Scoreboard.txt");
// }
//
// public static void writeArrayToFile(ArrayList<String> array, String fileName)
// throws Exception
// {
// PrintWriter out = new PrintWriter(fileName);
// for (int k = 0; k < array.size(); k++)
// out.println(array.get(k));
// out.close();
// }
//
// public void addScore(Score sc)
// {
// hiscores.add(sc);
// for (int k = 0; k < hiscores.size(); k++)
// {
// Score min = hiscores.get(k);
// int ind = k;
// for (int j = k; j < hiscores.size(); j++)
// {
// if (hiscores.get(j).compareTo(min) == 1)
// {
// min = hiscores.get(j);
// ind = j;
// }
// }
// hiscores.add(k, min);
// hiscores.remove(ind + 1);
// }
// }
//
// public void dispScores(Graphics g, int x, int y)
// {
// g.setColor(Color.white);
// g.setFont(new Font("Serif", Font.BOLD, 100));
// g.drawString("High Scores", Zambeez.retMaxX()/2-200, 325);
// g.setFont(new Font("Serif", Font.BOLD, 45));
// for(int k = 425; k < 675 ; k+=50)
// {
// g.drawString(sts.get(((k-425)/50)),Zambeez.retMaxX()/2-75, k);
// }
// }
// }

