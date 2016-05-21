package isg.project.core.ai;

import isg.project.core.ai.evaluation.Evaluator;
import isg.project.core.ai.transposition.Transposition;
import isg.project.core.ai.transposition.TranspositionTable;
import isg.project.core.ai.transposition.Transposition.Flag;
import isg.project.core.gfx.navigable.GameLoop;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Alphabeta
{
	private static final int MIN_ALPHA = Integer.MIN_VALUE;
	private static final int MAX_BETA = Integer.MAX_VALUE;
	private static final long MAX_TIME_WAITING = 25000000000L;
	
	private Node root;
	private int startingDepth;
	private static long waiting = 500000000L;
	
	public Alphabeta()
	{
		this.root = new Node(GameLoop.getInstance().getBoards());

		for (String pos : root.getState())
			if (pos.equals(""))
				startingDepth++;
	}

	public int negamax()
	{	
		long startTime = System.nanoTime();
		long lastMoveTime = startTime;
		long secondLastMoveTime = startTime;
		int searchDepth = 1;
		
		Node winningNode = winning(root);
		Node losingNode = losing(root);
		
		if (winningNode != null)
			return winningNode.getLastMove();
		
		if (losingNode != null)
			return losingNode.getLastMove();
		
		do
		{
			negamax(root, searchDepth, MIN_ALPHA, MAX_BETA);
			searchDepth += 1;
		} 
		while (searchDepth < startingDepth && (System.nanoTime() - startTime) < waiting * (secondLastMoveTime / lastMoveTime));
		
		if (waiting < MAX_TIME_WAITING)
			waiting += 1000000000L;
		
		return getBestNode().getLastMove();
	}
	
	private int negamax(Node node, int depth, int alpha, int beta)
	{
		int alphaOrig = alpha;

		// Transposition Table Lookup; node is the lookup key for ttEntry
		Transposition ttEntry = TranspositionTable.getInstance().getTransposition(node.getHash());

		if (ttEntry != null && ttEntry.getDepth() >= depth)
		{
			if (ttEntry.getFlag() == Flag.EXACT)
				return ttEntry.getScore();

			if (ttEntry.getFlag() == Flag.LOWERBOUND)
				alpha = Math.max(alpha, ttEntry.getScore());

			else if (ttEntry.getFlag() == Flag.UPPERBOUND)
				beta = Math.min(beta, ttEntry.getScore());

			if (alpha >= beta)
				return ttEntry.getScore();
		}

		else
			ttEntry = new Transposition();

		if (depth == 0 || node.isTerminal())
		{
			ttEntry.store(node, Flag.EXACT, depth);
			return ttEntry.getScore();
		}
		
		int bestScore = MIN_ALPHA;
		
		Set<Node> childNodes = generateOrderedMoves(node);

		for (Node child : childNodes)
		{

			int val = -negamax(child, depth - 1, -beta, -alpha);
			bestScore = Math.max(bestScore, val);
			alpha = Math.max(alpha, val);

			if (alpha >= beta)
				break;
		}

		// Transposition Table Store; node is the lookup key for ttEntry
		ttEntry = new Transposition();

		if (bestScore <= alphaOrig)
			ttEntry.store(node, Flag.UPPERBOUND, depth);
		
		else if (bestScore >= beta)
			ttEntry.store(node, Flag.LOWERBOUND, depth);
		
		else
			ttEntry.store(node, Flag.EXACT, depth);
		
		TranspositionTable.getInstance().addEntry(node.getHash(), ttEntry);
		
		return bestScore;
	}

	private Set<Node> generateOrderedMoves(Node node)
	{
		Set<Node> children = new TreeSet<Node>();
		List<String> state = node.getState();

		for (int cell = 0; cell < state.size(); cell++)
			if (state.get(cell).equals(""))
			{
				List<String> childState = new LinkedList<String>(state);
				childState.set(cell, node.isMax() ? "e" : "p");
				Node child = new Node(childState, !node.isMax(), cell, node.getHash());
				children.add(child);	
			}

		return children;
	}
	
	private Set<Node> generatePlayerMoves(Node node)
	{
		Set<Node> children = new TreeSet<Node>();
		List<String> state = node.getState();

		for (int cell = 0; cell < state.size(); cell++)
			if (state.get(cell).equals(""))
			{
				List<String> childState = new LinkedList<String>(state);
				childState.set(cell, "p");
				Node child = new Node(childState, !node.isMax(), cell, node.getHash());
				children.add(child);	
			}

		return children;
	}

	private Node getBestNode()
	{
		int bestScore = MIN_ALPHA;
		Node bestNode = null;

		for (Node child : generateOrderedMoves(root))
		{
			Transposition ttNode = TranspositionTable.getInstance().getTransposition(child.getHash());

			if (ttNode != null)
			{
				if (bestNode == null || bestScore > -ttNode.getScore())
				{
					bestNode = child;
					bestScore = -ttNode.getScore();
				}
			}

			if (ttNode == null)
			{
				if (bestNode == null || bestScore > -child.getScore())
				{
					bestNode = child;
					bestScore = -child.getScore();
				}
			}
		}

		return bestNode;
	}
	
	private Node winning(Node node)
	{
		for (Node child : generateOrderedMoves(node))
			if (Evaluator.getInstance().isWinning(child))
				return child;
		
		return null;
	}
	
	private Node losing(Node node)
	{
		for (Node child : generatePlayerMoves(node))
			if (Evaluator.getInstance().isLosing(child))
				return child;
		
		return null;
	}
}
