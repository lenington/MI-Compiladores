package lexico;

import java.io.FileNotFoundException;
import java.io.IOException;

public class MainTeste {
	public static void main(String[] args) {
		try {
           testarArquivo("teste.entrada");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
	
	private static void testarArquivo(String arquivo) throws IOException{
		Buffer buffer = new Buffer(arquivo);
		Lexico lexico = new Lexico(buffer);
        
        lexico.rodarAutomatos();
        lexico.saveTokensInFile();
	}
}
