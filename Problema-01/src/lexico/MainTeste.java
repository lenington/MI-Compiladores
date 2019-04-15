package lexico;


import java.io.IOException;
import java.util.LinkedList;

public class MainTeste {
	public static void main(String[] args) {
		
		String caminhoEntrada = "arquivos para teste/arquivosEntrada/";
		String caminhoSaida = "arquivos para teste/arquivosSaida/";
		
		//Essas duas linha abaixo acessa a pasta teste e retorna os nomes do arquivos com a extensao .entrada
		LinkedList<String> files = new FilesListed(caminhoEntrada).arquivosEntrada();
		int tam = files.size();
		
		for (int i = 0; i < tam; i++) {
		try {
           testarArquivo(files.get(i), caminhoEntrada, caminhoSaida);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
}
	
	private static void testarArquivo(String arquivo, String caminhoEntrada, String caminhoSaida) throws IOException{
		
		
		Buffer buffer = new Buffer(caminhoEntrada + arquivo);
		Lexico lexico = new Lexico(buffer, caminhoSaida + arquivo.replace("entrada", "saida"));
        
        lexico.rodarAutomatos();
        lexico.saveTokensInFile();
        
        buffer.closeFile();
	}
}
