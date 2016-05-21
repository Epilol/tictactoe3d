package isg.project.core.ai.transposition;

import java.security.SecureRandom;
import java.util.List;

public class ZobristHashing
{
	private static final int PLAYERS = 2;
	private static final int NUMBER_OF_CELLS = 64;
	private static ZobristHashing instance;
	public static long[][] zobristArray;
	
	private ZobristHashing()
	{
	}
	
	public static ZobristHashing getInstance()
	{
		if (instance == null)
			instance = new ZobristHashing();
		
		return instance;
	}

	private void initArray()
	{
		zobristArray = new long[PLAYERS][NUMBER_OF_CELLS];
		SecureRandom r = new SecureRandom();
		
		for (int i = 0; i < PLAYERS; i++)
			for (int j = 0; j < NUMBER_OF_CELLS; j++)
				zobristArray[i][j] = r.nextLong();
	}
	
	private long generateHash(List<String> positions)
	{
		long zobristHash = 0;
		
		for (int i = 0; i < positions.size(); i++)
		{
			if (positions.get(i).equals(""))
				zobristHash ^= zobristArray[0][i];
						
			else if (positions.get(i).equals(""))
			{
				zobristHash ^= zobristArray[1][i];
			}
		}

		return zobristHash;
	}
	
	public long getNewHash(List<String> positions)
	{
		initArray();
		return generateHash(positions);
	}
	
	public long getHash(long zobristHash, int player, int cell)
	{
		return zobristHash ^ zobristArray[player][cell];
	}
}
