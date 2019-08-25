import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.imageio.ImageIO;

enum colour 
	{
	Green, 
	White, 
	Blue
	}

enum direction{
	Left,
	Straight,
	Right
}

//https://gamedev.stackexchange.com/questions/23447/moving-from-ax-y-to-bx1-y1-with-constant-speed?noredirect=1&lq=1
public class Car implements Runnable, Subject
{
	private BufferedImage mGreenCar, mGreenCarLeft, mGreenCarRight, mBlueCar, mBlueCarLeft, mBlueCarRight, mWhiteCar, mWhiteCarLeft, mWhiteCarRight;

	//Car details
	private static int carCounter = 0;
	private int mCarID;
	private colour mColour;
	private direction mDirection;
	private Boolean mOnRoundabout = false;
	private Boolean mRoundaboutComplete = false;
	private int mWidth;
	private int mHeight;
	private BufferedImage mCarImage;
	private Boolean mStopCar = false;
	
	//Move car location
	private String mStartID;
	private int mStartX, mStartY;
	private float mLocationX;
	private float mLocationY;
	private int mRoadEndX;
	private int mRoadEndY;
	private float mSpeed;
	private float mElapsed = 1; //Changing this updates position more frequently, however may cause errors if changed.
	private float mDistance;
	private float mDistanceToPoint;
	private float mDirectionX, mDirectionY;
	private boolean mMoving;
	private double mRotation;
	
	//Points car will move to
	private ArrayList<MoveToPoints> points;
	private int pointIndex = 0;
	
	//Observers
	private ArrayList<Observer> observers;
	private int e;
	
