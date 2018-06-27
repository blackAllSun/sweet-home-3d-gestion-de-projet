package listener;

import java.util.EventListener;

import event.HomeEvent;

public interface HomeListener extends EventListener{
	public void homeChanged(HomeEvent ev);
}
