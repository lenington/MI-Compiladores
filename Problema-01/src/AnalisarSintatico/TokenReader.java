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
		 i = i+1;
		 String s =  this.listoken.get(i).getLexama();
		 return s;
	}
	
	public String tokenType() {
		return this.listoken.get(i).getClassificacao();
	}
	
	public boolean hasNextToken() {
		return true;
	}
	
	public String lookAhead() {
		int j = i+1;
		return this.listoken.get(j).getLexama();
	}
	
}