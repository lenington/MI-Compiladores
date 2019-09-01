package AnalisadorSemantico;

import java.util.HashMap;
import java.util.Set;

public class TabelaSemantica {
	
	HashMap<String, AtributosConstVar> tabelaConstVar;
	public TabelaSemantica() {
		
		tabelaConstVar = new HashMap<String, AtributosConstVar>();
	}
	
	public void inserirTabela(String cadeia, String token, String cat, String tipo, String valor ,String primeiroIndice, String segundoIndice, boolean utilizado) {
		
		if (tabelaConstVar.containsKey(cadeia) == false)
			tabelaConstVar.put(cadeia, new AtributosConstVar(token, cat, tipo, valor, primeiroIndice, segundoIndice, utilizado));
	
	}
	
	public boolean temConstVar(String cadeia) {
		if (tabelaConstVar.containsKey(cadeia))
			return true;
		else
			return false;
	}
	
	public boolean validaIdentificadorVetor(String cadeia) {
		//System.out.println(tabelaConstVar.get(cadeia).getTipo());
		if (tabelaConstVar.containsKey(cadeia)) {
			if (tabelaConstVar.get(cadeia).getTipo().equals("inteiro"))
					return true;
		}
		return false;
	}
	
	public void printTabela() {
		System.out.println(tabelaConstVar.keySet());
		Set<String> a = tabelaConstVar.keySet();
		for (String chave: a) {
			if (chave != null) {
				System.out.println(chave);
				tabelaConstVar.get(chave).imprimirString();
			}
		}
	}

}
