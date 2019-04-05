package lexico;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * Classe responsavel por ler o arquivo principal e fazer toda a manipulacao
 * 
 * 
 */
public class Buffer {
	
	private int count_linha; //conta a quantidade de linhas
	private int count_coluna; //usado para percorrer a string da linha lida
	private int tamanho_char; //recebe o tamanho da linha para fim de saber se ja chegou no final do arquivo
	private Scanner scan;
	private String linha;
	
	
	public Buffer(String arquivo) throws FileNotFoundException {
		this.count_linha = 0;
		this.count_coluna = 0;
		this.linha = "";
		
		//leitura do arquivo
		scan = new Scanner(new File(arquivo));
	}
	
	public boolean temProximaLinha() {
		return scan.hasNextLine();
	}
	
	public void lerLinha() {
		linha = scan.nextLine();
		count_linha++;
		count_coluna = 0;
		tamanho_char = linha.length() - 1;
	}
	
	public boolean temProximoChar() {
		if (count_coluna > tamanho_char)
			return false;
		else
			return true;
	}
	
	public char lerChar() {
		char c = linha.charAt(count_coluna);
		count_coluna++;
		return c;
	}
	
	public char lerCharAtual() {
		return linha.charAt(count_coluna);
	}
	
	public boolean possoVerProximo() {
		if ((count_coluna+1) > tamanho_char)
			return false;
		return true;
	}
	
	public char verProximo() {
		return linha.charAt(count_coluna+1);
	}
	
	
	public void closeFile() {
		scan.close();
	}
	
	public int getLinha() {
		return count_linha;
	}
	
	public int getColuna() {
		return count_coluna+1;
	}
	
}

