package project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class MyPoint 
{
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private Color backgroundColour = Color.GREEN;
    private String lion_id;
    //private Color fillColor;

    public MyPoint(String lion_id, int x1, int y1, int x2, int y2, Color backgroundColour) 
    {	
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.backgroundColour = backgroundColour;
        this.lion_id = lion_id;
    }

    public void drawPoint(Graphics g) 
    {
    	g.setColor(backgroundColour);
    	g.drawLine(x1, y1, x2, y2);
    }
    
    public String getLionId()
	{
		return lion_id;
	}
    
    public void setFillColor(Color backgroundColour)
	{
		this.backgroundColour = backgroundColour;
	}
    
    public int getX()
    {
    	return x1;
    }
    
    public int getY()
    {
    	return y1;
    }
}