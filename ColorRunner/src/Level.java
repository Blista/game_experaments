import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;



public class Level {
	
	String obnoxRect, special;
	AssetManager manager;
	LinkedList<Entity> walls, trapDoors, powerUp, toRemoveP;
	LinkedList<Vector2> possibleStart;
	Entity exit;
	static Rectangle viewport;
	Player player;
	Music exitTunes;
	Sound powerGet;
	long start_time, delayms;
	boolean light;
	float startX, startY;
	
	protected Level(Player player, Rectangle viewport){
		manager = new AssetManager();
		manager.load("res/bullet.png", Texture.class);
		manager.load("res/BatteryPowerUp.png", Texture.class);
		manager.load("res/RaasZ+Mr Roya Moke - spacebeat.mp3", Music.class);
		manager.load("res/Powerup52.wav", Sound.class);
		manager.finishLoading();
		
		obnoxRect = "res/bullet.png";
		special = "res/BatteryPowerUp.png";
		exitTunes = manager.get("res/RaasZ+Mr Roya Moke - spacebeat.mp3", Music.class);
		powerGet = manager.get("res/Powerup52.wav", Sound.class);
		
		this.player = player;
		this.viewport = viewport;
		
		walls = new LinkedList<Entity>();
		trapDoors = new LinkedList<Entity>();
		powerUp = new LinkedList<Entity>();
		possibleStart = new LinkedList<Vector2>();
		toRemoveP = new LinkedList<Entity>();
		
		exit = new Entity(obnoxRect);//, 400,0,50,50);
		light = false;
		
		delayms = 1000;
		startX = 0;
		startY = 0;
		
	}
	
	public void makeWall(float x, float y, float width, float height){
		Entity wall = new Entity(obnoxRect);//, (float)x, (float)y, (float)width, (float)height);
		walls.add(wall);
		
	}
	
	public void makeExit(float x, float y, float width, float height){
		Entity exit = new Entity(obnoxRect);//, (float)x, (float)y, (float)width, (float)height);
		this.exit = exit;
	}
	
	public void makeTrap(float x, float y, float width, float height){
		Entity exit = new Entity(obnoxRect);//, (float)x, (float)y, (float)width, (float)height);
		trapDoors.add(exit);
	}
	
	public void makePower(float x, float y, float width, float height){
		Entity power = new Entity(special);//, (float)x, (float)y, (float)width, (float)height);
		powerUp.add(power);
	}
	
	public void setStartPos(float x, float y){
		//possibleStart.add(new Vector2(x, y));
		startX = x;
		startY = y;
	}
	
	public int levelStart(float delta){
		//Vector2[] starts = possibleStart.toArray(new Vector2[0]);
		//Vector2 start = starts[(int)((starts.length - 1) * Math.random())];
		player.setPosition(startX, startY);//start.x, start.y);
		player.update(delta);
		exitTunes.play();
		exitTunes.setLooping(true);
		return update(delta);
	}
	
	public void levelEnd(){
		exitTunes.stop();
	}
	
	public int update(float delta){
		boolean win = false;
		
		if(System.currentTimeMillis() - start_time > delayms){light = false;}
		
		/*
		player.collX(0);
		player.collX(viewport.width);
		player.collY(100);
		player.collY(viewport.height);
		*/
		
		for(Entity p : powerUp){
			if(Direction.left == player.collision(p.hitbox)){
				powerGet.setVolume(powerGet.play(), (float).1);
				start_time = System.currentTimeMillis();
				toRemoveP.add(p);
				light = true;
				}
			if(light){
				//p.undPanzer(true);
			}
			else{
				//p.undPanzer(false);
			}
		}
		for(Entity w : walls){
			if(light){
				//w.undPanzer(true);
			}
			else{
				//w.undPanzer(false);
			}
		//	player.collision(w);
		}
		for(Entity t : trapDoors){
			if(light){
				//t.undPanzer(true);
			}
			else{
				//t.undPanzer(false);
			}
		//	if(player.collision(t)){
		//		levelEnd();
		//		return 2;
		//	}
		}
	//	Vector2 exitDist = new Vector2(exit.x - player.sprite.getX(), exit.y - player.sprite.getY());
		Vector2 exitNorm = new Vector2(viewport.width, viewport.height);
	//	float volume = ((float)exitNorm.len()/(float)32.0)/(float)exitDist.len();
		//if(volume > 0.6) text.loadMusic();
	//	if(volume > 1) volume = (float)1;
	//	exitTunes.setVolume(volume);
		if(light){
	//		exit.undPanzer(true);
		}
		else{
		//	exit.undPanzer(false);
		}
		//win = player.collision(exit);
		if(win){
			levelEnd();
			return 1;
		}
		player.update(delta);
		return 0;
	}
	
	public void render(SpriteBatch batch){
		
		for(Iterator<Entity> i = toRemoveP.iterator(); i.hasNext();){
			powerUp.remove(i.next());
			i.remove();
		}
		
		
		for(Entity p : powerUp){
			p.render(batch);
		}
		for(Entity w : walls){
			w.render(batch);
		}
		for(Entity t : trapDoors){
			t.render(batch);
		}
		exit.render(batch);
		player.sprite.draw(batch);
	}
	
}
