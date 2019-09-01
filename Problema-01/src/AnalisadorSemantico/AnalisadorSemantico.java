package AnalisadorSemantico;

import java.util.LinkedList;

import AnalisarSintatico.TokenReader;

public class AnalisadorSemantico {

	private TokenReader s;
	private LinkedList<String> tipo;
	private TabelaSemantica tabSem;
	private VerificadorCasos vc;
	private String token;

	public AnalisadorSemantico(TokenReader s) {
		this.s = s;
		tipo = new LinkedList<String>();
		tabSem = new TabelaSemantica();
		vc = new VerificadorCasos();
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

				if (token.equals("}"))
					System.out.println("SUCESSO");
				tabSem.printTabela();
			}
		}
	}

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
			}

		} else
			return; // senao vier uma "constantes" retorna
	}

	public void estruturaConstantes() {

		if (tipo.contains(token)) {
			// chamar metodo de constantes; <Constantes>
			vc.setTipo(token); // inserindo o tipo que pode ser real inteiro texto ou boleano
			token = s.nextToken();
			constanteS();
			if (token.trim().equals(";")) {
				token = s.nextToken();
				estruturaConstantes();
			}
		} else if (token.equals("}")) {
			return;
		}
	}

	public void constanteS() {
		token = s.tokenType();

		if (token.equals("Identificador")) {
			vc.setNomeConstante(s.getAtualToken()); // inserindo o nome da variavel dentro da classe para depois inserir na tabela
			
			if (tabSem.temConstVar(s.getAtualToken()))
				System.out.println("Error Semantico. Possui duas variaveis ou constantes identificas");
			
			token = s.nextToken().trim();
			if (token.equals("=")) {
				// chamar <constante><multiconst>
				token = s.nextToken();

				vc.setValor(token);
				vc.setTokenType(s.tokenType()); // insere o tipo da variavel classificada: Numero, cadeia Caractere e
												// etc

				constante();

				// analisa a semantica das entradas e salva na tabela
				if (!vc.semanticaConstante())
					System.out.println("Tem erro semantico. Na constante");
				else 
					tabSem.inserirTabela(vc.getNomeConstante(), vc.getTokenType(), "constante", vc.getTipo(),vc.getValor(), "", "",false);
					/////////////////////////////////////////////////////////////////////////////////////////
				
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
		}
	}

	public void multiconst() {

		if (token.equals(",")) {
			token = s.nextToken();
			constanteS();
		} else
			return;
	}
	/* AQUI FINALIZA O BLOCO DAS CONSTANTES */

	/* AQUI INICIA O BLOCO DO ESCOPO DO PROGRAMA */
	public void escopoPrograma() {
		vc.zerarStrings();
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
									// escopoMetodo();
									if (token.equals("}")) {
										token = s.nextToken();
										return;
									}
								}
							}

						}

					}
				}

			}
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
		} else if (token.equals(")")) {
			return; // tratamento de vazio aqui
		}
	}

	public void maisParametros() {
		if (token.equals(",")) {
			token = s.nextToken();
			listaParametros();
		} else
			return; // retorno de vazio
	}

	public void declaracaoVariaveis() {
		if (token.equals("variaveis")) {
			token = s.nextToken();
			if (token.equals("{")) {
				token = s.nextToken();
				vc.setTipo(token); // inserindo o tipo do variavel na classe. real, inteiro...
				VarV();

				if (token.equals("}")) {
					token = s.nextToken();
					return;
				}
			}
		} else
			return;// esse else eh para tratar o vazio do <DeclaracaoVariaveis> ::= 'variaveis' //
					// '{'<VarV>'}' | <>
	}

	public void VarV() {
		if (tipo.contains(token)) {
			token = s.nextToken();
			vc.setNomeConstante(token);
			complementoV();
			maisVariaveis();
		}
	}

	public void complementoV() {
		token = s.tokenType();
		if (token.equals("Identificador")) {
			vc.setNomeConstante(s.getAtualToken());
			
			if (tabSem.temConstVar(s.getAtualToken()))
				System.out.println("erro semantico. Ja possui uma constante ou variavel com esse nome. Aqui ta dentro de variaveis" );
			
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
			vc.setValorVetorPrimeiro(token);
			OpI2();
			OpIndice();
			if (token.equals("]")) {
				if (s.lookAhead().equals("[") || s.lookAhead().equals(";")) {
					token = s.nextToken();
					matriz();
				}
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

		if (token.equals("Numero")) {
			if(vc.sematicaVetor() == false)
				System.out.println("Possui erro sematico. Numero possui pontos!");
			token = s.nextToken();
			return;
		}else if (token.equals("Identificador")) {
			if (tabSem.validaIdentificadorVetor(s.getAtualToken()) == false)
				System.out.println("Possui erro sematico. Identificador nao declarado");
			token = s.nextToken();
			return;
		}
	}

	private void matriz() {
		// <Matriz> ::= '[' <OpI2><OpIndice> ']' | <>
		if (token.equals("[")) {
			token = s.nextToken();
			vc.setValorVetorSegundo(token);
			OpI2();
			OpIndice();
			if (token.equals("]")) {
				token = s.nextToken();
				return;
			}
		} else {
			// token = s.nextToken(); //antes pegava o tokenAtual
			return;// tratar vazio
		}
	}

	public void variavelMesmoTipo() {
		if (token.equals(",")) {
			tabSem.inserirTabela(vc.getNomeConstante(), vc.getTokenType(), "variavel", vc.getTipo(), "",vc.getValorVetorPrimeiro(), vc.getValorVetorSegundo(), false);
			vc.zerarVetores();
			token = s.nextToken();
			complementoV();
		}

		else if (token.equals(";")) {
			tabSem.inserirTabela(vc.getNomeConstante(), vc.getTokenType(), "variavel", vc.getTipo(), "",vc.getValorVetorPrimeiro(), vc.getValorVetorSegundo(), false);
			vc.zerarStrings();
			token = s.nextToken();
			return;
		}
	}

	public void maisVariaveis() {
		// <MaisVariaveis> ::= <VarV> | <>
		if (tipo.contains(token)) {
			vc.setTipo(token);
			token = s.nextToken();
			complementoV();
			maisVariaveis();
		} else
			return;
	}

	/* AQUI FINALIZA O BLOCO DO ESCOPO DO PROGRAMA */

}
