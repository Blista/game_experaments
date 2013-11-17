
import com.badlogic.gdx.Game;

/**
 * 
 * @author Andrew
 * runs the game, but this class mainly used for organizing the order
 * in which the screens happen. Pretty much just runs the splash screen.
 * 
 */
public class ColorRunner extends Game{
	
	@Override
	public void create() {	
		setScreen(new Play());//Splash());
		//setScreen(new Instructions());
	}

	@Override
	public void dispose() {	
		super.dispose();
	}

	@Override
	public void pause() {	
		super.pause();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int arg0, int arg1) {	
		super.resize(arg0, arg1);
	}

	@Override
	public void resume() {	
		super.resume();
	}
}
