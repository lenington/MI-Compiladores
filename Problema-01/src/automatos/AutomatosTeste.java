package automatos;

import java.util.LinkedList;
import java.util.Scanner;

import lexico.Buffer;
import lexico.characterDiscover;

public class AutomatosTeste {
	private Buffer buffer;
	private LinkedList<String> palavras_reservadas; //lista encadeada para palavras reservadas
	private characterDiscover charDiscover;
	
	public AutomatosTeste(Buffer buffer) {
		this.buffer = buffer;
		
		this.palavras_reservadas = new LinkedList<String>();
		this.inicializaPalavrasReservadas();
		
		this.charDiscover = new characterDiscover();
	}
	
	public void inicializaPalavrasReservadas() {
		palavras_reservadas.add("programa");
		palavras_reservadas.add("constantes");
		palavras_reservadas.add("variaveis");
		palavras_reservadas.add("metodo");
		palavras_reservadas.add("resultado");
		palavras_reservadas.add("principal");
		palavras_reservadas.add("se");
		palavras_reservadas.add("entao");
		palavras_reservadas.add("senao");
		palavras_reservadas.add("enquanto");
		palavras_reservadas.add("leia");
    	palavras_reservadas.add("escreva");
		palavras_reservadas.add("vazio");
		palavras_reservadas.add("inteiro");
		palavras_reservadas.add("real");
		palavras_reservadas.add("boleano");
		palavras_reservadas.add("texto");
		palavras_reservadas.add("verdadeiro");
		palavras_reservadas.add("falso");
	}
	
	public boolean palavrasReservadas(String palavra) {
		if (palavras_reservadas.contains(palavra))
			return true;
		return false;
	}
	
	/*
	 * Pega o proximo caractere e armazena no lexema
	 * */
	//private void consumirCaractere(){
    //	char c = this.buffer.charSeguinte();
    //}
	
