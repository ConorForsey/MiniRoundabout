import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class RoundaboutView_GUI extends JPanel implements Observer, DisplayElement
{
	private JPanel Display;

	//Background
	private BufferedImage background;
	
	//Array of cars
	private ArrayList<Car> roadCars = new ArrayList<Car>();
	
	//Road points
	private ArrayList<road> roads = new ArrayList<road>();
	private Boolean viewPoints = false;
	
	public RoundaboutView_GUI() 
	{
		Display = new JPanel(new BorderLayout());
		try {
			background = ImageIO.read(getClass().getResource("/roundabout.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Dimension size = new Dimension(background.getWidth(null), background.getHeight(null));
		Dimension maxSize = new Dimension(background.getWidth(null)-200, background.getHeight(null)-500);
		setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(maxSize);
	    setSize(maxSize);
	    setLayout(null);
		this.add(Display);
	}
	
	public void display() {	
	}

	public void update(Car c, int e)
	{			
		repaint();
	}
	
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, null);
		for(Car car : roadCars) {					
			Graphics2D g2d = (Graphics2D)g;
			AffineTransform trans = new AffineTransform();
			trans.rotate(car.getRotation(), car.getLocationX() + car.getWidth()/2, car.getLocationY() + car.getHeight()/2);
			trans.translate(car.getLocationX(), car.getLocationY());
			g2d.drawImage(car.getCarImage(), trans, null);
		}	
		if(viewPoints == true) {
			for(int j = 0; j < roads.size(); j++) {		
				road r = roads.get(j);
					g.setColor(Color.black);
					g.fillOval(r.GetRoadStartX(),r.GetRoadStartY(),10,10);
					g.setColor(Color.blue);
					g.fillOval(r.GetRoadEndX(), r.GetRoadEndY(), 10, 10);
					g.setColor(Color.red);
					g.fillOval(r.GetControlPointX(), r.GetControlPointY(), 10, 10);
			}
		}
	}
	
	public void setRoadCars(ArrayList<Car> cars){
		roadCars = cars;
	}
	
	public void setRoads(ArrayList<road> rs) {
		roads = rs;
	}
	
	public void setViewStatus(Boolean view) {
		viewPoints = view;
		repaint();
	}
	
	public Boolean getViewStatus() {
		return viewPoints;
	}
}
