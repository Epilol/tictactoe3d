package isg.project.core;

import static playn.core.PlayN.log;
import static playn.core.PlayN.mouse;
import isg.project.core.gfx.navigable.GameLoop;
import isg.project.core.gfx.navigable.Menu;
import isg.project.core.gfx.navigable.Navigable;
import isg.project.core.media.AudioManager;
import isg.project.core.media.AudioManager.SoundType;
import playn.core.AssetWatcher;
import playn.core.Game;
import playn.core.Mouse;
import playn.core.Mouse.ButtonEvent;
import playn.core.Mouse.MotionEvent;
import playn.core.Mouse.WheelEvent;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class TicTacToe extends Game.Default
{
	public static final String BUTTON_CLICK_AUDIO = "sound/sound_effects/button";
	public static final String BALL_CLICK_AUDIO = "sound/sound_effects/ball";

	private AssetWatcher watcher;
	private static Navigable navigable;
	private static IPoint pointer;
	
	public TicTacToe()
	{
		super(33);
	}
	
	@Override
	public void init()
	{
		loadAssets();
		setMouseListener();
		AudioManager.getInstance().addSound(SoundType.BALL_CLICK, BALL_CLICK_AUDIO);
		AudioManager.getInstance().addSound(SoundType.BUTTON_CLICK, BUTTON_CLICK_AUDIO);
		GameLoop.getInstance().init();
		navigable = Menu.getInstance();
		navigable.show();
	}
	
	public void loadAssets()
	{
		watcher = new AssetWatcher(new AssetWatcher.Listener()
		{
			@Override
			public void error(Throwable e)
			{
				log().error(e.getMessage());
			}

			@Override
			public void done()
			{
			}
		});
		
		watcher.start();
		Menu.loadAssets(watcher);
		GameLoop.loadAssets(watcher);
	}
	
	public void setMouseListener()
	{
		pointer = new Point();

		mouse().setListener(new Mouse.Listener()
		{
			@Override
			public void onMouseDown(ButtonEvent event)
			{
				navigable.onMouseDown(event);
			}

			@Override
			public void onMouseMove(MotionEvent event)
			{
				pointer = new Point(event.localX(), event.localY());
			}

			@Override
			public void onMouseUp(ButtonEvent event)
			{
			}

			@Override
			public void onMouseWheelScroll(WheelEvent event)
			{

			}
		});
	}

	public static IPoint getPointer()
	{
		return pointer;
	}
	
	public static void setNavigable(Navigable navigable)
	{
		TicTacToe.navigable = navigable;
	}
	
	@Override
	public void update(int delta)
	{
		navigable.update(delta);
	}

	@Override
	public void paint(float alpha)
	{
	}



}
