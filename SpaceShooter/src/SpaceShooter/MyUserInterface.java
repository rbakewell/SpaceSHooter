package SpaceShooter;

import gameNet.GameNet_UserInterface;
import gameNet.GamePlayer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;



public class MyUserInterface extends JFrame implements  GameNet_UserInterface
{   
	static GamePlayer  myGamePlayer=null;
	
	String myName=null;
	JLabel statusLabel = new JLabel("");
	static MyGameInput myGameInput = new MyGameInput();
	PaintPanel paintPanel=null;
	static Player player = null;
	
	public MyUserInterface()
	{
		super("S P A C E");  

	}

	public void receivedMessage(Object ob)
	{
		MyGameOutput myGameOutput = (MyGameOutput)ob;
		if (paintPanel != null)
			paintPanel.updatePaint(myGameOutput.myGame);
		else
			System.out.println("Thread is already sending  stuff before I am ready");
	}


	public static void sendMessage(int command)
	{		
		myGameInput.command = command;
		myGamePlayer.sendMessage(myGameInput);
	}


	public void startUserInterface (GamePlayer gamePlayer) {   
		myGamePlayer = gamePlayer;
		myName=gamePlayer.getPlayerName();
		myGameInput.setName(myName);
		
		mylayout();
		player = new Player(myName);
			
		Set_Window sw = new Set_Window(player);
		MyUserInterface.sendMessage(MyGameInput.CONNECTING);
		
		sw.pick_ship();	
		addWindowListener(new Termination());
		
		;
		setVisible(true); 
	} 
	
	private void mylayout()
	{
		setLayout(new BorderLayout());
		paintPanel = new PaintPanel( statusLabel,  myName, myGamePlayer);
		add(paintPanel, BorderLayout.CENTER);
		paintPanel.setFocusable(true);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());     
		//JButton b = new JButton("Reset");
		//topPanel.add(b);
		//b.addActionListener(new ActionMonitor()); 
		statusLabel.setText(myName);
		topPanel.add(statusLabel);
		add(topPanel, BorderLayout.NORTH);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //me
		int screenHeight = screenSize.height;
		int screenWidth = screenSize.width;
		System.out.println(screenWidth+ ":"+screenHeight);
		int w = (int) (500*1.5); // 1000 good
		int h = (int) (300*1.5); //600 good 
		setSize(w,h);  // 500, 300
		this.setResizable(false);
	}

/*
	class ActionMonitor implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{            
			//sendMessage(MyGameInput.RESETTING);
			for(int i =0; i< myGame.players.size(); i++)
				System.out.println(myGame.players.get(i));
		}
	} */

	//*******************************************************
	// Inner Class
	//*******************************************************

	class Termination extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			myGameInput.setinplay(false);
			sendMessage(MyGameInput.PLAYEROUT);
			sendMessage(MyGameInput.DISCONNECTING);
			
			myGamePlayer.doneWithGame();
			System.exit(0);
		}
	}

}



