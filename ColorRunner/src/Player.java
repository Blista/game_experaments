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
	Sprite sprite;
	float maxSpeed;
	//float scaleSpeed;
	boolean jumping, down, left, right;
	Vector2 velocity;
	Vector2 grav, jump;
	//float scale = 1;
	//Circle hitbox;
	Rectangle hitbox;
	
	protected Player(String imgLoc) {
		
		super(imgLoc);
		sprite.setScale((float)0.1);
		
		velocity = new Vector2();
		//scale = 1;
		jumping = false;
		down = false;
		left = false;
		right = false;
		maxSpeed = 100f;
		grav = new Vector2(0, -50);
		jump = new Vector2(0, 300);
		
		//hitbox = new Circle(sprite.getX(), sprite.getY(), Math.max(sprite.getWidth(), sprite.getHeight()));
		//hitbox = new Rectangle(sprite.getOriginX()-sprite.getWidth()*sprite.getScaleX()/2, sprite.getOriginY()-sprite.getHeight()*sprite.getScaleX()/2, sprite.getWidth()*sprite.getScaleX(), sprite.getHeight()*sprite.getScaleX());
		hitbox = new Rectangle();
		hitbox.setWidth(sprite.getWidth()*sprite.getScaleX());
		hitbox.setHeight(sprite.getHeight()*sprite.getScaleY());
	}
	
	//public void scale(float factor){
	//	scaleSpeed = maxSpeed / scale;
		
	//}
	
	public void setPosition(float x, float y){
		float horiz = x - sprite.getWidth()/2 + sprite.getWidth()*sprite.getScaleX()/2;
		float vert = y - sprite.getHeight()/2 + sprite.getHeight()*sprite.getScaleY()/2;
		sprite.setPosition(horiz, vert);
	}
	
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
			velocity.add(-maxSpeed, 0);
		}
		if(right && velocity.x <= 0){
			//velocity = new Vector2(maxSpeed, (float)0);
			velocity.add(maxSpeed, 0);
		}
		
		
		if(velocity.len() > maxSpeed){
			velocity.nor().mul(maxSpeed);
		}
		
		sprite.translate(velocity.x * delta, velocity.y * delta);
		
		hitbox.x = sprite.getX()+sprite.getWidth()/2 -hitbox.width/2;
		hitbox.y = sprite.getY()+sprite.getHeight()/2 -hitbox.height/2;
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
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
			velocity.mul((float)0);
			//velocity.sub((float)Math.cos(velocity.angle())*maxSpeed, (float)0);
			return true;
		}
		if(keycode == Keys.S){
			down = false;
			velocity.mul((float)0);
			//velocity.sub((float)0, (float)Math.sin(velocity.angle())*maxSpeed);
			return true;
		}
		if(keycode == Keys.D){
			right = false;
			velocity.mul((float)0);
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