	//AUTOMATOS ENTRAM AQUI:
	/*public String automatoOperadorAritmetico() {
		int state = 0;

		//roda enquanto nao for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			char c = buffer.charAtual();

			switch(state) {
			case 0:
				if(c == '+') { //SOMA
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR ARITMETICO SOMA";
                    } else state = 1; //vai pro estado 1 para verificar se ï¿½ incremento
				} else if(c == '-') { //SUBTRACAO
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR ARITMETICO SUBTRACAO";
                    } else state = 2; //vai pro estado 2 para verificar se ï¿½ decremento
				} else if(c == '*') {
					this.consumirCaractere();
					return "OPERADOR ARITMETICO MULTIPLICACAO";
				} else if(c == '/') {
					this.consumirCaractere();
					return "OPERADOR ARITMETICO DIVISAO";
				} else state = -1;
				
				break;
			case 1:
				this.consumirCaractere();
				if(c == '+') {
					return "OPERADOR ARITMETICO INCREMENTO";
				} else {
					//significa que nï¿½o ï¿½ um operador aritmetico de incremento
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR ARITMETICO SOMA";
				}
			case 2: 
				this.consumirCaractere();
				if(c == '-') {
					return "OPERADOR ARITMETICO DECREMENTO";
				} else {
					//significa que nao eh um operador aritmetico de subtracao
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR ARITMETICO SUBTRACAO";
				}
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		} 
		
		return "TOKEN INDEFINIDO";
	}
	
	public String automatoOperadorRelacional() {
		int state = 0;
		
		//roda enquanto n�o for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			char c = buffer.charAtual();
			
			switch(state) {
			case 0:
				if(c == '!') { //
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR LOGICO NEGADO";
                    } 
					//agora ele precisa verificar se � operador relacional: !=
					state = 1;
				} else if(c == '=') { //
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR RELACIONAL ATRIBUI��O";
                    } 
					//
					state = 2; 
				} else if(c == '<') {
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR RELACIONAL MENOR";
                    }
					//
					state = 3; 
				} else if(c == '>') {
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR RELACIONAL MAIOR";
                    }
					//
					state = 4; 
				}
				
				else state = -1;
				
				break;
			case 1:
				this.consumirCaractere();
				if(c == '=') {
					return "OPERADOR RELACIONAL DIFERENTE";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR L�GICO NEGADO";
				}
			case 2: 
				this.consumirCaractere();
				if(c == '=') {
					return "OPERADOR RELACIONAL IGUAL";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR RELACIONAL ATRIBUI��O";
				}
			case 3: 
				this.consumirCaractere();
				if(c == '=') {
					return "OPERADOR RELACIONAL MENOR IGUAL QUE";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR RELACIONAL MENOR";
				}
			case 4: 
				this.consumirCaractere();
				if(c == '=') {
					return "OPERADOR RELACIONAL MAIOR IGUAL QUE";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR RELACIONAL MAIOR";
				}
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		}
		return "TOKEN INDEFINIDO";
	}

	public String automatoOperadorLogico() {
		int state = 0;
		
		//roda enquanto n�o for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			char c = buffer.charAtual();
			
			switch(state) {
			case 0:
				if(c == '!') { //
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR LOGICO NEGADO";
                    } 
					//agora ele precisa verificar se � operador relacional: !=
					state = 1;
				} else if(c == '&') { //
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "SIMBOLO";
                    } 
					//verifica se � operador l�gico AND (&&)
					state = 2; 
				} else if(c == '|') {
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "SIMBOLO";
                    }
					//verifica se � operador l�gico or (||)
					state = 3; 
				} else state = -1;
				
				break;
			case 1:
				this.consumirCaractere();
				if(c == '=') {
					return "OPERADOR RELACIONAL DIFERENTE";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR L�GICO NEGADO";
				}
			case 2: 
				this.consumirCaractere();
				if(c == '&') {
					return "OPERADOR L�GICO E/AND";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR L�GICO MAL FORMADO (???)";
				}
			case 3: 
				this.consumirCaractere();
				if(c == '|') {
					return "OPERADOR L�GICO OU/OR";
				} else {
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR L�GICO MAL FORMADO (???)";
				}
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		} 
		
		return "TOKEN INDEFINIDO";
	}
	
	public String automatoDelimitador() {
		int state = 0;
		
		//roda enquanto n�o for o fim do script/arquivo
		while(!this.buffer.temProximoChar()) {
			char c = buffer.lerChar();
			
			switch(state) {
			case 0:
				if (c == ';') {
					consumirCaractere();
					return "DELIMITADOR PONTO VIRGULA";
				} else if (c == '.') {
					consumirCaractere();
					return "DELIMITADOR PONTO";
				} else if (c == '(') {
					consumirCaractere();
					return "DELIMITADOR ABRE PARENTESES";
				} else if (c == ')') {
					consumirCaractere();
					return "DELIMITADOR FECHA PARENTESES";
				} else if (c == '{') {
					consumirCaractere();
					return "DELIMITADOR ABRE CHAVE";
				} else if (c == '}') {
					consumirCaractere();
					return "DELIMITADOR FECHA CHAVE";
				} else if (c == ',') {
					consumirCaractere();
					return "DELIMITADOR VIRGULA";
				} else if (c == ']') {
					consumirCaractere();
					return "DELIMITADOR FECHA COLCHETE";
				} else if (c == '[') {
					consumirCaractere();
					return "DELIMITADOR ABRE COLCHETE";
				} else {
					state = -1;
				}
				break;
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		} 
		
		return "TOKEN INDEFINIDO";
	}
	*/
	
	
	/*
	 * Automato responsavel pela leitura da cadeia de caractere
	 * */
	public String automatoCadeiaCaractere() {
		int state = 0;
		char c;
		//roda enquanto nao for fim do script/arquivo
			while(buffer.temProximoChar()) {
			c = buffer.lerChar();
			switch(state) {
			case 0:
				if (c == '\\')
					state = 2;
				else if(this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
					
				} 
				else if(c == '"') {
					return "cadeia de cactere";
				}
				break;
			case 1:
				if (c == '\\')
					state = 2;
				else if (c == '"') {
					return "cadeia caractere valida";
				}
				else if(this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;				
				}
		
				break;
			case 2:
				if (c == '"') {
					state = 3;
				}
				else if(this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
				}
				break;
			case 3:
				if (c == '"') {
					return "cadeia caractere validada";
				}
				else if (c == '\\') {
					state = 2;
				}
				else if (this.charDiscover.isLetra(c) || this.charDiscover.isDigito(c) || this.charDiscover.isSimbolo(c)) {
					state = 1;
				}
				break;
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		} 
		return "TOKEN INDEFINIDO aqui";
	}
	
	public String automatoComentarios() {
		int state = 0;
		char  c;
		while(buffer.temProximoChar()) {
			c = buffer.lerChar();
			switch(state) {
			case 0:
				if (c == '/') state = 1; //entao vou tratar o caso do cometario de linha
				else state = 2;
				break;
			case 1:
				if (buffer.temProximoChar())
					state = 1;
				else
					return "eh um comentario de linha";
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
						return "error! comentario de bloco nao foi formando completamente";
				break;
			case 3:
				if (c == '\\')
					return "comentario de bloco";
				else {
					state = 2;

					if (buffer.temProximoChar() == false)
						if (buffer.temProximaLinha()) 
							buffer.lerLinha();
						else 
							return "error! comentario de bloco nao foi formando completamente";
				}
				
			break;
			}
			
			
			
		}
		
		return "error";
	}
}