	public Car(float speed) {	
		try {
			mGreenCar = ImageIO.read(getClass().getResource("/smallGreencar.png"));
			mGreenCarLeft = ImageIO.read(getClass().getResource("/smallGreenLeft.png"));
			mGreenCarRight = ImageIO.read(getClass().getResource("/smallGreenRight.png"));
			mBlueCar = ImageIO.read(getClass().getResource("/smallBluecar.png"));
			mBlueCarLeft = ImageIO.read(getClass().getResource("/smallBlueLeft.png"));
			mBlueCarRight = ImageIO.read(getClass().getResource("/smallBlueRight.png"));
			mWhiteCar = ImageIO.read(getClass().getResource("/smallWhitecar.png"));
			mWhiteCarLeft = ImageIO.read(getClass().getResource("/smallWhiteLeft.png"));
			mWhiteCarRight = ImageIO.read(getClass().getResource("/smallWhiteRight.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		this.mCarID = carCounter;
		carCounter++;	
		this.mColour = RandomColour();
		this.mCarImage = SetImage(mColour);
		this.mSpeed = speed; 
		observers = new ArrayList<Observer>();
		
		mMoving = true;
		
		points = new ArrayList<MoveToPoints>();
	}
	
	private void StartPoints() {
		if(points.size() >= (pointIndex+1)) {
			mStartX = points.get(pointIndex).GetPointX();
			mStartY = points.get(pointIndex).GetPointY();
			mLocationX = mStartX;
			mLocationY = mStartY;
			mRoadEndX = points.get(pointIndex+1).GetPointX();
			mRoadEndY = points.get(pointIndex+1).GetPointY();
			mDistance = Math.round(Math.sqrt(Math.pow(mRoadEndX-mLocationX,2)+Math.pow(mRoadEndY-mLocationY,2)));
			mDirectionX = (mRoadEndX-mLocationX) / mDistance;
			mDirectionY = (mRoadEndY-mLocationY) / mDistance;
			mRotation = Math.atan2(mDirectionY, mDirectionX);
			if(mDirectionY == 1 || mDirectionY == -1) {
			mWidth = mCarImage.getHeight();
			mHeight = mCarImage.getWidth();
			} else {
				mWidth = mCarImage.getWidth();
				mHeight = mCarImage.getHeight();
			}
		} else {
			System.out.println("Cannot create points - car class");
		}
	}

	public void run() {
		Random rand = new Random();
		try {
			int sleepTime = rand.nextInt(500 * rand.nextInt(5) + 1);
				Thread.sleep(sleepTime);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		e = 0;
		StartPoints();
		notifyObservers();
		MoveCar();
	}
	
	private void MoveCar() {
		while(mMoving) {
			while(mStopCar) {
				try {
					Thread.sleep(5);
					e = 6;
					mStopCar = false;
					notifyObservers();
				} catch (InterruptedException e1) {e1.printStackTrace();}
			}
			location(pointIndex);
			mLocationX += mDirectionX * mSpeed * mElapsed;
			mLocationY += mDirectionY * mSpeed * mElapsed;
			mDistanceToPoint = (float) Math.sqrt(Math.pow(mRoadEndX-mLocationX,2)+Math.pow(mRoadEndY-mLocationY,2));
			try {
				Thread.sleep(30);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if(Math.sqrt(Math.pow(mLocationX-mStartX,2)+Math.pow(mLocationY-mStartY,2)) >= mDistance) {
				mLocationX = mRoadEndX;
				mLocationY = mRoadEndY;
				mMoving = false;
				pointIndex++;
				if(pointIndex+1 != points.size()) {
					StartPoints();
					mMoving = true;
					if(this.getPointDistanceToEnd() < 6) {
						this.setDirection(direction.Left);
					}
				}else e = 4;
			}
			notifyObservers();
		}	
	}
	
	private void location(int pointIndex) {
		String Location = points.get(pointIndex).GetLoc();
		switch(Location) {
		case "start":
			mOnRoundabout = false;
			e = 1;
			break;
		case "roundabout":
			this.mCarImage = SetImage(mColour);
			mOnRoundabout = true;
			setSpeed(2);
			e = 2;
			notifyObservers();
			break;
		case "control":
			this.mCarImage = SetImage(mColour);
			mOnRoundabout = true;
			e = 3;
			break;
		case "road":
			this.mDirection = direction.Straight;
			this.mCarImage = SetImage(mColour);
			mOnRoundabout = false;
			setRoundaboutComplete(true);
			e = 1;
			break;
		case "end":
			
			break;
		}
	}
	
	//Generate random colour
	private static colour RandomColour(){
		Random generator = new Random();
		return colour.values()[generator.nextInt(colour.values().length)];
	}
	
	//Set Car image based on colour
	private BufferedImage SetImage(colour c) {
		switch (c) 
		{
		case Green:
			if(this.getDirection() == direction.Left) {
				mCarImage = mGreenCarLeft;
			}else if(this.getDirection() == direction.Right) {
				mCarImage = mGreenCarRight;
			}else mCarImage = mGreenCar;
			break;
		case White:
			if(this.getDirection() == direction.Left) {
				mCarImage = mWhiteCarLeft;
			}else if(this.getDirection() == direction.Right) {
				mCarImage = mWhiteCarRight;
			}else mCarImage = mWhiteCar;
			break;
		case Blue:
			if(this.getDirection() == direction.Left) {
				mCarImage = mBlueCarLeft;
			}else if(this.getDirection() == direction.Right) {
				mCarImage = mBlueCarRight;
			}else mCarImage = mBlueCar;
			break;
		}
		return mCarImage;		
	}
	
	//Getters and setters
	public int getCarID() {
		return this.mCarID;
	}
	
	public float getLocationX(){
		return this.mLocationX;
	}
	
	public float getLocationY(){
		return this.mLocationY;
	}
	
	public float getSpeed() {
		return mSpeed;
	}
	
	public void setSpeed(float speed) {
		mSpeed = speed;
	}
	
	public colour getColour() {
		return mColour;
	}
	
	public BufferedImage getCarImage() {
		return mCarImage;
	}
	
	public Boolean IsOnRoundabout() {
		return mOnRoundabout;
	}
	
	public int getWidth() {
		return mWidth;
	}
	
	public int getHeight() {
		return mHeight;
	}
	
	public double getRotation() {
		return mRotation;
	}
	
	public void addPoints(String loc, int x, int y) {
		points.add(new MoveToPoints(loc, x, y));
	}
	
	public float getDirectionX() {
		return mDirectionX;
	}
	
	public float getDirectionY() {
		return mDirectionY;
	}
	
	public void setStopCar(Boolean stop) {
		mStopCar = stop;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)mLocationX, (int)mLocationY, mWidth + 7, mHeight + 7);
	}
	
	public float getDistance() {
		return mDistanceToPoint;
	}
	public void setDirection(direction value) {
		mDirection = value;
	}
	public direction getDirection() {
		return mDirection;
	}
	public Boolean getRoundaboutComplete() {
		return mRoundaboutComplete;
	}
	public void setRoundaboutComplete(Boolean mRoundaboutComplete) {
		this.mRoundaboutComplete = mRoundaboutComplete;
	}
	public String getStartID() {
		return mStartID;
	}
	public void setStartID(String mStartID) {
		this.mStartID = mStartID;
	}	
	public int getPointDistanceToEnd() {
		return ((points.size()+1) - pointIndex);
	}	
	public int getPointIndex() {
		return pointIndex;
	}

	//Observer
	public void registerObserver(Observer o) 
	{
		observers.add(o);
	}

	public void removeObserver(Observer o)
	{
		int i = observers.indexOf(o);
		if (i >= 0) 
		{
			observers.remove(i);
		}
	}

	public void notifyObservers() 
	{
		for (int i = 0; i < observers.size(); i++) 
		{
			Observer observer = (Observer)observers.get(i);
			observer.update(this, this.e);
		}
	}
	
}
