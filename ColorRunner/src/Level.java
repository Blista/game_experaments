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
	
	LinkedList<Entity> walls;
	Player player;
	float startX, startY;
	
	protected Level(Player player, Rectangle viewport){
		this.player = player;
		walls = new LinkedList<Entity>();
	}
	
	public void makeWall(String tex, float x, float y, float width, float height){
		Entity wall = new Entity(tex, (float)x, (float)y, (float)width, (float)height);
		walls.add(wall);
		
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
		
		for(Entity w : walls){
			player.update(delta);
			w.update(delta);
			player.collision(w.hitbox);
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
