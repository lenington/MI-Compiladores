package AnalisarSintatico;

import java.util.LinkedList;

public class AnalisadorSintatico {

	private TokenReader s;
	private LinkedList<String> tipo;
	private String token;
	private boolean hasError;
	private Erro_Sintatico er;

	public AnalisadorSintatico(TokenReader s) {
		this.s = s;
		tipo = new LinkedList<String>();
		tipo.add("real");
		tipo.add("inteiro");
		tipo.add("boleano");
		tipo.add("texto");
		tipo.add("vazio");
		hasError = false;
		er = new Erro_Sintatico();
	}
	
	public LinkedList<String> getErro_Sintatico() {
		return er.getErrosList();
	}
	
	public void programa() {

		token = s.nextToken();
		if (token.equals("programa")) {
			token = s.nextToken();
			if (token.equals("{")) {

				token = s.nextToken();
				blocoConstantes();
				escopoPrograma();

				if (token.equals("}")) {
					if (hasError == true)
						er.guardarErros(s.getLine(), "SEMSUCESSO");
					else
						er.guardarErros(s.getLine(), "SUCESSO");//System.out.println("SUCESSO");

				} else {
					er.guardarErros(s.getLine(), "}");
				}
				
			} else { // tratamento do erro caso nao encontre {
				er.guardarErros(s.getLine(), "{");
				token = s.nextToken();
				blocoConstantes();
				escopoPrograma();

				if (token.equals("}"))
					return; //System.out.println("SUCESSO");
				else // tratamento do erro caso nao encontre }
					er.guardarErros(s.getLine(), "}");

			}
		} else {
			er.guardarErros(s.getLine(), "programa");
		}
	}

	// *****************
	// *******DAQUI*********
	public void blocoConstantes() {

		if (token.equals("constantes")) {
			token = s.nextToken();
			if (token.equals("{")) {
				// chamar metodo<estrutura de constantes>
				token = s.nextToken();
				estruturaConstantes();

				if (token.equals("}")) {
					token = s.nextToken();
					return;
				}
				else {
					er.guardarErros(s.getLine(), " } ");
					token = s.nextToken();
					return;
				}
			} else {
				// tratar o erro da falta do { depois das constantes
				er.guardarErros(s.getLine(), " { ");
				token = s.nextToken();
				estruturaConstantes();

				if (token.equals("}")) {
					token = s.nextToken();
					return;
				} else {
					er.guardarErros(s.getLine(), "}");
					token = s.nextToken();
					return;
				}

			}

		} else {
			return; // senao vier uma "constantes" retorna
		}
	}

	public void estruturaConstantes() {

		if (tipo.contains(token)) {
			// chamar metodo de constantes; <Constantes>
			token = s.nextToken();
			constanteS();
			if (token.trim().equals(";")) {
				token = s.nextToken();
				estruturaConstantes();
				// tem que tratar essa parte ainda. Pois depois de um ponto e virgula, pode vir
				// mais constantes ou nao
			} else {
				er.guardarErros(s.getLine(), ", ou ;");
				hasError = true;
				return;
			}
		} else if (token.equals("}")) {
			return;
		} else {
			// tratamento de ERRO caso nao encontre um identificador no inicio das
			// constantes
			er.guardarErros(s.getLine(), " tipo ou  um }");
			hasError = true;
			token = s.nextToken();
			constanteS();
			if (token.equals(";")) {
				token = s.nextToken();
				estruturaConstantes();

			} else {
				// tratamento de erro, caso nao venha o ponto e virgula
				er.guardarErros(s.getLine(), ";");
				token = s.nextToken();
				estruturaConstantes();
			}

		}
	}

	public void constanteS() {
		token = s.tokenType();

		if (token.equals("Identificador")) {
			token = s.nextToken().trim();
			if (token.equals("=")) {
				// chamar <constante><multiconst>
				token = s.nextToken();
				constante();
				multiconst();
			} else {
				// tratando erro de nao encontrar o =
				er.guardarErros(s.getLine(), "=");
				token = s.nextToken();
				constante();
				multiconst();
			}
		} else {
			//tratamento de erro caso nao encontre um identificador
			er.guardarErros(s.getLine(), "identificador");
			hasError = true;
			token = s.nextToken().trim();
			if (token.equals("=")) {
				// chamar <constante><multiconst>
				token = s.nextToken();
				constante();
				multiconst();
			} else {
				// tratando erro de nao encontrar o =
				er.guardarErros(s.getLine(), "=");
				token = s.nextToken();
				constante();
				multiconst();
			}
		}
	}

