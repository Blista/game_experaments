
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
	String color;
	
	protected Entity(String imgLoc, float x, float y, float width, float height, String color) {
		manager = new AssetManager();
		manager.load(imgLoc, Texture.class);
		manager.finishLoading();
		
		entityTex = manager.get(imgLoc, Texture.class);
		
		velocity = new Vector2();
		
		sprite = new Sprite(entityTex);//
		sprite.setX(x);
		sprite.setY(y);
		sprite.setSize(width, height);
		sprite.setOrigin(0,0);
		//sprite.setOrigin(sprite.getWidth()/2, sprite.getHeight()/2);
		//sprite.setScale((float)0.1);
		
		hitbox = new Rectangle();
		//hitbox.setX(x);
		//hitbox.setY(y);
		hitbox.setWidth(width);//sprite.getWidth()*sprite.getScaleX());
		hitbox.setHeight(height);//sprite.getHeight()*sprite.getScaleY());
		this.color = color;
	}
	
	
	public void setPosition(float x, float y){
		//float horiz = x - sprite.getWidth()/2 + sprite.getWidth()*sprite.getScaleX()/2;
		//float vert = y - sprite.getHeight()/2 + sprite.getHeight()*sprite.getScaleY()/2;
		
		sprite.setPosition(x, y);
	}
	
	public void update(float delta){
		//hitbox.x = sprite.getX()+sprite.getWidth()/2 -hitbox.width/2;
		//hitbox.y = sprite.getY()+sprite.getHeight()/2 -hitbox.height/2;
		hitbox.x = sprite.getX();
		hitbox.y = sprite.getY();
	}
	
	public void render(SpriteBatch batch){
		sprite.draw(batch);
	}
	public void setColor(String colorParam)
	{
		color = colorParam;
	}
	public String getColor()
	{
		return color;
	}
	
}