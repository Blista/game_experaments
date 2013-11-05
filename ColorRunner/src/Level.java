

import java.util.Iterator;
import java.util.LinkedList;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * 
 * @author Andrew
 * creates the walls of each level
 */
public class Level {
	
	//asset manager to create the music
	AssetManager manager;
	//the music object
	Music bgm;
	//object to generate walls that are within jumping distance
	WallGenerator gen;
	//linked list of walls. add before the viewport and to remove after exiting viewport
	LinkedList<Entity> walls, toAdd, toRemove;
	Player player;
	//starting x and y of the player
	float startX, startY;
	//the speed at which the level moves
	float levelSpeed;
	// rectangle to know the bounds of the screen
	Rectangle viewport;
	//array of all the different colors
	public static String[] color = new String[8];
	
	/**
	 * constructs all wall linked lists, players, manager, bgm, and color[]
	 * @param player
	 * @param viewport
	 */
	protected Level(Player player, Rectangle viewport){
		this.player = player;
		walls = new LinkedList<Entity>();
		toAdd = new LinkedList<Entity>();
		toRemove = new LinkedList<Entity>();
		levelSpeed = 100;
		this.viewport = viewport;
		gen = new WallGenerator(this);
		manager = new AssetManager();
		manager.load("res/livingTooLong.mp3", Music.class);
		manager.finishLoading();
		bgm = manager.get("res/livingTooLong.mp3", Music.class);
		bgm.play();
		bgm.setLooping(true);
		
		color[0] = "white";
		color[1] = "red";
		color[2] = "yellow";
		color[3] = "blue";
		color[4] = "orange";
		color[5] = "purple";
		color[6] = "green";
		color[7] = "black";
		
	}
	/**
	 * method to make a wall with these parameters
	 * @param tex
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param color
	 */
	public void makeWall(String tex, float x, float y, float width, float height, String color){
		Entity wall = new Entity(tex, (float)x, (float)y, (float)width, (float)height, color);
		toAdd.add(wall);
		
	}	
	/**
	 * method to make a wall with rectangle parameter
	 * @param tex
	 * @param r
	 * @param color
	 */
	public void makeWall(String tex, Rectangle r, String color){
		Entity wall = new Entity(tex, r.x, r.y, r.width, r.height, color);
		toAdd.add(wall);
	}
	/**
	 * sets starting position
	 * @param x
	 * @param y
	 */
	public void setStartPos(float x, float y){
		startX = x;
		startY = y;
	}
	
	/**
	 * starts the level
	 * @param delta
	 * @return update
	 */
	public int levelStart(float delta){
		player.setPosition(startX, startY);
		return update(delta);
	}
	/**
	 * updates the level
	 * @param delta
	 * @return 0; for no apparent reason
	 */
	public int update(float delta){
		Direction dir, bestDir;
		bestDir = Direction.still;
		
		player.update(delta);
		gen.wallGen();

		
		for(Entity w : walls){
			w.velocity.x = -levelSpeed;
			w.sprite.translate(w.velocity.x * delta, w.velocity.y * delta);
			player.update(0);
			w.update(delta);
			//player.alignDirection(w.hitbox);
			dir = player.alignDirection(w.hitbox);
			
			if(w.getColor().equals(player.getColor()))
			{
				if(player.getColor().equals("white"))
				{
					player.collision(w, dir);
				}
			}
			else
			{
				player.collision(w, dir);
			}
			
			if(bestDir != Direction.down){
				if(dir == Direction.down || (bestDir != Direction.left && bestDir != Direction.right)){
					player.canJump(dir);
					bestDir = dir;
				}
			}
			
			if(!viewport.contains(w.hitbox) && !viewport.overlaps(w.hitbox)){
				toRemove.add(w);
			}
		}
		
		for(Iterator<Entity> i = toRemove.iterator(); i.hasNext();){
			walls.remove(i.next());
			i.remove();
		}
		
		for(Iterator<Entity> i = toAdd.iterator(); i.hasNext();){
			walls.add(i.next());
			i.remove();
		}
		
		//if(!viewport.contains(player.hitbox) && !viewport.overlaps(player.hitbox))
		if(player.sprite.getX() + player.sprite.getWidth() < viewport.x || player.sprite.getY() + player.sprite.getHeight() + 10 < viewport.y)
		{
			bgm.stop();
			bgm.dispose();
			((Game) Gdx.app.getApplicationListener()).setScreen(new GameOver());
		}
		return 0;
	}
	/**
	 * renders everything on the level
	 * @param batch
	 */
	public void render(SpriteBatch batch){
		for(Entity w : walls){
			w.render(batch);
		}
		player.render(batch);
	}

}
