package SpaceShooter;

import gameNet.GamePlayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Set_Window extends JFrame implements ActionListener {

	private JPanel ship_a, ship_b, ship_c;
	private static final MouseListener A = null;
	private boolean no_ship = true;
	static int me;
	Player player = MyUserInterface.player;
	MyGameInput myGameInput = MyUserInterface.myGameInput;
	public Set_Window(Player p ) {
		super("Ship Selection");
		System.out.println("boo");
		player = p;
		System.out.println("boo 2");
	}
	public void pick_ship()
	{
		setLayout(new BorderLayout());
		JPanel setup = new JPanel();
		JPanel top = new JPanel();
		top.setLayout(new GridLayout(0,3));

		ship_a = new ShipA();
		ship_b = new ShipB();
		ship_c = new ShipC();

		
		ship_a.addMouseListener(new SHIPListener());
		ship_b.addMouseListener(new SHIPListener());
		ship_c.addMouseListener(new SHIPListener());
		
		top.add(ship_a);
		top.add(ship_b);
		top.add(ship_c);

		JButton bc = new JButton("base color");
		JButton cc = new JButton("center color");
		JButton done = new JButton("done");

		setup.add(bc);
		setup.add(cc);
		setup.add(done);

		bc.addActionListener(this);
		cc.addActionListener(this);
		done.addActionListener(this);



		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(320, 180);
		setup.setVisible(true);
		top.setVisible(true);
		add(top, BorderLayout.CENTER);
		add(setup, BorderLayout.SOUTH);
		this.setAlwaysOnTop(true);

		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		
		switch(e.getActionCommand())
		{
		case "base color":
			player.setBaseColor(JColorChooser.showDialog(null, "Choose a color", player.getBaseColor()));
			break;
		case "center color":
			player.setCenterColor(JColorChooser.showDialog(null, "Choose a color", player.getCenterColor()));
			break;
		case "done":
			if(no_ship)
			{
				Component frame = null;
				JOptionPane.showMessageDialog(frame, "You need to select a ship.", "Pick a Ship",JOptionPane.WARNING_MESSAGE );
				break;
			}
			player.setinplay(true);
			
			ImageIcon sicon = new ImageIcon();
			switch(player.getship()){
			case "ShipA":
				sicon.setImage(ShipA.draw_ship(player.getvector(), player.getBaseColor(), player.getCenterColor()));
				break;
			case "ShipB":
				sicon.setImage(ShipB.draw_ship(player.getvector(), player.getBaseColor(), player.getCenterColor()));
				break;
			case "ShipC":
				sicon.setImage(ShipC.draw_ship(player.getvector(), player.getBaseColor(), player.getCenterColor()));
				break;
			}
			
			
			player.setIship(sicon);

			myGameInput.setship(player.getship(), player.getBaseColor(), player.getCenterColor(), player.getIship(), player.getinplay(), player.getDpoint(), player.getvector(), player.getspeed());
			MyUserInterface.sendMessage(MyGameInput.SETSHIP);
			
			removeAll();
			this.dispose();
			break;
			

		}
		repaint();
		

	}
	private class SHIPListener extends MouseAdapter implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0) {
		
		}
		
		public void mouseClicked(MouseEvent e){
			String ship = e.getComponent().toString().substring(13, 18);
			Color clear = new Color(0f,0f,0f,.001f );
			Color mist = new Color(0f,0f,0f,.5f );

			switch (ship)
			{
			case "ShipA":
				ship_a.setBackground(clear);
				ship_b.setBackground(mist);
				ship_c.setBackground(mist);
				
				player.setship("ShipA");
				//System.out.println(player.getship());
				no_ship = false;
				break;
			case "ShipB":
				
				ship_a.setBackground(mist);
				ship_b.setBackground(clear);
				ship_c.setBackground(mist);

				player.setship("ShipB");
				no_ship = false;
				break;
			case "ShipC":
				ship_a.setBackground(mist);
				ship_b.setBackground(mist);
				ship_c.setBackground(clear);
				
				player.setship("ShipC");
				no_ship = false;

			}
	
			repaint();

		}

		public void mouseExited(MouseEvent e) {
			
		}
		public void mouseEntered(MouseEvent e) {
			
		}


	}
}
