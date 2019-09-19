package AnalisadorSemantico;

import java.util.LinkedList;

import AnalisarSintatico.TokenReader;

public class AnalisadorSemantico {

	private TokenReader s;
	private LinkedList<String> tipo;
	private TabelaSemantica tabSem;
	private VerificadorCasos vc;
	private VerificadorCasosMetodos vcm;
	private String token;

	public AnalisadorSemantico(TokenReader s) {
		this.s = s;
		tipo = new LinkedList<String>();
		tabSem = new TabelaSemantica();
		vc = new VerificadorCasos();
		vcm = new VerificadorCasosMetodos();
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
				System.out.println("Error Semantico. Possui duas variaveis ou constantes identicas");
			
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
					tabSem.inserirTabela(vc.getNomeConstante(), vc.getTokenType(), "constante", vc.getTipo(),vc.getValor(), "", "",false, "");
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
		vcm.inicializaAtributos();
		
		if (token.trim().equals("metodo")) {
			token = s.nextToken();
			System.out.println("Token de metodos: " + token);
			if (s.tokenType().equals("Identificador") || token.equals("principal")) {
				vcm.setNomeMetodo(token.trim()); //insere o nome do metodo na classe de verificacao do semantico
				token = s.nextToken();
				if (token.equals("(")) {
					token = s.nextToken();
					//como os parametros podem vir em conjunto eles devem ser inseridos na lista de parametros dentro da classe que verifica a semantica
					listaParametros();
					if (token.equals(")")) {
						token = s.nextToken();
						if (token.equals(":")) {
							token = s.nextToken();
							if (tipo.contains(token)) {
								vcm.setTipoRetorno(token.trim()); //insere o tipo de retorno na classe que verifica a semantica da tabela
								//esse if verifica o erro semantico de que o metodo principal nao pode ter parametros e deve ser unico
								if (vcm.semanticaMetodo() == false)
									if (tabSem.inserirTabelaMetodos(vcm.getNomeMetodo(), vcm.getTipoRetorno(), vcm.getNomeParametro(), vcm.getTipoPrametro()) == false)
										System.out.println("Sobrescrita de metodos detectado");
								
								token = s.nextToken();
								if (token.equals("{")) {
									token = s.nextToken();
									declaracaoVariaveis(); 
									escopoMetodo();
									if (token.equals("}")) {
										vcm.oRetornoAparaceu();//verifica se o metodo eh diferente de vazio e se o retorno apareceu
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
			vcm.setTipoPrametro(s.getAtualToken().trim()); //insere o tipo do parametro na lista
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {
				
				if (vcm.duplicateParametro(s.getAtualToken())) //verificar se tem parametros duplicados. Exemplo.: metodo soma(inteiro casa, real casa)
					System.out.println("Erro semantico! Nao eh permitido o parametros com o mesmo nome: " + s.getAtualToken());
				
				vcm.setNomeParametro(s.getAtualToken()); //insere o nome do parametro na lista
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
			
			if (tabSem.temConstVar(s.getAtualToken()) || tabSem.varConstDeclaradaMetodo(vcm.getNomeMetodo(), s.getAtualToken()))
				System.out.println("erro semantico. Ja possui uma constante ou variavel com esse nome ou esta declarada no escopo do metodo. Aqui ta dentro de variaveis" );
			
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
				else
					token = s.nextToken(); //voltar
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
			tabSem.inserirTabela(vc.getNomeConstante(), vc.getTokenType(), "variavel", vc.getTipo(), "",vc.getValorVetorPrimeiro(), vc.getValorVetorSegundo(), false, vcm.getNomeMetodo());
			vc.zerarVetores();
			token = s.nextToken();
			complementoV();
		}

		else if (token.equals(";")) {
			tabSem.inserirTabela(vc.getNomeConstante(), vc.getTokenType(), "variavel", vc.getTipo(), "",vc.getValorVetorPrimeiro(), vc.getValorVetorSegundo(), false, vcm.getNomeMetodo());
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
	
	public void escopoMetodo() {
		// <escopoMetodo> ::= <comandos><escopoMetodo> | <>
		// <comandos> ::= <leia> | <escreva> | <se> | <enquanto> |
		// <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> |
		// 'resultado' <retorno> ';'
		
		comandos("metodo");
	}
	
	public void verBloco(String bloco) {
		if (bloco.equals("se")) 
			//blocoSe();
		if (bloco.equals("enquanto"))
			//conteudoLaco();
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
		} else if (s.tokenType().equals("Identificador") || s.tokenType().equals("Operador Aritmetico")) { 
			//token = s.nextToken();
			if (s.lookAhead().equals("(")) {
				// <chamadaDeMetodo> ::= Identificadores'('<var>')'
				//tabSem.metodoExiste(token); //verifica se o metodo sendo chamado existe
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
					
					if (tabSem.temVar(s.getAtualToken())) {
						atribuicaoDeVariavel(); 
						verBloco(bloco);
					} else 
						System.out.println("Error Semantico. A variavel "+s.getAtualToken()+" nao foi declarada!");
				} else if (s.tokenType().equals("Identificador") || s.tokenType().equals("Operador Aritmetico") || s.tokenType().equals("Operador Aritmetico")) { 
					incremento();
					if (token.equals(";") || s.getAtualToken().equals("Delimitador")) { //pois pode voltar com o ; logo para casos com vetor
						token = s.nextToken();
						return;
					}
					token = s.nextToken();
					if (token.equals(";")) {
						token = s.nextToken();
						return;
					} 
					verBloco(bloco);
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
	
	public void retorno() {
		verificaCaso(); 
		//token = s.nextToken(); 
		if (token.equals(";")) {
			token = s.nextToken();
			return;
		} 
	}
	
	public void verificaCaso() {
		// <verificaCaso> ::= <incremento>
		// | <expressao>
		// | <booleano>

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
	
	private void novoMetodo() {
		System.out.println("Entrou em chamada de metodo");
		tabSem.zerarCountParametro();
		if (token.equals("(")) {
			token = s.nextToken();	
			var();
			if (token.trim().equals(")")) {
				tabSem.zerarCountParametro();
				token = s.nextToken();
				if (token.equals(";")) {
					token = s.nextToken();
				} 
			}
		} else {// rever isso
			vetor();
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
			} 
		} 
	}
	
	public void incremento() {
		// <incremento> ::= '('Incrementador Identificadores<Vetor> ')'
		// | '('Identificadores<Vetor> Incrementador')'
		// | Incrementador Identificadores<Vetor>
		// | Identificadores<Vetor> Incrementador
		if (token.equals("(")) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Operador Aritmetico")) {
				token = s.nextToken();
				token = s.tokenType();
				if (token.equals("Identificador")) {
					vetor();
					token = s.nextToken();
					if (token.equals(")")) {
						return;
					} 
				} 
			} else if (token.equals("Identificador")) {
				String atual = s.getAtualToken();
				vetor();
				token = s.nextToken();
				token = s.tokenType();
				if (token.equals("Operador Aritmetico")) {
					if (tabSem.podeIncrementar(atual, vcm.getNomeMetodo()) == false)
						System.out.println("Error! A variavel nao existe no escopo ou o tipo nao eh real/inteiro ou nao esta inserida nesse escpo");
					return;
				} 
			}
		} else if (s.tokenType().equals("Operador Aritmetico")) {
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador")) {
				if (tabSem.podeIncrementar(s.getAtualToken(), vcm.getNomeMetodo()) == false)
					System.out.println("Error! A variavel nao existe no escopo ou o tipo nao eh real/inteiro ou nao esta inserida nesse escpo");
				vetor();
				return;
			}
		} else if (s.tokenType().equals("Identificador")) {
			String atual = s.getAtualToken();
			vetor();
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Operador Aritmetico")) {
				if (tabSem.podeIncrementar(atual, vcm.getNomeMetodo()) == false)
					System.out.println("Error! A variavel nao existe no escopo ou o tipo nao eh real/inteiro ou nao esta inserida nesse escpo");
				return;
			}
		} 
	}
	
	
	/* AQUI COMECA A GRAMATICA DA CHAMADA DE METODO */
	private void var() {

		if (s.tokenType().equals("Identificador")) {
			tabSem.checaAtributoChamadaMetodo(token, s.tokenType(), vcm.getNomeMetodo()); //aqui faz a verificacao dos atributos do metodo
			token = s.nextToken();
			fatVar();
		} else if (s.tokenType().equals("Numero") || token.equals("verdadeiro") || token.equals("falso")
				|| s.tokenType().equals("Cadeia de Caractere")) {
			tabSem.checaAtributoChamadaMetodo(token, s.tokenType(), vcm.getNomeMetodo());
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
	
	/*
	 * Parte que verifica o tipo do atributo [x = 1, verifica se 1 eh inteiro, real, booleano ou texto
	 * */
	public String getTipo(String obj) {
		if(obj.equals("verdadeiro") || obj.equals("falso")) {
			return "boleano";
		} else if(obj.startsWith("\"") && obj.endsWith("\"")){
			return "texto";
		} else if(obj.matches("^[0-9]*$")) {
			return "inteiro";
		} else if(obj.contains(".")) {
			return "real";
		} else 
		return "error";
	}
	
	public void atribuicaoDeVariavel() {
		// <atribuicaoDeVariavel> ::= Identificadores<Vetor> '=' <verificaCaso>';'
		
		String token_aux = token; //variavel da atribuicao
		token = s.nextToken();
		if (token.trim().equals("=")) {
			token = s.nextToken(); 
			if(tabSem.validaTipoVariavel(token_aux, getTipo(token)) == false) {
				System.out.println("Error Semantico. Variavel "+token_aux+" nao eh do tipo "+getTipo(token));
				return;
			}
			verificaCaso(); 
			
			if (token.equals(";")) { 
				token = s.nextToken();
				return;
			} else if(s.tokenType().equals("Identificador") || s.tokenType().equals("Operador Aritmetico") || 
					s.tokenType().equals("Numeral")){
				
				if(s.tokenType().equals("Identificador") && !tabSem.getTipoVar(token_aux).equals(tabSem.getTipoVar(token))) {
					System.out.println("Error Semantico. Variavel "+token_aux+" nao eh do tipo "+getTipo(token));
					return;
				} 
				
				if(s.tokenType().equals("Numeral") && !tabSem.validaTipoVariavel(token_aux, getTipo(token))) {
					System.out.println("Error Semantico. Variavel "+token_aux+" nao eh do tipo "+getTipo(token));
					return;
				}
				
				token = s.nextToken(); 
				
				if(s.tokenType().equals("Identificador") || s.tokenType().equals("Numeral")){
					if(s.tokenType().equals("Identificador") && !tabSem.getTipoVar(token_aux).equals(tabSem.getTipoVar(token))) {
						System.out.println("Error Semantico. Variavel "+token_aux+" nao eh do tipo "+tabSem.getTipoVar(token));
						return;
					} 
					
					if(s.tokenType().equals("Numeral") && !tabSem.validaTipoVariavel(token_aux, getTipo(token))) {
						System.out.println("Error Semantico. Variavel "+token_aux+" nao eh do tipo "+getTipo(token));
						return;
					}
					
					token = s.nextToken(); 
				}
				
				if (token.equals(";")) { 
					token = s.nextToken();
					return;
				} 
			} 
		}
	}
	
	public void enquanto() {
		/*
		 * <enquanto> ::= 'enquanto' '(' <operacao_relacional> ')' '{' <conteudo_laco> '}'
		 * <operacao_relacional> ::= <complemento_operador> OperadoresRelacionais <complemento_operador> | <negar> Identificadores<Vetor>
		 * 
		 * <complemento_operador> ::= Identificadores<Vetor> | Numeros | CadeiaCaracteres | TipoBooleano
		 * 
		 * <conteudo_laco> ::= <comandos> <conteudo_laco> | <>
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
					} 
				} 
			} 
		} 
	}
	
	private void operacaoRelacional() {
		/*
		 * <operacao_relacional> ::= <complemento_operador> OperadoresRelacionais <complemento_operador> 
		   | <negar> Identificadores<Vetor>
		 * 
		 * <complemento_operador> ::= Identificadores<Vetor> | Numeros | CadeiaCaracteres | TipoBooleano
		 * 
		 * <conteudo_laco> ::= <comandos> <conteudo_laco> | <>
		 */
		//System.out.println("------------------------------");
		//System.out.println("operacaoRelacional >>" +token);
		if (token.trim().equals("!")) {
			token = s.nextToken();
			String tokenType = s.tokenType();
			if (tokenType.equals("Identificador")) {
				token = s.nextToken();
				vetor();
			}
		} else {
			String complemento = token;
			complementoOperador(); 

			if(token.equals(")")) {
				return;
			}
			
			token = s.nextToken();
			
			//verifica se as variaveis sao de tipos diferentes para IDENTIFICADORES
			if(s.tokenType().equals("Identificador") && !tabSem.getTipoVar(complemento).equals(tabSem.getTipoVar(token))) {
				System.out.println("Erro Semantico: Variaveis de tipos diferentes!");
				return;
			}
			
			//verifica se as variaveis sao de tipos diferentes para atributos
			if(!s.tokenType().equals("Identificador") && !tabSem.getTipoVar(complemento).equals(getTipo(token))) {
				System.out.println("Erro Semantico: Variavel "+complemento+" nao eh do tipo "+getTipo(token));
				return;
			}
			
			complementoOperador(); 
			
			if(token.equals("&&") || token.equals("||")) {
				token = s.nextToken();
				operacaoRelacional();
			}
			
		}

	}
	
	private void complementoOperador() {
		/*
		 * <complemento_operador> ::= Identificadores<Vetor> | Numeros | CadeiaCaracteres | TipoBooleano
		 * 
		 * <conteudo_laco> ::= <comandos> <conteudo_laco> | <>
		 */
		
		//System.out.println("Complemento >>" +token);
		
		if(s.tokenType().equals("Identificador") && !tabSem.temVar(token)) { //verifica se NAO tem a variavel na tabela
			System.out.println("Erro Semantico: Variavel "+token+" nao foi declarada!");
			return;
		}
		
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
		} else if (token.equals("Boleano") || s.getAtualToken().equals("verdadeiro") || s.getAtualToken().equals("falso")) {
			token = s.nextToken();
			return;
		} 
	}
	
	private void conteudoLaco() {
		comandos("enquanto");
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
				} 
			} 
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
			} 
		} 
	}

	public void cond() {
		// <cond> ::= <termo> OperadoresRelacionais <termo>
		// | <negar> Identificadores<Vetor>
		token = s.nextToken();
		if (token.equals("!")) { //<negar>
			token = s.nextToken();
			token = s.tokenType();
			if (token.equals("Identificador"))
				vetor();
		} else {
			termo();
			token = s.tokenType(); 
			if (token.equals("Operador Relacional") || token.equals("Operador Logico") ) {
				termo();
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
				} 
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
			} 
		} else { 
			return;
		}
	}
	
	public void blocoSe() {
		// <blocoSe> ::= <comandos><blocoSe> | <>
		// <comandos> ::= <leia> | <escreva> | <se> | <enquanto> |
		// <atribuicaoDeVariavel> | <chamadaDeMetodo> ';' | <incrementador> |
		// 'resultado' <retorno> ';'
		comandos("se");
	}
	
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
				} 
			} 
		} 
	}
	
	public void ParamEscrita() {
		// <Param Escrita> ::= <verificaCaso><MaisParametroE>
		verificaCaso(); 
		MaisParametroE();
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
				} 
			} 
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
}
