package automatos;

import java.util.LinkedList;
import java.util.Scanner;

import lexico.Buffer;

public class AutomatosTeste {
	int count;
	private Buffer buffer;
	private LinkedList<String> palavras_reservadas; //lista encadeada para palavras reservadas
	
	public AutomatosTeste(Buffer buffer) {
		this.buffer = buffer;
		
		this.palavras_reservadas = new LinkedList<String>();
		this.inicializaPalavrasReservadas();
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
	 * Retorna verdadeiro se o caractere informado percente ao alfabeto [a-z] [A-Z]
	 * Em lower case ASCII: a = 97, b = 98, ..., z = 122
     * Em upper case ASCII: A = 65, B = 66, ..., Z = 90
	 * */
	public boolean isLetra(char c){
        int ascii = (int) c; //transforma para ascii
        
        if ((ascii >= 65 && ascii <= 90) ||
    	    (ascii >= 97 && ascii <= 122)) {
        	return true;
        } else return false;
    }
	
	/*
	 * Retorna verdadeiro se o caractere informado percente ao conjunto de digitos [0-9]
	 * Em ASCII: 0 = 48, 1 = 49, ..., 9 = 57
	 * */
	public boolean isDigito(char c) {
        int ascii = (int) c; //transforma para ascii
        
        if (ascii >= 48 && ascii <= 57) {
        	return true;
        } else return false;
	}
	
	/*
	 * Retorna verdadeiro se o caractere informado percente ao conjunto de simbolos 
	 * ASCII 34 = " (aspas dupla)
	 * */
	public boolean isSimbolo(Character c){
    	int ascii = (int) c;
    	
    	if (ascii >= 32 && ascii <= 126 && ascii != 34) {
    		return true;
    	} else return false;
    }
	
	/*
	 * Retorna verdadeiro se o caractere atual for um espaço: 
	 * ASCII = 9 equivale a \t
	 * ASCII = 32 equivale a espaço
	 */
	public boolean isEspaco(char c) {
		int ascii = (int) c;
		if(ascii == 9 || ascii == 32) { 
			return true;
		} else return false;
	}
	
	/*
	 * Pega o próximo caractere e armazena no lexema
	 * */
	private void consumirCaractere(){
    	char c = this.buffer.charSeguinte();
    }
	
	//AUTOMATOS ENTRAM AQUI:
	public String automatoOperadorAritmetico() {
		int state = 0;
		
		//roda enquanto não for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			char c = buffer.lookAhead();
			
			switch(state) {
			case 0:
				if(c == '+') { //SOMA
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR ARITMÉTICO SOMA";
                    } else state = 1; //vai pro estado 1 para verificar se é incremento
				} else if(c == '-') { //SUBTRAÇÃO
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR ARITMÉTICO SUBTRAÇÃO";
                    } else state = 2; //vai pro estado 2 para verificar se é decremento
				} else if(c == '*') {
					this.consumirCaractere();
					return "OPERADOR ARITMÉTICO MULTIPLICAÇÃO";
				} else if(c == '/') {
					this.consumirCaractere();
					return "OPERADOR ARITMÉTICO DIVISÃO";
				} else state = -1;
				
				break;
			case 1:
				this.consumirCaractere();
				if(c == '+') {
					return "OPERADOR ARITMÉTICO INCREMENTO";
				} else {
					//significa que não é um operador aritmetico de incremento
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR ARITMÉTICO SOMA";
				}
			case 2: 
				this.consumirCaractere();
				if(c == '-') {
					return "OPERADOR ARITMÉTICO DECREMENTO";
				} else {
					//significa que não é um operador aritmetico de subtração
					this.buffer.setPosicaoAtual(this.buffer.getPosicaoAtual()-1);
					return "OPERADOR ARITMÉTICO SUBTRAÇÃO";
				}
			default:
				//TOKEN INDEFINIDO
				return "TOKEN INDEFINIDO";
			}
		} 
		
		return "TOKEN INDEFINIDO";
	}
	
	
}
