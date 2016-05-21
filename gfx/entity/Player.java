package isg.project.core.gfx.entity;

import isg.project.core.TicTacToe;
import isg.project.core.gfx.entity.Ball.BallType;
import isg.project.core.gfx.entity.board.GameBoard;
import isg.project.core.gfx.entity.board.GameBoardCell;
import isg.project.core.gfx.navigable.GameLoop;

public class Player extends PlayingEntity
{
	private boolean click;

	public Player(BallType ballType)
	{
		super(ballType);
	}
	

	public boolean click()
	{
		return click;
	}

	public void setClick(boolean click)
	{
		this.click = click;
	}

	@Override
	public void update(int delta)
	{
		super.update(delta);

		if (click())
		{
			setClick(false);
			
			for (GameBoard board : GameLoop.getInstance().getBoards())
			{
				if (board.intersects(board.transformPoint(TicTacToe.getPointer())))
				{
					GameBoardCell cell = board.at(board.transformPoint(TicTacToe.getPointer()));
					if (cell != null && cell.isEmpty())
						addBall(board, cell);
				}
			}
		}
	}
}
