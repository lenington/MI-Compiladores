package automatos;


import lexico.Buffer;
import lexico.ConcatenadorString;
import lexico.characterDiscover;

public class AutomatosTeste {
	private Buffer buffer;
	private characterDiscover charDiscover;
	private ConcatenadorString concatenarString;
	
	
	public AutomatosTeste(Buffer buffer, ConcatenadorString concatenarString) {
		this.buffer = buffer;
		this.concatenarString = concatenarString;
		this.charDiscover = new characterDiscover();
	}
	
	//AUTOMATOS ENTRAM AQUI:
	public String automatoOperadorAritmetico() {
		int state = 0;
		char c = buffer.lerCharAtual();
		if(c == '+') { 
			 state = 0;
		} else if(c == '-') {
			state = 1; 
		} else if(c == '*') {
			return "OPERADOR ARITMETICO MULTIPLICACAO";
		} else if(c == '/') {
			return "OPERADOR ARITMETICO DIVISAO";
		} 
		
		//roda enquanto nao for o fim do script/arquivo
		while(this.buffer.temProximoChar()) {
			c = buffer.lerChar(); 
			switch(state) {
			case 0:
				if(c == '+') {
					concatenarString.concatenar_String(c);
					return "OPERADOR ARITMETICO INCREMENTO";
				} else {
					return "OPERADOR ARITMETICO SOMA";
				}
			case 1: 
				if(c == '-') {
					concatenarString.concatenar_String(c);
					return "OPERADOR ARITMETICO DECREMENTO";
				} else {
					return "OPERADOR ARITMETICO SUBTRACAO";
				}
			default:
				return "TOKEN INDEFINIDO";
			}
		} 
		return "TOKEN INDEFINIDO";
	}
	
	public String automatoOperadorRelacional() {
		int state = 0;
		char c = buffer.lerCharAtual();
		if(c == '!') { 
			 state = 0;
		} else if(c == '=') {
			state = 1; 
		} else if(c == '<') {
			state = 2; 
		} else if(c == '>') {
			state = 3; 
		} 
		
		//roda enquanto nao for o fim do script/arquivo
		while(this.buffer.temProximoChar()) {
			c = buffer.lerChar(); 
			concatenarString.concatenar_String(c);
			switch(state) {
			case 0:
				if(c == '=') {
					return "OPERADOR RELACIONAL DIFERENTE";
				} else {
					return "OPERADOR LOGICO NEGACAO";
				}
			case 1: 
				if(c == '=') {
					return "OPERADOR LOGICO IGUAL";
				} else {
					return "OPERADOR LOGICO ATRIBUICAO";
				}
			case 2: 
				if(c == '=') {
					return "OPERADOR LOGICO MENOR IGUAL";
				} else {
					return "OPERADOR LOGICO MENOR";
				}
			case 3: 
				if(c == '=') {
					return "OPERADOR LOGICO MAIOR IGUAL";
				} else {
					return "OPERADOR LOGICO MAIOR";
				}
			default:
				return "TOKEN INDEFINIDO";
			}
		} 
		return "TOKEN INDEFINIDO";
	}

	public String automatoOperadorLogico() {
		int state = 0;
		char c = buffer.lerCharAtual();
		if(c == '!') { 
			 state = 0;
		} else if(c == '&') {
			state = 1; 
		} else if(c == '|') {
			state = 2; 
		}
		
		//roda enquanto nao for o fim do script/arquivo
		while(this.buffer.temProximoChar()) {
			c = buffer.lerChar(); 
			concatenarString.concatenar_String(c);
			switch(state) {
			case 0:
				if(c == '=') {
					return "OPERADOR RELACIONAL DIFERENTE";
				} else {
					return "OPERADOR LOGICO NEGACAO";
				}
			case 1: 
				if(c == '&') {
					return "OPERADOR LOGICO E (AND)";
				} else {
					return "SIMBOLO";
				}
			case 2: 
				if(c == '|') {
					return "OPERADOR LOGICO OU (OR)";
				} else {
					return "SIMBOLO";
				}
			default:
				return "TOKEN INDEFINIDO";
			}
		} 
		return "TOKEN INDEFINIDO";
	}
	
