import java.util.ArrayList;
import java.awt.Rectangle;

public class Model implements Observer
{
	private ArrayList<road> roads = new ArrayList<road>();
	private ArrayList<Car> roadCars = new ArrayList<Car>();	
	
	public Model() 
	{
		//comment  this
		roads.add(new road("ARight", 335, 220, 335, 10, 335, 265));
		roads.add(new road("ALeft", 370, -50, 370, 180, 370, 265));	
		
		roads.add(new road("BRight", 460, 285, 760, 285, 415, 285));
		roads.add(new road("BLeft", 800, 350, 460, 350, 415, 350));
		
		roads.add(new road("CRight", 370, 420, 370, 600, 370, 360));
		roads.add(new road("CLeft", 335, 640, 335, 420, 335, 360));
		
		roads.add(new road("DRight", 250, 350, 10, 350, 300, 350));
		roads.add(new road("DLeft", -50, 280, 212, 280, 300, 280));
	}
	
	public void carCreate(String startRoad, String endRoad, float speed, Observer text, Observer gui, Observer model) 
	{
		Car newCar = new Car(speed);
		Boolean startFlag = false;
		Boolean endFlag = false;
		int numRoads = 0;
		
		//Initialises car with points that it needs to move between
		while (endFlag == false) {
			for(road road : roads) {
				if(startRoad == endRoad && road.GetRoadID() == startRoad) {
					newCar.addPoints("start", road.GetRoadStartX(), road.GetRoadStartY());
					newCar.addPoints("roundabout", road.GetRoadEndX(), road.GetRoadEndY());
					endFlag = true;
					newCar.setStartID(startRoad);
					newCar.registerObserver(text);
					newCar.registerObserver(gui);
					newCar.registerObserver(model);
					Thread thread = new Thread(newCar);
					thread.start();
					roadCars.add(newCar);
					break;
				} else if(road.GetRoadID() == startRoad && startFlag == false) {
					numRoads++;
					newCar.addPoints("start", road.GetRoadStartX(), road.GetRoadStartY());
					newCar.addPoints("roundabout", road.GetRoadEndX(), road.GetRoadEndY());
					newCar.addPoints("control", road.GetControlPointX(), road.GetControlPointY());
					startFlag = true;
				} else if(road.GetRoadID() != endRoad && startFlag == true) {
					numRoads++;
					newCar.addPoints("control", road.GetControlPointX(), road.GetControlPointY());
				} else if(road.GetRoadID() == endRoad && startFlag == true) {
					numRoads++;
					endFlag = true;
					newCar.addPoints("control", road.GetControlPointX(), road.GetControlPointY());
					newCar.addPoints("road", road.GetRoadStartX(), road.GetRoadStartY());
					newCar.addPoints("end", road.GetRoadEndX(), road.GetRoadEndY());
					newCar.setStartID(startRoad);
					newCar.registerObserver(text);
					newCar.registerObserver(gui);
					newCar.registerObserver(model);
					Thread thread = new Thread(newCar);
					thread.start();
					roadCars.add(newCar);
					break;
				}
			}					
		}	
		
		//Gives the car the direction it will be heading, this can then be checked by other cars
		if(numRoads == 0) {
			newCar.setDirection(direction.Straight);
		}else if(numRoads == 2) {
			newCar.setDirection(direction.Left);
		}else if(numRoads == 4) {
			newCar.setDirection(direction.Straight);
		}else if (numRoads == 6) {
			newCar.setDirection(direction.Right);
		}
	}	

	public synchronized void CollisionCheck(Car c) {
		Rectangle cr = c.getBounds();
		
		for(int i = 0; i < roadCars.size(); i++) {
			Car car2 = roadCars.get(i);
			
			if(c.getCarID() != car2.getCarID()) {
				Rectangle cr2 = car2.getBounds();
				
				//Collision checking on a straight line, doesn't work well for roundabout
				if(cr.intersects(cr2) && cr2.intersects(cr)
						&& c.getDistance() > car2.getDistance()){
					c.setStopCar(true);														
					}	
				}
			}
	}
	
	public synchronized void RoundaboutClearCheck(Car c) {
		for(int i = 0; i < roadCars.size(); i++) {
			Car car2 = roadCars.get(i);
			
			//if car is not on roundabout
			if(c.getCarID() != car2.getCarID() 
					&& car2.getRoundaboutComplete() == false 
					&& car2.IsOnRoundabout() == false
					&& car2.getPointIndex() == 0
					&& car2.getDistance() <= 40){

				Boolean found = false;
				Boolean start = false;
				int roadCount = 0;
				
				while(found == false) {
					for(road r : roads) {
						if(c.getStartID() == car2.getStartID()) {
							found = true;
							break;
						}else if(r.GetRoadID() == c.getStartID() && start == false) {
							start = true;
						}else if(r.GetRoadID() != car2.getStartID() && start == true) {
							roadCount++;
						}else if(r.GetRoadID() == car2.getStartID() && start == true) {
							found = true;
							roadCount++;	
							break;
						}
					}
				}	
				
				if(roadCount == 2) {
					car2.setStopCar(true);
				}else if(roadCount == 4 && car2.IsOnRoundabout()) {
					c.setStopCar(true);
				}else if(roadCount == 6 && c.getDirection() != direction.Left) {
					c.setStopCar(true);
				}
				
			} else if(c.getCarID() != car2.getCarID() && car2.IsOnRoundabout()) {//If the other car is on the roundabout				
					if(car2.getPointDistanceToEnd() < 5 || car2.getStartID() == c.getStartID()) {
						c.setStopCar(false);
					} else c.setStopCar(true);	
			}
		}
	}
	
	public synchronized void DestinationReached(Car c) {
		if(c.getRoundaboutComplete() == true) {
		for(int i = 0; i < roadCars.size(); i++) {
			if(c.getCarID() == roadCars.get(i).getCarID()) {
				roadCars.remove(c);
			}
		}
		}
	}
	public synchronized void update(Car c, int e) {
		if(e != 0) {
			CollisionCheck(c);
			if(c.getDistance() < 10 
					&& c.IsOnRoundabout() == false 
					&& c.getRoundaboutComplete() == false
					&& c.getPointIndex()+1 == 1) {
				RoundaboutClearCheck(c);
			}
			if(e == 4) {
				DestinationReached(c);
			}
		}		
	}
	
	public ArrayList<road> GetRoads(){
		return roads;
	}
	
	public ArrayList<Car> GetCars(){
		return roadCars;
	}
	
	public void ClearCars() {
		roadCars.clear();
	}
}
