package metier;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * @author spooky
 *
 */
public class Wall {
	float xStart;
	float yStart;
	float xEnd;
	float yEnd;
	Wall wallAtStart;
	Wall wallAtEnd;
	private float thickness;
	/**
	 * @param xStart
	 * @param yStart
	 * @param xEnd
	 * @param yEnd
	 * @param thickness
	 */
	public Wall(float xStart, float yStart, float xEnd, float yEnd, float thickness) {
		super();
		this.xStart = xStart;
		this.yStart = yStart;
		this.xEnd = xEnd;
		this.yEnd = yEnd;
		this.thickness = thickness;
		System.out.println(this);
	}
	@Override
	public String toString() {
		return "Wall [xStart=" + xStart + ", yStart=" + yStart + ", xEnd=" + xEnd + ", yEnd=" + yEnd + ", wallAtStart="
				+ wallAtStart + ", wallAtEnd=" + wallAtEnd + ", thickness=" + thickness + "]";
	}
	/**
	 * @return float
	 */
	public float getXStart() {
		return xStart;
	}
	/**
	 * @param xStart
	 */
	public void setXStart(float xStart) {
		this.xStart = xStart;
	}
	/**
	 * @return float
	 */
	public float getYStart() {
		return yStart;
	}
	/**
	 * @param yStart
	 */
	public void setYStart(float yStart) {
		this.yStart = yStart;
	}
	/**
	 * @return float
	 */
	public float getXEnd() {
		return xEnd;
	}
	/**
	 * @param xEnd
	 */
	public void setXEnd(float xEnd) {
		this.xEnd = xEnd;
	}
	/**
	 * @return float
	 */
	public float getYEnd() {
		return yEnd;
	}
	/**
	 * @param yEnd
	 */
	public void setYEnd(float yEnd) {
		this.yEnd = yEnd;
	}
	/**
	 * @return Wall
	 */
	public Wall getWallAtStart() {
		return wallAtStart;
	}
	/**
	 * @param wallAtStart
	 */
	public void setWallAtStart(Wall wallAtStart) {
		this.wallAtStart = wallAtStart;
	}
	/**
	 * @return Wall
	 */
	public Wall getWallAtEnd() {
		return wallAtEnd;
	}
	/**
	 * @param wallAtEnd
	 */
	public void setWallAtEnd(Wall wallAtEnd) {
		this.wallAtEnd = wallAtEnd;
	}
	/**
	 * @return float
	 */
	public float getThickness() {
		return thickness;
	}
	/**
	 * @param thickness
	 */
	public void setThickness(float thickness) {
		this.thickness = thickness;
	}
	/**
	 * @param x0
	 * @param y0
	 * @param x1
	 * @param y1
	 * @return boolean
	 */
	public boolean intersectsRectangle(float x0, float y0,float x1, float y1) {
		Rectangle2D rectangle = new Rectangle2D.Float(x0, y0, 0, 0);
		rectangle.add(x1, y1);
		return getShape().intersects(rectangle);}
	/**
	 * @param x
	 * @param y
	 * @param margin
	 * @return boolean
	 */
	public boolean containsWallStartAt(float x, float y, float margin){
		float [][] wallPoints = getPoints(); 
		Line2D startLine = new Line2D.Float(wallPoints [0][0],
		wallPoints [0][1], wallPoints [3][0], wallPoints [3][1]);
		return containsShapeAtWithMargin(startLine, x, y, margin);
	}
	/**
	 * @param x
	 * @param y
	 * @param margin
	 * @return boolean
	 */
	public boolean containsWallEndAt(float x, float y, float margin) {
		float [][] wallPoints = getPoints();
		Line2D endLine = new Line2D.Float(wallPoints [1][0],
		wallPoints [1][1], wallPoints [2][0], wallPoints [2][1]);
		return containsShapeAtWithMargin(endLine, x, y, margin);}
	
