import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;



public class Level {
	
	LinkedList<Entity> walls, toAdd, toRemove;
	LinkedList<String> textures;
	Player player;
	float startX, startY;
	float levelSpeed;
	Random rand;
	Rectangle viewport;
	long lastTime;
	
	protected Level(Player player, Rectangle viewport){
		this.player = player;
		walls = new LinkedList<Entity>();
		toAdd = new LinkedList<Entity>();
		toRemove = new LinkedList<Entity>();
		textures = new LinkedList<String>();
		levelSpeed = 100;
		this.viewport = viewport;
		lastTime = System.currentTimeMillis();
	}
	
	public void makeWall(String tex, float x, float y, float width, float height){
		Entity wall = new Entity(tex, (float)x, (float)y, (float)width, (float)height);
		toAdd.add(wall);
		
	}	
	
	public void addTexString(String tex){
		textures.add(tex);
	}
	
	public void wallGen(){
		rand = new Random();
		String[] texts = textures.toArray(new String[0]);
		int texlen = texts.length;
		
		makeWall(texts[0], viewport.x+viewport.width, rand.nextFloat()*200, 100, 10);
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
		long time = System.currentTimeMillis();
		
		if(time - lastTime > 200000/levelSpeed){
			lastTime = time;
			wallGen();
		}
		
		for(Entity w : walls){
			w.velocity.x = -levelSpeed;
			w.sprite.translate(w.velocity.x * delta, w.velocity.y * delta);
			player.update(delta);
			w.update(delta);
			//player.alignDirection(w.hitbox);
			player.collision(w.hitbox, player.alignDirection(w.hitbox));
			
			if(w.hitbox.x + w.hitbox.width < 0){
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
		
		
		
		return 0;
	}
	
	public void render(SpriteBatch batch){
		for(Entity w : walls){
			w.render(batch);
		}
		player.render(batch);
	}
	
}
