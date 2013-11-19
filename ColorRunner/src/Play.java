
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
/**
 * 
 * @author Andrew
 * the class that you actually play the game on,
 * has levels in it that create the platforms
 * 
 */


public class Play implements Screen{
	
	SpriteBatch batch;
	//LinkedList<Entity> entities, toAdd, toRemove;
	Player player;
	Rectangle viewport;
	//levels that create the walls
	Level[] levels;
	// how far you are in the game
	int gameProg;
	int levelStatus;
	// font for score
	private BitmapFont white;
	//int for the score
	private int score = 0; 
	//string for the score
	public static String scoreString;
	
	@Override
	public void dispose() {

	}

	@Override
	public void pause() {	
		
	}

	@Override
	public void render(float delta) {
		levels[gameProg].update(delta);//Gdx.graphics.getDeltaTime());
		score += 1;
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		scoreString = "Score: " + score;
		white.draw(batch, scoreString, 20, Gdx.graphics.getHeight() - 50);
		if(gameProg < 6)levels[gameProg].render(batch);
		batch.end();
	}

	@Override
	public void resize(int arg0, int arg1) {	

	}

	@Override
	public void resume() {	
	
	}
	
	@Override
	public void hide()
	{
		
	}
	@Override
	public void show()
	{
		//creates the sprite batch and font
		batch = new SpriteBatch();
		white = new BitmapFont(Gdx.files.internal("res/white.fnt"), false);
		
		if(viewport == null){
			viewport = new Rectangle(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		}
		
		//creates the characterImage
		player = new Player("res/CharacterImage.png", 100, 100, 32, 50, "white");
		
		LinkedList<Level> tempLvl = new LinkedList<Level>();
		levelStatus = 0;
		gameProg = 0;
		
		Level lev1 = lev1();
		lev1.addPregen(fab1());
		tempLvl.add(lev1);
		
		levels = (Level[]) tempLvl.toArray(new Level[0]);
		levels[gameProg].levelStart(Gdx.graphics.getDeltaTime());
		Gdx.input.setInputProcessor(player);
		

	}
	
	/**
	 * creates the beginning levels
	 * @return lev which is a Level object
	 */
	public Level lev1(){
		Level lev = new Level(player, viewport);
		/*
		lev.makeWall("res/buttonDown.png", 10, 200, 800, 50, "red");
		
		lev.makeWall("res/buttonDown.png", 100, 300, 80, 10, "red");
		
		lev.makeWall("res/bullet.png", 200, 400, 100, 50, "white");
	 
		//lev.makeWall("res/bullet.png", 800, 200, 20, 600);
		*/
		lev.gen.addTexString("res/bullet.png");
		
		lev.setStartPos(100, 500);
		return lev;
	}
	
	public LinkedList<Entity> fab1(){
		LinkedList<Entity> set = new LinkedList<Entity>();
		set.add(new Entity("res/bullet.png",0,0,400,20,"white"));
		
		set.add(new Entity("res/bullet.png",180,90,20,630,"white"));
//		set.add(new Entity("res/bullet.png",180,90,20,380,"white"));
		set.add(new Entity("res/bullet.png",400,0,200,20,"white"));

		set.add(new Entity("res/bullet.png",200,90,400,20,"red"));
		set.add(new Entity("res/bullet.png",200,180,400,20,"red"));
		set.add(new Entity("res/bullet.png",200,270,400,20,"red"));
		set.add(new Entity("res/bullet.png",200,360,400,20,"red"));
//		set.add(new Entity("res/bullet.png",200,450,400,20,"red"));
//		set.add(new Entity("res/bullet.png",200,540,400,20,"red"));
//		set.add(new Entity("res/bullet.png",200,700,700,20,"white"));
//		set.add(new Entity("res/bullet.png",200,450,700,20,"white"));
		
		set.add(new Entity("res/bullet.png",600,0,70,380,"white"));
		return set;
	}
	
	
}
