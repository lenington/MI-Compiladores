package AnalisadorSemantico;

public class VerificadorCasos {
	
	public boolean semanticaConstante(String tipo, String numero, String tokenType) {
		if (tipo.equals("real") && tokenType.equals("Numero")) 
			return true;
		
		else if (tipo.equals("inteiro") && tokenType.equals("Numero") && numero.contains(".") == false) 
			return true;
		
		else if (tipo.equals("texto") && tokenType.equals("Cadeia de Caractere")) 
			return true;
		
		else if (tipo.equals("boleano") && (numero.equals("verdadeiro") || numero.equals("falso")))
			return true;
		else
			return false;
	}

}
