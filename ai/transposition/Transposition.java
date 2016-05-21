package isg.project.core.ai.transposition;

import isg.project.core.ai.Node;
import isg.project.core.ai.evaluation.Evaluator;

public class Transposition
{
	public enum Flag
	{
		EXACT, LOWERBOUND, UPPERBOUND;
	}
	
	private Flag flag;
	private int score;
	private int depth;

	public Flag getFlag()
	{
		return flag;
	}

	public void setFlag(Flag flag)
	{
		this.flag = flag;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getDepth()
	{
		return depth;
	}

	public void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	public void store(Node node, Flag flag, int depth)
	{
		setScore(Evaluator.getInstance().evaluate(node));
		setDepth(depth);
		setFlag(flag);
		TranspositionTable.getInstance().addEntry(node.getHash(), this);
	}
}
