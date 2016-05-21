package isg.project.core.ai.evaluation;

import isg.project.core.ai.Node;
import isg.project.core.gfx.entity.board.GameBoard;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Evaluator
{
	private static List<Integer> scores;
	private static Evaluator instance;
	private boolean winning;
	private boolean losing;
	
	private Evaluator()
	{
		scores = new ArrayList<Integer>(Collections.nCopies(4, 0));
	}
    
	public static Evaluator getInstance()
	{
		if (instance == null)
			instance = new Evaluator();
		
		scores = new ArrayList<Integer>(Collections.nCopies(4, 0));
		return instance;
	}
    
    public List<Integer> getSequenceScores(Node node)
    {
    	winning = false;
    	losing = false;
    	evaluateVec2Horizontal(node);
    	evaluateVec2Vertical(node);
    	evaluateVec2PrincipalDiagonal(node);
    	evaluateVec2SecondaryDiagonal(node);
    	evaluateVec3Columns(node);
    	evaluateVec3Vertical1(node);
    	evaluateVec3Vertical2(node);
    	evaluateVec3Horizontal1(node);
    	evaluateVec3Horizontal2(node);
    	evaluateVec3Diagonal1(node);
    	evaluateVec3Diagonal2(node);
    	evaluateVec3Diagonal3(node);
    	evaluateVec3Diagonal4(node);
    	
    	return scores;
    }
    
    public boolean isWinning(Node node)
    {
    	getSequenceScores(node);
    	return winning;
    }
    
    public boolean isLosing(Node node)
    {
    	getSequenceScores(node);
    	return losing;
    }

	private void evaluateVec2Horizontal(Node node)
	{
		List<String> state = node.getState();
		
		for (int board = 0; board < GameBoard.CURRENT_BOARDS; board++)
		{
			for (int row = 0; row < GameBoard.ROWS; row++)
			{
				int index = row * 4 + board * 16;
				String sequence = state.get(0 + index) + state.get(1 + index) + state.get(2 + index) + state.get(3 + index);
				
				if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
				{
					int occurrence = sequence.length() - 1;
					
					if (occurrence == 3)
						checkTerminalState(node, (sequence).charAt(0));
					
					scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
				}
			}
		}
	}
	
	private void evaluateVec2Vertical(Node node)
	{
		List<String> state = node.getState();
		
		for (int board = 0; board < GameBoard.CURRENT_BOARDS; board++)
		{
			for (int column = 0; column < GameBoard.COLUMNS; column++)
			{
				int index = column + board * 16;
				String sequence = state.get(0 + index) + state.get(4 + index) + state.get(8 + index) + state.get(12 + index);
				
				if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
				{
					int occurrence = sequence.length() - 1;
					
					if (occurrence == 3)
						checkTerminalState(node, (sequence).charAt(0));
					
					scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
				}
			}
		}
	}
	
	private void evaluateVec2PrincipalDiagonal(Node node)
	{
		List<String> state = node.getState();
		
		for (int board = 0; board < GameBoard.CURRENT_BOARDS; board++)
		{
			int index = board * 16;
			String sequence = state.get(0 + 0 + index) + state.get(4 + 1 + index) + state.get(8 + 2 + index) + state.get(12 + 3 + index);
			
			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
			{
				int occurrence = sequence.length() - 1;
				
				if (occurrence == 3)
					checkTerminalState(node, (sequence).charAt(0));
				
				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
			}
		}
	}
	
	private void evaluateVec2SecondaryDiagonal(Node node)
	{
		List<String> state = node.getState();
		
		for (int board = 0; board < GameBoard.CURRENT_BOARDS; board++)
		{
			int index = board * 16;
			String sequence = state.get(0 + 3 + index) + state.get(4 + 2 + index) + state.get(8 + 1 + index) + state.get(12 + 0 + index);
			
			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
			{
				int occurrence = sequence.length() - 1;
				
				if (occurrence == 3)
					checkTerminalState(node, (sequence).charAt(0));
				
				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
			}
		}
	}
	
	private void evaluateVec3Columns(Node node)
	{
		List<String> state = node.getState();
		
		for (int row = 0; row < GameBoard.ROWS; row++)
		{
			for (int column = 0; column < GameBoard.COLUMNS; column++)
			{
    			int index = row * 4 + column;
    			String sequence = state.get(0 + index) + state.get(16 + index) + state.get(32 + index) + state.get(48 + index);
    			
    			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
    			{
    				int occurrence = sequence.length() - 1;
    				
					if (occurrence == 3)
						checkTerminalState(node, (sequence).charAt(0));
					
    				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
    			}
			}
		}
	}
	
	private void evaluateVec3Vertical1(Node node)
	{
		List<String> state = node.getState();
		
		for (int column = 0; column < GameBoard.COLUMNS; column++)
		{
   			String sequence = state.get(column + 0 + 0) + state.get(column + 4 + 16) + state.get(column + 8 + 32) + state.get(column + 12 + 48);
    			
   			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
   			{
   				int occurrence = sequence.length() - 1;

				if (occurrence == 3)
					checkTerminalState(node, (sequence).charAt(0));
				
   				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   			}
		}
	}
	
	private void evaluateVec3Vertical2(Node node)
	{
		List<String> state = node.getState();
		
		for (int column = 0; column < GameBoard.COLUMNS; column++)
		{
   			String sequence = state.get(column + 12 + 0) + state.get(column + 8 + 16) + state.get(column + 4 + 32) + state.get(column + 0 + 48);
    			
   			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
   			{
   				int occurrence = sequence.length() - 1;
   				
				if (occurrence == 3)
					checkTerminalState(node, (sequence).charAt(0));
				
   				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   			}
		}
	}
	
	private void evaluateVec3Horizontal1(Node node)
	{
		List<String> state = node.getState();
		
		for (int row = 0; row < GameBoard.ROWS; row++)
		{
			int index = row * 4;
   			String sequence = state.get(index + 0 + 0) + state.get(index + 1 + 16) + state.get(index + 2 + 32) + state.get(index + 3 + 48);
    			
   			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
   			{
   				int occurrence = sequence.length() - 1;
   				
				if (occurrence == 3)
					checkTerminalState(node, (sequence).charAt(0));
				
   				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   			}
		}
	}
	
	private void evaluateVec3Horizontal2(Node node)
	{
		List<String> state = node.getState();
		
		for (int row = 0; row < GameBoard.ROWS; row++)
		{
			int index = row * 4;
   			String sequence = state.get(index + 3 + 0) + state.get(index + 2 + 16) + state.get(index + 1 + 32) + state.get(index + 0 + 48);
    			
   			if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
   			{
   				int occurrence = sequence.length() - 1;
   				
				if (occurrence == 3)
					checkTerminalState(node, (sequence).charAt(0));
				
   				scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   			}
		}
	}
	
	private void evaluateVec3Diagonal1(Node node)
	{
		List<String> state = node.getState();
		
  		String sequence = state.get(0 + 0 + 0) + state.get(4 + 1 + 16) + state.get(8 + 2 + 32) + state.get(12 + 3 + 48);
    			
   		if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
  		{
   			int occurrence = sequence.length() - 1;
   			
			if (occurrence == 3)
				checkTerminalState(node, (sequence).charAt(0));
			
   			scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   		}
	}
	
	private void evaluateVec3Diagonal2(Node node)
	{
		List<String> state = node.getState();
		
  		String sequence = state.get(12 + 3 + 0) + state.get(8 + 2 + 16) + state.get(4 + 1 + 32) + state.get(0 + 0 + 48);
    			
   		if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
  		{
   			int occurrence = sequence.length() - 1;
   			
			if (occurrence == 3)
				checkTerminalState(node, (sequence).charAt(0));
			
   			scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   		}
	}
	
	private void evaluateVec3Diagonal3(Node node)
	{
		List<String> state = node.getState();
		
  		String sequence = state.get(0 + 3 + 0) + state.get(4 + 2 + 16) + state.get(8 + 1 + 32) + state.get(12 + 0 + 48);
    			
   		if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
  		{
   			int occurrence = sequence.length() - 1;
   			
			if (occurrence == 3)
				checkTerminalState(node, (sequence).charAt(0));
			
   			scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   		}
	}
	
	private void evaluateVec3Diagonal4(Node node)
	{
		List<String> state = node.getState();
		
  		String sequence = state.get(12 + 0 + 0) + state.get(8 + 1 + 16) + state.get(4 + 2 + 32) + state.get(0 + 3 + 48);
    			
   		if (sequence.indexOf("p") == -1 ^ sequence.indexOf("e") == -1)
  		{
   			int occurrence = sequence.length() - 1;
   			
			if (occurrence == 3)
				checkTerminalState(node, (sequence).charAt(0));
			
   			scores.set(occurrence, scores.get(occurrence) + ((sequence).charAt(0) == 'e' ? 1 : -1));
   		}
	}

	public int evaluate(Node node)
	{
		int score = 0;
		
		List<Integer> scores = getSequenceScores(node);
		
		for (int i = 0; i < scores.size(); i++)
			score += scores.get(i) * Math.pow(76, i);
		
		return score;
	}
	
	private void checkTerminalState(Node node, char winner)
	{
		if (!node.isTerminal())
			node.setTerminal(true);
			
		if (winner == 'e')
			winning = true;
			
		else
			losing = true;
	}
}