	public void constante() {
		token = s.tokenType();

		if (token.equals("Identificador")) {
			token = s.nextToken();
			return;
		} else if (token.equals("Numero")) {
			token = s.nextToken();
			return;
		} else if (token.matches("Cadeia de Caractere")) {
			token = s.nextToken();
			return;
		} else {
			er.guardarErros(s.getLine(), " numero ou identificador ou caracteres");
		}
	}

	public void multiconst() {

		if (token.equals(",")) {
			token = s.nextToken();
			constanteS();
		} else
			return;

	}

	// *********************************************
	// ******ATE AQUI. EH O TRATAMENTO DAS CONSTANTES***

	public void escopoPrograma() {
		metodo();
		if (token.equals("metodo")) {
			escopoPrograma();

		} else {
			return;
		}
		// tem que tratar vazio ainda

	}

	public void metodo() {
		/*
		 * <metodo> ::= 'metodo'
		 * Identificadores'('<listaParametros>'):'Tipo'{'<DeclaracaoVariaveis>
		 * <escopoMetodo>'}' <listaParametros> ::= Tipo Identificadores <maisParametros>
		 * | <> <maisParametros> ::= ','<listaParametros> | <>
		 */
		if (token.trim().equals("metodo")) {
			token = s.nextToken();
			if (s.tokenType().equals("Identificador") || token.equals("principal")) {
				token = s.nextToken();
				if (token.equals("(")) {
					token = s.nextToken();
					listaParametros();
					if (token.equals(")")) {
						token = s.nextToken();
						if (token.equals(":")) {
							token = s.nextToken();
							if (tipo.contains(token)) {
								token = s.nextToken();
								if (token.equals("{")) {
									token = s.nextToken();
									declaracaoVariaveis();
									escopoMetodo();
									if (token.equals("}")) {
										token = s.nextToken();
										return;
									} else {
										er.guardarErros(s.getLine(), "}");
										token = s.nextToken();
										return;
									}
								} else {
									er.guardarErros(s.getLine(), "{");
									chamadaParaTratarErroMetodo();
								}
							} else {
								er.guardarErros(s.getLine(), "tipo");
								chamadaParaTratarErroMetodo();
							}

						} else {
							// tratar o erro por nao encontrar o :
							er.guardarErros(s.getLine(), ":");
							chamadaParaTratarErroMetodo();
						}

					} else {
						// tratar o erro de nao encontrar o )
						er.guardarErros(s.getLine(), ")");
						chamadaParaTratarErroMetodo();
					}
				} else {
					// tratar o erro de nao encontrar o (
					er.guardarErros(s.getLine(), "(");
					chamadaParaTratarErroMetodo();
				}

			} else {
				// tratar o erro de nao encontrar o identificador em seguida
				er.guardarErros(s.getLine(), "identificador");
				chamadaParaTratarErroMetodo();
			}
		}
		// erro do metodo
		else {
			er.guardarErros(s.getLine(), "metodo");
			chamadaParaTratarErroMetodo();
		}
	}

	/*
	 * esse metodo eh para tratar o erro da chamada de metodo e para nao ficar
	 * reptindo codigo
	 */
	public void chamadaParaTratarErroMetodo() {
		hasError = true;
		s.ignoreLine();
		declaracaoVariaveis();
		escopoMetodo();
		if (token.equals("}")) {
			token = s.nextToken();
			return;
		} else {
			er.guardarErros(s.getLine(), "}");
			token = s.nextToken();
			return;
		}
	}
	/* TERMINA AQUI */

