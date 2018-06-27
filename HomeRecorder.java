package metier;

import io.RecorderException;

public interface HomeRecorder {
	void writeHome(HomeModel home,String name) throws RecorderException;
	HomeModel readHome(String name) throws RecorderException;
	boolean exists(String name);
	void writeHome(Home home, String name);
}
