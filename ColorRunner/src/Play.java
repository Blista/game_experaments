



import java.util.LinkedList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Play implements Screen{
	
	SpriteBatch batch;
	//LinkedList<Entity> entities, toAdd, toRemove;
	Player player;
	Rectangle viewport;
	Level[] levels;
	int gameProg;
	int levelStatus;
	private BitmapFont white;
	private int score; 
	
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
		white.draw(batch, "Score: " + score, 20, Gdx.graphics.getHeight() - 50);
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
		batch = new SpriteBatch();
		white = new BitmapFont(Gdx.files.internal("res/white.fnt"), false);
		
		
		if(viewport == null){
			viewport = new Rectangle(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		}
		
		player = new Player("res/CharacterImage.png", 100, 100, 50, 50);
		
		LinkedList<Level> tempLvl = new LinkedList<Level>();
		levelStatus = 0;
		gameProg = 0;
		
		tempLvl.add(lev1());
		
		levels = (Level[]) tempLvl.toArray(new Level[0]);
		levels[gameProg].levelStart(Gdx.graphics.getDeltaTime());
		Gdx.input.setInputProcessor(player);
		
		/*if(levels[gameProg].gameOver())
		{
			((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
		}*/
	}
	
	public Level lev1(){
		Level lev = new Level(player, viewport);
		lev.makeWall("res/bullet.png", 10, 200, 800, 50);
		
		lev.makeWall("res/bullet.png", 100, 300, 80, 10);
		
		lev.makeWall("res/bullet.png", 200, 400, 100, 50);
	 
		//lev.makeWall("res/bullet.png", 800, 200, 20, 600);
		lev.addTexString("res/bullet.png");
		
		lev.setStartPos(100, 500);
		return lev;
	}
	
	
}