	/**
	 * @return float[][]
	 */
	public float[][] getPoints() {
		float [][] wallPoints = getRectanglePoints();
		float limit = 2 * this.thickness;
		if (this.wallAtStart != null) { 
			float [][] wallAtStartPoints =this.wallAtStart.getRectanglePoints();
			if (this.wallAtStart.getWallAtEnd() == this) {
				computeIntersection(wallPoints [0], wallPoints [1],wallAtStartPoints [1], wallAtStartPoints [0], limit);
				computeIntersection(wallPoints [3], wallPoints [2],wallAtStartPoints [2], wallAtStartPoints [3], limit);
			} else if (this.wallAtStart.getWallAtStart() == this) {
				computeIntersection(wallPoints [0], wallPoints [1],wallAtStartPoints [2], wallAtStartPoints [3], limit);
				computeIntersection(wallPoints [3], wallPoints [2],wallAtStartPoints [0], wallAtStartPoints [1], limit);}
		if (this.wallAtEnd != null) { 
			float [][] wallAtEndPoints =this.wallAtEnd.getRectanglePoints();
			if (this.wallAtEnd.getWallAtStart() == this) {
				computeIntersection(wallPoints [1], wallPoints [0],wallAtEndPoints [0], wallAtEndPoints [1], limit);
				computeIntersection(wallPoints [2], wallPoints [3],wallAtEndPoints [3], wallAtEndPoints [2], limit);
			} else if (this.wallAtEnd.getWallAtEnd() == this) {
				computeIntersection(wallPoints [1], wallPoints [0],wallAtEndPoints [3], wallAtEndPoints [2], limit);
				computeIntersection(wallPoints [2], wallPoints [3],wallAtEndPoints [0], wallAtEndPoints [1], limit);}}	}
		return wallPoints;}
	
	private float [][] getRectanglePoints() {
		double angle = Math.atan2(this.yEnd- this.yStart,this.xEnd- this.xStart);
		float dx = (float)Math.sin(angle) *this.thickness / 2;
		float dy = (float)Math.cos(angle) *this.thickness / 2;
		return new float [][] {
			{this.xStart+dx,this.yStart-dy},{this.xEnd+dx,this.yEnd-dy},{this.xEnd+dx,this.yEnd-dy},{this.xStart+dx,this.yStart-dy}};}
	/**
	 * @param shape
	 * @param x
	 * @param y
	 * @param margin
	 * @return boolean
	 */
	private boolean containsShapeAtWithMargin(Shape shape,float x, float y, float margin) {
		return shape.intersects(x - margin, y - margin,2 * margin, 2 * margin); }
	/**
	 * @return Shape
	 */
	private Shape getShape() { 
		float [][] points = getPoints();
		GeneralPath wallPath = new GeneralPath();
		wallPath.moveTo(points [0][0], points [0][1]);
		for (int i = 1; i < points.length; i++) {
		wallPath.lineTo(points [i][0], points [i][1]);
		}
		wallPath.closePath();
		return wallPath;
		}
	/**
	 * @param point1
	 * @param point2
	 * @param point3
	 * @param point4
	 * @param limit
	 */
	private void computeIntersection(float [] point1,float [] point2, float [] point3, float [] point4,float limit) {
		float x = point1 [0];
		float y = point1 [1];
		float a1 = (point2 [1] - point1 [1]) / (point2 [0] - point1 [0]);
		float b1= point2 [1] - a1 * point2 [0];
		float a2 = (point4 [1] - point3 [1])/ (point4 [0] - point3 [0]);
		float b2= point4 [1] - a2 * point4 [0];
		if (a1 != a2) { 
			if (point1 [0] == point2 [0]) {
			x = point1 [0];
			y = a2 * x + b2; 
		} else if (point3 [0] == point4 [0]) {
			x = point3 [0];
			y = a1 * x + b1; 
		} else {
			x = (b2 - b1) / (a1 - a2);
			y = a1 * x + b1;}
		if (Point2D.distanceSq(x, y, point1 [0], point1 [1])< limit * limit) {point1 [0] = x;point1 [1] = y;}
	}}
}
