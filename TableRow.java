public class TableRow
{
	int index,add;
	String symbol;
	public TableRow(String s,int a,int i)
	{
		symbol=s;
		index=i;
		add=a;
	}
	public String getSymbol()
	{
		return symbol;
	}
	public void setSymbol(String s)
	{
		symbol=s;
	}
	
	public int getAddress()
	{
		return add;
	}
	public void setAddress(int a)
	{
		add=a;
	}
	
	public int getIndex()
	{
		return index;
	}
	public void setIndex(int i)
	{
		index=i;
	}
}
