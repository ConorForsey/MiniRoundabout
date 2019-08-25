import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

enum startRoads{
	ALeft, 
	BLeft, 
	CLeft,
	DLeft
}
enum endRoads{
	ARight,
	BRight,
	CRight,
	DRight,
}
public class RoundaboutController
{	
	private ControllerView controllerView;
	private ControllerView_Controls controls;
	private RoundaboutView_Text textView;
	private RoundaboutView_GUI guiView;
	private Model theModel;
		
	public RoundaboutController(ControllerView cView, ControllerView_Controls cView2, RoundaboutView_Text tView, RoundaboutView_GUI GUIView, Model theModel) 
	{		
		this.controllerView = cView;
		this.controls = cView2;
		this.textView = tView;
		this.guiView = GUIView;
		this.theModel = theModel;	
	    
		this.controllerView.addTestCaseListener(new RoundaboutControllerListener());
		this.controls.addTestCaseListener(new RoundaboutControllerListener());
		
	}
	
	class RoundaboutControllerListener implements ActionListener
	{		
		public void actionPerformed(ActionEvent e) 
		{									
			try
			{
				if(e.getSource() == controllerView.GetTestCase1Button())
				{
					theModel.ClearCars();
					theModel.carCreate("DLeft", "DLeft", 2, textView, guiView, theModel);
					theModel.carCreate("DLeft", "DLeft", 2, textView, guiView, theModel);
					guiView.setRoadCars(theModel.GetCars());
				} 
				else if (e.getSource() == controllerView.GetTestCase2Button())
				{
					theModel.ClearCars();
					theModel.carCreate("DLeft", "CRight", 2, textView, guiView, theModel);
					theModel.carCreate("DLeft", "BRight", 2, textView, guiView, theModel);
					guiView.setRoadCars(theModel.GetCars());
				}
				else if (e.getSource() == controllerView.GetTestCase3Button())
				{
					theModel.ClearCars();
					guiView.setRoadCars(theModel.GetCars());
					
					Random numOfCars = new Random();
					String startPosition = "";
					String endPosition = "";
					int randomSpeed;
					for(int i = 0; i < numOfCars.nextInt(12) + 1; i++) {
						Random RandStartPoint = new Random();
						Random RandEndPoint = new Random();
						Random RandSpeed = new Random();
						randomSpeed = RandSpeed.nextInt(4 - 1) + 2;
						startRoads start = startRoads.values()[RandStartPoint.nextInt(startRoads.values().length)];
						endRoads end = endRoads.values()[RandEndPoint.nextInt(endRoads.values().length)];
						
						switch (start) {
						case ALeft:
							startPosition = "ALeft";
							break;
						case BLeft:
							startPosition = "BLeft";
							break;
						case CLeft:
							startPosition = "CLeft";
							break;
						case DLeft:
							startPosition = "DLeft";
							break;
						}
						
						switch(end) {
						case ARight:
							endPosition = "ARight";
							break;
						case BRight:
							endPosition = "BRight";
							break;
						case CRight:
							endPosition = "CRight";
							break;
						case DRight:
							endPosition = "DRight";
							break;
						}
						
						theModel.carCreate(startPosition, endPosition, randomSpeed, textView, guiView, theModel);
					}
					guiView.setRoadCars(theModel.GetCars());
				}else if (e.getSource() == controllerView.GetTestCase4Button())
				{
					theModel.ClearCars();
					guiView.setRoadCars(theModel.GetCars());
					
					Random numOfCars = new Random();
					String startPosition = "";
					String endPosition = "";
					int randomSpeed;
					for(int i = 0; i < numOfCars.nextInt(12) + 1; i++) {
						Random RandStartPoint = new Random();
						Random RandEndPoint = new Random();
						Random RandSpeed = new Random();
						randomSpeed = RandSpeed.nextInt(4 - 1) + 2;
						startRoads start = startRoads.values()[RandStartPoint.nextInt(startRoads.values().length)];
						endRoads end = endRoads.values()[RandEndPoint.nextInt(endRoads.values().length)];
						
						switch (start) {
						case ALeft:
							startPosition = "ALeft";
							break;
						case BLeft:
							startPosition = "BLeft";
							break;
						case CLeft:
							startPosition = "CLeft";
							break;
						case DLeft:
							startPosition = "DLeft";
							break;
						}
						
						switch(end) {
						case ARight:
							endPosition = "ARight";
							break;
						case BRight:
							endPosition = "BRight";
							break;
						case CRight:
							endPosition = "CRight";
							break;
						case DRight:
							endPosition = "DRight";
							break;
						}
						
						theModel.carCreate(startPosition, endPosition, randomSpeed, textView, guiView, theModel);
					}
					guiView.setRoadCars(theModel.GetCars());
				}
				
				if(e.getSource() == controls.GetCreateCarButton()) {
					int numCars = controls.GetNumCars();
					String startPosition = controls.GetStartValue();
					String endPosition = controls.GetEndValue();
					
					for(int i = 0; i < numCars; i++) {
						theModel.carCreate(startPosition, endPosition, 2, textView, guiView, theModel);
					}
					guiView.setRoadCars(theModel.GetCars());
				}
				if(e.getSource() == controls.GetViewPointsButton()) {
					if(guiView.getViewStatus() == false) {
						guiView.setViewStatus(true);
						guiView.setRoads(theModel.GetRoads());
					}else guiView.setViewStatus(false);
					
				}
			}
			catch(NumberFormatException ex)
			{				
				System.out.println(ex);								
			}		
		}	
	}
	
}
