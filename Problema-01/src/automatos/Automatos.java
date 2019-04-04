package automatos;

import java.util.LinkedList;
import java.util.Scanner;

public class Automatos {
	int count;
	LinkedList<String> palavras_reservadas; //lista encadeada para palavras reservadas
	public Automatos() {
		palavras_reservadas = new LinkedList<String>();
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
	
	
	
	public String automatoComentarios(String line, Scanner leitor_arquivo, int state) {
		int line_tamanho = line.length() - 1;

		while (true) {
			switch (state) {
			case 1: //nesse estado ja se sabe que é comentario de linha, assim so precisa achar o fim
				this.count++;
				if (this.count < line.length())
					state = 1;
				else
					return "comentario de linha";
				break;
			case 2:
				this.count++;
				if (line.charAt(this.count) == '*') state = 2;
				else if (line.charAt(this.count) == '/') state = 4;
				else state = 3;
				
				if (this.count == line_tamanho) {
					if (leitor_arquivo.hasNextLine()) {
						line = leitor_arquivo.nextLine();
						line_tamanho = line.length() - 1;
						this.count = -1;
					}
					
				}
				break;
			case 3:
				this.count++;
				if (line.charAt(this.count) == '*') state = 2;
				else state = 3;
				
			case 4:
				return "comentario de bloco";

			}
		}
	}

	public String automatoNumero(String line) {
		int state = -1;
		char char_line;
		int num_asc;
		int line_tamanho = line.length() - 1;
		
		if (line.charAt(this.count) == '-') {
			state = 0;
		}

		while (true) {
			switch (state) {
			case 0:
				this.count++;
				if (line.charAt(this.count) == ' ') state = 1;
				break;
			case 1:
				this.count++;
				if (line.charAt(this.count) == ' ') state = 1;
				else state = 2;
				break;
			case 2:
				this.count++;
				char_line = line.charAt(this.count);
				num_asc = (int) char_line;
				if (num_asc >= 48 && num_asc <= 57) state = 2;
				else if (line.charAt(this.count) == '.') state = 3;
				
				if (this.count >= line_tamanho) return "so numero";
				break;
			case 3:
				this.count++;
				char_line = line.charAt(this.count);
				num_asc = (int) char_line;
				if (num_asc >= 48 && num_asc <=57) state = 3;
				else if (line.charAt(this.count) == '.') state = 4;
				else state = 3;
				
				if (this.count >= line_tamanho) return "numero com ponto";
				break;
			case 4: 
				return "error";
			default:
				break;
			}
		}

	}
	
	public String automatoIndentificador(String line) {
		int state = 1;
		char char_line;
		int num_asc;
		int line_tamanho = line.length() - 1;
		
		while(true) {
			switch (state) {
			case 1:
				this.count++;
				char_line = line.charAt(this.count);
				num_asc = (int) char_line;
				if ((num_asc >= 97 && num_asc <= 122) || 
						(num_asc >= 65 && num_asc <= 90) || 
							(num_asc >= 48 && num_asc <= 57) || char_line == '_') state = 1;
				else if (char_line == ' ' || char_line == '+') return "idetificador";
				
				if (this.count == line_tamanho) return "identificador_fim";
				break;
			
			}
		}
	}
	
	//AUTOMATOS LENINGTON:
	/*
	 * Por enquanto ele verifica se tem operacoes separadas apenas
	 * Ex: +, ++, -, --, *, /, +1, -1... mas n verifica se tem um numero antes
	 */
	public String automatoOperadorAritmetico(String line) {
		int state = 0;
		int line_tamanho = line.length()-1;
		char char_line;
		System.out.println(line_tamanho);
		this.count = count-1;
		
		while(true) {
			switch (state) {
			case 0:
				this.count++;
				char_line = line.charAt(this.count);
				System.out.println(char_line);
				if (char_line == '+') {
					if (line.charAt(1+this.count) == '+') {
						return "operador aritm�tico de incremento";
					} else {
						//verifica se tem um n�mero...
						return "operador aritm�tico de adi��o";
					} 
				} else if (char_line == '-') { 
					if (line.charAt(1+this.count) == '-') {
						return "operador aritm�tico de decremento";
					} else {
						//verifica se tem um n�mero...
						return "operador aritm�tico de subtra��o";
					} 
				} else if (char_line == '*') {
					return "operador aritm�tico de multiplica��o";
				} else if (char_line == '/') {
					return "operador aritm�tico de divis�o";
				} else {
					state = 1;
				}
				
				break;
			case 1: 
				return "error";
			default:
				break;
			}
		}
	}
	
	/*
	 * 
	 */
	public String automatoOperadorRelacional(String line) {
		int state = 0;
		int line_tamanho = line.length()-1;
		char char_line;
		System.out.println(line_tamanho);
		this.count = count-1;
		
		while(true) {
			switch (state) {
			case 0:
				this.count++;
				char_line = line.charAt(this.count);
				System.out.println(char_line);
				if (char_line == '<') {
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional menor ou igual";
					} else {
						return "operador relacional menor";
					} 
				} else if (char_line == '>') {
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional maior ou igual";
					} else {
						return "operador relacional maior";
					} 
				} else if (char_line == '=') { 
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional igual";
					} else {
						return "operador relacional atribui��o";
					} 
				} else if (char_line == '!') { 
					if (line.charAt(1+this.count) == '=') {
						return "operador relacional diferente";
					} else {
						return "operador l�gico falso";
					} 
				} else {
					state = 1;
				}
				
				break;
			case 1: 
				return "error";
			default:
				break;
			}
		}
	}
	
	/*
	 * 
	 */
	public String automatoOperadorLogico(String line) {
		int state = 0;
		int line_tamanho = line.length()-1;
		char char_line;
		System.out.println(line_tamanho);
		this.count = count-1;
		
		while(true) {
			switch (state) {
			case 0:
				this.count++;
				char_line = line.charAt(this.count);
				System.out.println(char_line);
				if (char_line == '!') {
					return "operador l�gico falso";
				} else if (char_line == '&') {
					if (line.charAt(1+this.count) == '&') {
						return "operador l�gico 'e'";
					} else {
						//
					} 
				} else if (char_line == '|') { 
					if (line.charAt(1+this.count) == '|') {
						return "operador l�gico 'ou'";
					} else {
						//
					} 
				} else {
					state = 1;
				}
				
				break;
			case 1: 
				return "error";
			default:
				break;
			}
		}
	}	
	
	public String automatoCadeiaCaractere(String line) {
		int state = 0;
		
		if (line.charAt(this.count) == '"')
			state = 1;
		
		while (true) {
			switch(state) {
			case 1:
				this.count++;
				if (line.charAt(this.count) == '"') state = 5;
				else if (line.charAt(this.count) == '\\') state = 3;
				else state = 2;
				break;
			case 2:
				this.count++;
				if (line.charAt(this.count) == '\\') state = 3;
				else if (line.charAt(this.count) == '"') state = 5;
				else state = 2;
				break;
			case 3:
				this.count++;
				if (line.charAt(this.count) == '"') state = 4;
				else return "error"; //por enquanto tratarei como erro
				break;
			case 4:
				this.count++;
				if (line.charAt(this.count) == '"') state = 5;
				else state = 2;
				break;
			case 5:
				return "validado";
			}
		}
	}
	
	public int getCounter() {
		return count;
	}
	
	public void setCounter(int count) {
		this.count = count;
	}

}
