package AnalisarSintatico;
import java.util.LinkedList;

public class AnalisadorSintatico {

	TokenReader s;
	LinkedList<String> tipo;
	
	public AnalisadorSintatico(TokenReader s) {
		this.s = s;
		tipo = new LinkedList<String>();
		tipo.add("real");
		tipo.add("inteiro");
		tipo.add("boleano");
		tipo.add("texto");
		tipo.add("vazio");
	}

	public void programa() {
		String token;
		token = s.nextToken();
		if (token.equals("programa")) {
			token = s.nextToken();
			if (token.equals("{")) {

				blocoConstantes();

				token = s.nextToken();
				// aqui tem que Tratar os Nao terminai
				if (token.equals("}"))
					System.out.println("SUCESSO");
				else
					System.out.println("ERROR");

			}
		}
	}

	public void blocoConstantes() {
		String token = s.nextToken();
		
		if (token.equals("constantes")) {
			token = s.nextToken();
			if (token.equals("{")) {
				
				// chamar metodo<estrutura de constantes>
				estruturaConstantes();
				
				token = s.nextToken();
				if (token.equals("}")) {
					return;
				}
			}
		}
		
		else {
			//tratar o caso vazio <>
		}
	}
	
	public void estruturaConstantes() {
		String token = s.nextToken();
		
		if (tipo.contains(token)) {
			//chamar metodo de constantes; <Constantes>
			constanteS();
			token = s.nextToken();
			if (token.equals(";")) {
				//estruturaConstantes();
				return;
			}
		}
	}
	
	public void constanteS() {
		String token = s.nextToken();
		token = s.tokenType();
		
		if (token.equals("Identificador")) {
			token = s.nextToken();
			token = token.trim();
			if (token.equals("=")) {
				//chamar <constante><multiconst>
				constante();
				
				multiconst();
			}
		}	
	}
	
	public void constante() {
		String token = s.nextToken();
		token = s.tokenType();

		if (token.equals("Identificador")) {
			return;
		}
		else if (token.equals("Numero")) {
			return;
		}
		else if (token.matches("Cadeia de Caractere")) {
			return;
		}
		else {
			//tratar erro
		}
		
	}
	
	public void multiconst() {
		String token = s.lookAhead();//aqui tem gambiarra ajeitar
		
		if (token.equals(",")) {
			token = s.nextToken();
			constanteS();
		}
		else if (token.equals(";")){
			return;
		}
		
	}
	
	
}
