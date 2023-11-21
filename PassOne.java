import java.io.*;
import java.util.*;


public class PassOne {
	static LinkedHashMap<String,TableRow> SYMTAB;
	
	public static void main(String[] args) throws Exception 
	{
		SYMTAB=new LinkedHashMap();
		InstTable lookup=new InstTable();
		String line ,code;
		int LC=0,SymIndex=0;
		BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\sai\\eclipse-workspace\\Assembler\\src\\assmbly.txt"));
		BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\sai\\eclipse-workspace\\Assembler\\src\\ic.txt"));
		
		while((line=br.readLine())!=null)
		{
			String parts[]=line.split("\\s+");
			
			if(parts[1].equals("START"))
			{
				LC=Integer.parseInt(parts[2]);
				code="(AD,01)\t(C,"+LC+")";
				bw.write(code+"\n");
			}
			
			if(parts[1].equals("END"))
			{
				code="(AD,02)\t";
				bw.write(code+"\n");
			}
			if(!parts[0].isEmpty())
			{
				if(SYMTAB.containsKey(parts[0]))
					SYMTAB.put(parts[0],new TableRow(parts[0],LC,SYMTAB.get(parts[0]).getIndex()));
				else
					SYMTAB.put(parts[0],new TableRow(parts[0],LC,++SymIndex));
			}
			
			if(parts[1].equals("DC")) 
			{
				parts[2]=parts[2].replace("'","");
				int constant=Integer.parseInt(parts[2]);
				code="(DL,01)\t(C,"+constant+")";
				bw.write(code+"\n");
				LC=LC+1;
			}
			if(parts[1].equals("DS")) 
			{
				int size=Integer.parseInt(parts[2]);
				code="(DL,02)\t(C,"+size+")";
				bw.write(code+"\n");
				LC=LC+size;
			}
			if(lookup.getType(parts[1]).equals("IS"))
			{
				int j=2;
				String acode="";
				code="(IS,0"+lookup.getCode(parts[1])+")\t";
				while(j<parts.length)
				{
					parts[j]=parts[j].replace(",","");
					if(lookup.getType(parts[j]).equals("RG"))
					{
						acode=acode+"(RG,0"+lookup.getCode(parts[j])+")\t";
					}
					else
					{
						if(SYMTAB.containsKey(parts[j]))
						{
							int ind=SYMTAB.get(parts[j]).getIndex();
							acode=acode+"(S,0"+ind+")";
						}
						else
						{
							SYMTAB.put(parts[j],new TableRow(parts[j],-1,++SymIndex));
							int ind=SYMTAB.get(parts[j]).getIndex();
							acode=acode+"(S,0"+ind+")";
						}
					}
					j++;
				}
				code+=acode;
				bw.write(code+"\n");
				LC=LC+1;
			}
		}	
		br.close();
		bw.close();
		System.out.println("files closed");
		printSymTab();
	}
	static void printSymTab() throws Exception
	{
		BufferedWriter bwr=new BufferedWriter(new FileWriter("C:\\Users\\sai\\eclipse-workspace\\Assembler\\src\\symtab.txt"));
		Iterator<String>itr=SYMTAB.keySet().iterator();
		System.out.println("Symbol Table \n");
		while(itr.hasNext())
		{
			String key=itr.next().toString();
			TableRow row =SYMTAB.get(key);
			System.out.println(row.getIndex()+"\t"+row.getSymbol()+"\t"+row.getAddress());
			bwr.write(row.getIndex()+"\t"+row.getSymbol()+"\t"+row.getAddress()+"\n");
		}
		bwr.close();
	}
}
