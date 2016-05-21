package isg.project.core.ai;

import isg.project.core.ai.transposition.TranspositionTable;
import isg.project.core.ai.transposition.ZobristHashing;
import java.util.List;

public class Node implements Comparable<Node>
{
	private State state;
	private int score;
	private boolean isMax;
	private long zobristHash;
	private int lastMove;
	private boolean isTerminal;

	public Node(List<?> boards)
	{
		this(boards, true, -1);
		zobristHash = getNewHash();
	}

	public Node(List<?> boards, boolean isMax, int lastMove, long prevZobrist)
	{
		this(boards, isMax, lastMove);
		zobristHash = getHash(prevZobrist, isMax() ? 1 : 0, lastMove);
	}

	private Node(List<?> boards, boolean isMax, int lastMove)
	{
		this.state = new State(boards);
		this.isMax = isMax;
		this.lastMove = lastMove;
	}

	public List<String> getState()
	{
		return state.getState();
	}

	public void setState(State state)
	{
		this.state = state;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public boolean isMax()
	{
		return isMax;
	}

	public int getLastMove()
	{
		return lastMove;
	}

	public void setLastMove(int lastMove)
	{
		this.lastMove = lastMove;
	}

	public boolean isTerminal()
	{
		return isTerminal;
	}

	public void setTerminal(boolean isTerminal)
	{
		this.isTerminal = isTerminal;
	}

	public long getHash()
	{
		return zobristHash;
	}

	private long getNewHash()
	{
		return ZobristHashing.getInstance().getNewHash(getState());
	}

	private long getHash(long zobristHash, int player, int cell)
	{
		return ZobristHashing.getInstance().getHash(zobristHash, player, cell);
	}

	@Override
	public int hashCode()
	{
		return (int) (zobristHash ^ (zobristHash >>> 32));
	}

	@Override
	public boolean equals(Object node)
	{
		if (this == node)
			return true;
		
		if (node == null)
			return false;
		
		if (getClass() != node.getClass())
			return false;
		
		return zobristHash == ((Node) node).zobristHash;
	}
	
	@Override 
	public String toString()
	{
		TranspositionTable ttTable = TranspositionTable.getInstance();
		return zobristHash + "=" + (ttTable.contains(zobristHash) ? ttTable.getScore(zobristHash) : getScore());
	}

	@Override
	public int compareTo(Node node)
	{
		TranspositionTable ttTable = TranspositionTable.getInstance();

		if (!ttTable.contains(getHash()) || !ttTable.contains(node.getHash()))
			return 1;

		if (ttTable.getScore(getHash()) == ttTable.getScore(node.getHash()))
			return 0;

		if (ttTable.getScore(getHash()) < ttTable.getScore(node.getHash()))
			return 1;

		return -1;
	}
}
