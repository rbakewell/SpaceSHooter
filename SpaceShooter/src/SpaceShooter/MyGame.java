package SpaceShooter;

import gameNet.GameControl;
import gameNet.GameNet_CoreGame;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;


public class MyGame extends GameNet_CoreGame implements Serializable, Runnable
{	
	transient GameControl gameControl ;
	static final int NUM_DISKS = 5;               //20
	static int NUM_PLAYERS = 1;
	static final double fieldHeight =50.0*2.5;        //50
	static final double fieldWidth=100.0*2.5;
	static final double diskRadius=4.0;
	static final double shotRadius=1.0;
	static final double shipRadius=4.0;

	static final int sleepTime=20;
	static final double OUT_OF_BOUNDS = -fieldWidth;
	static MyGameInput myGameInput = MyUserInterface.myGameInput;
	Disk[] disks=  new Disk[NUM_DISKS];

	boolean continueToPlay = true;

	CopyOnWriteArrayList<Player> players = new CopyOnWriteArrayList<Player>();
	CopyOnWriteArrayList<Shot> shots = new CopyOnWriteArrayList<Shot>();   // CopyOnWriteArrayList
	CopyOnWriteArrayList<ShipBoom> booms = new CopyOnWriteArrayList<ShipBoom>();


	public void startGame(GameControl g)
	{
		this.gameControl = g;
		Thread t = new Thread(this);
		restartGame();
		t.start();
	}
	public void run()
	{
		while(continueToPlay)
		{
			try
			{
				Thread.sleep(sleepTime);
				for (int i=0; i < NUM_DISKS; i++)
				{
					disks[i].update();

				}
				
				if(!shots.isEmpty()){
				// shots
				for(int i = 0; i < shots.size(); i++){

					boolean fade = shots.get(i).update_shot();
					//System.out.println("fade"+fade);
					if(fade) {
						int in = getClientIndex(shots.get(i).owner);
						players.get(in).minshots();
						shots.remove(i);
						continue;
					}
					// test for hit
					//if(!shots.isEmpty()){
						for(int p = 0; p < players.size(); p++){
							if(shots.get(i).test_hit(players.get(p).getDpoint()) && players.get(p).getinplay() && !shots.get(i).owner.equals(players.get(p).name)){

								int in = getClientIndex(shots.get(i).owner);
								players.get(in).count++;
								players.get(in).minshots();

								players.get(p).setinplay(false);
								booms.add(new ShipBoom(players.get(p).name, players.get(p).dp, players.get(p).ship, players.get(p).getvector(), players.get(p).vx, players.get(p).vy));
								players.get(p).hits++;
								shots.remove(i);
								break;
							}
						}
					}

				}
				// ship booms
				if(!booms.isEmpty()){
					for( int i = 0; i< booms.size(); i++){
						boolean test = booms.get(i).update();

						if(test){
							int in = getClientIndex(booms.get(i).name);
							players.get(in).setinplay(true);

							booms.remove(i);
						}
					}
				}

				MyGameOutput myGameOutput = new MyGameOutput(this);
				gameControl.putMsgs(myGameOutput);
			} catch (InterruptedException e){}


		}
	}

	//public tshot()

	// Process commands from each of the game players

	public MyGameOutput process(Object inputs)
	{
		myGameInput = (MyGameInput)inputs;

		//System.out.println("INP  " +myGameInput.myName);
		switch (myGameInput.command)
		{
		case MyGameInput.CONNECTING:
			// This routine will add a new user if the 
			//  player doesn't already exist
			System.out.println("START it UP");
			getClientIndex(myGameInput.myName);
			break;
		case MyGameInput.CLICK:
			//System.out.println("checking x="+ myIn.x + " y="+ myIn.y);
			for (int i=0; i < NUM_DISKS; i++)
			{
				if (disks[i].insideDisk(myGameInput.dpoint, diskRadius))
				{ // The click nailed a disk

					int index = getClientIndex(myGameInput.myName);
					if (index >= 0)
						players.get(index).count+=1;
					break; // Only get one ball per click
				}
			}
			break;
		case MyGameInput.DISCONNECTING:
			int index = getClientIndex(myGameInput.myName);
			players.get(index).setinplay(myGameInput.inplay);
			//players.remove(index);
			if (index >= 0){
				//System.out.println(players.get(index).name+" is out");
				players.remove(index);
				
			}

			break;
		case MyGameInput.SETSHIP:
			index = getClientIndex(myGameInput.myName);
			players.get(index).setship(myGameInput.myShip);
			players.get(index).setBaseColor(myGameInput.myshipBase);
			players.get(index).setCenterColor(myGameInput.myshipCenter);
			players.get(index).setIship(myGameInput.myIship);
			players.get(index).setDpoint(myGameInput.dpoint);
			players.get(index).setinplay(myGameInput.inplay);

			break;
		case MyGameInput.PLAYEROUT:
			index = getClientIndex(myGameInput.myName);
			players.get(index).setinplay(myGameInput.inplay);
			break;
			 
		case MyGameInput.SPEED:
			index = getClientIndex(myGameInput.myName);
			players.get(index).setVectorSpeed();
			break;
		case MyGameInput.VECTOR:
			index = getClientIndex(myGameInput.myName);
			players.get(index).setvector(myGameInput.myvector);
			break;
		case MyGameInput.DPOINT:
			index = getClientIndex(myGameInput.myName);
			players.get(index).setDpoint(myGameInput.dpoint);
			break;

		case MyGameInput.RESETTING:
			restartGame();
			break;
		case MyGameInput.PLAYERCOUNT:
			NUM_PLAYERS = players.size();
			break;
		case MyGameInput.SHOOT:
			index = getClientIndex(myGameInput.myName);
			
			if(players.get(index).getshots() < 10 && players.get(index).inplay){
				players.get(index).addshots();

				shots.add(new Shot(myGameInput.myName, players.get(index).getDpoint(), players.get(index).getvector()));
			} 
			break;

		} 
		MyGameOutput myGameOutput = new MyGameOutput();
		myGameOutput.myGame = this;
		return myGameOutput;
	}





	// Find index of existing player.  If he doesn't exist add him.
	// There is no limit to the number of clients to this game
	private int getClientIndex(String name)
	{
		for (int i=0; i < players.size(); i++)
		{
			if (name.equals(players.get(i).name) )
			{
				//System.out.println("SIZE  inner  " + players.size());
				return i;
			}
		}

		players.add(new Player(name));
		System.out.println("SIZE  " + players.size());
		//Set_Window.me = players.size()-1;

		return players.size() -1;
	}

	private void restartGame()
	{
		for (int i=0; i < players.size(); i++)
		{
			players.get(i).count=0;
		}
		for (int i=0; i < NUM_DISKS; i++)
		{
			disks[i] = new Disk(); // Generates Random Disks
		}
	}
	//private void resetShip(){

	//	players.get(index).setinplay(true);

	//}





	public String getScore()
	{
		String retStr="";
		for (int i=0; i < players.size(); i++)
			retStr += players.get(i).toString()+"   ";

		return retStr;
	}//*/


}



