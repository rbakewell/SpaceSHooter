package SpaceShooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ShipC extends JPanel {

	static Color backgr = new Color(0f,0f,0f,.0f );
	
	static int maxX = 100;     // 60,60 is a closer fit

	static int maxY = 100;
	static int shipX = maxX/2; // ships center
	static int shipY = maxY/2;
	static int scale =2;

	static int xship[] = {shipX-(1*scale), shipX-(3*scale),shipX-(3*scale), shipX-(1*scale), shipX+(1*scale), shipX+(3*scale), shipX+(3*scale), shipX+(1*scale), shipX+(1*scale), shipX+(2*scale), shipX+(3*scale), shipX+(4*scale),shipX+(4*scale),shipX+(5*scale),shipX+(6*scale),shipX+(7*scale),shipX+(7*scale),shipX+(9*scale),shipX+(6*scale),shipX+(6*scale),shipX+(5*scale),shipX+(5*scale),shipX+(3*scale),shipX+(3*scale),shipX+(2*scale),shipX-(2*scale),shipX-(3*scale),shipX-(3*scale),shipX-(5*scale),shipX-(5*scale),shipX-(6*scale),shipX-(6*scale),shipX-(9*scale),shipX-(7*scale),shipX-(7*scale),shipX-(6*scale),shipX-(5*scale),shipX-(4*scale),shipX-(4*scale),shipX-(3*scale),shipX-(2*scale),shipX-(1*scale)};
				           // 1                2                3                 4                5                 6              7                 8               9                 10              11              12            13              14              15              16              17               18            19                 20              21              22                23                24        25              26                  27           28             29             30                31              32                 33           34             35                36             37              38             39               40            41                42
	static int yship[] = {shipY-(3*scale), shipY-(5*scale), shipY-(7*scale),shipY-(9*scale), shipY-(9*scale), shipY-(7*scale), shipY-(5*scale), shipY-(3*scale), shipY          , shipY+(1*scale), shipY+(1*scale), shipY+(2*scale),shipY+(3*scale),shipY+(4*scale),shipY+(4*scale),shipY+(3*scale),shipY+(1*scale),shipY+(3*scale),shipY+(6*scale),shipY+(7*scale),shipY+(8*scale),shipY+(9*scale),shipY+(9*scale),shipY+(8*scale),shipY+(7*scale),shipY+(7*scale),shipY+(8*scale),shipY+(9*scale),shipY+(9*scale),shipY+(8*scale),shipY+(7*scale),shipY+(6*scale),shipY+(3*scale),shipY+(1*scale),shipY+(3*scale),shipY+(4*scale),shipY+(4*scale),shipY+(3*scale),shipY+(2*scale),shipY+(1*scale),shipY+(1*scale),shipY};
	static int xshipCen[] = {shipX          , shipX+(2*scale),shipX+(3*scale),shipX+(3*scale),shipX+(2*scale),shipX+(1*scale),shipX-(1*scale),shipX-(2*scale),shipX-(3*scale),shipX-(3*scale),shipX-(2*scale)};
	//                             1                  2          3                4             5               6               7               8               9                10             11
	static int yshipCen[] = {shipY+(1*scale), shipY+(3*scale),shipY+(3*scale),shipY+(5*scale),shipY+(6*scale),shipY+(5*scale),shipY+(5*scale),shipY+(6*scale),shipY+(5*scale),shipY+(3*scale),shipY+(3*scale)};
	static Polygon ship = new Polygon(xship, yship, xship.length);
	static Polygon shipCen = new Polygon(xshipCen, yshipCen, xshipCen.length);
	//MyGame myGame = null;
	
	//public ShipC(MyGame g) {
		//myGame = g;
	//}
	public void paint(Graphics g)
	{
		super.paintComponent(g);
		this.setOpaque(false);
		this.setBackground(backgr);
		Graphics2D g2d = (Graphics2D) g.create();
		this.setSize(maxX,maxY);  
		g2d.drawImage(PaintPanel.img, 0, 0,maxX, maxY, null);

		g2d.drawImage(draw_ship(0.0,MyUserInterface.player.getBaseColor(), MyUserInterface.player.getCenterColor()), 0, 0,maxX, maxY, new Color(0f,0f,0f,0.0f ), null);
		Color backgr = new Color(0f,0f,0f,.0f );
		if(MyUserInterface.player.getship() != "ShipC")
			backgr = new Color(0f,0f,0f,.5f );

		g2d.setColor(backgr);
		g2d.fillRect(0, 0, maxX, maxY);

	}
	public static Image draw_ship(double vector, Color bc, Color cc)	{ 
		Image img;
		BufferedImage ship_aI; 
		Color baseSC = bc;
		Color cenSC = cc;
				
		ship_aI = new BufferedImage( 100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ship_a = ship_aI.createGraphics() ;
		ship_a.setBackground(backgr);
		ship_a.clearRect(0,0,100,100);
		
		ship_a.rotate(vector,shipX,shipY);  

		ship_a.setColor(baseSC);
		ship_a.fillPolygon(ship);
		ship_a.setColor(cenSC);
		ship_a.fillPolygon(shipCen);
		
		img = ship_aI;
		return img;
	}

}
