import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Player extends Entity implements InputProcessor{
	AssetManager manager;
	static Texture playerTex, playerTexLeft, playerTexRight;
	//Sprite sprite;
	float maxSpeed;
	//float scaleSpeed;
	boolean jumping, down, left, right;
	
	Vector2 grav, jump;
	//float scale = 1;
	
	protected Player(String imgLoc, float x, float y, float width, float height) {
		
		super(imgLoc, x, y, width, height);
		//sprite.setScale((float)1);
		
		//scale = 1;
		jumping = false;
		down = false;
		left = false;
		right = false;
		maxSpeed = 400f;
		grav = new Vector2(0, -10);
		jump = new Vector2(0, 300);
		
	}
	
	//public void scale(float factor){
	//	scaleSpeed = maxSpeed / scale;
		
	//}
	
	
	public void enableJump(){
		jumping = false;
	}
	
	public void update(float delta){
		velocity.add(grav);
		
		/*
		if(down && velocity.y >= 0){
			//velocity = new Vector2((float)0, -maxSpeed);
			velocity.add(0, -maxSpeed);
		}
		*/
		if(left && velocity.x >= 0){
			//velocity = new Vector2(-maxSpeed, (float)0);
			velocity.add(-100, 0);
		}
		if(right && velocity.x <= 0){
			//velocity = new Vector2(maxSpeed, (float)0);
			velocity.add(100, 0);
		}
		
		
		//if(velocity.len() > maxSpeed){
		//	velocity.nor().mul(maxSpeed);
		//}
		
		sprite.translate(velocity.x * delta, velocity.y * delta);
		
		//hitbox.x = sprite.getX()+sprite.getWidth()/2 -hitbox.width/2;
		//hitbox.y = sprite.getY()+sprite.getHeight()/2 -hitbox.height/2;
		hitbox.x = sprite.getX();
		hitbox.y = sprite.getY();
	}
	
	public boolean collision(Rectangle r){
		float horiz = hitbox.getWidth();//*sprite.getScaleX()/2;
		float vert = hitbox.getHeight();//*sprite.getScaleY()/2;
		int buffer = 10;
		
		if(hitbox.overlaps(r) || hitbox.contains(r)){
			if(velocity.x < 0 && hitbox.x - r.x > 0 && hitbox.x - r.x < r.width
					&& !( (hitbox.y - r.y > 0 && hitbox.y - r.y <= r.height && hitbox.y - r.y >= r.height - buffer)
					|| (r.y + r.height - hitbox.y > 0 && r.y - hitbox.y <= hitbox.height && r.y - hitbox.y >= hitbox.height + buffer) )
					){
				sprite.setPosition(r.x + r.getWidth(), sprite.getY());
				velocity.x = 0;
				enableJump();
			}
			if(velocity.x > 0 && r.x + r.width - hitbox.x > 0 && r.x - hitbox.x < hitbox.width
					&& !( (hitbox.y - r.y > 0 && hitbox.y - r.y <= r.height && hitbox.y - r.y >= r.height - buffer)
					|| (r.y + r.height - hitbox.y > 0 && r.y - hitbox.y <= hitbox.height && r.y - hitbox.y >= hitbox.height + buffer) ) 
					){
				sprite.setPosition(r.x - horiz, sprite.getY());
				velocity.x = 0;
				enableJump();
			}
			if(velocity.y < 0 && hitbox.y - r.y > 0 && hitbox.y - r.y < r.height
					&& !((hitbox.x - r.x > 0 && hitbox.x - r.x <= r.width && hitbox.x - r.x >= r.width - buffer)
					|| (r.x + r.width - hitbox.x > 0 && r.x - hitbox.x <= hitbox.width && r.x - hitbox.x >= hitbox.width + buffer)					)
					){
				sprite.setPosition(sprite.getX(), r.y + r.getHeight());
				velocity.y = 0;
				enableJump();
			}
			if(velocity.y > 0 && r.y + r.height - hitbox.y > 0 && r.y - hitbox.y < hitbox.height
					&& !((hitbox.x - r.x > 0 && hitbox.x - r.x <= r.width && hitbox.x - r.x >= r.width - buffer)
					|| (r.x + r.width - hitbox.x > 0 && r.x - hitbox.x <= hitbox.width && r.x - hitbox.x >= hitbox.width + buffer)					)
					){
				sprite.setPosition(sprite.getX(), r.y - vert);
				velocity.y = 0;
			}
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		if(!jumping && (keycode == Keys.W || keycode == Keys.SPACE)){
			velocity.add(jump);
			jumping = true;
			return true;
		}
		if(keycode == Keys.A){
			left = true;
			return true;
		}
		if(keycode == Keys.S){
			down = true;
			return true;
		}
		if(keycode == Keys.D){
			right = true;
			return true;
		}
		return false;
	}

	@Override
	public boolean keyTyped(char arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.A){
			left = false;
			velocity.x = 0;
			//velocity.sub((float)Math.cos(velocity.angle())*maxSpeed, (float)0);
			return true;
		}
		if(keycode == Keys.S){
			down = false;
			//velocity.mul((float)0);
			//velocity.sub((float)0, (float)Math.sin(velocity.angle())*maxSpeed);
			return true;
		}
		if(keycode == Keys.D){
			right = false;
			velocity.x = 0;
			//velocity.sub((float)Math.cos(velocity.angle())*maxSpeed, (float)0);
			return true;
		}
		return false;
	}
	
	@Override
	public boolean mouseMoved(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
