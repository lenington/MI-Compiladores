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
				escopoPrograma();
				
				token = s.nextToken();
				// aqui tem que Tratar os Nao terminai
				if (token.equals("}"))
					System.out.println("SUCESSO");
				else
					System.out.println("ERROR");

			}
		}
	}
	
	//*****************
	//*******DAQUI*********
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
				//estruturaConstantes(); ou pode ir para o vazio, tem que tratar
				//tem que tratar essa parte ainda. Pois depois de um ponto e virgula, pode vir mais constantes ou nao
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
	
	//*********************************************
	//******ATE AQUI. EH O TRATAMENTO DAS CONSTANTES***
	
	public void escopoPrograma() {
		metodo();
		escopoPrograma();
		//tem que tratar vazio ainda
		
	}
	
	public void metodo() {
		String token = s.nextToken();
		
		if (token.equals("metodo")) {
			token = s.nextToken();
			if (s.tokenType().equals("Identificador")) {
				token = s.nextToken();
				if (token.equals("(")) {
					listaParametros();
					token = s.nextToken();
					if (token.equals("):")) {
						token = s.nextToken();
						if (tipo.contains(token)) {
							token = s.nextToken();
							if (token.equals("{")) {
								declaracaoVariaveis();
								escopoMetodo();
								token = s.nextToken();
								if (token.equals("}"))
									return;
							}
						}
					}
				}
				
			}
		}
	}
	
	public void listaParametros() {
		String token = s.nextToken();
		if (tipo.contains(token)) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {
				maisParametros();
			}
		}
	}
	
	public void declaracaoVariaveis() {
		
	}
	
	public void escopoMetodo() {
		
	}
	
	public void maisParametros() {
		String token = s.nextToken();
		if (token.equals(",")) {
			listaParametros();
		}
		else {
			//tratar vazio e erros
		}
	}
	
	
	
}
