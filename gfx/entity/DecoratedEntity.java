package isg.project.core.gfx.entity;

import static playn.core.PlayN.graphics;
import isg.project.core.gfx.entity.board.GameBoard;
import isg.project.core.gfx.entity.board.GameBoardCell;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import pythagoras.f.IPoint;
import pythagoras.f.IRectangle;
import pythagoras.f.Rectangle;

public abstract class DecoratedEntity extends Entity
{
	private IRectangle rectangle;
	private ImageLayer layer;
	
	public DecoratedEntity(int row, int column, IRectangle rectangle)
	{
		this(row, column, rectangle, true);
	}

	public DecoratedEntity(int row, int column, IRectangle rectangle, boolean init)
	{
		super(row, column);
		this.rectangle = rectangle;
		
		if (init)
			initLayer(getImage(), depth());
	}
	
	public static void loadAssets(AssetWatcher watcher)
	{
		BackGround.loadAssets(watcher);
		GameBoard.loadAssets(watcher);
		GameBoardCell.loadAssets(watcher);
		Ball.loadAssets(watcher);
	}
	
	public float x()
	{
		return rectangle.x();
	}
	
	public float y()
	{
		return rectangle.y();
	}
	
	public void setPos(IPoint pos)
	{
		rectangle = new Rectangle(pos, rectangle.size());
	}
	
	public float width()
	{
		return rectangle.width();
	}
	
	public float height()
	{
		return rectangle.height();
	}
	
	public boolean intersects(IPoint pointer)
	{
		return rectangle.contains(pointer);
	}
	
	public void initLayer(Image image, float depth)
	{
		layer = graphics().createImageLayer(image);
		layer.setDepth(depth);
		layer.setTranslation(rectangle.x(), rectangle.y());
		layer.setVisible(false);
		graphics().rootLayer().add(layer);
	}
	
	public void destroy()
	{
		graphics().rootLayer().remove(layer);
		layer.destroy();
	}

	public ImageLayer getLayer()
	{
		return layer;
	}

	public boolean visible()
	{
		return layer.visible();
	}
	
	public void setVisible(boolean visible)
	{
		layer.setVisible(visible);
	}
	
	public void setTranslation(float x, float y)
	{
		layer.setTranslation(x, y);
	}
	
	protected void setTranslation(IPoint pos)
	{
		layer.setTranslation(pos.x(), pos.y());
	}
	
	protected void setRectangle(Rectangle rectangle)
	{
		this.rectangle = rectangle;
	}
	
	protected abstract Image getImage();
	
	protected abstract float depth();
}
