package isg.project.core.gfx.entity;

import isg.project.core.gfx.entity.Ball.BallType;
import isg.project.core.gfx.entity.board.GameBoard;
import isg.project.core.gfx.entity.board.GameBoardCell;
import isg.project.core.gfx.navigable.GameLoop;
import isg.project.core.media.AudioManager;
import isg.project.core.media.AudioManager.SoundType;
import java.util.ArrayList;
import java.util.List;

public abstract class PlayingEntity
{
	private List<Ball> balls;
	private BallType ballType;
	
	public PlayingEntity(BallType ballType)
	{
		balls = new ArrayList<Ball>();
		this.ballType = ballType;
	}

	public List<Ball> getBalls()
	{
		return balls;
	}
	
	public BallType getBallType()
	{
		return ballType;
	}

	public void addBall(GameBoard board, GameBoardCell cell)
	{
		AudioManager.getInstance().play(SoundType.BALL_CLICK);
		Ball ball = new Ball(cell, board, getBallType());
		cell.setBall(ball);
		board.addBall(ball);
		getBalls().add(ball);
		
		if (this instanceof Player)
			GameLoop.getInstance().setPlayerTurn(false);
		
		else
			GameLoop.getInstance().setPlayerTurn(true);
		
		GameLoop.getInstance().setNewState();
	}
	
	public void update(int delta)
	{
		
	}
}
