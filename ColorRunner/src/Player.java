import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;




public class Player extends Entity implements InputProcessor{
	AssetManager manager;
	static Texture playerTex, red, yellow, blue, orange, purple, green, black, white;
	//Sprite sprite;
	float maxSpeed, horizAccel;
	long timer;
	//float scaleSpeed;
	boolean canJump, down, left, right, canWallJumpLeft, canWallJumpRight;
	boolean redB, yellowB, blueB, acceleration;
	Vector2 grav, jump, slide, wallJumpLeft, wallJumpRight;
	final static int BOX4C = 21;
	Rectangle[] botCollision, topCollision, rightCollision, leftCollision;
	String color = "white";
	
	//float scale = 1;
	
	protected Player(String imgLoc, float x, float y, float width, float height) {
		
		super(imgLoc, x, y, width, height);
		//sprite.setScale((float)1);
		//scale = 1;
		canJump = false;
		timer = 0;
		down = false;
		left = false;
		right = false;
		acceleration = true;

		maxSpeed = 200;
		horizAccel = 1000;
		grav = new Vector2(0, -980);
		slide = new Vector2(0, 6);
		jump = new Vector2(0, 500);
		wallJumpLeft = new Vector2(200,500);
		wallJumpRight= new Vector2(-200,500);
		
		manager = new AssetManager();
		manager.load("res/red.png", Texture.class);
		manager.load("res/yellow.png", Texture.class);
		manager.load("res/blue.png", Texture.class);
		manager.load("res/orange.png", Texture.class);
		manager.load("res/purple.png", Texture.class);
		manager.load("res/green.png", Texture.class);
		manager.load("res/black.png", Texture.class);
		manager.load("res/CharacterImage.png", Texture.class);
		manager.finishLoading();
		
		red = manager.get("res/red.png", Texture.class);
		yellow = manager.get("res/yellow.png", Texture.class);
		blue  = manager.get("res/blue.png", Texture.class);
		orange = manager.get("res/orange.png", Texture.class);
		purple = manager.get("res/purple.png", Texture.class);
		green = manager.get("res/green.png", Texture.class);
		black = manager.get("res/black.png", Texture.class);
		white = manager.get("res/CharacterImage.png", Texture.class);
		
		
		/*Sprite redSprite = new Sprite(manager.get("res/red.png", Texture.class));
		Sprite yellowSprite = new Sprite(manager.get("res/yellow.png", Texture.class));
		Sprite blueSprite = new Sprite(manager.get("res/blue.png", Texture.class));
		Sprite orangeSprite = new Sprite(manager.get("res/orange.png", Texture.class));
		Sprite purpleSprite = new Sprite(manager.get("res/purple.png", Texture.class));
		Sprite greenSprite = new Sprite(manager.get("res/green.png", Texture.class));
		Sprite blackSprite = new Sprite(manager.get("res/black.png", Texture.class));*/
		
		initCollBoxes(x,y,width,height);		
	}
	
	//public void scale(float factor){
	//	scaleSpeed = maxSpeed / scale;
		
