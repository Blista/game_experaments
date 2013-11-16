import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Rectangle;


public class WallGenerator {

	LinkedList<String> textures;
	
	Random rand;
	Level lev;
	float nextDist;
	boolean wait;
	
	public WallGenerator(Level lev){
		textures = new LinkedList<String>();
		this.lev = lev;
		wait = false;
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
		
		if(!wait){
			rand = new Random();
			LinkedList<Entity>[] pregens = (LinkedList<Entity>[]) (lev.pregens.toArray(new LinkedList[0]));
			
			wait = true;
			float x, y = 0, width, height = 0;
			float range = 300;
			Rectangle r;
			
			x = lev.viewport.x + lev.viewport.width + 1;
			
			if(old == null){
				x = lev.player.sprite.getX() - 200;
				y = lev.player.sprite.getY() - 60;
				width = 800;
				height = 20;
				r = new Rectangle(x, y, width, height);
				lev.makeWall(texts[0], r, "white");
			}else{
				if(rand.nextInt(5) == 3){
					LinkedList<Entity> nextGens = pregens[rand.nextInt(pregens.length)];
					
					while(nextGens.peekFirst() == null || nextGens.peekFirst().sprite.getX() + nextGens.peekFirst().sprite.getHeight() > range / 2.0){
						nextGens = pregens[rand.nextInt(pregens.length)];
					}
					
					if(nextGens.peekFirst() != null){
						Iterator<Entity> iter = nextGens.listIterator(0);
						
						while(iter.hasNext()){
							Entity template = iter.next();
							Entity nextWall = new Entity(template.entityTex, template.sprite.getX(), template.sprite.getY(), template.sprite.getWidth(), template.sprite.getHeight(), template.getColor());
							
							if(nextWall.getColor() != "white"){
								//nextWall.setColor(lev.color[rand.nextInt(8)]);
							}
							nextWall.setPosition(nextWall.sprite.getX() + lev.viewport.x + lev.viewport.width, nextWall.sprite.getY());
							lev.makeWall(nextWall);
							y = nextWall.hitbox.y;
							height = nextWall.hitbox.height;
						}
					}
				}
				else{
					/*if(old.height > 100){
						y = old.y - old.height/2;
						height = old.height;
						width = 100;
					}else*/ if((lev.viewport.y + lev.viewport.height - old.y - old.height - range/2 - 20) < (lev.player.sprite.getHeight()*3)){
						width = 100;
						height = 20;
						//y =  lev.viewport.y + lev.viewport.height - rand.nextFloat()*(range) - (lev.player.sprite.getHeight()*3);
						y =  rand.nextFloat()*(lev.viewport.y + lev.viewport.height - (lev.player.sprite.getHeight()*3 - 20));
					}else{
						width = 100;
						height = 20;
						//y = rand.nextFloat()*(range) + old.y + old.height - range/2;
						y = rand.nextFloat()*(old.y + old.height + range/2);
					}
					
					if(y < 0){
						y = 0;
					}
					
					r = new Rectangle(x, y, width, height);
					
					lev.makeWall(texts[0], r, "white");
				}
			}
			
			float scale = ((y+height) / (range/(float)2.0)); 
			if(scale > 1){ scale = 1;}
			
			nextDist = rand.nextFloat()*(300 - scale*100);
			//System.out.println(nextDist);
			

		}
	}
}
