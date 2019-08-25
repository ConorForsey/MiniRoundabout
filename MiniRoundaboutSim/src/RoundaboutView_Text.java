import java.awt.BorderLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class RoundaboutView_Text extends JPanel implements Observer, DisplayElement
{
	//Container elements
	private JTextArea textFieldDisplay;
	private JPanel txtDisplay;
	private JScrollPane scroll;
	
	//Time
	LocalTime time = LocalTime.now();
	
	//Formatted Time
	String fTime = time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
	
	//Car Data to display
	private int carID;
	private float x;
	private float y;
	private colour carColour;
	
	//Event ID
	private int event;
	
	public RoundaboutView_Text() 
	{	
		//View
		txtDisplay = new JPanel(new BorderLayout());
		textFieldDisplay = new JTextArea(18,28);
		textFieldDisplay.setAlignmentX(CENTER_ALIGNMENT);
		scroll = new JScrollPane(textFieldDisplay);
		txtDisplay.add(scroll);
		this.add(txtDisplay);
		
	}

	public void display() 
	{
		switch(event) {
			case 0://Car created
				textFieldDisplay.append(fTime + " " + carColour +" Car: " + carID + " Created at x:" + (int)x + " y:" + (int)y + "\n");
				break;
			case 1://Car Moving
					textFieldDisplay.append(fTime + " " + carColour + " Car: " + carID + " Moved to x:" + (int)x + " y:" + (int)y + "\n");
				break;
			case 2://Entered Roundabout
				textFieldDisplay.append(fTime + " " + carColour + " Car: " + carID + " Has entered the roundabout\n");
				break;
			case 3://Moving on roundabout
				textFieldDisplay.append(fTime + " " + carColour + " Car: " + carID + " Moving on the roundabout at x:" + (int)x + " y:" + (int)y + "\n");
				break;
			case 4://Destination Reached
				textFieldDisplay.append(fTime + " " + carColour + " Car: " + carID + " Destination reached at x:" + (int)x + " y:" + (int)y + "\n");
				break;
			case 6://Waiting
				textFieldDisplay.append(fTime + " " + carColour + " Car: " + carID + " Is waiting\n");
				break;
		}
	}

	public void update(Car c, int e) 
	{
		this.carID = c.getCarID();
		this.x = c.getLocationX();
		this.y = c.getLocationY();
		this.carColour = c.getColour();
		this.event = e;
		display();	
	}
}
