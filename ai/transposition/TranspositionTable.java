package isg.project.core.ai.transposition;

import java.util.HashMap;

public class TranspositionTable
{
	private static TranspositionTable instance;
	private HashMap<Long, Transposition> transpositions;
	
	private TranspositionTable()
	{
		transpositions = new HashMap<Long, Transposition>();
	}
	
	public static TranspositionTable getInstance()
	{
		if (instance == null)
			instance = new TranspositionTable();
		
		return instance;
	}
	
	public void addEntry(long hash, Transposition entry)
	{
		transpositions.put(hash, entry);
	}
	
	public Transposition getTransposition(long hash)
	{
		return transpositions.get(hash);
	}

	public int size()
	{
		return transpositions.size();
	}

	public boolean contains(long hash)
	{
		return transpositions.containsKey(hash);
	}

	public int getScore(long hash)
	{
		return transpositions.get(hash).getScore();
	}
}
