package event;

import java.util.EventObject;


import metier.HomeModel;



public class HomeEvent extends EventObject{

	public enum Type {ADD, DELETE, UPDATE}

	private Type type;
	HomeModel home;

	public HomeEvent(Object source, HomeModel home, Type eventType) {
		super(source);
		this.home=home;
		this.type=eventType;
	}
	public Type getType() {return this.type;}
	public HomeModel getHome() {return home;}
}
