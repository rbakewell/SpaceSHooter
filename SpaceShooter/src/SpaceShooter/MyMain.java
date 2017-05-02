package SpaceShooter;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import gameNet.*;

// test this is a test to see if code is copied or not

// ***************************************************
public class MyMain extends GameCreator{   
 
  public GameNet_CoreGame createGame()
  {
	  return new MyGame();
  }
  
  
  public static void main(String[] args) throws IOException 
  {   
  	MyMain myMain = new MyMain();
  	GameNet_UserInterface myUserInterface = new MyUserInterface();
    
  	myMain.enterGame(myUserInterface); 
  }// end of main
}// end of class
