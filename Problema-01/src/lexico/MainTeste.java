package lexico;


import java.io.IOException;
import java.util.LinkedList;

public class MainTeste {
	public static void main(String[] args) {
		//Essas duas linha abaixo acessa a pasta teste e retorna os nomes do arquivos com a extensao .entrada
		LinkedList<String> files = new FilesListed("teste").arquivosEntrada();
		
		try {
           testarArquivo("teste.entrada");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
	
	private static void testarArquivo(String arquivo) throws IOException{
		Buffer buffer = new Buffer(arquivo);
		Lexico lexico = new Lexico(buffer, "teste.saida");
        
        lexico.rodarAutomatos();
        lexico.saveTokensInFile();
        
        buffer.closeFile();
	}
}
