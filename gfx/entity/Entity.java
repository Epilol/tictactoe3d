package isg.project.core.gfx.entity;

public abstract class Entity
{
	private int row;
	private int column;
	private String id;
	
	public Entity(int row, int column)
	{
		this.row = row;
		this.column = column;
		id = getClass() + "" + row + "" + column;
	}

	public int row()
	{
		return row;
	}

	public int column()
	{
		return column;
	}
	
	public void setRow(int row)
	{
		this.row = row;
	}

	public void setColumn(int column)
	{
		this.column = column;
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object e)
	{
		if (e == null)
			return false;
		
		if (this == e)
			return true;
		
		Entity other = (Entity) e;
		String otherId = other.getClass() + "" + other.row + "" + other.column;
		
		return otherId.equals(id);
	}
}
