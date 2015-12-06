package project;

import java.awt.Color;
import java.awt.Polygon;

public class MapPolygon extends Polygon
{
	private boolean selected;
	private Color fillColor;
	private String region_id;
	
	public MapPolygon(String region_id, int[] xpoints, int[] ypoints, int npoints, Color color)
	{
		super(xpoints, ypoints, npoints);
		this.fillColor = color;
		this.selected = false;
		this.region_id = region_id;
	}
	
	public Color getFillColor()
	{
		return fillColor;
	}
	
	public void setFillColor(Color fillColor)
	{
		this.fillColor = fillColor;
	}
	
	public boolean isSelected()
	{
		return selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}
	
	public String getRegionId()
	{
		return region_id;
	}
}