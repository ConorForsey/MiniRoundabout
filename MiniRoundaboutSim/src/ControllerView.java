import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControllerView extends JPanel
{
	private JPanel controller;
	private JButton testCase1Button;
	private JButton testCase2Button;
	private JButton testCase3Button;
	private JButton testCase4Button;
	private Box buttonContainer;

	
	public ControllerView()
	{	    
	    controller = new JPanel(new BorderLayout());
	    this.setSize(75, 100);
	    buttonContainer = Box.createHorizontalBox();
	    
	    testCase1Button = new JButton("Test 1");
	    testCase2Button = new JButton("Test 2");
	    testCase3Button = new JButton("Test 3");
	    testCase4Button = new JButton("Test 4");
	    
	    buttonContainer.add(testCase1Button);
	    buttonContainer.add(testCase2Button);
	    buttonContainer.add(testCase3Button);
	    buttonContainer.add(testCase4Button);
	        
	    controller.setLayout(new GridLayout(1,3));
	    controller.add(buttonContainer);
	    
	    this.add(controller);  
	}
	
	public void addTestCaseListener(ActionListener ListenForButtons) 
    {
    	testCase1Button.addActionListener(ListenForButtons);
    	testCase2Button.addActionListener(ListenForButtons);
    	testCase3Button.addActionListener(ListenForButtons);
    	testCase4Button.addActionListener(ListenForButtons);
    }
	
	public JButton GetTestCase1Button() 
	{
		return testCase1Button;
	}
	
	public JButton GetTestCase2Button() 
	{
		return testCase2Button;
	}
	
	public JButton GetTestCase3Button() 
	{
		return testCase3Button;
	}
	
	public JButton GetTestCase4Button() 
	{
		return testCase4Button;
	}
}