	//}
	
	
	public void update(float delta){
		velocity.add(grav.x *delta, grav.y * delta);
		
		/*
		if(down && velocity.y >= 0){
			//velocity = new Vector2((float)0, -maxSpeed);
			velocity.add(0, -maxSpeed);
		}
		*/
		delayMovement(timer);
		if(System.currentTimeMillis() - timer >= 100)
		{
			if(left && velocity.x > -maxSpeed){
				//velocity = new Vector2(-maxSpeed, (float)0);
				velocity.add(-horizAccel*delta, 0);
			}else if(!left && velocity.x < 0){
				velocity.add(horizAccel*delta, 0);
				if(velocity.x > 0){
					velocity.x = 0;
				}
			}else if(velocity.x < -maxSpeed){
				velocity.x = -maxSpeed;
			}
			if(right && velocity.x < maxSpeed){
				//velocity = new Vector2(maxSpeed, (float)0);
				velocity.add(horizAccel*delta, 0);
			}else if(!right && velocity.x > 0){
				velocity.add(-horizAccel*delta, 0);
				if(velocity.x < 0){
					velocity.x = 0;
				}
			}else if(velocity.x > maxSpeed){
				velocity.x = maxSpeed;
			}		
			
			if(velocity.y < -800){
				velocity.y = -800;
			}
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
		changeColor();
	}
	
	public void collision(Rectangle r, Direction dir){
		float height = sprite.getHeight();
		float width = sprite.getWidth();
		float buffer = (float) 0;
		System.out.println(dir);
		
		if(dir == Direction.up){
			sprite.setPosition(sprite.getX(), r.y - height + buffer);
			velocity.y = 0;
		}else if (dir == Direction.rightUp){
			sprite.setPosition(r.x - width + buffer, r.y - height + buffer);
//			velocity.x = 0;
			velocity.y = 0;
		}else if (dir == Direction.right){
			sprite.setPosition(r.x - width + buffer, sprite.getY());
//			velocity.x = 0;
		}else if (dir == Direction.rightDown){
			sprite.setPosition(r.x - width + buffer, r.y + r.getHeight()- buffer);
//			velocity.x = 0;
			velocity.y = 0;
		}else if (dir == Direction.down){
			sprite.setPosition(sprite.getX(), r.y + r.getHeight()-buffer);
			velocity.y = 0;
		}else if (dir == Direction.leftDown){
			sprite.setPosition(r.x + r.getWidth()-buffer, r.y + r.getHeight()+buffer);
//			velocity.x = 0;
			velocity.y = 0;
		}else if (dir == Direction.left){
			sprite.setPosition(r.x + r.getWidth()-buffer, sprite.getY());
//			velocity.x = 0;
		}else if (dir == Direction.leftUp){
			sprite.setPosition(r.x + r.getWidth()-buffer, r.y - height+buffer);
//			velocity.x = 0;
			velocity.y = 0;
		}
	}
	
	@Override
	public boolean keyDown(int keycode) {
		
		if((keycode == Keys.W || keycode == Keys.SPACE)){
			if(canJump){
				velocity.add(jump);
			}
			else if (canWallJumpLeft)
			{
				velocity.x = wallJumpLeft.x;
				velocity.y = wallJumpLeft.y;
				acceleration = false;
				timer = System.currentTimeMillis();
			}
			else if(canWallJumpRight)
			{
				velocity.x = wallJumpRight.x;
				velocity.y = wallJumpRight.y;
				acceleration = false;
				timer = System.currentTimeMillis();
			}
			canJump = false;
			canWallJumpLeft = false;
			canWallJumpRight = false;
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
		if(keycode == Keys.I)
		{
			redB = true;
			return true;
		}
		if(keycode == Keys.O)
		{
			yellowB = true; 
			return true;
		}
		if(keycode == Keys.P)
		{
			blueB = true;
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
			return true;
		}
		if(keycode == Keys.S){
			down = false;
			return true;
		}
		if(keycode == Keys.D){
			right = false;
			return true;
		}
		if(keycode == Keys.I)
		{
			redB = false;
			return true;
		}
		if(keycode == Keys.O)
		{
			yellowB = false; 
			return true;
		}
		if(keycode == Keys.P)
		{
			blueB = false;
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
	public void changeColor()
	{
		if(!redB && !yellowB && !blueB)
		{
			sprite.setTexture(white);
			color = "white";
		}
		else if(redB && yellowB && blueB)
		{
			sprite.setTexture(black);
			color = "black";
		}
		else if(redB && yellowB)
		{
			sprite.setTexture(orange);
			color = "orange";
		}
		else if(redB && blueB)
		{
			sprite.setTexture(purple);
			color = "purple";
		}
		else if(yellowB && blueB)
		{
			sprite.setTexture(green);
			color = "green";
		}
		else if(redB)
		{
			sprite.setTexture(red);
			color = "red";
		}
		else if(yellowB)
		{
			sprite.setTexture(yellow);
			color = "yellow";
		}
		else if(blueB)
		{
			sprite.setTexture(blue);
			color = "blue";
		}
	}
	public void canJump(Direction dir)
	{
		canJump = false;
		canWallJumpLeft = false;
		canWallJumpRight = false;
		
		if(dir == Direction.down)
		{
			canJump = true;
		}
		else if(dir == Direction.left)
		{
			canWallJumpLeft = true;
		}
		else if(dir == Direction.right)
		{
			canWallJumpRight = true;
		}
	}

	public void delayMovement(float timer)
	{
		if(System.currentTimeMillis() - timer >= 100)
			acceleration = true;
	}
}
