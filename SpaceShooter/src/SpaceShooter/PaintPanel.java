package SpaceShooter;

import gameNet.GamePlayer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PaintPanel extends JPanel {
	Image gOffScreenImage = null;
	static Image img = new ImageIcon("images/NGC6188_Pugh_960.jpg").getImage();

	public final double ACCL = .01;
	public final double MAXSPEED = .05;

	Player player = MyUserInterface.player;
	int lastWidth = -1, lastHeight = -1;
	MyGame myGame = null;

	JLabel statusLabel;
	String myName;
	MyGameInput myGameInput =  MyUserInterface.myGameInput;
	GamePlayer myGamePlayer;

	BoardDimensions boardDimensions = new BoardDimensions();

	PaintPanel(JLabel statusLabel, String myName, GamePlayer gamePlayer) {
		this.statusLabel = statusLabel;
		this.myName = myName;
		myGameInput.setName(myName);
		myGamePlayer = gamePlayer;
		//addMouseListener(new MouseClickMonitor());
		addKeyListener(new KeyBoard());

	}

	public void updatePaint(MyGame g) {
		myGame = g;
		repaint();
	}

	public void paint(Graphics g) {
		if (myGame == null)
			return;

		Insets insets = super.getInsets();
		Dimension d = getSize();
		if (lastWidth != d.width || lastHeight != d.height) {
			gOffScreenImage = createImage(getBounds().width, getBounds().height);
			lastWidth = d.width;
			lastHeight = d.height;
		}
		Graphics offScreenGraphics = gOffScreenImage.getGraphics();

		offScreenGraphics.drawImage(img, 0, 0, d.width, d.height, null);

		statusLabel.setText(myName + "'s Screen: " + myGame.getScore());
		//statusLabel.setText(myName + "'s Screen: " );
		boardDimensions.setParms(insets.top, insets.left, d.width - insets.left
				- insets.right, d.height - insets.top - insets.bottom);

		offScreenGraphics.setColor(Color.black);

		// Draw Disks
		for (int i = 0; i < MyGame.NUM_DISKS; i++) {
			Disk disk = myGame.disks[i];
			if (disk.isInPlay()) {
				offScreenGraphics.setColor(disk.color);
				DPoint ddp = boardDimensions.toPaintPoint(disk.dp);
				int dx = (int) ddp.x;
				int dy = (int) ddp.y;
				int dr = (int) (boardDimensions.toPaintScaleX(MyGame.diskRadius));
				offScreenGraphics.fillOval(dx - dr, dy - dr, 1 * dr, 1 * dr);
			}
		} 

		MyUserInterface.player.setVectorSpeed();
		myGameInput.setdpoint(MyUserInterface.player.getDpoint());
		MyUserInterface.sendMessage(MyGameInput.DPOINT);
		MyUserInterface.sendMessage(MyGameInput.SPEED);

		
		for (int i = 0; i < myGame.players.size(); i++) {
			Player ply = myGame.players.get(i);

			if (ply.getinplay()) {
				DPoint pdp = boardDimensions.toPaintPoint(ply.getDpoint());
				int px = (int) pdp.x;
				int py = (int) pdp.y;
				int pr = (int) (boardDimensions.toPaintScaleX(MyGame.shipRadius));

				BufferedImage ship_aI = new BufferedImage( 100, 100, BufferedImage.TYPE_INT_ARGB);
 				Graphics2D ship_a = ship_aI.createGraphics() ;
				ship_a.setBackground(new Color(1f,0f,0f,.0f ));
				ship_a.rotate(ply.getvector(),50,50);
				Image image = ply.getIship().getImage();
				ship_a.drawImage(image, 0, 0, null);
				
				offScreenGraphics.drawImage(ship_aI, px-pr, py-pr, null);
			}
			// Draw Shots

			if(!myGame.shots.isEmpty()){
				for (int s = 0; s < myGame.shots.size(); s++) {
					Shot shot = myGame.shots.get(s);
					offScreenGraphics.setColor(Color.PINK);
					DPoint sdp = boardDimensions.toPaintPoint(shot.sdp);
					int sx = (int) sdp.x;
					int sy = (int) sdp.y;
					int sr = (int) (boardDimensions.toPaintScaleX(MyGame.shotRadius));
					offScreenGraphics.fillOval(sx - sr, sy - sr, 2 * sr, 3 * sr);
					
				}
			}
			//draw boom
			if(!myGame.booms.isEmpty()){
				for( int b = 0; b< myGame.booms.size(); b++) {
					ShipBoom boom = myGame.booms.get(b);
					DPoint sdp = boardDimensions.toPaintPoint(boom.dp);
					int sx = (int) sdp.x;
					int sy = (int) sdp.y;
					int sr = (int) boom.rate;
					int pr = (int) (boardDimensions.toPaintScaleX(MyGame.shipRadius));
					offScreenGraphics.setColor(Color.ORANGE);
					offScreenGraphics.fillOval(sx - sr, sy - sr, 2 * sr, 2 * sr);

				}
			}
			/*
			 * Disk disk = myGame.disks[i];
			 * 
			 * if (disk.isInPlay()) { offScreenGraphics.setColor(disk.color);
			 * DPoint dp = boardDimensions.toPaintPoint(disk.dp); int x =
			 * (int)dp.x; int y = (int)dp.y; int r=
			 * (int)(boardDimensions.toPaintScaleX(MyGame.diskRadius));
			 * offScreenGraphics.fillOval(x-r, y-r, 2*r, 2*r); } } //
			 */

			g.drawImage(gOffScreenImage, 0, 0, this);

		}
	}

	// *******************************************
	// Another Inner class of PaintPanel
	// *******************************************
	class MouseClickMonitor extends MouseAdapter {
		public void mousePressed(MouseEvent e) {
			DPoint dpoint = boardDimensions.toGenericPoint(e.getPoint());
			if (dpoint != null) {
				//myGameInput.dpoint = dpoint;
				//myGameInput.command = MyGameInput.CLICK;
				//myGamePlayer.sendMessage(myGameInput);
			}
		}

	}
	// new inner
	// key class
	//	class KeyBoard implements KeyListener
	class KeyBoard extends KeyAdapter
	{


		public void keyPressed(KeyEvent e){
			int keyCode = e.getKeyCode();

			switch( keyCode ) { 
			case KeyEvent.VK_UP:
				// handle up
				MyUserInterface.player.setspeed(MyUserInterface.player.getspeed() + ACCL);
				MyUserInterface.player.setVectorSpeed();
				myGameInput.setspeed(MyUserInterface.player.getspeed());
				MyUserInterface.sendMessage(MyGameInput.SPEED);
				break;
			case KeyEvent.VK_DOWN:
				// handle down 
				break;
			case KeyEvent.VK_LEFT:
				MyUserInterface.player.setvector(MyUserInterface.player.getvector() - Math.PI/6);
				myGameInput.setvector(MyUserInterface.player.getvector());
				MyUserInterface.sendMessage(MyGameInput.VECTOR);
				break;
			case KeyEvent.VK_RIGHT :
				MyUserInterface.player.setvector(MyUserInterface.player.getvector() + Math.PI/6);
				myGameInput.setvector(MyUserInterface.player.getvector());
				MyUserInterface.sendMessage(MyGameInput.VECTOR);
				break;
			case KeyEvent.VK_SPACE :
				// shoot
				MyUserInterface.sendMessage(MyGameInput.SHOOT);
				break;
			}

			repaint();
		}
		public void keyTyped(KeyEvent e){
			//System.out.println("KEYSSPPP  " + e.getKeyCode());
		}
		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			//System.out.println("key up");
		}
	}
	// end keys



}




