package isg.project.core.gfx.navigable;

import static playn.core.PlayN.assets;
import isg.project.core.TicTacToe;
import isg.project.core.gfx.entity.DecoratedEntity;
import isg.project.core.gfx.entity.Ball.BallType;
import isg.project.core.gfx.navigable.Button.ButtonType;
import isg.project.core.media.AudioManager;
import isg.project.core.media.AudioManager.SoundType;
import java.util.ArrayList;
import java.util.List;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.Dimension;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class Menu extends DecoratedEntity implements Navigable
{
	private static final String BACKGROUND_PATH = "images/game/menu.png";
	private static final float BACKGROUND_DEPTH = 0.0f;
	private static final IRectangle SCREEN = new Rectangle(new Point(0.0f, 0.0f), new Dimension(1280.0f, 800.0f));
	private static Image backgroundImage;
	private static Menu instance;
	
	private List<Button> buttons;
	
	private Menu()
	{
		super(-1, -1, SCREEN);
		
		buttons = new ArrayList<Button>();
		buttons.add(new Button(ButtonType.BLUE));
		buttons.add(new Button(ButtonType.RED));
		buttons.add(new Button(ButtonType.FIRST));
		buttons.add(new Button(ButtonType.SECOND));
	}
	
	public static Menu getInstance()
	{
		if (instance == null)
			instance = new Menu();
		
		return instance;
	}
	
	@Override
	protected Image getImage()
	{
		return backgroundImage;
	}

	@Override
	protected float depth()
	{
		return BACKGROUND_DEPTH;
	}
	
	public static void loadAssets(AssetWatcher watcher)
	{
		Button.loadAssets(watcher);
		backgroundImage = assets().getImage(BACKGROUND_PATH);
		watcher.add(backgroundImage);
	}

	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		if (buttons.get(0).visible())
		{
			if (buttons.get(0).intersects(TicTacToe.getPointer()))
			{
				GameLoop.getInstance().setPlayer(BallType.BLUE);
				GameLoop.getInstance().setEnemy(BallType.ORANGE);
				nextSetting();
				AudioManager.getInstance().play(SoundType.BUTTON_CLICK);
			}
			
			else if (buttons.get(1).intersects(TicTacToe.getPointer()))
			{
				GameLoop.getInstance().setPlayer(BallType.ORANGE);
				GameLoop.getInstance().setEnemy(BallType.BLUE);
				nextSetting();
				AudioManager.getInstance().play(SoundType.BUTTON_CLICK);
			}
			return this;
		}
		
		else
		{
			if (buttons.get(2).intersects(TicTacToe.getPointer()))
			{
				hide();
				GameLoop game = GameLoop.getInstance();
				game.setPlayerTurn(true);
				game.show();
				TicTacToe.setNavigable(game);
				AudioManager.getInstance().play(SoundType.BUTTON_CLICK);
				return game;
			}
			
			if (buttons.get(3).intersects(TicTacToe.getPointer()))
			{
				hide();
				GameLoop game = GameLoop.getInstance();
				game.setPlayerTurn(false);
				game.show();
				TicTacToe.setNavigable(game);
				AudioManager.getInstance().play(SoundType.BUTTON_CLICK);
				return game;
			}
			
			return this;
		}
	}
	
	private void nextSetting()
	{
		buttons.get(0).setVisible(false);
		buttons.get(1).setVisible(false);
		buttons.get(2).setVisible(true);
		buttons.get(3).setVisible(true);
	}

	@Override
	public void show()
	{
		getLayer().setVisible(true);
		buttons.get(0).setVisible(true);
		buttons.get(1).setVisible(true);
	}

	@Override
	public void hide()
	{
		getLayer().setVisible(false);

		for (Button button : buttons)
			button.setVisible(false);
	}

	@Override
	public void update(int delta)
	{
		
	}
}
