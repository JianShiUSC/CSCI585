package project;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class PolygonExample extends JPanel
{
	private static List<MapPolygon> polygons;
	private static List<MyCircle> circles;
	private static List<MyPoint> points;
	
	private static final int WIDTH = 1500;
	private static final int HEIGHT = 1000;
	
	public static boolean FLAG = false;
	public static JCheckBox box;
	
	
	public PolygonExample()
	{
		polygons = new LinkedList<MapPolygon>();
		circles = new LinkedList<MyCircle>();
		points = new LinkedList<MyPoint>();
		String r_id = "";
		int x1 = 0;
		int x2 = 0;
		int x3 = 0;
		int x4 = 0;
		int y1 = 0;
		int y2 = 0;
		int y3 = 0;
		int y4 = 0;
		
		String p_id = "";
		int h1 = 0;
		int h2 = 0;
		int h3 = 0;
		int v1 = 0;
		int v2 = 0;
		int v3 = 0;
		
		String l_id = "";
		int l1 = 0;
		int l2 = 0;
		
		box = new JCheckBox("geo");
		box.setBounds(500, 10, 400, 30);
		this.add(box);
		box.setText("show lions and ponds in the selected region");
		box.addItemListener(new ItemListener()
		{
			public void itemStateChanged(ItemEvent e)
			{
				FLAG = (e.getStateChange() == 1) ? true: false;
				if(FLAG==false){
					for (MyPoint point : points)
					{
						point.setFillColor(Color.GREEN);
					}
					for (MyCircle circle : circles)
					{
						circle.setFillColor(Color.BLUE);
					}
				}
				repaint();
			}
		});
		
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "js", "password");
			Statement st = con.createStatement();
			String sql_r = "select region_id, t.x, t.y from region, TABLE(sdo_util.getvertices(region.vertice_coord)) t";
			ResultSet rs = st.executeQuery(sql_r);
			while(rs.next())
			{
				r_id = rs.getString(1);
				x1 = rs.getInt(2);
				y1 = rs.getInt(3);
				rs.next();
				x2 = rs.getInt(2);
				y2 = rs.getInt(3);
				rs.next();
				x3 = rs.getInt(2);
				y3 = rs.getInt(3);
				rs.next();
				x4 = rs.getInt(2);
				y4 = rs.getInt(3);
				polygons.add(new MapPolygon(r_id, new int[]{x1, x2, x3, x4, x1}, new int[]{y1, y2, y3, y4, y1}, 5, Color.BLUE));
			}
			
			String sql_c = "select pond_id, t.x, t.y from pond, TABLE(sdo_util.getvertices(pond.vertice_coord)) t";
			ResultSet rs_c = st.executeQuery(sql_c);
			while(rs_c.next())
			{
				p_id = rs_c.getString(1);
				h1 = rs_c.getInt(2);
				v1 = rs_c.getInt(3);
				rs_c.next();
				h2 = rs_c.getInt(2);
				v2 = rs_c.getInt(3);
				rs_c.next();
				h3 = rs_c.getInt(2);
				v3 = rs_c.getInt(3);
				int center_x = h3;
				int center_y = v2;
				circles.add(new MyCircle(p_id, center_x, center_y, Color.BLUE));
			}
			
			String sql_p = "select lion_id, t.x, t.y from lion, TABLE(sdo_util.getvertices(lion.vertice_coord)) t";
			ResultSet rs_p = st.executeQuery(sql_p);
			while(rs_p.next())
			{
				l_id = rs_p.getString(1);
				l1 = rs_p.getInt(2);
				l2 = rs_p.getInt(3);
				points.add(new MyPoint(l_id, l1,l2,l1,l2, Color.GREEN));
			}
			con.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				for(MapPolygon mapPiece : polygons)
				{
					if(FLAG)
					{
						if(mapPiece.isSelected() && !mapPiece.contains(e.getPoint()))
						{
							System.out.print("shijian");
							for (MyPoint point : points)
							{
								point.setFillColor(Color.GREEN);
							}
							for (MyCircle circle : circles)
							{
								circle.setFillColor(Color.BLUE);
							}
						}
						else if(!mapPiece.isSelected() && mapPiece.contains(e.getPoint()))
						{
							for (int i=0;i<points.size();i++)
							{	MyPoint point=points.get(i);
								String point_id = point.getLionId();
								int x_coord = point.getX();
								int y_coord = point.getY();
								if(mapPiece.contains(new Point(x_coord, y_coord)))
								{	
									points.set(i,new MyPoint(point_id, x_coord,y_coord,x_coord,y_coord, Color.RED));
								}else{
									points.set(i,new MyPoint(point_id, x_coord,y_coord,x_coord,y_coord, Color.GREEN));
								}
							}
							try
							{
								String ssss = mapPiece.getRegionId();
								Class.forName("oracle.jdbc.driver.OracleDriver");
								Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "js", "password");
								Statement st = con.createStatement();
								String sql_test1 = "select pond_id from region r, pond p where SDO_FILTER(r.vertice_coord, p.vertice_coord) = 'TRUE' AND region_id = '"+ssss+"'";
								ResultSet rs1 = st.executeQuery(sql_test1);

								if(rs1.next())
								{
									do
									{
										for (MyCircle circle : circles) 
										{
											if(circle.getPondId().equals(rs1.getString(1)))
											{
												circle.setFillColor(Color.RED);
											}
											else
											{
												circle.setFillColor(Color.BLUE);
											}
										}
									}while(rs1.next());
								}

								else
								{
									for (MyCircle circle : circles)
									{
										circle.setFillColor(Color.BLUE);
									}
								}

								con.close();
							}
							catch(Exception e2)
							{
								System.out.println(e2);
							}
						}
						repaint();
					}
					if(!FLAG)
					{
						for (MyPoint point : points)
						{
							point.setFillColor(Color.GREEN);
						}
						for (MyCircle circle : circles)
						{
							circle.setFillColor(Color.BLUE);
						}
						repaint();
					}
				}
			}
		});
	}
	public Dimension getPreferredSize()
	{
		return new Dimension(WIDTH, HEIGHT);
	}
	
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		Stroke oldStroke=g2.getStroke();
		Stroke stroke_region=new BasicStroke(2);
		g2.setStroke(stroke_region);
		final Color outlineColor = Color.BLACK;
		for(MapPolygon mapPiece : polygons)
		{
			if(mapPiece.isSelected())
			{
				g2.setColor(outlineColor);
				g2.drawPolygon(mapPiece);
				g2.setColor(mapPiece.getFillColor());
				g2.fillPolygon(mapPiece);
			}
			else
			{
				g2.setColor(outlineColor);
				g2.drawPolygon(mapPiece);
				g2.setColor(Color.WHITE);
				g2.fillPolygon(mapPiece);
			}
		}
		g2.setStroke(oldStroke);
		for (MyCircle circle : circles) 
        {
            circle.drawCircle(g);
        }
		for (MyPoint point : points) 
        {
            point.drawPoint(g);
        }
	}
		
	public static void main(String args[])
	{
		JFrame frame = new JFrame("CSCI585");
		JPanel panel = new PolygonExample();
		panel.add(box);
		frame.getContentPane().add(panel);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}