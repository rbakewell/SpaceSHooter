package SpaceShooter;

import java.io.Serializable;

public class ShipBoom implements Serializable {
	double vx,vy;
	DPoint dp;
	String name;
	String ship;
	double vector;
	double rate = 0.0;
	//MyGameInput myGameInput = MyUserInterface.myGameInput;

	ShipBoom(String n, DPoint d, String s, double v, double x, double y)
	{
		name = n;
		dp = new DPoint();
		dp = d;
		ship = s;
		vector = v;
		vx = x;
		vy = y;
		setup();
	}
	//public ShipBoom(String name2, DPoint dp2, String ship2, double vector2,
	//		double vx2, double vy2) {
		// TODO Auto-generated constructor stub
//	}
	void setup(){
		dp.x += 12.5;
		dp.y += 12.5;
	}

	public boolean update(){
		dp.x += vx;
		dp.y += vy;
		
		rate += 1;
		if(rate > 15){
			
			return true;
		}
		
		return false;
	}
}
