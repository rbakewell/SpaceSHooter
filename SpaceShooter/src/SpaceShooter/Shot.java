package SpaceShooter;

import java.io.Serializable;

public class Shot implements Serializable{

	static final int SHOTLIFE = 50;
	static final double SHOTSPEED = 1.3;
	DPoint sdp;
	String owner;
	double svx, svy;
	double vec;
	int life;
	//public static int LiveShots;

	Shot(String n, DPoint p, double v){
		owner = n;
		life = SHOTLIFE;
		sdp = new DPoint();
		svx=0;
		svy=0;
		sdp = p;
		vec = v;
		set_shot_vector();
	}

	void set_shot_vector(){
		svx = SHOTSPEED*Math.sin(vec);
		svy = SHOTSPEED*Math.cos(vec)*-1;
		sdp.x += 12.5  + (svx*10);
		sdp.y += 12.5 + (svy*10);
	}

	public boolean update_shot(){

		sdp.x += svx;
		sdp.y += svy;


		// Bounce off top wall
		if (sdp.y <  -MyGame.shipRadius)
		{
			//vy = -vy;
			sdp.y= MyGame.fieldHeight; //MyGame.shipRadius;

		}
		// Bounce off bottom wall
		if (sdp.y > MyGame.fieldHeight)
		{
			//vy = - vy;
			sdp.y= 0; //MyGame.shipRadius; //MyGame.fieldHeight-MyGame.shipRadius;
		}

		// Check Right Wall
		if (sdp.x > MyGame.fieldWidth)
		{
			//vx = -vx;
			sdp.x = 0; //MyGame.shipRadius; //MyGame.fieldWidth-MyGame.shipRadius;
		}

		// Check Left Wall
		if (sdp.x < 0)
		{
			//vx = -vx;
			sdp.x = MyGame.fieldWidth; //MyGame.shipRadius;
			//dp.x = MyGame.shipRadius;
		}
		life--;
		
		if(life < 0){
			
			return true;
		}
		return false;
	}
	
	public boolean test_hit(DPoint p){
		if ( (sdp.x <= (p.x + 24)) && (sdp.x >= (p.x + 2)) && (sdp.y <= (p.y + 24)) && (sdp.y >= (p.y + 2)))
		{
			return true;
		}
			return false;
	}

}
