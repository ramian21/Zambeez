package zmbz;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Image;
import javax.swing.JFrame;

public class Menu
{
	byte[] imageByte = new byte[0];
	Cursor myCursor;
	Point myPoint = new Point(0, 0);
	Image cursorImage = Toolkit.getDefaultToolkit().createImage(imageByte);
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	public static JFrame frame;

	public Menu()
	{
		int maxX = (int) dim.getWidth();
		int maxY = (int) dim.getHeight();
		frame = new JFrame("ZOMGZAMBEEZ");
		myCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, myPoint, "cursor");
		frame.setCursor(myCursor);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, 1200, 700);
		frame.setContentPane(new Zambeez(frame));
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public static void main(String[] args)
	{

		new Menu();

	}
}
