package swing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import event.HomeEvent;
import listener.HomeListener;
import metier.HomeModel;
import metier.HomeRecorder;
import metier.UserPreferences;

public abstract class HomeApplication {
	private List<HomeModel> homes = new ArrayList<HomeModel>();
	private List<HomeListener> homeListeners =new ArrayList<HomeListener>();
	public void addHomeListener(HomeListener homeListener) {this.homeListeners.add(homeListener);}
	public void removeHomeListener(HomeListener homeListener) {this.homeListeners.remove(homeListener);}
	public List<HomeModel> getHomes() {return Collections.unmodifiableList(this.homes);}
	public void addHome(HomeModel home) {
		this.homes = new ArrayList<HomeModel>(this.homes);
		this.homes.add(home);
		fireHomeEvent(home, HomeEvent.Type.ADD);}
	public void deleteHome(HomeModel home) {
		this.homes = new ArrayList<HomeModel>(this.homes);
		this.homes.remove(home);
		fireHomeEvent(home, HomeEvent.Type.DELETE);}
	private void fireHomeEvent(HomeModel home,HomeEvent.Type eventType) {
			if (!this.homeListeners.isEmpty()) {
			HomeEvent homeEvent = new HomeEvent(this, home, eventType);
			HomeListener [] listeners = this.homeListeners.
			toArray(new HomeListener [this.homeListeners.size()]);
			for (HomeListener listener : listeners) {
				listener.homeChanged(homeEvent);}}}
	public abstract HomeRecorder getHomeRecorder(); 
	public abstract UserPreferences getUserPreferences();
}
