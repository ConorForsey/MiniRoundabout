import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimExe extends JFrame{
	
	public static void main(String[] args) 
	{
		JPanel controllerContainer = new JPanel();
		Box SidePanel = Box.createVerticalBox();
		
		Model theModel = new Model();
		ControllerView controllerView = new ControllerView();
		ControllerView_Controls controls = new ControllerView_Controls();
		RoundaboutView_Text tView = new RoundaboutView_Text();
		RoundaboutView_GUI guiView = new RoundaboutView_GUI();
		SidePanel.add(tView);
		SidePanel.add(controls);
		controllerContainer.add(SidePanel);
		
		RoundaboutController theController = new RoundaboutController(controllerView, controls, tView, guiView, theModel);
		
		JFrame frame = new JFrame("Roundabout Simulation by Conor Forsey");
		frame.add(controllerView, BorderLayout.NORTH);
		frame.add(guiView, BorderLayout.EAST);
		frame.add(controllerContainer, BorderLayout.WEST);		
		frame.setLocationByPlatform(true);
		frame.setSize(1100, 700);	
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiView.setVisible(true);
		frame.setVisible(true);
	}
	
}
