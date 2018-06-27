package io;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ResourceBundle;

/**
 * @author spooky
 *
 */
public class ControllerAction extends ResourceAction{
	private Object controller;
	private Method controllerMethod;
	/**
	 * @param resource
	 * @param actionPrefix
	 * @param controller
	 * @param method
	 * @throws NoSuchMethodException
	 */
	public ControllerAction(ResourceBundle resource,String actionPrefix, Object controller, String method)
			throws NoSuchMethodException {
		super(resource, actionPrefix);
		this.controller = controller;
		this.controllerMethod =controller.getClass().getMethod(method);
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		try {this.controllerMethod.invoke(controller);
		} catch(IllegalAccessException ex) {throw new RuntimeException(ex); 
		} catch(InvocationTargetException ex) {throw new RuntimeException(ex); }
	}
}
