import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Entity {
	AssetManager manager;
	static Texture entityTex;
	Sprite sprite;
	Vector2 velocity;
	//float scale = 1;
	//Circle hitbox;
	Rectangle hitbox;
	
	protected Entity(String imgLoc, float x, float y, float width, float height) {
		manager = new AssetManager();
		manager.load(imgLoc, Texture.class);
		manager.finishLoading();
		
		entityTex = manager.get(imgLoc, Texture.class);
		
		velocity = new Vector2();
		
		sprite = new Sprite(entityTex, (int)x, (int)y, (int)width, (int)height);
		sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setScale((float)0.1);
		
		hitbox = new Rectangle();
		hitbox.setWidth(sprite.getWidth()*sprite.getScaleX());
		hitbox.setHeight(sprite.getHeight()*sprite.getScaleY());
	}
	
	
	public void setPosition(float x, float y){
		float horiz = x - sprite.getWidth()/2 + sprite.getWidth()*sprite.getScaleX()/2;
		float vert = y - sprite.getHeight()/2 + sprite.getHeight()*sprite.getScaleY()/2;
		sprite.setPosition(horiz, vert);
	}
	
	public void update(float delta){
		
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
	}
	
	
	/*
	public Direction collision(Rectangle r){
		if(hitbox.overlaps(r) || hitbox.contains(r)){
			if(velocity.x < 0 && hitbox.x - r.x >= 0 && hitbox.x - r.x <= r.width){
				if(velocity.y < 0 && hitbox.y - r.y >= 0 && hitbox.y - r.y <= r.height){
					return Direction.leftDown;
				}
				if(velocity.y > 0 && r.y - hitbox.y >= 0 && r.y - hitbox.y <= hitbox.height){
					return Direction.leftUp;
				}
				return Direction.left;
			}
			if(velocity.x > 0 && r.x - hitbox.x >= 0 && r.x - hitbox.x <= hitbox.width){
				if(velocity.y < 0 && hitbox.y - r.y >= 0 && hitbox.y - r.y <= r.height){
					return Direction.rightDown;
				}
				if(velocity.y > 0 && r.y - hitbox.y >= 0 && r.y - hitbox.y <= hitbox.height){
					return Direction.rightUp;
				}
				return Direction.right;
			}
			if(velocity.y < 0 && hitbox.y - r.y >= 0 && hitbox.y - r.y <= r.height){
				return Direction.down;
			}
			if(velocity.y > 0 && r.y - hitbox.y >= 0 && r.y - hitbox.y <= hitbox.height){
				return Direction.up;
			}
			
		}
		return Direction.still;
	}
	*/
	/*
	public void collX(float x){
		if(left && hitbox.x+hitbox.width - x >= 0 && hitbox.x - x <= 0){
			left = false;
			sprite.setPosition(sprite.getX()+2, sprite.getY());
			velocity.mul((float)0);
		}
		if(right && x - hitbox.x >= 0 && x - hitbox.x <= hitbox.width){
			right = false;
			sprite.setPosition(sprite.getX()-2, sprite.getY());
			velocity.mul((float)0);
		}
		
		//if(hitbox.x - x > 0 && hitbox.x - x < hitbox.radius){
		//	left = false;
		//}
		//if(x - hitbox.x > 0 && x - hitbox.x < hitbox.radius){
		//	right = false;
		//}		
	}
	
	public void collY(float y){
		if(down && hitbox.y+hitbox.height/2 - y > 0 && hitbox.y - y <= 0){
			down = false;
			sprite.setPosition(sprite.getX(), sprite.getY()+2);
			velocity.mul((float)0);
		}
		if(up && y - hitbox.y >= 0 && y - hitbox.y <= hitbox.height){
			up = false;
			sprite.setPosition(sprite.getX(), sprite.getY()-2);
			velocity.mul((float)0);
		}
		
		//if(hitbox.y - y > 0 && hitbox.y - y < hitbox.radius){
		//	down = false;
		//}
		//if(y - hitbox.y > 0 && y - hitbox.y < hitbox.radius){
		//	up = false;
		//}
	}	
	*/
}