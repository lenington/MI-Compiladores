package AnalisarSintatico;

import java.util.LinkedList;

public class AnalisadorSintatico {

	private TokenReader s;
	private LinkedList<String> tipo;
	private String token;

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

		token = s.nextToken();
		if (token.equals("programa")) {
			token = s.nextToken();
			if (token.equals("{")) {

				token = s.nextToken();
				blocoConstantes();
				escopoPrograma();

				// token = s.nextToken();
				// aqui tem que Tratar os Nao terminai
				if (token.equals("}"))
					System.out.println("SUCESSO");
				else
					System.out.println("ERROR");

			}
		}
	}

	// *****************
	// *******DAQUI*********
	public void blocoConstantes() {

		if (token.equals("constantes")) {
			token = s.nextToken();
			if (token.equals("{")) {

				// chamar metodo<estrutura de constantes>
				estruturaConstantes();

				token = s.nextToken();
				if (token.equals("}")) {
					return;
				}
			} else {
				// tratar o erro da falta do { depois das constantes
			}

		} else {
			return; // senao vier uma "constantes" retorna
		}
	}

	public void estruturaConstantes() {
		token = s.nextToken();

		if (tipo.contains(token)) {
			// chamar metodo de constantes; <Constantes>
			constanteS();
			if (token.equals(";")) {
				estruturaConstantes();
				// tem que tratar essa parte ainda. Pois depois de um ponto e virgula, pode vir
				// mais constantes ou nao
			} else {
				// tratar erro
			}
		} else {
			return;
		}
	}

	public void constanteS() {
		token = s.nextToken();
		token = s.tokenType();

		if (token.equals("Identificador")) {
			token = s.nextToken().trim();
			if (token.equals("=")) {
				// chamar <constante><multiconst>
				constante();

				multiconst();
			}
		}
	}

	public void constante() {
		token = s.nextToken();
		token = s.tokenType();

		if (token.equals("Identificador")) {
			return;
		} else if (token.equals("Numero")) {
			return;
		} else if (token.matches("Cadeia de Caractere")) {
			return;
		} else {
			// tratar erro
		}

	}

	public void multiconst() {
		token = s.nextToken();// aqui tem gambiarra ajeitar

		if (token.equals(",")) {
			constanteS();
		} else
			return;

	}

	// *********************************************
	// ******ATE AQUI. EH O TRATAMENTO DAS CONSTANTES***

	public void escopoPrograma() {
		metodo();
		// escopoPrograma();
		// tem que tratar vazio ainda

	}

	public void metodo() {

		if (token.equals("metodo")) {
			token = s.nextToken();
			if (s.tokenType().equals("Identificador")) {
				token = s.nextToken();
				if (token.equals("(")) {
					listaParametros();
					if (token.equals(")")) {
						token = s.nextToken();
						if (token.equals(":")) {
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

						} else {
							// tratar o erro por nao encontrar o :
						}

					} else {
						// tratar o erro de nao encontrar o )
					}
				} else {
					// tratar o erro de nao encontrar o (
				}

			} else {
				// tratar o erro de nao encontrar o identificador em seguida
			}
		}
	}

	public void listaParametros() {
		token = s.nextToken();
		if (tipo.contains(token)) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {
				maisParametros();
			}
		} else {
			return; // tratamento de vazio aqui
		}
	}

	public void declaracaoVariaveis() {
		token = s.nextToken();
		if (token.equals("variaveis")) {
			token = s.nextToken();
			if (token.equals("{")) {
				VarV();
				token = s.nextToken();
				if (token.equals("}")) {
					return;
				}
			}
		}
		else {
			return;
			//esse else eh para tratar o vazio do <DeclaracaoVariaveis> ::= 'variaveis' '{'<VarV>'}' | <>
		}
		// escopoMetodo();
	}

	public void VarV() {
		token = s.nextToken();
		if (tipo.contains(token)) {
			complementoV();
			maisVariaveis();
		}
		else {
			//tratar o erro de nao encontrar o tipo aqui --> <VarV> ::= Tipo <complementoV> <MaisVariaveis> 
		}

	}

	public void complementoV() {
		token = s.nextToken();
		token = s.tokenType();

		if (token.equals("Identificador")) {
			vetor();
			variavelMesmoTipo();

		}
	}

	public void vetor() {
		//<Vetor> ::= '[' <OpI2><OpIndice> ']' <Matriz> | <>
		//<Matriz> ::= '[' <OpI2><OpIndice> ']' | <>
		
		token = s.nextToken();
		if (token.equals("[")) {
			//<OpI2><OpIndice>
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Numeros") || token.equals("Identificadores")){
				token = s.nextToken();
			}
			token = s.nextToken();
			if (token.equals("]")) {
				matriz();
			}
		}
		else {
			return; //aqui eh onde trata o vazio
		}
	}

	private void matriz() {
		// TODO Auto-generated method stub
		
	}

	public void variavelMesmoTipo() {

	}

	public void maisVariaveis() {

	}

	public void escopoMetodo() {

	}

	public void maisParametros() {
		token = s.nextToken();
		if (token.equals(",")) {
			listaParametros();
		} else {
			// retorno de vazio
			return;
		}
	}

}
