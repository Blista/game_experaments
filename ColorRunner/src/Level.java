

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;



public class Level {
	
	LinkedList<Entity> walls, toAdd, toRemove;
	LinkedList<String> textures;
	Player player;
	float startX, startY;
	float levelSpeed;
	Random rand, rand2;
	Rectangle viewport;
	long lastTime;
	float lastWallHeight;
	
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
	
	public void makeWall(String tex, float x, float y, float width, float height, String color){
		Entity wall = new Entity(tex, (float)x, (float)y, (float)width, (float)height, color);
		toAdd.add(wall);
		
	}	
	
	public void addTexString(String tex){
		textures.add(tex);
	}
	
	public void wallGen(){
		rand = new Random();
		String[] texts = textures.toArray(new String[0]);
		//int texlen = texts.length;
		
		makeWall(texts[0], viewport.x+viewport.width, rand.nextFloat()*200, 100, 10, "white");
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
		long time = System.currentTimeMillis();
		
		player.update(delta);
		if(time - lastTime > 150000/levelSpeed){
			lastTime = time;
			wallGen();
		}
		
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
					player.collision(w.hitbox, dir);
				}
			}
			else
			{
				player.collision(w.hitbox, dir);
			}
			
			if(bestDir != Direction.down){
				if(dir == Direction.down || (bestDir != Direction.left && bestDir != Direction.right)){
					player.canJump(dir);
					bestDir = dir;
				}
			}
			
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
		
		//if(!viewport.contains(player.hitbox) && !viewport.overlaps(player.hitbox))
		if(player.sprite.getX() + player.sprite.getWidth() < viewport.x || player.sprite.getY() + player.sprite.getHeight() + 10 < viewport.y)
		{
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
