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
	 * Pega o proximo caractere e armazena no lexema
	 * */
	private void consumirCaractere(){
    	char c = this.buffer.charSeguinte();
    }
	
	//AUTOMATOS ENTRAM AQUI:
	public String automatoOperadorAritmetico() {
		int state = 0;
		
		//roda enquanto n�o for o fim do script/arquivo
		while(!this.buffer.isFimScript()) {
			char c = buffer.lookAhead();
			
			switch(state) {
			case 0:
				if(c == '+') { //SOMA
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR ARITMETICO SOMA";
                    } else state = 1; //vai pro estado 1 para verificar se � incremento
				} else if(c == '-') { //SUBTRACAO
					this.consumirCaractere();
					if (this.buffer.isFimScript() == true) {
                        return "OPERADOR ARITMETICO SUBTRACAO";
                    } else state = 2; //vai pro estado 2 para verificar se � decremento
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
					//significa que n�o � um operador aritmetico de incremento
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
	
	
}
