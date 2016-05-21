package isg.project.core.gfx.entity.board;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import isg.project.core.gfx.entity.Ball;
import isg.project.core.gfx.entity.DecoratedEntity;
import isg.project.core.gfx.entity.Ball.BallType;
import isg.project.core.gfx.entity.board.GameBoardCell.CellType;
import playn.core.AssetWatcher;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.Layer;
import pythagoras.f.AffineTransform;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;
import pythagoras.f.Rectangle;
import pythagoras.f.Transforms;

public class GameBoard extends DecoratedEntity
{
	public static final int CURRENT_BOARDS = 4;
	public static final int BOARDS = 4;
	public static final int ROWS = 4;
	public static final int COLUMNS = 4;
	private static final float BOARD_DEPTH = 1.0f;
	private static final IDimension SIZE = new Dimension(208.0f, 208.0f);
	private static final float OFFSET = -40.0f;
	private static final IPoint DEFAULT_POS = new Point(500.0f, -450.0f);
	private static final String BOARD_PATH = "images/game/board2.png";
	private static Image boardImage;
	
	private GameBoardCell[][] board;
	private IPoint position;
	private int id;
	private AffineTransform transform;
	private GroupLayer cellBg;
	private GroupLayer boardBg;
	
	public GameBoard(int id, float countw, float counth)
	{
		super(0, 0, new Rectangle(DEFAULT_POS.add(countw * (SIZE.width() + OFFSET), counth * (SIZE.height() + OFFSET)), SIZE));
		position = DEFAULT_POS.add(countw * (SIZE.width() + OFFSET), counth * (SIZE.height() + OFFSET));
		this.id = id;
		initAffineTransform();
		boardBg = graphics().createGroupLayer();
		boardBg.setDepth(BOARD_DEPTH);
		cellBg = graphics().createGroupLayer();
		cellBg.setDepth(BOARD_DEPTH + 1);
		boardBg.add(getLayer());
		graphics().rootLayer().add(boardBg);
		applyIsometry(cellBg);
		applyIsometry(boardBg);
	}

	public GameBoard initBoard()
	{
		board = new GameBoardCell[ROWS][COLUMNS];
		
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
			{
				board[i][j] = (i + j) % 2 == 0 ? new GameBoardCell(i, j, CellType.EVEN, this) : new GameBoardCell(i, j, CellType.ODD, this);
				cellBg.add(board[i][j].getLayer());
			}
		
		graphics().rootLayer().add(cellBg);
		
		return this;
	}

	public static void loadAssets(AssetWatcher watcher)
	{
		boardImage = assets().getImage(BOARD_PATH);
		watcher.add(boardImage);
	}
	
	@Override
	protected Image getImage()
	{
		return boardImage;
	}

	@Override
	protected float depth()
	{
		return BOARD_DEPTH;
	}

	public IPoint pos()
	{
		return position;
	}

	public int size()
	{
		return BOARDS;
	}
	
	public GameBoardCell at(int i, int j)
	{
		return board[i][j];
	}
	
	public IPoint gfxCoords(int row, int column)
	{
		float x = pos().x() + column * (GameBoardCell.size().width() + GameBoardCell.DISTANCE) + GameBoardCell.DISTANCE;
		float y = pos().y() + row * (GameBoardCell.size().height() + GameBoardCell.DISTANCE) + GameBoardCell.DISTANCE;
		return new Point(x, y);
	}
	
	public GameBoardCell at(IPoint p)
	{
		IDimension cellSize = GameBoardCell.size();
		float dist = GameBoardCell.distance();
		int i = (int)((p.y() - (pos().y() + dist)) / (cellSize.height() + dist));
		int j = (int)((p.x() - (pos().x() + dist)) / (cellSize.width() + dist));
		return (i >= 0 && i < size() && j >= 0 && j < size() && p.y() >= pos().y() && p.x() >= pos().x()) ? board[i][j] : null;
	}
	
	public void addBall(Ball ball)
	{
		board[ball.row()][ball.column()].setBall(ball);
	}
	
	public Ball getBall(int i, int j)
	{
		return board[i][j].getBall();
	}
	
	public boolean isSameType(BallType type, int i, int j)
	{
		return !board[i][j].isEmpty() && type == board[i][j].getBall().type();
	}
	
	public int id()
	{
		return id;
	}
	
	private void initAffineTransform()
	{
		transform = new AffineTransform();
		float angle = pythagoras.f.FloatMath.toRadians(25);
		float scaleY = 0.66f;
		AffineTransform isometricScale = new AffineTransform(1, 0, 0, scaleY, 0, 0);
		float shearX = pythagoras.f.FloatMath.tan(-angle);
		AffineTransform isometricShear = new AffineTransform(1, 0, shearX, 1, 0, 0);
		float sin = pythagoras.f.FloatMath.sin(angle);
		float cos = pythagoras.f.FloatMath.cos(angle);
		AffineTransform isometricRotation = new AffineTransform(cos, sin, -sin, cos, 0, 0);
		Transforms.multiply(isometricRotation, Transforms.multiply(isometricShear, isometricScale, transform), transform);
		transform.translate(150,-50);
	}
	
	public IPoint transformPoint(IPoint p)
	{
		Point into = new Point();
		return transform.inverseTransform(p, into);
	}
	
	public void applyIsometry(Layer layer)
	{
		layer.transform().setTransform(transform.m00, transform.m01, transform.m10, transform.m11, transform.tx, transform.ty);
	}
	
	public IPoint getIsometricPos(GameBoardCell cell)
	{
		Point into = new Point();
		return transform.transform(new Point(cell.x(), cell.y()), into).subtract(5, 0);
	}
}
