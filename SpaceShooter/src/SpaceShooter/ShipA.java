package SpaceShooter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class ShipA extends JPanel implements Serializable 
{

	public static Color backgr = new Color(0f,0f,0f,.0f );
	static int maxX = 100;//100;     // 60,60 is a closer fit
	static int maxY = 100; //100;
	static int shipX = maxX/2;  // 30
	static int shipY = maxY/2;
	static int scale =2;

	static int xship[] = {shipX-(2*scale), shipX,           shipX+(2*scale), shipX+(2*scale), shipX+(3*scale), shipX+(8*scale), shipX+(8*scale), shipX+(9*scale), shipX+(9*scale), shipX-(9*scale), shipX-(9*scale), shipX-(8*scale),shipX-(8*scale),shipX-(3*scale),shipX-(2*scale)};
	// 1                2                3                 4                5                 6              7                 8               9                 10              11              12
	 static int yship[] = {shipY,           shipY-(9*scale), shipY,           shipY+(1*scale), shipY+(6*scale), shipY+(6*scale), shipY+(3*scale), shipY+(3*scale), shipY+(9*scale), shipY+(9*scale), shipY+(3*scale), shipY+(3*scale),shipY+(6*scale), shipY+(6*scale),shipY+(1*scale)};
	static int xshipCen[] = {shipX,shipX-(2*scale),shipX+(2*scale)};
	static int yshipCen[] = {shipY-(2*scale),shipY+(8*scale),shipY+(8*scale)};
	static Polygon ship = new Polygon(xship, yship, xship.length);
	static Polygon shipCen = new Polygon(xshipCen, yshipCen, xshipCen.length);
	//static Image img;
	
	public void paint(Graphics g)
	 {
		 super.paintComponent(g);
		 this.setOpaque(false);
		 this.setBackground(backgr);
		 Graphics2D g2d = (Graphics2D) g.create();
		 this.setSize(maxX,maxY);  
		 g2d.drawImage(PaintPanel.img, 0, 0, maxX, maxY, null);

		 g2d.drawImage(draw_ship(0.0,MyUserInterface.player.getBaseColor(), MyUserInterface.player.getCenterColor()), 0, 0,maxX, maxY, new Color(0f,0f,0f,0.0f ), null);
		 Color backgr = new Color(0f,0f,0f,.0f );
		 if(MyUserInterface.player.getship() != "ShipA")
			 backgr = new Color(0f,0f,0f,.5f );
		 g2d.setColor(backgr);
		 g2d.fillRect(0, 0, maxX, maxY);  

	 }
	 public void setbackgr(Color c){
		 backgr = c;
	 }
	 public static Image draw_ship(double vector, Color bc, Color cc)	{ 
		 Image img;
		 Color baseSC = bc;
		 Color cenSC = cc;
		 
		 BufferedImage ship_aI; 
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



