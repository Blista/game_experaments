

import tween.SpriteAccessor;
import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Splash implements Screen
{
	private Sprite splash;
	private SpriteBatch batch;
	private TweenManager tweenManager;

	@Override
	public void render(float delta)
	{
		//makes black background
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//updates the tween, creates the animation
		tweenManager.update(delta);
		
		//actually draw the batch
		batch.begin();
		splash.draw(batch);
		batch.end();
	}
	@Override
	public void resize(int width, int height)
	{
		
	}
	@Override
	public void show()
	{
		//create the batch / tween manage / sprite for splash
		batch = new SpriteBatch();
		tweenManager = new TweenManager();
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		Texture splashTex = new Texture("res/aliens.png");
		splash = new Sprite(splashTex);
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//fades the splash in and out, then calls the main menu
		Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
		Tween.to(splash, SpriteAccessor.ALPHA, 2).target(1).repeatYoyo(1,2).setCallback(new TweenCallback() 
		{
			@Override
			public void onEvent(int type, BaseTween<?> source)
			{
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());
			}
		}).start(tweenManager);
	}
	@Override
	public void hide()
	{
		
	}
	@Override
	public void pause()
	{
		
	}
	@Override
	public void resume()
	{
		
	}
	@Override
	public void dispose()
	{
		batch.dispose();
		splash.getTexture().dispose();
	}
}
