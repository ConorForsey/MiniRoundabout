public class MoveToPoints {
	private String location;
	private int x;
	private int y;
	
	public MoveToPoints(String loc, int nx, int ny) {
		location = loc;
		x = nx;
		y = ny;
	}
	public void SetPointX(int nx) {
		x = nx;
	}
	public void SetPointY(int ny) {
		y = ny;
	}
	public void SetLoc(String loc) {
		location = loc;
	}
	public int GetPointX() {
		return x;
	}
	public int GetPointY() {
		return y;
	}
	public String GetLoc() {
		return location;
	}
}
