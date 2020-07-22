package PolylineZ;
public class BBox {

	private double minX;
	private double minY;
	private double maxX;
	private double maxY;

	public BBox() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BBox(double minX, double minY, double maxX, double maxY) {
		super();
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}
	
	public BBox[] splitBox() {
	
		BBox[] childbox = new BBox[4];
		childbox[0] = new BBox();
		childbox[1] = new BBox();
		childbox[2] = new BBox();
		childbox[3] = new BBox();
		
		childbox[0].setMinX(this.minX);
		childbox[0].setMaxX((this.minX + this.maxX)/2);
		childbox[0].setMinY((this.minY + this.maxY)/2);
		childbox[0].setMaxY(this.maxY);
		
		childbox[1].setMinX((this.minX + this.maxX)/2);
		childbox[1].setMaxX(this.maxX);
		childbox[1].setMinY((this.minY + this.maxY)/2);
		childbox[1].setMaxY(this.maxY);
		
		childbox[2].setMinX((this.minX + this.maxX)/2);
		childbox[2].setMaxX(this.maxX);
		childbox[2].setMinY(this.minY);
		childbox[2].setMaxY((this.minY + this.maxY)/2);
		
		childbox[3].setMinX(this.minX);
		childbox[3].setMaxX((this.minX + this.maxX)/2);
		childbox[3].setMinY(this.minY);
		childbox[3].setMaxY((this.minY + this.maxY)/2);
		
		return childbox;
	}
	
	
	
	
}
