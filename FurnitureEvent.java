package event;

import java.util.EventObject;

import metier.PieceOfFurniture;

/**
 * @author spooky
 *
 */
public class FurnitureEvent extends EventObject{

	public enum Type {ADD, DELETE, UPDATE};
	private PieceOfFurniture piece;
	private int index;
	private Type type;
	/**
	 * @return PieceOfFurniture
	 */
	public PieceOfFurniture getPieceOfFurniture() {
		return piece;
	}
	/**
	 * @param piece
	 */
	public void setPiece(PieceOfFurniture piece) {
		this.piece = piece;
	}
	/**
	 * @return int
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return Type
	 */
	public Type getType() {
		return type;
	}
	/**
	 * @param type
	 */
	public void setType(Type type) {
		this.type = type;
	}
	/**
	 * @param source
	 * @param piece
	 * @param index
	 * @param type
	 */
	public FurnitureEvent(Object source, PieceOfFurniture piece,int index, Type type) {
		super(source);
		this.piece = piece;
		this.index = index;
		this.type = type;}
}
