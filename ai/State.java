package isg.project.core.ai;

import isg.project.core.gfx.entity.board.GameBoard;
import java.util.LinkedList;
import java.util.List;

public class State
{
	private List<String> state;

	@SuppressWarnings("unchecked")
	public State(List<?> boards)
	{
		if (boards != null && boards.size() > 0)
		{
			if (boards.get(0).getClass().equals(GameBoard.class))
				init((List<? extends GameBoard>) boards);

			if (boards.get(0).getClass().equals(String.class))
				state = (List<String>) boards;
		}
	}

	private void init(List<? extends GameBoard> boards)
	{
		state = new LinkedList<String>();

		for (GameBoard board : boards)
		{
			for (int i = 0; i < GameBoard.ROWS; i++)
			{
				for (int j = 0; j < GameBoard.COLUMNS; j++)
				{
					if (board.at(i, j).isEmpty())
						state.add("");

					else
						state.add(board.at(i, j).isPlayer() ? "p" : "e");
				}
			}
		}
	}
	
	public List<String> getState()
	{
		return state;
	}
}