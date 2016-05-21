package isg.project.core.gfx.entity;

import isg.project.core.ai.Alphabeta;
import isg.project.core.gfx.entity.Ball.BallType;
import isg.project.core.gfx.entity.board.GameBoard;
import isg.project.core.gfx.entity.board.GameBoardCell;
import isg.project.core.gfx.navigable.GameLoop;

public class Enemy extends PlayingEntity
{
	public Enemy(BallType ballType)
	{
		super(ballType);
	}
	
	public void play()
	{
		Alphabeta ab = new Alphabeta();
		int move = ab.negamax();
		GameBoard board = GameLoop.getInstance().getBoards().get(move / 16);
		GameBoardCell cell = board.at(new Integer((move % 16) / 4), new Integer(move % 4));
		addBall(board, cell);
	}
}
