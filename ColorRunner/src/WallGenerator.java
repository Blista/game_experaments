import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;


public class WallGenerator {

	LinkedList<String> textures;
	//LinkedList<Rectangle> history;
	
	Random rand;
	Level lev;
	long lastTime, nextTime;
	boolean wait;
	
	public WallGenerator(Level lev){
		textures = new LinkedList<String>();
		//history = new LinkedList<Rectangle>();
		this.lev = lev;
		wait = false;
		//lastTime = System.currentTimeMillis() - 20000;
	}
	
	public void addTexString(String tex){
		textures.add(tex);
	}
	
	public void wallGen(){
		rand = new Random();
		String[] texts = textures.toArray(new String[0]);
		int texlen = texts.length;
		Entity wall = lev.walls.peekLast();
		Rectangle old = null;
		if(wall != null){
			old = wall.hitbox;
		}
		
		if(old == null){
			System.out.println("once");
			wait = false;
		}else if(wait && (lev.viewport.x + lev.viewport.width) > (wall.sprite.getX() + wall.sprite.getWidth())){
			//System.out.println(wall.sprite.getX());
			wait = false;
			lastTime = System.currentTimeMillis();
			
		}
		
		long time = System.currentTimeMillis();
		if(!wait && time - lastTime > nextTime){///lev.levelSpeed){
			wait = true;
			float x, y, width, height;
			float range = 300;
			Rectangle r;
			
			x = lev.viewport.x + lev.viewport.width + 1;
			
			if(old == null){
				x = lev.player.sprite.getX() - 200;
				y = lev.player.sprite.getY() - 20;
				width = 800;
				height = 20;
			}else{
				if(old.height > 100){
					y = old.y - old.height/2;
					height = old.height;
					width = 100;
				}else if((lev.viewport.y + lev.viewport.height - old.y - old.height - range/2) < (lev.player.sprite.getHeight()*3)){
					width = 100;
					height = 20;
					y = rand.nextFloat()*(range) + lev.viewport.y + lev.viewport.height - range - (lev.player.sprite.getHeight()*3);

				}else{
					width = 100;
					height = 20;
					y = rand.nextFloat()*(range) + old.y + old.height - range/2;				
				}
				
				
				//history.removeFirst();
			}
			r = new Rectangle(x, y, width, height);
			//history.add(r);
			
			//.5s to 3s
			nextTime = (long)(rand.nextFloat()*((y+height)/(lev.viewport.y+lev.viewport.height))*2500 + 500);
			System.out.println(nextTime);
			
			lev.makeWall(texts[0], r);
		}
	}
}
