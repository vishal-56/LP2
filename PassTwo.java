import java.io.*;
import java.util.ArrayList;
public class PassTwo 
{
	public static void main(String []arg)throws Exception
	{
		BufferedReader br=new BufferedReader(new FileReader("C:\\Users\\sai\\eclipse-workspace\\Assembler\\src\\symtab.txt"));
		ArrayList<TableRow>SYMTAB=new ArrayList<>();
		String line;
		
		while((line=br.readLine())!=null)
		{
			String parts[]=line.split("\\s+");
			SYMTAB.add(new TableRow(parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[0])));
		}
		BufferedReader br1=new BufferedReader(new FileReader("C:\\Users\\sai\\eclipse-workspace\\Assembler\\src\\ic.txt"));
		BufferedWriter bw=new BufferedWriter(new FileWriter("C:\\Users\\sai\\eclipse-workspace\\Assembler\\src\\machine.txt"));
		while((line=br1.readLine())!=null)
		{
			String part[]=line.split("\\s+");
			if(part[0].contains("AD")||part[0].contains("DL,02"))
			{
				bw.write("\n");
			}
			else if(part.length==2)
			{
				if(part[0].contains("DL,01"))
				{
					String code;
					int c=Integer.parseInt(part[1].replaceAll("[^0-9]",""));
					code="+00\t0\t"+String.format("%03d",c);
					bw.write(code+"\n");
				}
				
			}
			else if(part.length==1)
			{
				int opcode=Integer.parseInt(part[0].replaceAll("[^0-9]",""));
				String code="+00\t0\t"+String.format("%03d",opcode);
				bw.write(code+"\n");
			}
			else if(part.length==3 && part[0].contains("IS"))
			{
				int instcode=Integer.parseInt(part[0].replaceAll("[^0-9]",""));
				int op1code=Integer.parseInt(part[1].replaceAll("[^0-9]",""));
				int symindex=Integer.parseInt(part[2].replaceAll("[^0-9]",""));
				TableRow op2code=SYMTAB.get(symindex-1);
				int operand=op2code.getAddress();
				String code="+"+String.format("%02d",instcode)+"\t"+String.format("%02d",op1code)+"\t"+String.format("%02d",operand);
				bw.write(code+"\n");
			}
			
		}
	br.close();
	br1.close();
	bw.close();
	}
}
