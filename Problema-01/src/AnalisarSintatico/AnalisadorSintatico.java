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
		/*
		 * <metodo> ::= 'metodo' Identificadores'('<listaParametros>'):'Tipo'{'<DeclaracaoVariaveis> <escopoMetodo>'}'
		   <listaParametros> ::= Tipo Identificadores <maisParametros> | <>
           <maisParametros> ::= ','<listaParametros> | <>
		 * */

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
			OpI2();
			OpIndice();
			token = s.nextToken();
			if (token.equals("]")) {
				matriz();
			}
		}
		else {
			return; //aqui eh onde trata o vazio
		}
		
	}

	private void OpIndice() {
		//<OpIndice> ::= OperadoresAritmeticos <OpI2> <OpIndice> | <>
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Operador Aritmetico")) {
			OpI2();
			OpIndice();
		}
		else {
			return; //tratamento de vazio aqui
		}
		
	}

	private void OpI2() {
		//<OpI2> ::= Numeros | Identificadores 
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Numeros") || token.equals("Identificadores"))
			return;
		else {
			//tratar o erro aqui
		}
	}

	private void matriz() {
		//<Matriz> ::= '[' <OpI2><OpIndice> ']' | <>
		token = s.nextToken();
		if (token.equals("[")) {
			token = s.nextToken();
			OpI2();
			OpIndice();
			token = s.nextToken();
			if (token.equals("]")) {
				return;
			}
		}
		else {
			return;//tratar vazio
		}
	}

	public void variavelMesmoTipo() {
		token = s.nextToken();
		if (token.equals(",")) {
			complementoV();
		}
		else if (token.equals(";")) {
			return;
		}
		else {
			//tratar erro aqui;
		}
	}

	public void maisVariaveis() {
		//<MaisVariaveis> ::= <VarV> | <>
		token = s.nextToken();
		if (tipo.contains(token)) {
			complementoV();
			maisVariaveis();
		}
		else {
			return; 
		}
	}

	public void escopoMetodo() {
		//<escopoMetodo> ::= <comandos><escopoMetodo> | <>
		
		token = s.nextToken();
		if(token.equals("escreva")) {
			escreva();
			escopoMetodo();
		} else if(token.equals("leia")) {
			leia();
			escopoMetodo();
		} else if(token.equals("se")) {
			se();
			escopoMetodo();
		} else if(token.equals("enquanto")) {
			//FAZER AINDA
			escopoMetodo();
		} else if(token.equals("resultado")) {
			//FAZER AINDA
			escopoMetodo();
		}
		// FAZER RESTANTE...
		else {
			return; //para vazio <>
		}
	}
	
	public void comandos() {
		//<comandos> ::= <leia> | <escreva> | <se> | <enquanto> | 
		//               <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> | 
		//               'resultado' <retorno> ';'
		token = s.nextToken();
		if(token.equals("escreva")) {
			escreva();
			escopoMetodo();
		} else if(token.equals("leia")) {
			leia();
			escopoMetodo();
		} else if(token.equals("se")) {
			
		} else if(token.equals("enquanto")) {
			
		} else if(token.equals("resultado")) {
			
		}
		
		else {
			return; //para vazio <>
		}
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
	
	/*
	 * Escrita
	 * 
	*/
	public void escreva() {
		//<escreva> ::= 'escreva' '(' <Param Escrita> ')'';'
		//<Param Escrita> ::= <verificaCaso><MaisParametroE>
		//<MaisParametroE> ::= ',' <Param Escrita> | <>
		token = s.nextToken();
		if(token.equals("(")) {
			ParamEscrita();
			
			if(token.equals(")")) {
				token = s.nextToken();
				if(token.equals(";")) { 
					return;
				}
			}
		} else {
			//ERROR
		}
	}
	
	public void ParamEscrita() {
		//<Param Escrita> ::= <verificaCaso><MaisParametroE>
		verificaCaso();
		MaisParametroE();
	}
	
	public void verificaCaso() {
		//<verificaCaso> ::= <incremento> 
        //| <expressao> 
        //| <booleano>
		token = s.nextToken();
		token = s.tokenType();
		
		if (token.equals("Incremento") || token.equals("Booleano") || token.equals("Expressao")) {
			return;
		} else {
			// ERROR
		}
		
	}
	
	public void MaisParametroE() {
		//<MaisParametroE> ::= ',' <Param Escrita> | <>
		token = s.nextToken();
		
		if(token.equals(",")) {
			ParamEscrita();
		} else {
			return;
		}
	}
	
	public void leia() {
		//<leia> ::= 'leia' '(' <conteudoLeia> ')'';'
		//<conteudoLeia> ::= Identificadores<Vetor> <lerMais>
		//<lerMais> ::= ',' <conteudoLeia> | <>
		
		token = s.nextToken(); 
		if(token.equals("(")) {
			conteudoLeia();
			
			if(token.equals(")")) {
				token = s.nextToken();
				if(token.equals(";")) { 
					return;
				}
			}
		} else {
			//ERROR
		}
	} 
	
	public void conteudoLeia() {
		//<conteudoLeia> ::= Identificadores<Vetor> <lerMais>
		token = s.nextToken(); 
		token = s.tokenType();

		if (token.equals("Identificador")) {
			vetor();
			lerMais();
		} else {
			// ERROR
		}
	}
	
	public void lerMais() {
		//<lerMais> ::= ',' <conteudoLeia> | <>
		token = s.nextToken();

		if(token.equals(",")) {
			conteudoLeia();
		} else {
			return;
		}
	}
	
	public void se() {
		//<se> ::= 'se' <condse> 'entao''{' <blocoSe> '}' <senao>
		//<condse> ::= '(' <cond> <maisCond> ')'
		//<cond> ::= <termo> OperadoresRelacionais <termo> 
		//        | <negar> Identificadores<Vetor> 
		condse();
		token = s.nextToken();

		if(token.equals("entao")) {
			token = s.nextToken();
			if(token.equals("{")) {
				blocoSe();
				token = s.nextToken();
				if(token.equals("}")) {
					senao();
				} else {
					//ERROR
				}
			} else {
				//ERROR
			}
		} else {
			//ERROR
		}
		
	}
	
	public void blocoSe() {
		//<blocoSe> ::= <comandos><blocoSe> | <>
		token = s.nextToken();
		if(token.equals("escreva")) {
			escreva();
			blocoSe();
		} else if(token.equals("leia")) {
			leia();
			blocoSe();
		} else if(token.equals("se")) {
			se();
			blocoSe();
		} else if(token.equals("enquanto")) {
			enquanto(); //FAZER AINDA...
			blocoSe();
		} else if(token.equals("resultado")) {
			//FAZER AINDA...
			blocoSe();
		}
		//FAZER RESTANTE...
		else {
			return; //para vazio <>
		}
	}
	
	public void senao() {
		//<senao> ::= <> | 'senao' <condSenao> '{' <blocoSe> '}' <senao>
		token = s.nextToken();
		if(token.equals("senao")) {
			condSenao();
			token = s.nextToken();
			if(token.equals("{")) {
				blocoSe();
				token = s.nextToken();
				if(token.equals("}")) {
					senao();
				} else {
					//ERROR
				}
			} else {
				//ERROR
			}
		} else {
			return; 
		}
	}
	
	public void condSenao() {
		//<condSenao> ::= 'se' <condse> 'entao' | <>
		token = s.nextToken();
		if(token.equals("se")) {
			condse();
			token = s.nextToken();
			if(token.equals("entao")) {
				return;
			} else {
				// ERROR
			}
		} else {
			return; 
		}
	}
	
	public void condse() {
		//<condse> ::= '(' <cond> <maisCond> ')'
		token = s.nextToken();

		if(token.equals("(")) {
			cond();
			maisCond();
			token = s.nextToken();
			if(token.equals(")")) {
				return;
			} else {
				// ERROR
			}
		} else {
			// ERROR
		}
	}
	
	public void cond() {
		//<cond> ::= <termo> OperadoresRelacionais <termo> 
		//        | <negar> Identificadores<Vetor> 
		token = s.nextToken();
		if(token.equals("!")) { // <negar> 
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) 
				vetor();
		} else {
			termo();
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Operador Relacional")) {
				termo();
			} else {
				// ERRO
			}
		}
	}
	
	public void termo() {
		//<termo> ::= <tipoTermo> <op>
		//<tipoTermo> ::= Identificadores<Vetor> 
        //| Numeros 
        //| CadeiaCaracteres 
        //| TipoBooleano
		//<op> ::= OperadorAritmeticos <tipoTermo> <op>|<>
		tipoTermo();
		op();
		
	}
	
	public void tipoTermo() {
		//<tipoTermo> ::= Identificadores<Vetor> 
        //| Numeros 
        //| CadeiaCaracteres 
        //| TipoBooleano
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Identificador")) {
			vetor();
		} else if(token.equals("Numeros") || token.equals("Cadeia de Caracteres") || token.equals("Booleano")) {
			return;
		} else {
			// ERROR
		}
		
	}
	
	public void op() {
		//<op> ::= OperadorAritmeticos <tipoTermo> <op>|<>
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Operador Aritmetico")) {
			tipoTermo();
			op();
		} else return;
	}
	
	public void maisCond() {
		//<maisCond> ::= OperadoresLogicos <cond> <maisCond> | <>
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Operador Logico")) {
			cond();
			maisCond();
		} else return;
		
	}
	
	
	public void enquanto() {
		//<enquanto> ::= 'enquanto' '(' <operacao_relacional> ')' '{' <conteudo_laco> '}'
		
		//<operacao_relacional> ::= <complemento_operador> OperadoresRelacionais <complemento_operador> | <negar> Identificadores<Vetor>

		//<complemento_operador> ::= Identificadores<Vetor> | Numeros | CadeiaCaracteres | TipoBooleano
		
		//<conteudo_laco> ::= <comandos> <conteudo_laco> | <>
	}
}
