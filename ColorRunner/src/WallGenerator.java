import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;


public class WallGenerator {

	LinkedList<String> textures;
	//LinkedList<Rectangle> history;
	
	Random rand;
	Level lev;
	float nextDist;
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
		}else if(wait && (lev.viewport.x + lev.viewport.width) - (wall.sprite.getX() + wall.sprite.getWidth()) > nextDist){
			//System.out.println(wall.sprite.getX());
			wait = false;
			
		}
		
		long time = System.currentTimeMillis();
		if(!wait){///lev.levelSpeed){
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
				}else if((lev.viewport.y + lev.viewport.height - old.y + old.height + range/2) < (lev.player.sprite.getHeight()*3)){
					width = 100;
					height = 20;
					//y =  lev.viewport.y + lev.viewport.height - rand.nextFloat()*(range) - (lev.player.sprite.getHeight()*3);
					y =  rand.nextFloat()*(lev.viewport.y + lev.viewport.height - (lev.player.sprite.getHeight()*3));
				}else{
					width = 100;
					height = 20;
					//y = rand.nextFloat()*(range) + old.y + old.height - range/2;
					y = rand.nextFloat()*(old.y + old.height + range/2);
				}
				
				if(y < 0){
					y = 0;
				}
				
				//history.removeFirst();
			}
			r = new Rectangle(x, y, width, height);
			
			
			//.5s to 3s
			float scale = (y / (range/(float)2.0)); 
			if(scale > 1){ scale = 1;}
			
			nextDist = rand.nextFloat()*(scale*100 + 100);
			System.out.println(nextDist);
			
			lev.makeWall(texts[0], r, "white");
		}
	}
}
