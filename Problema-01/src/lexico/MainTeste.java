package lexico;

import java.io.FileNotFoundException;

public class MainTeste {
	public static void main(String[] args) {
		try {
           testarArquivo("teste.entrada");

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
	
	private static void testarArquivo(String arquivo) throws FileNotFoundException{
		Buffer buffer = new Buffer(arquivo);
		Lexico lexico = new Lexico(buffer);
        
        lexico.rodarAutomatos();
	}
}
