public class road {
	private String mRoadID;
	private int mRoadStartX;
	private int mRoadStartY;
	private int mRoadEndX;
	private int mRoadEndY;
	private int mControlPointX;
	private int mControlPointY;
		
	public road(String roadID, int roadStartX, int roadStartY, int roadEndX, int roadEndY, int controlpointX, int controlpointY)
	{
		mRoadID = roadID;
		mRoadStartX = roadStartX;
		mRoadStartY = roadStartY;
		mRoadEndX = roadEndX;
		mRoadEndY = roadEndY;	
		mControlPointX = controlpointX;
		mControlPointY = controlpointY;
	}
	
	public String GetRoadID() 
	{
		return mRoadID;
	}
	
	public int GetRoadStartX() 
	{
		return mRoadStartX;
	}
	
	public int GetRoadStartY() 
	{
		return mRoadStartY;
	}
	
	public int GetRoadEndX() 
	{
		return mRoadEndX;
	}
	
	public int GetRoadEndY() 
	{
		return mRoadEndY;
	}
	
	public int GetControlPointX() {
		return mControlPointX;
	}
	
	public int GetControlPointY() {
		return mControlPointY;
	}
}
