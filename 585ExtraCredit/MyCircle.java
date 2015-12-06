package project;

import java.awt.Color;
import java.awt.Graphics;

public class MyCircle 
{
    private int x;
    private int y;
    private Color backgroundColour = Color.BLUE;
    private String pond_id;
    //private Color fillColor;

    private static final int RADIUS = 30;

    public MyCircle(String pond_id, int x, int y, Color backgroundColour) 
    {
        this.x = x-15;
        this.y = y-15;
        this.backgroundColour = backgroundColour;
        this.pond_id = pond_id;
    }

    public void drawCircle(Graphics g) 
    {
    	g.setColor(Color.BLACK);
    	g.drawOval(x, y, RADIUS, RADIUS);
        g.setColor(backgroundColour);
        g.fillOval(x, y, RADIUS, RADIUS);
    }
    
    public String getPondId()
	{
		return pond_id;
	}
    
    public void setFillColor(Color backgroundColour)
	{
		this.backgroundColour = backgroundColour;
	}
}