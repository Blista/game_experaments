
import java.util.LinkedList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
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
	Texture background;
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
	private long startTime;
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
		levels[gameProg].setScore(score);
		levels[gameProg].update(delta);//Gdx.graphics.getDeltaTime());
		score = (int)(System.currentTimeMillis() - startTime)/10;
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(background,0,0);
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
		background = new Texture(Gdx.files.internal("res/background2.png"));
		background.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
		
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
		lev1.addPregen(fab2());
		lev1.addPregen(fab3());
		lev1.addPregen(fab4());
		lev1.addPregen(fab5());
		lev1.addPregen(fab6());
		lev1.addPregen(fab7());
		lev1.addPregen(fab8());
		lev1.addPregen(fab9());
		lev1.addPregen(fab10());
		tempLvl.add(lev1);
		
		levels = (Level[]) tempLvl.toArray(new Level[0]);
		levels[gameProg].levelStart(Gdx.graphics.getDeltaTime());
		Gdx.input.setInputProcessor(player);
		
		startTime = System.currentTimeMillis();

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
		
		lev.setStartPos(260, 500);
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
	
	//img texture, x, y , width height, color
	public LinkedList<Entity> fab2() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		set.add(new Entity("res/bullet.png",0,340,100,20,"white"));
		set.add(new Entity("res/bullet.png",120,0,20,700, "red"));
		set.add(new Entity("res/bullet.png", 300,590,100,20,"white"));
		set.add(new Entity("res/bullet.png", 300,190,200,20,"white"));
		set.add(new Entity("res/bullet.png", 480,350,20,260,"red"));
		set.add(new Entity("res/bullet.png", 600,500,20,200,"red"));
		set.add(new Entity("res/bullet.png", 600,0,20,400,"white"));
		set.add(new Entity("res/bullet.png", 750,300,100,20,"white"));
		
		return set;
	}
	
	public LinkedList<Entity> fab3() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png",0,550,100,20,"white"));
		set.add(new Entity("res/bullet.png",0,0,20,550,"white"));
		set.add(new Entity("res/bullet.png",20,100,280,20,"red"));
		set.add(new Entity("res/bullet.png",80,300,120,20,"red"));
		set.add(new Entity("res/bullet.png",200,300,20,400,"white"));
		set.add(new Entity("res/bullet.png",250,200,100,20, "blue"));
		set.add(new Entity("res/bullet.png", 250,300,100,20,"yellow"));
		set.add(new Entity("res/bullet.png", 300,600,300,20,"white"));
		set.add(new Entity("res/bullet.png", 900,100,100,20,"white"));
		
		return set;
	}
	
	public LinkedList<Entity> fab4(){
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,400,100,20, "white"));
		set.add(new Entity("res/bullet.png", 100,0,20,700, " red"));
		set.add(new Entity("res/bullet.png", 200,500, 100, 20, "white"));
		set.add(new Entity("res/bullet.png", 350,0,20,700,"yellow"));
		set.add(new Entity("res/bullet.png", 450,550,50,20, "white"));
		set.add(new Entity("res/bullet.png", 550,0,20,700, "blue"));
		set.add(new Entity("res/bullet.png", 650,300,50,20, "white"));
		set.add(new Entity("res/bullet.png", 750,0,20,700, "orange"));
		set.add(new Entity("res/bullet.png", 810,400,100,20, "white"));
		
		return set;
	}
	
	public LinkedList<Entity> fab5() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,592,100,20, "white"));
		set.add(new Entity("res/bullet.png", 100,0,20,700, " red"));
		set.add(new Entity("res/bullet.png", 200,447, 100, 20, "white"));
		set.add(new Entity("res/bullet.png", 350,0,20,700,"yellow"));
		set.add(new Entity("res/bullet.png", 450,510,50,20, "white"));
		set.add(new Entity("res/bullet.png", 550,0,20,700, "blue"));
		set.add(new Entity("res/bullet.png", 650,220,50,20, "white"));
		set.add(new Entity("res/bullet.png", 750,0,20,700, "orange"));
		set.add(new Entity("res/bullet.png", 810,306,100,20, "white"));
		
		return set;
	}
	
	public LinkedList<Entity> fab6() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,222,100,20, "white"));
		set.add(new Entity("res/bullet.png", 100,0,20,700, " red"));
		set.add(new Entity("res/bullet.png", 200,312, 100, 20, "white"));
		set.add(new Entity("res/bullet.png", 350,0,20,700,"yellow"));
		set.add(new Entity("res/bullet.png", 450,402,50,20, "white"));
		set.add(new Entity("res/bullet.png", 550,0,20,700, "blue"));
		set.add(new Entity("res/bullet.png", 650,536,50,20, "white"));
		set.add(new Entity("res/bullet.png", 750,0,20,700, "orange"));
		set.add(new Entity("res/bullet.png", 810,580,100,20, "white"));
		
		return set;
	}
	
	public LinkedList<Entity> fab7() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,53,100,20, "white"));
		set.add(new Entity("res/bullet.png", 100,0,20,700, " red"));
		set.add(new Entity("res/bullet.png", 200,111, 100, 20, "white"));
		set.add(new Entity("res/bullet.png", 350,0,20,700,"yellow"));
		set.add(new Entity("res/bullet.png", 450,198,50,20, "white"));
		set.add(new Entity("res/bullet.png", 550,0,20,700, "blue"));
		set.add(new Entity("res/bullet.png", 650,250,50,20, "white"));
		set.add(new Entity("res/bullet.png", 750,0,20,700, "orange"));
		set.add(new Entity("res/bullet.png", 810,333,100,20, "white"));
		
		return set;
	}
	
	public LinkedList<Entity> fab8() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,200,80,20, "red"));
		set.add(new Entity("res/bullet.png",50,330,20,400,"white"));
		set.add(new Entity("res/bullet.png",130,70,20,530,"white"));
		set.add(new Entity("res/bullet.png",150,400,200,20,"blue"));
		set.add(new Entity("res/bullet.png",190,0,280,20,"white"));
		set.add(new Entity("res/bullet.png",350,400,20,300,"white"));
		set.add(new Entity("res/bullet.png", 480,0,20,700,"yellow"));
		set.add(new Entity("res/bullet.png",530,0,30,20, "yellow"));
		set.add(new Entity("res/bullet.png",580,0,20,700, "yellow"));
		set.add(new Entity("res/bullet.png",620,0,30,20, "yellow"));
		
		return set;
	}
	
	public LinkedList<Entity> fab9() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,500,50,20,"white"));
		set.add(new Entity("res/bullet.png", 50,500,20,200, "red"));
		set.add(new Entity("res/bullet.png", 150,380,50,20,"red"));
		set.add(new Entity("res/bullet.png", 200,380,20,200,"yellow"));
		set.add(new Entity("res/bullet.png", 300,260,50,20,"yellow"));
		set.add(new Entity("res/bullet.png", 350,260,20,200, "blue"));
		set.add(new Entity("res/bullet.png", 450,150,100,20, "blue"));
		set.add(new Entity("res/bullet.png", 660,0,40,700, "orange"));
		set.add(new Entity("res/bullet.png", 720,400,120,20, "orange"));
		
		return set;
	}
	
	public LinkedList<Entity> fab10() {
		LinkedList<Entity> set = new LinkedList<Entity>();
		
		set.add(new Entity("res/bullet.png", 0,300,400,20, "white"));
		
		set.add(new Entity("res/bullet.png", 0,520,20,180, "white"));
		set.add(new Entity("res/bullet.png", 0,500,540,20, "white"));
		
		set.add(new Entity("res/bullet.png", 380,0,20,300, "white"));
		set.add(new Entity("res/bullet.png", 520,300,20,200, "white"));
		
		set.add(new Entity("res/bullet.png", 380,0,20,300, "white"));
		set.add(new Entity("res/bullet.png", 520,280,20,240, "white"));
		set.add(new Entity("res/bullet.png", 380,0,20,320, "white"));
		set.add(new Entity("res/bullet.png", 520,280,400,20, "white"));
		
		set.add(new Entity("res/bullet.png", 20,320,20,180, "red"));
		set.add(new Entity("res/bullet.png", 140,320,20,180, "yellow"));
		set.add(new Entity("res/bullet.png", 260,320,20,180, "blue"));
		set.add(new Entity("res/bullet.png", 380,320,20,180, "orange"));
		
		set.add(new Entity("res/bullet.png", 400,300,120,20, "purple"));
		
		set.add(new Entity("res/bullet.png", 520,20,20,260, "green"));
		set.add(new Entity("res/bullet.png", 640,20,20,260, "red"));
		set.add(new Entity("res/bullet.png", 760,20,20,260, "yellow"));
		set.add(new Entity("res/bullet.png", 880,20,20,260, "blue"));
	

		set.add(new Entity("res/bullet.png", 380,0,540,20, "white"));
		
		return set;
	}
	
}
