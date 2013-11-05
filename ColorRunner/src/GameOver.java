

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;


public class GameOver implements Screen
{
	private Stage stage;
	private TextureAtlas atlas;
	private Skin skin;
	private Table table;
	private TextButton exitButton, playAgainButton;
	private BitmapFont white;
	private Label heading;
	
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Table.drawDebug(stage);
		stage.act(delta);
		stage.draw();
	}
	public void resize(int width, int height)
	{
		stage.setViewport(width,height, true);
		table.invalidateHierarchy();
		table.setSize(width,height);
	}
	public void show()
	{
	
		//creates stage
		stage = new Stage();
		//allows input events for anything on stage
		Gdx.input.setInputProcessor(stage);
	
		//creating texture atlas and skin
		atlas = new TextureAtlas("res/button.pack");
		skin = new Skin(atlas);
		
		//creating table
		table = new Table(skin);
		table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		//creating fonts
		white = new BitmapFont(Gdx.files.internal("res/white.fnt"), false);
		
		//creating button style
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonUp");
		style.down = skin.getDrawable("buttonDown");
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
		style.font = white;
		

		//creating exit button
		exitButton = new TextButton("Exit", style);
		exitButton.pad(10);
		exitButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				Gdx.app.exit();
			}
		});
		
		//creating play button
		playAgainButton = new TextButton("Play Again", style);
		playAgainButton.pad(10);
		playAgainButton.addListener(new ClickListener(){
			//doesnt do anything yet
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				//starts the game
				//not workinggggggg
				((Game) Gdx.app.getApplicationListener()).setScreen(new Play());
				 
			}
		});
		
		//creating heading
		LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);
		heading = new Label(" Game Over\n" + Play.scoreString ,headingStyle);
		heading.setFontScale(2);
		
		//add everything to table / create space between buttons
		table.add(heading);
		table.getCell(heading).spaceBottom(100);
		table.row();
		table.add(playAgainButton);
		table.getCell(playAgainButton).spaceBottom(20);
		table.row();
		table.add(exitButton);
		table.debug();
		stage.addActor(table);
		
	}
	public void hide()
	{
		
	}
	public void pause()
	{
		
	}
	public void resume()
	{
		
	}
	public void dispose()
	{
		stage.dispose();
		atlas.dispose();
		skin.dispose();
		white.dispose();
	}
}
