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
	private String linha; //linha do arquivo
	
	
	public Buffer(String arquivo) throws FileNotFoundException {
		this.count_linha = 0;
		this.count_coluna = 0;
		this.linha = "";
		
		//leitura do arquivo
		scan = new Scanner(new File(arquivo));
	}
	
	/*
	 * Verifica se tem uma proxima linha. Isso ajuda na construcao de um
	 * while para ler um arquivo. Quando esse metodo retornar false, isso
	 * indicara que nao ha mais nada para se ler no arquivo
	 * */
	public boolean temProximaLinha() {
		return scan.hasNextLine();
	}
	
	/*
	 * esse metodo ler uma linha do arquivo e aponta para a proxima.
	 * eh interessante sempre chamar esse metodo, apos verificar se tem uma proxima 
	 * linha. O metodo temProximaLinha faz essa verificacao
	 * */
	public void lerLinha() {
		linha = scan.nextLine();
		count_linha++;
		count_coluna = 0;
		tamanho_char = linha.length() - 1;
	}
	
	/*
	 * Verifica se tem um proximo caractere para ser lido
	 * */
	public boolean temProximoChar() {
		if (count_coluna > tamanho_char)
			return false;
		else
			return true;
	}
	
	/*
	 * retorna o char e aponta para o proximo char a ser lido da <b>String linha</b>
	 * */
	public char lerChar() {
		char c = linha.charAt(count_coluna);
		count_coluna++;
		return c;
	}
	
	/*
	 * Somente retorna o char atual. Diferente do metodo lerChar, ele nao aponta para o proximo char da String linha
	 * apenas retorna o charAtual
	 * */
	public char lerCharAtual() {
		return linha.charAt(count_coluna);
	}
	
	/*
	 * Retorna o proximo char. Eh aconselhavel utilizar esse metodo, apos verificar se tem um proximo char
	 * atraves do metodo temProximoChar(). Caso nao faca a verificacao esse metodo pode acessar um posicao
	 * que nao existe e retornar um erro ou excessao
	 * */
	public char verProximo() {
		return linha.charAt(count_coluna);
	}
	
	
	public void closeFile() {
		scan.close();
	}
	
	/*
	 * retorna a linha atual do arquivo
	 * */
	public int getLinha() {
		return count_linha;
	}
	
	/*
	 * retorna a coluna atual do arquivo
	 * */
	public int getColuna() {
		return count_coluna+1;
	}
	
}

