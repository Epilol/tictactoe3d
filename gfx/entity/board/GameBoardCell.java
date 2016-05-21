package isg.project.core.gfx.entity.board;

import static playn.core.PlayN.assets;
import isg.project.core.gfx.entity.Ball;
import isg.project.core.gfx.entity.DecoratedEntity;
import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.Rectangle;

public class GameBoardCell extends DecoratedEntity
{
	public static final float DISTANCE = 2.0f;
	private static final float DEPTH = 2.0f;
	private static final IDimension SIZE = new Dimension(50.0f, 50.0f);
	private static final String CELL_PATH = "images/game/cell.png";
	private static Image cellImage;
	
	private Ball ball;
	private CellType type;

	public enum CellType
	{
		ODD(0), EVEN(1);
		
		private int offset;
		
		CellType(int offset)
		{
			this.offset = offset;
		}
		
		public int offset()
		{
			return offset;
		}
	}
	
	public GameBoardCell(int row, int column, CellType type, GameBoard board)
	{
		super(row, column, new Rectangle(board.gfxCoords(row, column), SIZE), false);
		this.type = type;
		initLayer(getImage(), depth());
	}
	
	public static void loadAssets(AssetWatcher watcher)
	{
		cellImage = assets().getImage(CELL_PATH);
		watcher.add(cellImage);
	}
	
	protected static float distance()
	{
		return DISTANCE;
	}

	public static IDimension size()
	{
		return SIZE;
	}

	public void setBall(Ball ball)
	{
		this.ball = ball;
	}
	
	public Ball getBall()
	{
		return ball;
	}
	
	public boolean isEmpty()
	{
		return ball == null;
	}
	
	public boolean isPlayer()
	{
		return ball.isPlayer();
	}
	
	public static boolean admissible(int row, int column)
	{
		return row >= 0 && row < SIZE.height() && column >= 0 && column < SIZE.width();
	}

	@Override
	protected Image getImage()
	{
		return cellImage.subImage(width() * type.offset, 0, width(), height());
	}

	@Override
	protected float depth()
	{
		return DEPTH;
	}
}
