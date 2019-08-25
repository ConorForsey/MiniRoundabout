import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JPanel;
import javax.swing.text.NumberFormatter;

public class ControllerView_Controls extends JPanel{
	private JPanel controller;
	private Box controlsContainer;
	private JComboBox<startRoads> startRoad;
	private JComboBox<endRoads> endRoad;
	private JFormattedTextField numberCars;
	private JButton createCarButton;
	private JButton viewPointsButton;
	
	public ControllerView_Controls() {
		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(01);									//Minimum number of cars
		formatter.setMaximum(30);									//Maximum number of cars
		formatter.setAllowsInvalid(false);							//Only allows numeric values
		
		controller = new JPanel(new BorderLayout());
	    this.setSize(75, 100);
	    
		controlsContainer = Box.createVerticalBox();
		createCarButton = new JButton("Create Car(s)");
		startRoad = new JComboBox<startRoads>(startRoads.values());
	    endRoad = new JComboBox<endRoads>(endRoads.values());
	    numberCars = new JFormattedTextField(formatter);
	    viewPointsButton = new JButton("View Points");
	    
	    numberCars.setAlignmentX(CENTER_ALIGNMENT);
	    startRoad.setAlignmentX(CENTER_ALIGNMENT);
	    endRoad.setAlignmentX(CENTER_ALIGNMENT);
	    createCarButton.setAlignmentX(CENTER_ALIGNMENT);
	    viewPointsButton.setAlignmentX(CENTER_ALIGNMENT);
	    
	    controlsContainer.add(numberCars);
	    controlsContainer.add(startRoad);
	    controlsContainer.add(endRoad);
	    controlsContainer.add(createCarButton);
	    controlsContainer.add(Box.createRigidArea(new Dimension(0,20)));
	    controlsContainer.add(viewPointsButton);
	    
	    
	    controller.add(controlsContainer);
	    this.add(controller);
	}
	
	public void addTestCaseListener(ActionListener ListenForInput) 
    {
		createCarButton.addActionListener(ListenForInput);
		viewPointsButton.addActionListener(ListenForInput);
    }
	
	public JButton GetCreateCarButton() {
		return createCarButton;
	}
	
	public JButton GetViewPointsButton() {
		return viewPointsButton;
	}
	
	public String GetStartValue() {
		return String.valueOf(startRoad.getSelectedItem());
	}
	
	public String GetEndValue() {
		return String.valueOf(endRoad.getSelectedItem());
	}
	
	public int GetNumCars() {
		if(numberCars.getText() == null || numberCars.getText().trim().isEmpty()) {
			return 1;
		}else return Integer.parseInt(numberCars.getText());
	}
}