	public void listaParametros() {
		if (tipo.contains(token)) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {

				token = s.nextToken();
				maisParametros();
			}
		} else if (token.equals(")")) {
			return; // tratamento de vazio aqui
		} else {
			er.guardarErros(s.getLine(), "tipo");
		}
	}

	public void declaracaoVariaveis() {
		if (token.equals("variaveis")) {
			token = s.nextToken();
			if (token.equals("{")) {
				token = s.nextToken();
				VarV();
				
				if (token.equals("}")) {
					token = s.nextToken();
					return;
				}
				
				else {//tratamento do erro caso nao encontre o }
					er.guardarErros(s.getLine(), "}");
					token = s.nextToken();
					return;
				}
				
			}else { //tratamento caso nao encontre o { depois de variaveis
				
				er.guardarErros(s.getLine(), "{");
				token = s.nextToken();
				VarV();
				if (token.equals("}")) {
					token = s.nextToken();
					return;
				}
				
				else {
					er.guardarErros(s.getLine(), "}");
					token = s.nextToken();
					return;
				}
			}
			
		} else {
			return;
			// esse else eh para tratar o vazio do <DeclaracaoVariaveis> ::= 'variaveis'
			// '{'<VarV>'}' | <>
		}
		// escopoMetodo();
	}

	public void VarV() {
		if (tipo.contains(token)) {
			token = s.nextToken();
			complementoV();
			maisVariaveis();
		} else {
			//tratamento de erro
			er.guardarErros(s.getLine(), "tipo");
			token = s.nextToken();
			complementoV();
			maisVariaveis();
			// tratar o erro de nao encontrar o tipo aqui --> <VarV> ::= Tipo <complementoV>
			// <MaisVariaveis>
		}
	}

	public void complementoV() {
		token = s.tokenType();
		if (token.equals("Identificador")) {
			token = s.nextToken();
			vetor();
			variavelMesmoTipo();
		}
		else {
			er.guardarErros(s.getLine(), "identificador");
			token = s.nextToken();
			vetor();
			variavelMesmoTipo();
		}
	}

	public void vetor() {
		// <Vetor> ::= '[' <OpI2><OpIndice> ']' <Matriz> | <>
		// <Matriz> ::= '[' <OpI2><OpIndice> ']' | <>
		if (token.equals("[")) {
			// <OpI2><OpIndice>
			token = s.nextToken();
			OpI2();
			OpIndice();
			if (token.equals("]")) {
				if(s.lookAhead().equals("[") || s.lookAhead().equals(";") ) {
					token = s.nextToken();
					matriz();
				}
				else
					token = s.nextToken(); //voltar
			}
			else {
				hasError = true;
				er.guardarErros(s.getLine(), "]");
				matriz();
			}
		}else if (token.equals("]")) {
			hasError = true;
			er.guardarErros(s.getLine(), "[");
			token = s.nextToken();
			matriz();
		}
		else {
			return; // aqui eh onde trata o vazio
		}

	}

	private void OpIndice() {
		// <OpIndice> ::= OperadoresAritmeticos <OpI2> <OpIndice> | <>
		token = s.tokenType();
		if (token.equals("Operador Aritmetico")) {
			token = s.nextToken();
			OpI2();
			OpIndice();
		} else {
			token = s.getAtualToken();
			return; // tratamento de vazio aqui
		}

	}

	private void OpI2() {
		// <OpI2> ::= Numeros | Identificadores

		token = s.tokenType();

		if (token.equals("Numero") || token.equals("Identificador")) {
			token = s.nextToken();
			return;
		} else {
			er.guardarErros(s.getLine(), " Numero ou identificador ");
			token = s.nextToken();
			return;
		}
	}

	private void matriz() {
		// <Matriz> ::= '[' <OpI2><OpIndice> ']' | <>
		if (token.equals("[")) {
			token = s.nextToken();
			OpI2();
			OpIndice();
			if (token.equals("]")) {
				token = s.nextToken();
				return;
			}else {
				er.guardarErros(s.getLine(), " ] ");
				token = s.nextToken();
				return;
			}
		}else if (token.equals("]")) {
			er.guardarErros(s.getLine(), "[");
			token = s.nextToken();
			return;
		}
		else {
			//token = s.nextToken(); //antes pegava o tokenAtual
			return;// tratar vazio
		}
	}

	public void variavelMesmoTipo() {
		if (token.equals(",")) {
			token = s.nextToken();
			complementoV();
		}

		else if (token.equals(";")) {
			token = s.nextToken();
			return;
		} else {
			hasError = true;
			er.guardarErros(s.getLine(), " ; ");
			token = s.nextToken();
			return;
			// tratar erro aqui;
		}
	}

	public void maisVariaveis() {
		// <MaisVariaveis> ::= <VarV> | <>
		if (tipo.contains(token)) {
			token = s.nextToken();
			complementoV();
			maisVariaveis();
		} else {
			return;
		}
	}

	public void escopoMetodo() {
		// <escopoMetodo> ::= <comandos><escopoMetodo> | <>
		// <comandos> ::= <leia> | <escreva> | <se> | <enquanto> |
		// <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> |
		// 'resultado' <retorno> ';'
		
		comandos("metodo");
	}

	private void novoMetodo() {
		if (token.equals("(")) {
			token = s.nextToken();
			var();
			if (token.trim().equals(")")) {
				token = s.nextToken();
				if (token.equals(";")) {
					token = s.nextToken();
				} else {
					er.guardarErros(s.getLine(), ";");
					hasError = true;
					return;
				}
			}
		} else {// rever isso
			vetor();
		}
	}

	/* AQUI COMECA A GRAMATICA DA CHAMADA DE METODO */
	private void var() {
		if (s.tokenType().equals("Identificador")) {
			token = s.nextToken();
			fatVar();
		} else if (s.tokenType().equals("Numero") || token.equals("verdadeiro") || token.equals("falso")
				|| s.tokenType().equals("Cadeia de Caractere")) {
			token = s.nextToken();
			maisVariavel();
			return;
		} else if (s.tokenType().equals("Delimitador")) {
			return; // tratamento de vazios
		}
	}

	private void fatVar() {
		if (token.equals("(")) {
			token = s.nextToken();
			var();
			if (token.equals(")")) {
				token = s.nextToken();
				maisVariavel();
			}
		} else {
			vetor();
			maisVariavel();
		}

	}

	private void maisVariavel() {
		if (token.equals(",")) {
			
			token = s.nextToken();
			var();
		} else {
			return; // tratamento de vazio
		}
	}
	/* AQUI TERMINA A GRAMAATICA DE CHAAMDA DE METODO */

	public void maisParametros() {
		if (token.equals(",")) {
			token = s.nextToken();
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
		// <escreva> ::= 'escreva' '(' <Param Escrita> ')'';'
		// <Param Escrita> ::= <verificaCaso><MaisParametroE>
		// <MaisParametroE> ::= ',' <Param Escrita> | <>
		if (token.equals("(")) {
			token = s.nextToken();
			ParamEscrita();
			token = s.getAtualToken(); 
			if (token.equals(")")) { 
				token = s.nextToken(); 
				if (token.equals(";")) {
					token = s.nextToken();
					return;
				} else {
					er.guardarErros(s.getLine(), " ;");
					hasError = true;
					return;
				}
			} else {
				er.guardarErros(s.getLine(), " )");
				hasError = true;
				return; // TRATAR ESSE ERRO!
			}
		} else {
			er.guardarErros(s.getLine(), " (");
			hasError = true;
			return; //TRATAR ESSE ERRO!
		}
	}

	public void ParamEscrita() {
		// <Param Escrita> ::= <verificaCaso><MaisParametroE>
		verificaCaso(); 
		MaisParametroE();
	}

	public void verificaCaso() {
		// <verificaCaso> ::= <incremento>
		// | <expressao>
		// | <booleano>

		// ESSA PORRA TA CHEIA DE AMBIGUIDADE, PQP KKKKKK
		String token_ahead = s.lookAhead();

		if ((token.equals("(") && (token_ahead.equals("Incrementador") || token_ahead.equals("Identificador")))
				|| token.equals("Incrementador") || token.equals("Identificador")) {
			incremento();
		} else if ((token.equals("(") && (token_ahead.equals("!") || token_ahead.equals("Booleano")))
				|| token.equals("Booleano") || token.equals("!")) {
			booleano();
		} else {
			expressao(); // totalmente ambiguo
		}

	}

	public void incremento() {
		// <incremento> ::= '('Incrementador Identificadores<Vetor> ')'
		// | '('Identificadores<Vetor> Incrementador')'
		// | Incrementador Identificadores<Vetor>
		// | Identificadores<Vetor> Incrementador
		System.out.println("token type: " + s.tokenType() + " token: " + s.getAtualToken());
		if (token.equals("(")) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Incrementador")) {
				token = s.nextToken();
				token = s.tokenType();
				if (token.equals("Identificador")) {
					vetor();
					token = s.nextToken();
					if (token.equals(")")) {
						return;
					} else { //ERROR
						er.guardarErros(s.getLine(), " )");
						hasError = true;
						return;
					}
				} 
			} else if (s.tokenType().trim().equals("Identificador")) {
				vetor();
				token = s.nextToken();
				token = s.tokenType();
				if (token.equals("incrementador")) {
					return;
				} else { //ERROR
					er.guardarErros(s.getLine(), " Incrementador");
					hasError = true;
					return;
				}
			} else { //ERROR
				er.guardarErros(s.getLine(), " Identificador");
				hasError = true;
				return;
			}
		} else if (token.equals("Incrementador")) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {
				vetor();
				return;
			} else { //ERROR
				er.guardarErros(s.getLine(), " Incrementador");
				hasError = true;
				return;
			}
		} else if (token.equals("Identificador")) {
			vetor();
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("incrementador")) {
				return;
			} else { //ERROR
				er.guardarErros(s.getLine(), " Incrementador");
				hasError = true;
				return;
			}
		} else { // ERROR
			er.guardarErros(s.getLine(), " Identificador");
			hasError = true;
			return;
		}
	}

	public void booleano() {
		// <booleano> ::= '('TipoBooleano')'
		// | '(''!' TipoBooleano')'
		// | '(''!' Identificadores<Vetor> ')'
		// | TipoBooleano
		// | '!' TipoBooleano
		// | '!' Identificadores<Vetor>
		// token = s.nextToken();

		if (token.equals("(")) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Booleano")) {
				token = s.nextToken();
				if (token.equals(")")) {
					return;
				}
			} else if (token.equals("!")) {
				token = s.nextToken();
				token = s.tokenType();
				if (token.equals("Booleano")) {
					token = s.nextToken();
					if (token.equals(")")) {
						return;
					}
				} else if (token.equals("Identificador")) {
					vetor();
					token = s.nextToken();
					if (token.equals(")")) {
						return;
					}
				} else {
					// ERROR
					er.guardarErros(s.getLine(), " Identificador");
					hasError = true;
					return;
				}
			}
		} else if (token.equals("Booleano")) {
			token = s.nextToken();
		} else if (token.equals("!")) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Booleano")) {
				token = s.nextToken();
				if (token.equals(")")) {
					return;
				}
			} else if (token.equals("Identificador")) {
				vetor();
				token = s.nextToken();
				if (token.equals(")")) {
					return;
				}
			} else {
				// ERROR
				er.guardarErros(s.getLine(), " Identificador");
				hasError = true;
				return;
			}
		} else {
			// ERROR
			er.guardarErros(s.getLine(), " !");
			hasError = true;
			return;
		}
	}

	public void expressao() {
		// <expressao> ::= '('<expressao>')'
		// | '('<expressao>')' OperadoresAritmeticos <expressao>
		// | <operador> <maisOperacoes>
		// token = s.nextToken();

		if (token.equals("(")) {
			token = s.nextToken();
			expressao();
			token = s.nextToken();
			if (token.equals(")")) {
				token = s.nextToken();
				token = s.tokenType();
				if (token.equals("Operador Aritimetico")) {
					expressao();
				} else {
					return;
				}
			}
		} else {
			operador();
			maisOperacoes();
		}

	}

	public void maisOperacoes() {
		// <maisOperacoes> ::= OperadoresAritmeticos <maisOperacoes>
		// | OperadoresAritmeticos <expressao>
		// | <>
		// token = s.tokenType();

		if (token.equals("Operador Aritmetico")) {
			token = s.nextToken();
			if (token.equals("Operador Aritmetico")) {
				maisOperacoes();
			} else {
				expressao();
			}
		} else {
			return; // vazio
		}
	}

	public void operador() {
		// <operador> ::= Numeros
		// | CadeiaCaracteres
		// | Identificadores<Vetor>
		// | <chamadaDeMetodo>
		token = s.tokenType();
		if (token.equals("Numero") || token.equals("Cadeia de Caractere")) {
			token = s.nextToken();

		} else if (token.equals("Identificador")) {
			token = s.nextToken();
			if (token.equals("(")) {
				novoMetodo();
			} else {
				vetor();
			}
		}
	}

	public void MaisParametroE() {
		// <MaisParametroE> ::= ',' <Param Escrita> | <>
		// token = s.nextToken();
		if (token.equals(",")) {
			token = s.nextToken(); 
			if(s.lookAhead().equals("[")) { 
				token = s.nextToken(); 
				vetor();
				token = s.nextToken(); 
			}
			ParamEscrita();
		} else {
			//token = s.nextToken(); 
			return;
		}
	}

	public void leia() {
		// <leia> ::= 'leia' '(' <conteudoLeia> ')'';'
		// <conteudoLeia> ::= Identificadores<Vetor> <lerMais>
		// <lerMais> ::= ',' <conteudoLeia> | <>

		// token = s.nextToken();
		if (token.equals("(")) {
			token = s.nextToken();
			conteudoLeia();

			if (token.equals(")")) {
				token = s.nextToken();
				if (token.equals(";")) {
					token = s.nextToken();
					return;
				} else {
					er.guardarErros(s.getLine(), " ;");
					hasError = true;
					return;
				}
			} else {
				er.guardarErros(s.getLine(), " )");
				hasError = true;
				return;
			}
		} else {
			// ERROR
			er.guardarErros(s.getLine(), " (");
			hasError = true;
			return;
		}
	}

	public void conteudoLeia() {
		// <conteudoLeia> ::= Identificadores<Vetor> <lerMais>
		// token = s.nextToken();
		token = s.tokenType();

		if (token.equals("Identificador")) {
			token = s.nextToken();
			vetor();
			lerMais();
		} else {
			er.guardarErros(s.getLine(), " Identificador");
			hasError = true;
			return;
		}
	}

	public void lerMais() {
		// <lerMais> ::= ',' <conteudoLeia> | <>
		// token = s.nextToken();

		if (token.equals(",")) {
			token = s.nextToken();
			conteudoLeia();
		} else {
			return;
		}
	}

	public void se() {
		// <se> ::= 'se' <condse> 'entao''{' <blocoSe> '}' <senao>
		// <condse> ::= '(' <cond> <maisCond> ')'
		// <cond> ::= <termo> OperadoresRelacionais <termo>
		// | <negar> Identificadores<Vetor>
		condse();
		token = s.nextToken();

		if (token.equals("entao")) {
			token = s.nextToken(); 
			if (token.equals("{")) {
				token = s.nextToken(); 
				blocoSe(); 
				if (token.equals("}")) { 
					senao();
				} else { //ERROR
					er.guardarErros(s.getLine(), " }");
					hasError = true;
					return;
				}
			} else {
				// ERROR
				er.guardarErros(s.getLine(), " {");
				hasError = true;
				return;
			}
		} else {
			// ERROR
			er.guardarErros(s.getLine(), " entao");
			hasError = true;
			return;
		}

	}

	public void verBloco(String bloco) {
		if (bloco.equals("se")) 
			blocoSe();
		if (bloco.equals("enquanto"))
			conteudoLaco();
		if (bloco.equals("metodo"))
			escopoMetodo();
	}

	public void comandos(String bloco) {
		// <comandos> ::= <leia> | <escreva> | <se> | <enquanto> |
		// <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> |
		// 'resultado' <retorno> ';'
		
		if (token.equals("escreva")) {
			token = s.nextToken();
			escreva();
			verBloco(bloco);
		} else if (token.equals("leia")) {
			token = s.nextToken();
			leia();
			verBloco(bloco);
		} else if (token.equals("se")) { 
			se();
			verBloco(bloco);
		} else if (token.equals("enquanto")) {
			token = s.nextToken();
			enquanto();
			verBloco(bloco);
		} else if (token.equals("resultado")) { 
			token = s.nextToken();
			retorno();
			verBloco(bloco);
		} else if (s.tokenType().equals("Identificador")) { 
			//token = s.nextToken();
			if (s.lookAhead().equals("(")) {
				// <chamadaDeMetodo> ::= Identificadores'('<var>')'
				token = s.nextToken();
				novoMetodo(); // chamada de metodo
				verBloco(bloco);
			} else {
				// <incrementador> ::= Identificadores<Vetor> Incrementador ';'
				// <atribuicaoDeVariavel> ::= Identificadores<Vetor> '=' <verificaCaso>';'
				

				if(s.lookAhead().equals("[")) {
					token = s.nextToken();
					vetor();
				}
				
				if (s.lookAhead().trim().equals("=")) {
					atribuicaoDeVariavel();
					verBloco(bloco);
				} else if (s.lookAhead().equals("Identificador")) {
					incremento();
					token = s.nextToken();
					if (token.equals(";")) {
						return;
					} else {
						er.guardarErros(s.getLine(), " ;");
						hasError = true;
					}
					verBloco(bloco);
				} else {
					// ERROR
					System.out.println("entrou aqui no erro");
					er.guardarErros(s.getLine(), " Identificador");
					hasError = true;
					return;
				}
			}
		} else if(s.tokenType().equals("Comentario de Linha") || s.tokenType().equals("Comentario de Bloco")) {
			token = s.nextToken(); 
			comandos(bloco);
			return ;
		} else {
			return; // para vazio <>
		}
	}

	public void blocoSe() {
		// <blocoSe> ::= <comandos><blocoSe> | <>
		// <comandos> ::= <leia> | <escreva> | <se> | <enquanto> |
		// <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> |
		// 'resultado' <retorno> ';'
		comandos("se");
	}

	public void retorno() {
		verificaCaso(); 
		//token = s.nextToken(); 
		if (token.equals(";")) {
			token = s.nextToken();
			return;
		} else {
			// ERROR
			er.guardarErros(s.getLine(), " ;");
			hasError = true;
			token = s.nextToken();
			return;
		}
	}

	public void atribuicaoDeVariavel() {
		// <atribuicaoDeVariavel> ::= Identificadores<Vetor> '=' <verificaCaso>';'
		token = s.nextToken();
		if (token.trim().equals("=")) {
			token = s.nextToken();
			verificaCaso();
			token = s.nextToken(); 
			if (token.equals(";")) { 
				token = s.nextToken();
				return;
			} else if(s.tokenType().equals("Identificador") || s.tokenType().equals("Operador Aritmetico") || 
					s.tokenType().equals("Numeral")){
				token = s.nextToken(); 
				
				if(s.tokenType().equals("Identificador") || s.tokenType().equals("Numeral")){
					token = s.nextToken(); 
				}
				
				if (token.equals(";")) { 
					token = s.nextToken();
					return;
				} else {
					// ERROR
					er.guardarErros(s.getLine(), " ;");
					hasError = true;
					return;
				}
			} else {
				// ERROR
				er.guardarErros(s.getLine(), " Identificador, Operador Aritmetico ou Numeral");
				hasError = true;
				return;
			}
		} else {
			// ERROR
			er.guardarErros(s.getLine(), " =");
			hasError = true;
			return;
		}

	}

	public void senao() {
		// <senao> ::= <> | 'senao' <condSenao> '{' <blocoSe> '}' <senao>
		token = s.nextToken(); 
		if (token.equals("senao")) { 
			condSenao();
			if(!token.equals("{")) {
				token = s.nextToken();
			}
			if (token.equals("{")) {
				token = s.nextToken();
				blocoSe();
				if (token.equals("}")) { 
					senao(); 
				} else {
					// ERROR
					er.guardarErros(s.getLine(), " }");
					hasError = true;
					return;
				}
			} else {
				// ERROR
				er.guardarErros(s.getLine(), " {");
				hasError = true;
				return;
			}
		} else {
			return;
		}
	}

	public void condSenao() {
		// <condSenao> ::= 'se' <condse> 'entao' | <>
		token = s.nextToken();
		if (token.equals("se")) {
			condse();
			token = s.nextToken();
			if (token.equals("entao")) {
				return;
			} else {
				// ERROR
				er.guardarErros(s.getLine(), " entao");
				hasError = true;
				return;
			}
		} else { 
			return;
		}
	}

	public void condse() {
		// <condse> ::= '(' <cond> <maisCond> ')'
		token = s.nextToken();
		
		if (token.equals("(")) {
			cond(); 
			maisCond();
			if(!s.getAtualToken().equals(")")) {
				token = s.nextToken();
			}
			if (token.equals(")")) { 
				return;
			} else {
				// ERROR
				//er.guardarErros(s.getLine(), " )");
				//hasError = true;
				//return;
			}
		} else {
			// ERROR
			er.guardarErros(s.getLine(), " (");
			hasError = true;
			return;
		}
	}

	public void cond() {
		// <cond> ::= <termo> OperadoresRelacionais <termo>
		// | <negar> Identificadores<Vetor>
		token = s.nextToken();
		if (token.equals("!")) { // <negar>
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador"))
				vetor();
		} else {
			termo();
			token = s.tokenType(); 
			if (token.equals("Operador Relacional") || token.equals("Operador Logico") ) {
				termo();
			} else {
				// ERRO
				er.guardarErros(s.getLine(), " Operador Relacional");
				hasError = true;
				return;
			}
		}
	}

	public void termo() {
		// <termo> ::= <tipoTermo> <op>
		// <tipoTermo> ::= Identificadores<Vetor>
		// | Numeros
		// | CadeiaCaracteres
		// | TipoBooleano
		// <op> ::= OperadorAritmeticos <tipoTermo> <op>|<>
		tipoTermo();
		op();

	}

	public void tipoTermo() {
		// <tipoTermo> ::= Identificadores<Vetor>
		// | Numeros
		// | CadeiaCaracteres
		// | TipoBooleano
		// token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Identificador")) {
			vetor();
		} else if (token.equals("Numeros") || token.equals("Cadeia de Caracteres") || token.equals("Booleano")) {
			return;
		} else {
			// ERROR
			//er.guardarErros(s.getLine(), " Numeros, Cadeia de Caracteres ou Booleano");
			//hasError = true;
			//return;
		}

	}

	public void op() {
		// <op> ::= OperadorAritmeticos <tipoTermo> <op>|<>
		token = s.nextToken();
		token = s.tokenType();
		;
		if (token.equals("Operador Aritmetico")) {
			tipoTermo();
			op();
		} else
			return;
	}

	public void maisCond() {
		// <maisCond> ::= OperadoresLogicos <cond> <maisCond> | <>
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Operador Logico")) {
			cond();
			maisCond();
		} else
			return;

	}

	/* GRAMATICA DO ENQUANTO */
	public void enquanto() {
		/*
		 * <enquanto> ::= 'enquanto' '(' <operacao_relacional> ')' '{' <conteudo_laco>
		 * '}' <operacao_relacional> ::= <complemento_operador> OperadoresRelacionais
		 * <complemento_operador> | <negar> Identificadores<Vetor>
		 * <complemento_operador> ::= Identificadores<Vetor> | Numeros |
		 * CadeiaCaracteres | TipoBooleano <conteudo_laco> ::= <comandos>
		 * <conteudo_laco> | <>
		 */

		if (token.equals("(")) {
			token = s.nextToken();
			operacaoRelacional();
			if (token.equals(")")) {
				token = s.nextToken();
				if (token.equals("{")) {
					token = s.nextToken();
					conteudoLaco();
					if (token.equals("}")) {
						token = s.nextToken();
						return;
					} else {
						hasError = true;
						er.guardarErros(s.getLine(), "}");
						token = s.nextToken();
						return;
					}
				} else {
					hasError = true;
					er.guardarErros(s.getLine(), "{");
					s.ignoreLine();
					token = s.nextToken();
					conteudoLaco();
				}

			} else {
				hasError = true;
				er.guardarErros(s.getLine(), ")");
				s.ignoreLine();
				token = s.nextToken();
				conteudoLaco();
			}
		} else {
			hasError = true;
			er.guardarErros(s.getLine(), "(");
			s.ignoreLine();
			token = s.nextToken();
			conteudoLaco();
		}
	}

	private void operacaoRelacional() {
		//System.out.println("operacaoRelacional >>" +token);
		if (token.trim().equals("!")) {
			token = s.nextToken();
			String tokenType = s.tokenType();
			if (tokenType.equals("Identificador")) {
				token = s.nextToken();
				vetor();
			}
		} else {
			complementoOperador();
			token = s.nextToken();
			complementoOperador();
		}

	}

	private void complementoOperador() {
		//System.out.println("Complemento >>" +token);
		token = s.tokenType(); //System.out.println("TYPE >>> "+token);
		if (token.equals("Identificador")) {
			token = s.nextToken();
			vetor();
		} else if (token.equals("Numero")) {
			token = s.nextToken();
			return;
		} else if (token.equals("Cadeia caractere")) {
			token = s.nextToken();
			return;
		} else if (token.equals("boleano")) {
			token = s.nextToken();
			return;
		} else {
			hasError = true;
			er.guardarErros(s.getLine(), "nao esperado valor: " + s.getAtualToken());
			token = s.nextToken();
		}

	}

	private void conteudoLaco() {
		comandos("enquanto");
	}
	/* TERMINA AQUI A GRAMATICA DO ENQUANTO */
}