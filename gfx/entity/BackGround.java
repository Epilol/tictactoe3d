package isg.project.core.gfx.entity;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IRectangle;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;

public class BackGround extends DecoratedEntity
{
	private static final String BACKGROUND_PATH = "images/game/background.png";
	private static final float BACKGROUND_DEPTH = 0.0f;
	private static final IRectangle SCREEN = new Rectangle(new Point(0.0f, 0.0f), new Dimension(1280.0f, 800.0f));
	private static Image backgroundImage;
	
	public BackGround()
	{
		super(-1, -1, SCREEN);
	}

	public static void loadAssets(AssetWatcher watcher)
	{
		backgroundImage = assets().getImage(BACKGROUND_PATH);
		watcher.add(backgroundImage);
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
}
