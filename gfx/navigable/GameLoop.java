package isg.project.core.gfx.navigable;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;
import isg.project.core.ai.Node;
import isg.project.core.ai.evaluation.Evaluator;
import isg.project.core.gfx.entity.Ball.BallType;
import isg.project.core.gfx.entity.board.GameBoard;
import isg.project.core.gfx.entity.BackGround;
import isg.project.core.gfx.entity.DecoratedEntity;
import isg.project.core.gfx.entity.Enemy;
import isg.project.core.gfx.entity.Player;
import java.util.LinkedList;
import java.util.List;
import playn.core.AssetWatcher;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse.ButtonEvent;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.IPoint;
import pythagoras.f.Point;

public class GameLoop implements Navigable
{
	private static final String COMP_PATH = "images/game/component.png";
	private static final IPoint COMP_POS = new Point(750.0f, 126.0f);
	private static final float COMP_DEPTH = 7.0f;
	
	private static final String END_PATH = "images/game/end_text.png";
	private static final IPoint VICTORY_POS = new Point(76.0f, 350.0f);
	private static final IPoint LOSS_POS = new Point(210.0f, 380.0f);
	private static final IDimension END_SIZE = new Dimension(518.0f, 101.0f);
	private static final float END_DEPTH = 8.0f;
	
	private static final String TURN_PATH = "images/game/turn.png";
	private static final IPoint TURN_POS = new Point(66.0f, 643.0f);
	private static final IDimension TURN_SIZE = new Dimension(435.0f, 86.0f);
	private static final float TURN_DEPTH = 7.0f;
	
	private static final int TOTAL_DELAY = 99;
	private static GameLoop instance;
	private static Image compImage;
	private static Image endImage;
	private static Image turnImage;

	private int delay;
	private BackGround background;
	private List<GameBoard> boards;
	private Player player;
	private Enemy enemy;
	private boolean isPlayerTurn;
	private ImageLayer compLayer;
	private ImageLayer endLayer;
	private ImageLayer playerTurnLayer;
	private ImageLayer enemyTurnLayer;
	private boolean isOver;
	
	private GameLoop()
	{
	}
	
	public static GameLoop getInstance()
	{
		if (instance == null)
			instance = new GameLoop();
		
		return instance;
	}
	
	public static void loadAssets(AssetWatcher watcher)
	{
		DecoratedEntity.loadAssets(watcher);
		compImage = assets().getImage(COMP_PATH);
		watcher.add(compImage);
		endImage = assets().getImage(END_PATH);
		watcher.add(endImage);
		turnImage = assets().getImage(TURN_PATH);
		watcher.add(turnImage);
	}
	
	public void init()
	{
		setBackground(new BackGround());
		boards = new LinkedList<GameBoard>();
		boards.add(new GameBoard(0,0,0).initBoard());
		boards.add(new GameBoard(1,0.9f,1.45f).initBoard());
		boards.add(new GameBoard(2,1.8f,2.9f).initBoard());
		boards.add(new GameBoard(3,2.7f,4.35f).initBoard());
		initCompLayer();
		initTurnLayers();
	}

	public void initCompLayer()
	{
		compLayer = graphics().createImageLayer(compImage);
		compLayer.setDepth(COMP_DEPTH);
		compLayer.setTranslation(COMP_POS.x(), COMP_POS.y());
		compLayer.setVisible(false);
		graphics().rootLayer().add(compLayer);
	}
	
	public void initTurnLayers()
	{
		playerTurnLayer = graphics().createImageLayer(turnImage.subImage(0, 0, TURN_SIZE.width(), TURN_SIZE.height()));
		enemyTurnLayer = graphics().createImageLayer(turnImage.subImage(0, TURN_SIZE.height(), TURN_SIZE.width(), TURN_SIZE.height()));
		playerTurnLayer.setDepth(TURN_DEPTH);
		playerTurnLayer.setTranslation(TURN_POS.x(), TURN_POS.y());
		playerTurnLayer.setVisible(false);
		enemyTurnLayer.setDepth(TURN_DEPTH);
		enemyTurnLayer.setTranslation(TURN_POS.x(), TURN_POS.y());
		enemyTurnLayer.setVisible(false);
		graphics().rootLayer().add(playerTurnLayer);
		graphics().rootLayer().add(enemyTurnLayer);
	}
	
	public List<GameBoard> getBoards()
	{
		return boards;
	}
	
	public BackGround getBackground()
	{
		return background;
	}

	public void setBackground(BackGround background)
	{
		this.background = background;
	}
	
	public void setPlayerTurn(boolean isPlayerTurn)
	{
		this.isPlayerTurn = isPlayerTurn;
	}
	
	public void setPlayer(BallType type)
	{
		this.player = new Player(type);
	}
	
	public void setEnemy(BallType type)
	{
		this.enemy = new Enemy(type);
	}
	
	public void setNewState()
	{
		Node root = new Node(boards);
		List<Integer> score = Evaluator.getInstance().getSequenceScores(root);
		if (score.get(3) != 0)
			isOver = true;
	}
	
	public boolean isPlayerTurn()
	{
		return isPlayerTurn;
	}
	
    private void setEndGame()
    {
    	playerTurnLayer.setVisible(false);
    	enemyTurnLayer.setVisible(false);
    	
    	Image end = endImage.subImage(0, !isPlayerTurn ? 0 : END_SIZE.height(), END_SIZE.width(), END_SIZE.height());
		endLayer = graphics().createImageLayer(end);
		endLayer.setDepth(END_DEPTH);
		
		if (!isPlayerTurn)
			endLayer.setTranslation(VICTORY_POS.x(), VICTORY_POS.y());
		
		else
			endLayer.setTranslation(LOSS_POS.x(), LOSS_POS.y());
			
		endLayer.setVisible(true);
		graphics().rootLayer().add(endLayer);    	
    }
	
	@Override
	public Navigable onMouseDown(ButtonEvent event)
	{
		player.setClick(true);
		return this;
	}

	@Override
	public void show()
	{
		background.setVisible(true);
		compLayer.setVisible(true);
		
		for (GameBoard board : getBoards())
		{
			board.setVisible(true);
			
			for (int i = 0; i < GameBoard.ROWS; i++)
				for (int j = 0; j < GameBoard.COLUMNS; j++)
					board.at(i, j).setVisible(true);
		}
	}

	@Override
	public void hide()
	{
		background.setVisible(false);
		compLayer.setVisible(false);

		for (GameBoard board : getBoards())
		{
			board.setVisible(false);
			
			for (int i = 0; i < GameBoard.ROWS; i++)
				for (int j = 0; j < GameBoard.COLUMNS; j++)
					board.at(i, j).setVisible(false);
		}
	}
	
	@Override
	public void update(int delta)
	{
		if (!isOver)
		{
    		if (isPlayerTurn)
    		{	
    			if (!playerTurnLayer.visible())
    			{
    				enemyTurnLayer.setVisible(false);
    				playerTurnLayer.setVisible(true);
    			}
    			
    			player.update(delta);
    		}
    		
    		else
    		{
       			delay += delta;
       			
       			if (!enemyTurnLayer.visible())
       			{
       				playerTurnLayer.setVisible(false);
					enemyTurnLayer.setVisible(true);
       			}
       			
				if (delay > TOTAL_DELAY)
				{
					enemy.play();
					delay = 0;
				}
    		}
		}
		
		else
			setEndGame();
	}
}
