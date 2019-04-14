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
					return "Operador Relacional";
				} else {
					return "Operador Logico";
				}
			case 1: 
				if(c == '=') {
					return "Operador Logico";
				} else {
					return "Operador Logico";
				}
			case 2: 
				if(c == '=') {
					return "Operador Logico";
				} else {
					return "Operador Logico";
				}
			case 3: 
				if(c == '=') {
					return "Operador Logico";
				} else {
					return "Operador Logico";
				}
			default:
				return "Token Indefinido";
			}
		} 
		return "Token Indefinido";
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
					return "Operador Relacional";
				} else {
					return "Operador Logico";
				}
			case 1: 
				if(c == '&') {
					return "Operador Logico";
				} else {
					return "Simbolo";
				}
			case 2: 
				if(c == '|') {
					return "Operador Logico";
				} else {
					return "Simbolo";
				}
			default:
				return "Token Indefinido";
			}
		} 
		return "Token Indefinido";
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
					
					return "Delimitador";
				} else if (c == '.') {
					
					return "Delimitador";
				} else if (c == '(') {
					
					return "Delimitador";
				} else if (c == ')') {
					
					return "Delimitador";
				} else if (c == '{') {
					
					return "Delimitador";
				} else if (c == '}') {
					
					return "Delimitador";
				} else if (c == ',') {
					
					return "Delimitador";
				} else if (c == ']') {
					
					return "Delimitador";
				} else if (c == '[') {
					
					return "Delimitador";
				} else {
					return "Token Indefinido";
				}
			default:
				//TOKEN INDEFINIDO
				return "Token Indefinido";
			}
		} 
		return "Token Indefinido";
	}
	
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
					return "Cadeia de Caractere";
				}
				break;
			case 1:
				if (c == '\\') {
					state = 2;
				} else if (c == '"') {
					return "Cadeia de Caractere";
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
					return "Cadeia de Caractere";
				} else if (c == '\\') {
					state = 2;
				} else if (this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
				}
				break;
			default:
				return "ERRO! Cadeia de Caractere Mal Formada";
			}
		} 
		return "ERRO! Cadeia de Caractere Mal Formada";
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
		return "ERRO!";
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
	
	public String automatoOperadorAritmetico() {
		int state = -1;

		char c_anterior = buffer.lerCharAtual();
		if(c_anterior == '+') { 
			 state = 0;
		} else if(c_anterior == '-') {
			state = 1; //System.out.println("ENTROU "+c_anterior);
		} else if(c_anterior == '*' || c_anterior == '/') {
			return "Operador Aritmetico";
		}
		
		//roda enquanto nao for o fim do script/arquivo
		while(this.buffer.temProximoChar()) {
			char c = buffer.lerChar(); //System.out.println(c_anterior+" :: "+c);
			switch(state) {
			case 0:
				if(c == '+') {
					concatenarString.concatenar_String(c);
					return "Operador Aritmetico";
				} else {
					this.buffer.backChar();
					return "Operador Aritmetico";
				} 
			case 1: 
				if(c == '-') {
					concatenarString.concatenar_String(c);
					return "Operador Aritmetico";
				} else {
					this.buffer.backChar();
					return "Operador Aritmetico";
				}
			default:
				return "Token Indefinido";
			} 
		} 
		return "Token Indefinido";
	}
	
	public String automatoNumero() {
		int state = -1;
		int ponto = 0; //variavel de controle para verificar se ja tem um ponto no numero
		
		char c_anterior = buffer.lerCharAtual(); //retorna o char que chamou 
		if(c_anterior == '-' || this.charDiscover.isEspaco(c_anterior)) { //numero negativo 
			 state = 0;
		} else if(this.charDiscover.isDigito(c_anterior)) {
			state = 1; 
			if((buffer.temProximoChar() && !charDiscover.isDigito(buffer.verProximo()))
					|| !buffer.temProximoChar()) {
				//se o atual for digito e o proximo não, OU nao tiver proximo, retorna
				return "Numero";
			}
		} 
		
		
		//roda enquanto nao for o fim do script/arquivo
		while(this.buffer.temProximoChar()) {
			char c = buffer.lerChar(); 
			switch(state) {
			case 0:
				if(this.charDiscover.isEspaco(c)) {
					state = 0;
				} else if(this.charDiscover.isDigito(c)) {
					concatenarString.concatenar_String(c);
					state = 1;
				} else {
					this.buffer.backChar();
					return "Operador Aritmetico"; //recebeu apenas um menos
				}
				break;
			case 1: 
				if(buffer.temProximoChar()) {
					if(this.charDiscover.isDigito(c)) {
						concatenarString.concatenar_String(c);
					} else if(c == '.') {
						concatenarString.concatenar_String(c);
						if(charDiscover.isEspaco(buffer.verProximo())) {
							return "ERRO! Numero Mal Formado";
						}
						ponto++; 
					} 
					
					if(buffer.verProximo() != '.' && !charDiscover.isDigito(buffer.verProximo())) {
						//significa que tem mais de um ponto no numero ou ponto sem numero em seguida
						if(ponto >= 1) { 
							return "ERRO! Numero Mal Formado";
						} else if(!charDiscover.isDigito(c)) {
							buffer.backChar();
							return "Numero";
						} 
					} 
				} 
				break;
			default:
				return "ERRO! Numero Mal Formado";
			}
		} 
		return "ERRO! Numero Mal Formado";
	}
	
}