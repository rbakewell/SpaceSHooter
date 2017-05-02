package SpaceShooter;
import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class MyGameInput implements Serializable
{
	// command options
	static final int CONNECTING=1;
	static final int JOIN = 1;
	static final int CLICK=2;
	static final int SHOOT=3;
	static final int SETSHIP=4;
	static final int LOAD=5;
	static final int VECTOR=6;
	static final int PLAYERCOUNT=7;
	static final int SPEED=8;
	static final int DPOINT=9;
	static final int RESETSHIP=10;
	static final int PLAYEROUT=11;
	static final int DISCONNECTING=100;
	static final int RESETTING=200;

	int command=CONNECTING;  
	
	String myName;
	String myShip;
	Color myshipBase;
	Color myshipCenter;
	ImageIcon myIship;
	boolean inplay;
	double myvector, myspeed;
	DPoint dpoint;  

	public void setName(String name)
	{
		myName=name;
	} 


	public void setship(String ship, Color base, Color cen, ImageIcon myIs,boolean in, DPoint point, double vec, double speed){

		myShip = ship;
		myshipBase = base;
		myshipCenter = cen;
		myIship = myIs;
		inplay = in;
		dpoint = point;
		myvector = vec;
		myspeed = speed;
	}
/*	public void setshipmove(double vec, double speed){
		myvector = vec;
		myspeed = speed;

	} */
	public void setvector(double v){
		myvector = v;
	}
	public void setspeed(double s){
		myspeed = s;
	}
	public void setdpoint(DPoint p){
		dpoint = p;
	}
	public void setinplay(boolean in){
		
		inplay = in;
	}
	

}


class DPoint implements Serializable
{
	double x,y;

	DPoint()
	{
		x = MyGame.fieldWidth *Math.random();
		y = MyGame.fieldHeight*Math.random();
	}
	DPoint(double x, double y)
	{
		this.x = x;
		this.y = y;
	}
}
