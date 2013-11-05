

import java.util.Iterator;
import java.util.LinkedList;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;



public class Level {
	
	AssetManager manager;
	Music bgm;
	WallGenerator gen;
	LinkedList<Entity> walls, toAdd, toRemove;
	Player player;
	float startX, startY;
	float levelSpeed;
	Rectangle viewport;
	
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
	}
	
	public void makeWall(String tex, float x, float y, float width, float height, String color){
		Entity wall = new Entity(tex, (float)x, (float)y, (float)width, (float)height, color);
		toAdd.add(wall);
		
	}	
		
	
	public void makeWall(String tex, Rectangle r, String color){
		Entity wall = new Entity(tex, r.x, r.y, r.width, r.height, color);
		toAdd.add(wall);
	}
	public void setStartPos(float x, float y){
		startX = x;
		startY = y;
	}
	
	public int levelStart(float delta){
		player.setPosition(startX, startY);
		return update(delta);
	}
	public void levelEnd(){
		
	}
	
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
	
	public void render(SpriteBatch batch){
		for(Entity w : walls){
			w.render(batch);
		}
		player.render(batch);
	}

}
