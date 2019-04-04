package lexico;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Classe respons�vel por ler o arquivo principal e fazer toda a manipula��o
 * 
 * 
 */
public class Buffer {
	
	private String script; //todo o script do arquivo de entrada
	private int linhaAtual; //linha atual do c�digo
	private int posicaoAtual; //posi��o atual na linha do c�digo
	private int tamanhoCodigo; //tamanho do c�digo
	
	public Buffer(String arquivo) throws FileNotFoundException {
		this.linhaAtual = 0;
		this.posicaoAtual = 0;
		
		//leitura do arquivo
		Scanner scan = new Scanner(new File(arquivo));
		this.script = scan.useDelimiter("\\Z").next(); //l� imediatamente todo o arquivo
		
		this.tamanhoCodigo = this.script.length(); //tamanho do c�digo inteiro
		
		scan.close();
	}
	
	/* 
	 * Retorna true se a posi��o atual for igual ao tamanho total do c�digo
	 */
	public boolean isFimScript() {
		return (this.tamanhoCodigo == this.posicaoAtual);
	}
	
	/* 
	 * Para retornar um caractere anterior!!!!
	 */
	public void charAnterior() {
		this.posicaoAtual--; //ainda n sei se funciona assim!!!
	}
	
	/* 
	 * Retorna o pr�ximo caractere do c�digo
	 */
	public char charSeguinte() {
		char charSeguinte;
		//verifica se est� no final da linha atual
		if(this.script.charAt(this.posicaoAtual) == '\n') {
			this.setLinhaAtual(); //incrementa a vari�vel linha atual
		}
		
		charSeguinte = this.script.charAt(this.posicaoAtual++);
		
		return charSeguinte;
	}
	
	/*
	 * 
	 * */
	public char lookAhead(){
		//System.out.println("Entrou: "+this.script.charAt(posicaoAtual));
		if(this.isFimScript()){
			return (Character) null;  //caso tenha chegado no final do script
		}
		return this.script.charAt(posicaoAtual);
	}
	
	//Geters e Seters:
	public int getLinhaAtual() {
		return this.linhaAtual;
	}
	public void setLinhaAtual() {
		this.linhaAtual++;
	}
	
	public int getPosicaoAtual() {
		return this.posicaoAtual;
	}
	public void setPosicaoAtual(int posicaoAtual) {
		this.posicaoAtual = posicaoAtual;
	}
	
	public int getTamanhoCodigo() {
		return this.tamanhoCodigo;
	}
	public void setTamanhoCodigo(int tamanhoCodigo) {
		this.tamanhoCodigo = tamanhoCodigo;
	}
}
