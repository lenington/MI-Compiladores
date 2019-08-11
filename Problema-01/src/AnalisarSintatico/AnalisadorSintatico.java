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
				token = s.nextToken();
				estruturaConstantes();

				if (token.equals("}")) {
					token = s.nextToken();
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

		if (tipo.contains(token)) {
			// chamar metodo de constantes; <Constantes>
			token = s.nextToken();
			constanteS();
			if (token.equals(";")) {
				token = s.nextToken();
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
		token = s.tokenType();

		if (token.equals("Identificador")) {
			token = s.nextToken().trim();
			if (token.equals("=")) {
				// chamar <constante><multiconst>
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
			// tratar erro
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
			System.out.println("entrou no if do escopoPrograma");
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
									}
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
		} else {
		}
	}

	public void listaParametros() {
		if (tipo.contains(token)) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {

				token = s.nextToken();
				maisParametros();
			}
		} else {
			return; // tratamento de vazio aqui
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
	}

	public void vetor() {
		// <Vetor> ::= '[' <OpI2><OpIndice> ']' <Matriz> | <>
		// <Matriz> ::= '[' <OpI2><OpIndice> ']' | <>
		if (token.equals("[")) {
			// <OpI2><OpIndice>
			token = s.nextToken();
			OpI2();
			OpIndice();
			System.out.println(token);
			if (token.equals("]")) {
				token = s.nextToken();
				matriz();
			}
		} else {
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
			// tratar o erro aqui
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
			}
		} else {
			token = s.getAtualToken();
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

		// token = s.nextToken();
		System.out.println(token);
		if (token.equals("escreva")) {
			token = s.nextToken();
			escreva();
			escopoMetodo();
		} else if (token.equals("leia")) {
			token = s.nextToken();
			leia();
			escopoMetodo();
		} else if (token.equals("se")) {
			se();
			escopoMetodo();
		} else if (token.equals("enquanto")) {
			token = s.nextToken();
			enquanto();
			escopoMetodo();
		} else if (token.equals("resultado")) {
			// FAZER AINDA
			escopoMetodo();
		}
		// FAZER RESTANTE...
		else {
			return; // para vazio <>
		}
	}

	public void comandos() {
		// <comandos> ::= <leia> | <escreva> | <se> | <enquanto> |
		// <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> |
		// 'resultado' <retorno> ';'
		// token = s.nextToken();
		System.out.println(token);
		if (token.equals("escreva")) {
			token = s.nextToken();
			escreva();
			escopoMetodo();
		} else if (token.equals("leia")) {
			token = s.nextToken();
			leia();
			escopoMetodo();
		} else if (token.equals("se")) {

		} else if (token.equals("enquanto")) {
			token = s.nextToken();
			enquanto();

		} else if (token.equals("resultado")) {

		}

		else {
			return; // para vazio <>
		}
	}

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

			if (token.equals(")")) {
				token = s.nextToken();
				if (token.equals(";")) {
					token = s.nextToken();
					return;
				}
			}
		} else {
			// ERROR
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
		// token = s.nextToken();
		token = s.tokenType();

		if (token.equals("Incremento") || token.equals("Booleano") || token.equals("Expressao")) {
			token = s.nextToken();
			return;
		} else {
			// ERROR
		}

	}

	public void MaisParametroE() {
		// <MaisParametroE> ::= ',' <Param Escrita> | <>
		// token = s.nextToken();

		if (token.equals(",")) {
			token = s.nextToken();
			ParamEscrita();
		} else {
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
				}
			}
		} else {
			// ERROR
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
			// ERROR
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
				blocoSe();
				token = s.nextToken();
				if (token.equals("}")) {
					senao();
				} else {
					// ERROR
				}
			} else {
				// ERROR
			}
		} else {
			// ERROR
		}

	}

	public void blocoSe() {
		// <blocoSe> ::= <comandos><blocoSe> | <>
		token = s.nextToken();
		if (token.equals("escreva")) {
			escreva();
			blocoSe();
		} else if (token.equals("leia")) {
			leia();
			blocoSe();
		} else if (token.equals("se")) {
			se();
			blocoSe();
		} else if (token.equals("enquanto")) {
			enquanto(); // FAZER AINDA...
			blocoSe();
		} else if (token.equals("resultado")) {
			// FAZER AINDA...
			blocoSe();
		}
		// FAZER RESTANTE...
		else {
			return; // para vazio <>
		}
	}

	public void senao() {
		// <senao> ::= <> | 'senao' <condSenao> '{' <blocoSe> '}' <senao>
		token = s.nextToken();
		if (token.equals("senao")) {
			condSenao();
			token = s.nextToken();
			if (token.equals("{")) {
				blocoSe();
				token = s.nextToken();
				if (token.equals("}")) {
					senao();
				} else {
					// ERROR
				}
			} else {
				// ERROR
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
			token = s.nextToken();
			if (token.equals(")")) {
				return;
			} else {
				// ERROR
			}
		} else {
			// ERROR
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
		token = s.nextToken();
		token = s.tokenType();
		if (token.equals("Identificador")) {
			vetor();
		} else if (token.equals("Numeros") || token.equals("Cadeia de Caracteres") || token.equals("Booleano")) {
			return;
		} else {
			// ERROR
		}

	}

	public void op() {
		// <op> ::= OperadorAritmeticos <tipoTermo> <op>|<>
		token = s.nextToken();
		token = s.tokenType();
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
					// conteudoLaco();
					if (token.equals("}")) {
						token = s.nextToken();
						return;
					}

				}
			}
		}
	}

	private void operacaoRelacional() {
		
		if (token.trim().equals("!")){
			token = s.nextToken();
			String tokenType = s.tokenType();
			if (tokenType.equals("Identificador")) {
				token = s.nextToken();
				vetor(); 	
			}
		}
		else {
			complementoOperador();
			token = s.nextToken();
			complementoOperador();
		}

	}

	private void complementoOperador() {
		token = s.tokenType();
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
			// tratar erro
		}

	}

	private void conteudoLaco() {
		// TODO Auto-generated method stub
		// comandos();
		// conteudoLaco();

	}
	/* TERMINA AQUI A GRAMATICA DO ENQUANTO */
}