	public String automatoDelimitador() {
		int state = 0;
		char c;
		//roda enquanto nao for fim do script/arquivo
		while(buffer.temProximoChar()) {
			c = buffer.lerChar();
			concatenarString.concatenar_String(c);
			
			switch(state) {
			case 0:
				if (c == ';') {
					
					return "DELIMITADOR PONTO VIRGULA";
				} else if (c == '.') {
					
					return "DELIMITADOR PONTO";
				} else if (c == '(') {
					
					return "DELIMITADOR ABRE PARENTESES";
				} else if (c == ')') {
					
					return "DELIMITADOR FECHA PARENTESES";
				} else if (c == '{') {
					
					return "DELIMITADOR ABRE CHAVE";
				} else if (c == '}') {
					
					return "DELIMITADOR FECHA CHAVE";
				} else if (c == ',') {
					
					return "DELIMITADOR VIRGULA";
				} else if (c == ']') {
					
					return "DELIMITADOR FECHA COLCHETE";
				} else if (c == '[') {
					
					return "DELIMITADOR ABRE COLCHETE";
				} else {
					return "TOKEN INDEFINIDO";
				}
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		} 
		return "TOKEN INDEFINIDO";
	}
	
	/*
	 * Automato responsavel pela leitura da cadeia de caractere
	 * */
	public String automatoCadeiaCaractere() {
		int state = 0;
		char c;
		//roda enquanto nao for fim do script/arquivo
		while(buffer.temProximoChar()) {
		c = buffer.lerChar();
		concatenarString.concatenar_String(c);
		switch(state) {
			case 0:
				if (c == '\\') {
					state = 2;
				} else if(this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
				} else if(c == '"') {
					return "CADEIA DE CARACTERE";
				}
				break;
			case 1:
				if (c == '\\') {
					state = 2;
				} else if (c == '"') {
					return "CADEIA DE CARACTERE";
				} else if(this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;				
				}
				break;
			case 2:
				if (c == '"') {
					state = 3;
				} else if(this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
				}
				break;
			case 3:
				if (c == '"') {
					return "CADEIA DE CARACTERE";
				} else if (c == '\\') {
					state = 2;
				} else if (this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
				}
				break;
			default:
				return "TOKEN INDEFINIDO";
			}
		} 
		return "ERROR! CADEIA DE CARACTERE MAL FORMADA";
	}
	
	public String automatoComentarios() {
		int state = 0;
		char  c;
		while(buffer.temProximoChar()) {
			c = buffer.lerChar();
			concatenarString.concatenar_String(c);
			switch(state) {
			case 0:
				if (c == '/') state = 1; //entao vou tratar o caso do cometario de linha
				else state = 2;
				break;
			case 1:
				if (buffer.temProximoChar())
					state = 1;
				else
					return "COMENTARIO DE LINHA";
				//aqui eu ainda tenho que concatenar o comentario de linha
				break;
			case 2:
				if (c == '*')
					state = 3;
				else
					state = 2; //senao ele permance nesse estado concatenando caractere
				
				if (buffer.temProximoChar() == false)
					if (buffer.temProximaLinha()) 
						buffer.lerLinha();
					else 
						return "ERRO! COMENTARIO DE BLOCO MAL FORMADO";
				break;
			case 3:
				if (c == '/')
					return "COMENTARIO DE BLOCO";
				else {
					state = 2;
					if (buffer.temProximoChar() == false)
						if (buffer.temProximaLinha()) 
							buffer.lerLinha();
						else 
							return "ERRO! COMENTARIO DE BLOCO MAL FORMADO";
				}				
			break;
			}
		}
		return "error";
	}
	
	public String automatoIdentificador() {
		int state = -1;
		char c;
		
		c = buffer.verProximo();
		if (charDiscover.isLetra(c) || charDiscover.isDigito(c) || c == '_')
			state = 0;
		else return "IDENTIFICADOR";
		
		while (buffer.temProximoChar()) {
			c = buffer.lerChar();
			switch(state) {
			case 0:
				if (charDiscover.isLetra(c) || charDiscover.isDigito(c) || c == '_') {
					state = 0;
					concatenarString.concatenar_String(c);
				}
				
				if (buffer.temProximoChar()) {
					c = buffer.verProximo();
					if (charDiscover.isLetra(c) == false && charDiscover.isDigito(c) == false && c != '_')
						return "IDENTIFICADOR";
				}
				break;
			}
		}
		
		return "IDENTIFICADOR";
	}
	
	public String automatoNumero() {
		int state = -1;
		int block_ponto = 0; //variavel de controle para verificar se ja tem um ponto no numero
		
		char c = buffer.lerCharAtual();
		if(c == '-' || this.charDiscover.isEspaco(c)) { //numero negativo 
			 state = 0;
		} else if(this.charDiscover.isDigito(c)) {
			state = 1; 
		} 
		
		//roda enquanto nao for o fim do script/arquivo
		while(this.buffer.temProximoChar()) {
			c = buffer.lerChar();  
			switch(state) {
			case 0:
				if(this.charDiscover.isEspaco(c)) {
					state = 0;
				} else if(this.charDiscover.isDigito(c)) {
					concatenarString.concatenar_String(c);
					state = 1;
				} break;
			case 1: 
				if(buffer.temProximoChar()) {
					if(this.charDiscover.isDigito(c)) {
						concatenarString.concatenar_String(c);
						//System.out.println(c);
					} else if(c == '.' && block_ponto == 0) {
						block_ponto = 1; //bloqueia o ponto
						concatenarString.concatenar_String(c);
						state = 2; 
					} 
					
					if(buffer.verProximo() != '.' && !charDiscover.isDigito(buffer.verProximo())) {
						return "NUMERO";
					}
				} 
				break;
			case 2:
				if(this.charDiscover.isDigito(c)) {
					concatenarString.concatenar_String(c);
					state = 1;
				} else return "NUMERO";
				break;
			default:
				return "TOKEN INDEFINIDO";
			}
		} 
		return "TOKEN INDEFINIDOooo";
	}
	
}