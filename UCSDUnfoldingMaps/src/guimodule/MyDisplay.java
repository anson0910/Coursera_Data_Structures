package guimodule;

import processing.core.PApplet;

public class MyDisplay extends PApplet{
	
	public void setup()	{
		size(400, 400);
		background(200, 200, 200);	// gray
	}
	
	public void draw()	{
		fill(250 ,250, 0);	// yellow
		ellipse(200, 200, 390, 390);	// shape of face
		fill(0, 0, 0);		// black
		ellipse(120, 130, 50, 70);		// eyes
		ellipse(280, 130, 50, 70);
		
		noFill();						// mouth
		arc(200, 280, 75, 75, 0, PI);
	}
}
