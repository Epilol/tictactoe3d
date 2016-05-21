package isg.project.core.gfx.navigable;

import static playn.core.PlayN.assets;
import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import isg.project.core.gfx.entity.DecoratedEntity;

public class Button extends DecoratedEntity
{
	private static final float DEPTH = 2.0f;
	private static final IDimension SIZE = new Dimension(458.0f, 135.0f);
	private static final String PATH = "images/button/button.png";
	private static Image buttonImage;
	
	
	public enum ButtonType
	{
		BLUE(411.0f, 386.0f), RED(411.0f, 575.0f),
		FIRST(411.0f, 386.0f), SECOND(411.0f, 575.0f);
		
		private IPoint pos;
		
		private ButtonType(float x, float y)
		{
			pos = new Point(x, y);
		}
		
		public IPoint getPosition()
		{
			return pos;
		}
	}
	
	public Button(ButtonType type)
	{
		super(-1, -1, new Rectangle(type.pos, SIZE), false);
		initLayer(getImage().subImage(0, type.ordinal() * SIZE.height(), SIZE.width(), SIZE.height()), DEPTH);
	}
	
	@Override
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);
	}
	
	public static void loadAssets(AssetWatcher watcher)
	{
		buttonImage = assets().getImage(PATH);
		watcher.add(buttonImage);
	}
	
	@Override
	public void initLayer(Image image, float depth)
	{
		super.initLayer(image, depth);
	}

	@Override
	protected Image getImage()
	{
		return buttonImage;
	}
	
	@Override
	protected float depth()
	{
		return DEPTH;
	}

}
