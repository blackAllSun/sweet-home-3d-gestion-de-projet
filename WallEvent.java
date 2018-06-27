package event;

import java.util.EventObject;

import metier.Wall;

/**
 * @author spooky
 *
 */
public class WallEvent extends EventObject{
	private Wall wall;
	public enum Type {ADD, DELETE, UPDATE}

	private Type type;
	/**
	 * @param source
	 * @param wall
	 * @param type
	 */
	public WallEvent(Object source, Wall wall, Type type) {
	super(source);
	this.wall = wall;
	this.type = type;
	}
	/**
	 * @return Wall
	 */
	public Wall getWall() {
	return this.wall;
	}
	/**
	 * @return Type
	 */
	public Type getType() {
	return this.type;
	}
}
