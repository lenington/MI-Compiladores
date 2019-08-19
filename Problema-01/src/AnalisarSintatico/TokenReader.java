package AnalisarSintatico;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import lexico.Tokens;

public class TokenReader {
	
	private LinkedList<Tokens> listoken;
	private int i;
	public TokenReader(LinkedList<Tokens> listoken) throws FileNotFoundException {
		this.listoken = listoken;
		this.i = -1; 
	}
	
	public String nextToken() {
		 try {
		 i = i+1;
		 String s =  this.listoken.get(i).getLexama();
		 System.out.println(s);
		 return s;
		 }
		 catch(Exception e) {
			 System.out.println("Erro! O programa foi mal formado!"+e);
			 System.exit(0);
			 return "";
		 }
	}
	
	public String tokenType() {
		return this.listoken.get(i).getClassificacao();
	}
	
	public boolean hasNextToken() {
		return true;
	}
	
	public String getAtualToken() {
		return this.listoken.get(i).getLexama();
	}
	
	public String lookAhead() {
		int j = i+1;
		return this.listoken.get(j).getLexama();
	}
	
	public String lookDoubleAhead() {
		int j = i+2;
		return this.listoken.get(j).getLexama();
	}
	
	public void ignoreLine() {
		int linhaAtual = this.listoken.get(i).getLinha();
		int novalinha = this.listoken.get(i).getLinha();
		while (linhaAtual == novalinha) {
			i++;
			novalinha = this.listoken.get(i).getLinha();
		}
		i--;
	}
	
	public int getLine() {
		return this.listoken.get(i).getLinha();
	}
	
}
