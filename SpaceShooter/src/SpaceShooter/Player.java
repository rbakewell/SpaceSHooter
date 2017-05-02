package SpaceShooter;

import java.awt.Color;
import java.awt.Image;
import java.io.Serializable;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.ImageIcon;


class Player implements Serializable
{

	public final double MAXSPEED = .05;

	public final double ACCL = .01;
	int hits = 0;
	int count=0;
	String name;

	long start_time;
	String ship = "";
	Color base_color = Color.red;
	Color center_color = Color.yellow;
	ImageIcon iship;
	boolean inplay = false;
	double speed = 0;
	double vector = 0;
	int facing = 0;
	double vx = 0,vy = 0;
	DPoint dp;
	AtomicInteger LiveShots = new AtomicInteger();
	
	Player(String n)
	{
		name=n;
		start_time = Calendar.getInstance().getTimeInMillis();
		ship ="";
		base_color = Color.red;;
		center_color = Color.yellow;
		dp = new DPoint();
		vector = 0;
		speed = 0;
		iship = null;
		inplay = false;;
		dp.x = Math.random()*200;
		dp.y = Math.random()*100;
		count =0;
		LiveShots.set(0);
		hits=0;
	}
	public String toString()
	{
		return name + " : "+ ship + " = "+count;
	} 

	public void setBaseColor(Color b){
		base_color = b;
	}

	public void setCenterColor(Color c){
		center_color = c;
	}

	public Color getBaseColor(){
		return base_color;
	}

	public Color getCenterColor(){
		return center_color;
	}
	public ImageIcon getIship(){
		return iship;
	}

	public void setIship(ImageIcon sicon){
		iship = sicon;
	}
	public int getshots(){
		return LiveShots.get();
	}
	public void addshots(){
		LiveShots.getAndIncrement();
	
	}
	public void minshots(){
		LiveShots.getAndDecrement();
		
	}

	public String getship(){
		return ship;
	}
	public void setship(String s){
		ship =s;
	}

	public double getvector(){
		return vector;
	}
	public void setvector(double v){
		vector = v;
		if(vector > 2*Math.PI)
		{
			vector = vector - 2*Math.PI;
		}
		if(vector < 0)
		{
			vector = vector + 2*Math.PI;
		}

	}
	public boolean getinplay(){
		return inplay;
	}
	public void setinplay(boolean b){
		inplay = b;
	/*	if(inplay){
			dp = new DPoint();
			dp.x = Math.random()*200;
			dp.y = Math.random()*100;
			speed = 0;
			vx = 0;
			vy = 0;
		} */
	}
	public DPoint getDpoint(){
		return dp;
	}
	public void setDpoint(DPoint p){
		dp = p;
	}
	public double getspeed(){
		return speed;
	}
	public void setspeed(double d){
		speed = d;
	}
	public int score(){
		long now_time = Calendar.getInstance().getTimeInMillis();
		return (int) (count/(now_time - start_time));
	}

	public void setFacing(int i){
		facing = i;
	}

	public void setVectorSpeed(){

		//double A = Math.PI/2, C =(2*Math.PI)-(Math.PI/2 + vector);
		speed -= 0.005;

		if(speed >  .05 ) //  PaintPanel.MAXSPEED
			speed= .05;
		if(speed <  0)
			speed = 0;

		vy -= speed*Math.cos(vector);
		vx += speed*Math.sin(vector);

		dp.x += vx;
		dp.y += vy;

		// Bounce off top wall
		if (dp.y <  -MyGame.shipRadius)
		{
			//vy = -vy;
			dp.y= MyGame.fieldHeight; //MyGame.shipRadius;

		}
		// Bounce off bottom wall
		if (dp.y > MyGame.fieldHeight)
		{
			//vy = - vy;
			dp.y= 0; //MyGame.shipRadius; //MyGame.fieldHeight-MyGame.shipRadius;
		}

		// Check Right Wall
		if (dp.x > MyGame.fieldWidth)
		{
			//vx = -vx;
			dp.x = 0; //MyGame.shipRadius; //MyGame.fieldWidth-MyGame.shipRadius;
		}


		// Check Left Wall
		if (dp.x < 0)
		{
			//vx = -vx;
			dp.x = MyGame.fieldWidth; //MyGame.shipRadius;
			//dp.x = MyGame.shipRadius;
		}

	}

}
