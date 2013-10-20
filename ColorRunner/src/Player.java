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
	Vector2 grav, jump, slide;
	final static int BOX4C = 21;
	Rectangle[] botCollision, topCollision, rightCollision, leftCollision;
	
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
		grav = new Vector2(0, -5);
		slide = new Vector2(0, 6);
		jump = new Vector2(0, 300);
		/*Rectangle[] botCollision = new Rectangle[BOX4C];
		Rectangle[] topCollision = new Rectangle[BOX4C];
		Rectangle[] leftCollision = new Rectangle[BOX4C];
		Rectangle[] rightCollision = new Rectangle[BOX4C];
		
		float boxWidth = width / BOX4C;
		float boxHeight = height / BOX4C;
		
		for(int i = 0; i < BOX4C; i++)
		{
			botCollision[i] = new Rectangle(x + (boxWidth*i),y,boxWidth,height/2);
			topCollision[i] = new Rectangle(x + (boxWidth*i),y+height/2, boxWidth, height/2);
			leftCollision[i] = new Rectangle(x, y + (boxHeight*i), width/2, boxHeight);
			rightCollision[i] = new Rectangle(x + width/2, y + (boxHeight*i), width/2, boxHeight);
		}*/
		initCollBoxes(x,y,width,height);		
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
			velocity.add(-200, 0);
		}
		if(right && velocity.x <= 0){
			//velocity = new Vector2(maxSpeed, (float)0);
			velocity.add(200, 0);
		}
		
		
		//if(velocity.len() > maxSpeed){
		//	velocity.nor().mul(maxSpeed);
		//}
		
		sprite.translate(velocity.x * delta, velocity.y * delta);
		
		//hitbox.x = sprite.getX()+sprite.getWidth()/2 -hitbox.width/2;
		//hitbox.y = sprite.getY()+sprite.getHeight()/2 -hitbox.height/2;
		//hitbox.x = sprite.getX();
		//hitbox.y = sprite.getY();
		
		moveBox();
	}
	
	public void collision(Rectangle r, Direction dir){
		float height = sprite.getHeight();
		float width = sprite.getWidth();
		System.out.println(dir);
		
		if(dir == Direction.up){
			sprite.setPosition(sprite.getX(), r.y - height);
			velocity.y = 0;
		}else if (dir == Direction.rightUp){
			sprite.setPosition(r.x - width, r.y - height);
			velocity.x = 0;
			velocity.y = 0;
		}else if (dir == Direction.right){
			sprite.setPosition(r.x - width, sprite.getY());
			velocity.x = 0;
			enableJump();
		}else if (dir == Direction.rightDown){
			sprite.setPosition(r.x - width, r.y + r.getHeight());
			velocity.x = 0;
			velocity.y = 0;
		}else if (dir == Direction.down){
			sprite.setPosition(sprite.getX(), r.y + r.getHeight());
			velocity.y = 0;
			enableJump();
		}else if (dir == Direction.leftDown){
			sprite.setPosition(r.x + r.getWidth(), r.y + r.getHeight());
			velocity.x = 0;
			velocity.y = 0;
		}else if (dir == Direction.left){
			sprite.setPosition(r.x + r.getWidth(), sprite.getY());
			velocity.x = 0;
			enableJump();
		}else if (dir == Direction.leftUp){
			sprite.setPosition(r.x + r.getWidth(), r.y - height);
			velocity.x = 0;
			velocity.y = 0;
		}
		
		/*int buffer = 10;
		
		if(hitbox.overlaps(r) || hitbox.contains(r)){
			if(velocity.x < 0 && hitbox.x + hitbox.width - r.x - r.width > 0 && hitbox.x - r.x < r.width
					&& !((hitbox.y - r.y <= r.height && hitbox.y - r.y >= r.height - buffer)
					|| (r.y - hitbox.y <= hitbox.height && r.y - hitbox.y >= hitbox.height + buffer)) 
					){
				sprite.setPosition(r.x + r.getWidth(), sprite.getY());
				velocity.x = 0;
				enableJump();
			}
			if(velocity.x > 0 && hitbox.x - r.x < 0 && r.x - hitbox.x < hitbox.width
					&& !((hitbox.y - r.y <= r.height && hitbox.y - r.y >= r.height - buffer)
					|| (r.y - hitbox.y <= hitbox.height && r.y - hitbox.y >= hitbox.height + buffer)) 
					){
				sprite.setPosition(r.x - hitbox.width, sprite.getY());
				velocity.x = 0;
				enableJump();
			}
			if(velocity.y < 0 && hitbox.y + hitbox.height - r.y - r.height > 0 && hitbox.y - r.y < r.height
					&& !((hitbox.x - r.x <= r.width && hitbox.x - r.x >= r.width - buffer)
					|| (r.x - hitbox.x <= hitbox.width && r.x - hitbox.x >= hitbox.width + buffer))
					){
				sprite.setPosition(sprite.getX(), r.y + r.getHeight());
				velocity.y = 0;
				enableJump();
			}
			if(velocity.y > 0 && hitbox.y - r.y < 0 && r.y - hitbox.y < hitbox.height
					&& !((hitbox.x - r.x <= r.width && hitbox.x - r.x >= r.width - buffer)
					|| (r.x - hitbox.x <= hitbox.width && r.x - hitbox.x >= hitbox.width + buffer))
					){
				sprite.setPosition(sprite.getX(), r.y - hitbox.height);
				velocity.y = 0;
			}
			return true;
		}
		
		return false;*/
		
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
	public void initCollBoxes(float x,float y, float width, float height)
	{
		botCollision = new Rectangle[BOX4C];
		topCollision = new Rectangle[BOX4C];
		leftCollision = new Rectangle[BOX4C];
		rightCollision = new Rectangle[BOX4C];
		
		float boxWidth = width / BOX4C;
		float boxHeight = height / BOX4C;
		
		for(int i = 0; i < BOX4C; i++)
		{
			botCollision[i] = new Rectangle(x + (boxWidth*i),y,boxWidth,height/2);
			topCollision[i] = new Rectangle(x + (boxWidth*i),y+height/2, boxWidth, height/2);
			leftCollision[i] = new Rectangle(x, y + (boxHeight*i), width/2, boxHeight);
			rightCollision[i] = new Rectangle(x + width/2, y + (boxHeight*i), width/2, boxHeight);
		}
	}
	public int checkCollBoxes(Rectangle wall, Rectangle[] array)
	{
		int numColl = 0;
		for(int i = 0; i < BOX4C; i++)
		{
			if(array[i].contains(wall) || array[i].overlaps(wall))
			{
				numColl++;
			}
		}
		return numColl;
	}
	public Direction alignDirection(Rectangle wall)
	{
		int top = checkCollBoxes(wall, topCollision);
		int bot = checkCollBoxes(wall, botCollision);
		int left = checkCollBoxes(wall, leftCollision);
		int right = checkCollBoxes(wall, rightCollision);
		
		if(bot > left && bot > right)
		{
			return Direction.down;
		}
		else if(bot > left && bot == right)
		{
			return Direction.rightDown;
		}
		else if(bot == left && bot > right)
		{
			return Direction.leftDown;
		}
		
		else if(top > left && top > right)
		{
			return Direction.up;
		}
		else if(top > left && top == right)
		{
			return Direction.rightUp;
		}
		else if(top == left && top > right)
		{
			return Direction.leftUp;
		}
		
		else if(right > top && right > bot)
		{
			return Direction.right;
		}
		else if(right > top && right == bot)
		{
			return Direction.rightDown;
		}
		else if(right == top && right > bot)
		{
			return Direction.rightUp;
		}
		
		
		else if(left > top && left > bot)
		{
			return Direction.left;
		}
		else if(left > top && left == bot)
		{
			return Direction.leftDown;
		}
		else if(left == top && left > bot)
		{
			return Direction.leftUp;
		}
		return Direction.still;
	}
	
	public void moveBox()
	{
		float x = sprite.getX();
		float y = sprite.getY();
		float boxWidth = sprite.getWidth()/BOX4C;
		float boxHeight = sprite.getHeight()/BOX4C;
		float height = sprite.getHeight();
		float width = sprite.getWidth();
		
		for(int i = 0; i < BOX4C; i++)
		{
			botCollision[i].setX(x+(boxWidth*i));
			botCollision[i].setY(y);
			
			topCollision[i].setX(x + (boxWidth*i));
			topCollision[i].setY(y+ height/2);
			
			leftCollision[i].setX(x);
			leftCollision[i].setY(y + (boxHeight*i));
			
			rightCollision[i].setX(x + width/2);
			rightCollision[i].setY(y + (boxHeight*i));
		}
	}
}
