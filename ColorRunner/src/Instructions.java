import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

/**
 * creates the instructions screen
 */
public class Instructions implements Screen
{
	//draws everything on the table
	private Stage stage;
	//atlas that contains all the img used in instructions
	private TextureAtlas atlas;
	//skin used with atlas for img
	private Skin skin;
	//table to set the img in right locations 
	private Table table;
	//font
	private BitmapFont white;
	//img for a d w space i o p
	private Image left, right, jump, jump2 , i, o, p;
	//label style
	private LabelStyle textStyle;
	//labels for text telling them instructions left right jump or and color
	private Label leftRightText,jumpText,or, or2, changeColor, help ; 
	//button to go back to main menu
	private TextButton back;
	//button to handle the text on buttons
	private TextButtonStyle style;
	
	/**
	 * renders all the images
	 */
	public void render(float delta)
	{
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//Table.drawDebug(stage);
		stage.act(delta);
		stage.draw();
	}
	/**
	 * resizes the screen
	 */
	public void resize(int width, int height)
	{
		stage.setViewport(width,height, true);
		table.invalidateHierarchy();
		table.setSize(width,height);
	}
	public void show()
	{
		//Scaling scale = new Scaling();
		//creates stage
		stage = new Stage();
		
		//allows input events for anything on stage
		Gdx.input.setInputProcessor(stage);
	
		//creating texture atlas and skin
		atlas = new TextureAtlas("res/Instructions.pack");
		skin = new Skin(atlas);
		
		//creating table
		table = new Table(skin);
		table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		
		//creating fonts
		white = new BitmapFont(Gdx.files.internal("res/white.fnt"), false);
		
		LabelStyle textStyle = new LabelStyle(white, Color.WHITE);
		
		//create buttonStyle
		TextButtonStyle style = new TextButtonStyle();
		style.up = skin.getDrawable("buttonUp");
		style.down = skin.getDrawable("buttonDown");
		style.pressedOffsetX = 1;
		style.pressedOffsetY = -1;
		style.font = white;
		
		//creating back button
		back = new TextButton("Back", style);
		back.pad(10);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y)
			{
				((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenu());	 
			}
		});
		
		//creating images
		left = new Image(skin.getDrawable("keyA"));
		right = new Image(skin.getDrawable("keyD"));
		
		jump = new Image(skin.getDrawable("keyW"));
		jump2 = new Image(skin.getDrawable("keySpace"));
		
		i = new Image(skin.getDrawable("keyI"));
		o = new Image(skin.getDrawable("keyO"));
		p = new Image(skin.getDrawable("keyP"));
		
		//creating labels
		//leftText, rightText,jumpText, plus, or;
		
		leftRightText = new Label("To move Left or Right", textStyle);
		jumpText = new Label("To Jump", textStyle);
		changeColor = new Label("To change Colors", textStyle);
		//plus = new Label("+")
		or = new Label("Or", textStyle);
		or2 = new Label("Or", textStyle);
		help = new Label("Go through walls by being the same color. \n" +
				"You will always collide with white walls. \n" +
				"Mix colors to get different combinations. \n" +
				"Also try out wall jumping!"
				, textStyle);
		
		//adds everything to the table and centers it all correctly sort of
		table.top();
		table.left();
		table.padLeft(25);
		table.padTop(50);
		table.padRight(25);
		
		table.add(left).width(100);
		table.getCell(left).pad(10);
		table.add(or).pad(10).right().padRight(40);
		table.add(right).width(100);
		table.add(leftRightText).left().padLeft(40);
		table.row();
	
		table.add(jump).pad(10);
		table.add(or2).pad(10);
		table.add(jump2).width(150);
		table.add(jumpText).left().padLeft(40);
		table.row();
		
		table.add(i).pad(10);
		table.add(o).pad(10);
		table.getCell(o).width(100).padLeft(32);
		table.add(p).pad(10);
		table.add(changeColor).left().padLeft(40);
		table.row();
		table.add(help).colspan(4);
		table.row();
		table.add(back).spaceTop(30).colspan(4);

		//table.debug();
		
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