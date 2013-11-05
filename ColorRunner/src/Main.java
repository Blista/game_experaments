 

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


public class Main {
	
	public String scoreString;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.width = 900;
		cfg.height = 700;
		cfg.vSyncEnabled = true;
		cfg.fullscreen = false;
		cfg.useGL20 = true;
		cfg.title = "Color Runner";
		
		new LwjglApplication(new ColorRunner(), cfg);
	}
}
