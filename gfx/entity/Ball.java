package isg.project.core.gfx.entity;

import static playn.core.PlayN.assets;
import isg.project.core.gfx.entity.board.GameBoard;
import isg.project.core.gfx.entity.board.GameBoardCell;
import isg.project.core.gfx.navigable.GameLoop;
import playn.core.AssetWatcher;
import playn.core.Image;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.Rectangle;

public class Ball extends DecoratedEntity
{
	private static final float BALL_DEPTH = 3.0f;
	private static final IDimension SIZE = new Dimension(30.0f, 31.0f);
	private static final String PATH = "images/game/ball.png";
	private static Image ballImage;
	private boolean isPlayer;
	
	private BallType type;
	
	public enum BallType
	{
		ORANGE(0), BLUE(1);
		
		private int offset;
		
		BallType(int offset)
		{
			this.offset = offset;
		}
		
		public int offset()
		{
			return offset;
		}
	}
	
	public Ball(GameBoardCell cell, GameBoard board, BallType type)
	{
		super(cell.row(), cell.column(), new Rectangle(board.getIsometricPos(cell), SIZE), false);
		this.type = type;
		initLayer(getImage(), depth());
		setVisible(true);
		isPlayer = GameLoop.getInstance().isPlayerTurn();
	}
	
	public static void loadAssets(AssetWatcher watcher)
	{
		ballImage = assets().getImage(PATH);
		watcher.add(ballImage);
	}
	
	@Override
	protected Image getImage()
	{
		return ballImage.subImage(width() * type.offset, 0, width(), height());
	}
	
	public BallType type()
	{
		return type;
	}

	public boolean isPlayer()
	{
		return isPlayer;
	}

	@Override
	protected float depth()
	{
		return BALL_DEPTH + row();
	}
}
